package tatakae.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.controllers.StartMenuController;
import tatakae.graphics.Cursor;
import tatakae.graphics.ImageHandler;

public class StartMenu extends BasicGameState {

	private Image exitbutton;
	private Image playbutton;
	private Ellipse playhit;
	private Ellipse exithit;
	private StartMenuController controller;
	private Cursor cursor;
	private int cursorSize = 70;

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		
		exitbutton = ImageHandler.buildExitButton(container.getHeight());
		playbutton = ImageHandler.buildPlayButton(container.getHeight());
		
		playhit = new Ellipse(container.getWidth() / 2, container.getHeight() / 2, playbutton.getWidth() / 2,
				playbutton.getWidth() / 2);
		exithit = new Ellipse((float) (container.getWidth() / 1.5), container.getHeight() / 2,
				exitbutton.getWidth() / 2, exitbutton.getWidth() / 2);
		
		controller = new StartMenuController(this);
		cursor = new Cursor(cursorSize);
		container.setMouseCursor(new Image(32, 32), 0, 0);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		Input input = container.getInput();
		g.drawImage(exitbutton, exithit.getX(), exithit.getY());
		g.drawImage(playbutton, playhit.getX(), playhit.getY());
		cursor.render(g, input);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		controller.control(input, sbg);
	}

	@Override
	public int getID() {
		return 0;
	}

	/**
	 * @return play button hitbox
	 */
	public Ellipse getPlayHit() {
		return playhit;
	}

	/**
	 * @return exit button hitbox
	 */
	public Ellipse getExitHit() {
		return exithit;
	}

	public void playHover() {
		playbutton = playbutton.getScaledCopy((int) (playhit.getWidth() * 1.2), (int) (playhit.getWidth() * 1.2));
	}

	public void playNotHover() {
		playbutton = playbutton.getScaledCopy((int) (playhit.getWidth() * 1), (int) (playhit.getWidth() * 1));
	}
	
	public void exitHover() {
		exitbutton = exitbutton.getScaledCopy((int) (exithit.getWidth() * 1.2), (int) (exithit.getWidth() * 1.2));
	}

	public void exitNotHover() {
		exitbutton = exitbutton.getScaledCopy((int) (exithit.getWidth() * 1), (int) (exithit.getWidth() * 1));
	}
}
