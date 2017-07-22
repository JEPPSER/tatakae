package tatakae.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.audio.AudioManager;
import tatakae.builder.BeatmapBuilder;
import tatakae.controllers.GameController;
import tatakae.entities.Beatmap;
import tatakae.graphics.HitImage;
import tatakae.entities.Slider;
import tatakae.entities.Spinner;
import tatakae.entities.HitObject;
import tatakae.entities.OD;
import tatakae.graphics.ApproachCircle;
import tatakae.graphics.Cursor;
import tatakae.graphics.ImageHandler;

/**
 * A class that works as a play in the game.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name Game.java
 */
public class Game extends BasicGameState {

	private GameController controller;
	private Cursor cursor;
	private Beatmap map;
	private Image hitcircle;
	private Image hitcircleoverlay;
	private Image reverseArrow;
	private Image spinnercircle;
	private Image spinnerapproachcircle;
	private AudioManager audioManager;

	private HitObject currentObject;
	private OD od;

	private ArrayList<HitObject> added;
	private ArrayList<HitImage> hitImages;

	private int cursorSize = 70;
	private int circleSize;
	private double resConverter;
	private long time;
	private long startTime;
	private long approachRate;
	private int startDelay = 3000;
	private int timePassed = 0;
	private int index = 0;
	private boolean started = false;
	private double accuracy = 100;
	private int rawScore = 0;
	private int objectCount = 0;
	private int endCircleCount = 0;
	private int missTickCount = 0;
	private int hitTickCount = 0;

	/**
	 * Method that starts a play. It takes a beatmap file and creates a play
	 * based on that.
	 * 
	 * @param map
	 * @param songPath
	 * @param width
	 * @param height
	 * @throws FileNotFoundException
	 * @throws SlickException 
	 */
	public void playMap(File map, String songPath, int width, int height) throws FileNotFoundException, SlickException {
		audioManager = new AudioManager(songPath);
		controller = new GameController(this);
		resConverter = (double) width / (double) 872;
		BeatmapBuilder builder = new BeatmapBuilder(resConverter, width, height);
		this.map = builder.readFile(map);
		circleSize = (int) ((109 - 9 * this.map.getCircleSize()) * resConverter);
		currentObject = this.map.getList().get(0);
		approachRate = calculateAr(this.map.getAr());
		od = new OD(this.map.getOd());
		hitcircle = ImageHandler.buildHitcircle(circleSize);
		hitcircleoverlay = ImageHandler.buildHitcircleOverlay(circleSize);
		reverseArrow = ImageHandler.buildReverseArrow(circleSize);
		spinnercircle = ImageHandler.buildSpinnerCircle(height);
		spinnerapproachcircle = ImageHandler.buildSpinnerApproachCircle();
		startTime = System.currentTimeMillis() + startDelay;
	}

