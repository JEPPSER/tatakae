package tatakae.controllers;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import tatakae.audio.AudioManager;
import tatakae.graphics.HitImage;
import tatakae.entities.Slider;
import tatakae.entities.Spinner;
import tatakae.game.Game;
import tatakae.graphics.ImageHandler;

/**
 * Class that handles the inputs from a play.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name GameController.java
 */
public class GameController {

	private Game game;
	private AudioManager audioManager;
	private boolean hit = false;

	public GameController(Game game) {
		this.game = game;
		this.audioManager = game.getAudioManager();
	}

	/**
	 * Method called every loop and handles the inputs made by the player.
	 * 
	 * @param input
	 */
	public void control(Input input) {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(1);
		} else if (input.isKeyPressed(Input.KEY_X) || input.isKeyPressed(Input.KEY_Z)) {
			if (game.getCurrentObject() != null && game.getCurrentObject().getClass() != Slider.class) {
				circleHit(input);
			} else {
				sliderHit(input);
			}
		}
	}

	/**
	 * Handles what will happen when a circle is clicked.
	 * 
	 * @param input
	 */
	private void circleHit(Input input) {
		if (game.getCurrentObject() != null && game.getCurrentObject().getClass() != Slider.class
				&& game.getCurrentObject().getClass() != Spinner.class) {
			if (game.getCurrentObject().getHitBox().contains(input.getMouseX(), input.getMouseY())) {
				// Hit is too early or late so it counts as a miss.
				if (game.getTime() < game.getCurrentObject().getTime() - game.getOd().getHit50()
						|| game.getTime() > game.getCurrentObject().getTime() + game.getOd().getHit50()) {
					// Nothing happenes.
				} else {
					Image image;
					// Hit is worth 300 points.
					if (game.getTime() > game.getCurrentObject().getTime() - game.getOd().getHit300()
							&& game.getTime() < game.getCurrentObject().getTime() + game.getOd().getHit300()) {
						image = ImageHandler.buildHit300();
						game.setAccuracy(300);
					}
					// Hit is worth 100 points.
					else if (game.getTime() > game.getCurrentObject().getTime() - game.getOd().getHit100()
							&& game.getTime() < game.getCurrentObject().getTime() + game.getOd().getHit100()) {
						image = ImageHandler.buildHit100();
						game.setAccuracy(100);
					}
					// Hit is worth 50 points.
					else {
						image = ImageHandler.buildHit50();
						game.setAccuracy(50);
					}
					// Image is added to the screen.
					game.addHitImage(new HitImage(image, game.getCurrentObject().getXPosition(),
							game.getCurrentObject().getYPosition(), System.currentTimeMillis()));
					audioManager.playHitSound();
					game.getAddedList().remove(game.getCurrentObject());
					if (game.getAddedList().isEmpty()) {
						game.setCurrentObject(null);
					} else {
						game.setCurrentObject(game.getAddedList().get(0));
					}
				}
			}
		}
	}

	/**
	 * Handles what will happen when a slider is clicked.
	 * 
	 * @param input
	 */
	private void sliderHit(Input input) {
		// Get mouse location.
		double x = input.getMouseX();
		double y = input.getMouseY();
		if (game.getCurrentObject() != null && game.getCurrentObject().getHitBox().contains((float) x, (float) y)) {
			game.getCurrentObject().setHitcircleVisible(false);
			// Hit is too early or late so it counts as a miss.
			if (game.getTime() < game.getCurrentObject().getTime() - game.getOd().getHit50()
					|| game.getTime() > game.getCurrentObject().getTime() + game.getOd().getHit50()) {
				// Miss
			} else {
				hit = true;
				game.setAccuracy(300);
				audioManager.playHitSound();
			}
		}
	}

	/**
	 * Sets if a slider was hit or not.
	 * 
	 * @param hit
	 */
	public void setHit(boolean hit) {
		this.hit = hit;
	}

	/**
	 * Returns if a slider was hit or not.
	 * 
	 * @return hit
	 */
	public boolean isHit() {
		return hit;
	}
}