	/**
	 * Render loop that renders all objects on screen.
	 */
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		Input input = container.getInput();
		this.drawHitImages(g);
		this.drawHitObjects(g);
		cursor.render(g, input);
		this.drawAccuracy(g, container);
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		cursor = new Cursor(cursorSize);
		container.setMouseCursor(new Image(32, 32), 0, 0);
		added = new ArrayList<HitObject>();
		hitImages = new ArrayList<HitImage>();
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		time = System.currentTimeMillis() - startTime; // Update time.
		if(time > -100 && !started){
			audioManager.playSong();
			started = true;
		}
		if(time > map.getList().get(map.getList().size() - 1).getTime() + 3000){
			sbg.enterState(1);
			audioManager.stopSong();
		}
		Input input = container.getInput();
		controller.control(input);
		if(index < map.getList().size()){
			this.registerCircle();
		}
		this.removeCircle(input);
		this.setDuration();
	}

	/**
	 * Returns the audio manager.
	 * 
	 * @return audioManager
	 */
	public AudioManager getAudioManager() {
		return audioManager;
	}

	/**
	 * Returns the current hitobject.
	 * 
	 * @return currentObject
	 */
	public HitObject getCurrentObject() {
		return currentObject;
	}

	/**
	 * Sets the current hitobject.
	 * 
	 * @param object
	 */
	public void setCurrentObject(HitObject object) {
		this.currentObject = object;
	}

	/**
	 * Returns the current time of the play. This is how long the play has been
	 * going for.
	 * 
	 * @return time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * Returns the od of the map that is being played.
	 * 
	 * @return od
	 */
	public OD getOd() {
		return od;
	}

	/**
	 * Returns the list with all objects that are visible on screen.
	 * 
	 * @return added
	 */
	public ArrayList<HitObject> getAddedList() {
		return added;
	}

	/**
	 * Method that adds a hit image to be displayed on screen after a note.
	 * 
	 * @param image
	 */
	public void addHitImage(HitImage hitImage) {
		hitImage.setX(hitImage.getX() - hitImage.getImage().getWidth() / 2);
		hitImage.setY(hitImage.getY() - hitImage.getImage().getHeight() / 2);
		hitImages.add(hitImage);
	}

	/**
	 * Method that changes the accuracy depending on how many hitpoints the last
	 * hit was.
	 * 
	 * @param hitPoints
	 */
	public void setAccuracy(int hitPoints) {
		rawScore += hitPoints;
		objectCount++;
		accuracy = (rawScore / objectCount * 300) / 900;
	}

	/**
	 * Draws all hit images.
	 * 
	 * @param Graphics
	 */
	private void drawHitImages(Graphics g) {
		for (int i = 0; i < hitImages.size(); i++) {
			g.drawImage(hitImages.get(i).getImage(), hitImages.get(i).getX(), hitImages.get(i).getY(), null);
			hitImages.get(i).setDuration(System.currentTimeMillis());
			if (hitImages.get(i).getDuration() > 500) {
				hitImages.remove(i);
			}
		}
	}

	/**
	 * Draws the current accuracy.
	 * 
	 * @param Graphics
	 * @param container
	 */
	private void drawAccuracy(Graphics g, GameContainer container) {
		g.drawString(String.valueOf(accuracy) + "%", (float) container.getWidth() - 100, (float) 40);
	}

	/**
	 * Sets the duration of all circles on screen.
	 */
	private void setDuration() {
		for (int i = 0; i < added.size(); i++) {
			added.get(i).setDuration(time);
		}
	}

	/**
	 * Registers a circle when it is supposed to show up on screen. It adds the
	 * circle to the added list.
	 */
	private void registerCircle() {
		if (map.getList().get(index).getClass() == Spinner.class && time >= map.getList().get(index).getTime()) {
			added.add(map.getList().get(index));
			index++;
			if (added.size() == 1) {
				currentObject = added.get(0);
			}
		} else if (map.getList().get(index).getClass() != Spinner.class && index < map.getList().size()
				&& time >= map.getList().get(index).getTime() - approachRate) {
			added.add(map.getList().get(index)); // add circle to list.
			index++;
			if (added.size() == 1) {
				currentObject = added.get(0);
			}
			added.get(added.size() - 1).setStartTime(time);
		}
	}

	/**
	 * Removes missed or passed circles from the added list.
	 */
	private void removeCircle(Input input) {
		// Remove missed circles.
		if (!added.isEmpty() && currentObject != null) {
			if (currentObject.getClass() == Slider.class) {
				sliderLogic(input);
			} else if (currentObject.getClass() == Spinner.class) {
				if (time >= currentObject.getTime() + currentObject.getLength()) {
					added.remove(currentObject);
					if (!added.isEmpty()) {
						currentObject = added.get(0);
					}
				}
			} else if (time >= added.get(0).getTime() + od.getHit50()) {
				timePassed = 0;
				added.remove(currentObject);
				setAccuracy(0);
				// Image is added to the screen.
				addHitImage(new HitImage(ImageHandler.buildHit0(), currentObject.getXPosition(),
						currentObject.getYPosition(), System.currentTimeMillis()));
				if (!added.isEmpty()) {
					currentObject = added.get(0);
				}
			}
		}
	}

	/**
	 * Handles the logic for when a slider has been clicked.
	 * 
	 * @param input
	 */
	private void sliderLogic(Input input) {
		if (timePassed >= (currentObject.getLength() / currentObject.getKicks()) * (endCircleCount + 1)
				&& timePassed < (currentObject.getLength() / currentObject.getKicks()) * (endCircleCount + 2)) {
			// Get mouse location.
			double x = input.getMouseX();
			double y = input.getMouseY();
			boolean isPressed = (input.isKeyDown(Input.KEY_X) || input.isKeyDown(Input.KEY_Z));

			ArrayList<Ellipse> circles = getCurrentObject().getEndCircles();

			if (isPressed && circles.get(0).contains((float) x, (float) y)) {
				hitTickCount++;
				if (circles.size() == 1) {
					if (missTickCount > 0) {
						setAccuracy(100);
						addHitImage(new HitImage(ImageHandler.buildHit100(),
								(int) circles.get(0).getX() + (int) (circles.get(0).getWidth() / 2),
								(int) circles.get(0).getY() + (int) (circles.get(0).getWidth() / 2),
								System.currentTimeMillis()));
					} else {
						setAccuracy(300);
					}
					missTickCount = 0;
					hitTickCount = 0;
				}
				// add one combo
				audioManager.playHitSound();
			} else {
				if (circles.size() == 1) {
					if (hitTickCount == 0 && !controller.isHit()) {
						setAccuracy(0);
						addHitImage(new HitImage(ImageHandler.buildHit0(),
								(int) circles.get(0).getX() + (int) (circles.get(0).getWidth() / 2),
								(int) circles.get(0).getY() + (int) (circles.get(0).getWidth() / 2),
								System.currentTimeMillis()));
					} else {
						setAccuracy(100);
						addHitImage(new HitImage(ImageHandler.buildHit100(),
								(int) circles.get(0).getX() + (int) (circles.get(0).getWidth() / 2),
								(int) circles.get(0).getY() + (int) (circles.get(0).getWidth() / 2),
								System.currentTimeMillis()));
						audioManager.playHitSound();
					}
					missTickCount = 0;
					hitTickCount = 0;
				} else {
					// Combo break
					missTickCount++;
				}

			}
			currentObject.removeEndCircle(0);
			endCircleCount++;
			if (endCircleCount == currentObject.getKicks()) {
				getAddedList().remove(currentObject);
				if (getAddedList().isEmpty()) {
					currentObject = null;
				} else {
					currentObject = getAddedList().get(0);
				}
				timePassed = 0;
				endCircleCount = 0;
				controller.setHit(false);
			}
		} else {
			if (time >= added.get(0).getTime() + od.getHit50() && !controller.isHit()) {
				currentObject.setHitcircleVisible(false);
			}
			timePassed = (int) (time - currentObject.getTime());
		}
	}

	/**
	 * Draws all visible hitobjects.
	 * 
	 * @param Graphics
	 */
	private void drawHitObjects(Graphics g) {
		for (int i = added.size() - 1; i >= 0; i--) {
			if(added.get(i).getClass() == Spinner.class){
				added.get(i).render(g, circleSize, spinnercircle, spinnerapproachcircle, reverseArrow);
			} else {
				added.get(i).render(g, circleSize, hitcircle, hitcircleoverlay, reverseArrow);
			}
			if (added.get(i).getClass() == Slider.class) {
				if (time >= added.get(i).getTime()) {
					int x = added.get(i).getCurrentSliderPoint(time).x;
					int y = added.get(i).getCurrentSliderPoint(time).y;
					g.setColor(Color.white);
					g.drawOval(x - circleSize, y - circleSize, circleSize * 2, circleSize * 2);
				}
			}
			if (added.get(i).getClass() != Spinner.class) {
				ApproachCircle.drawApproachCircle(g, added.get(i), circleSize, approachRate);
			}
		}
	}

	/**
	 * Converts the ar given in the beatmap file to a long variable representing
	 * the time a circle should be visible on screen.
	 * 
	 * @param ar
	 * @return
	 */
	private long calculateAr(double ar) {
		long init = 1800;
		for (int i = 0; i < ar * 10; i++) {
			if (i < 50) {
				init -= 12;
			} else {
				init -= 15;
			}
		}
		return init;
	}

	@Override
	public int getID() {
		return 2;
	}
}
