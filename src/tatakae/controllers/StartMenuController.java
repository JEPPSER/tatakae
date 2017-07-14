package tatakae.controllers;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.menu.StartMenu;

public class StartMenuController {

	private Ellipse playhit;
	private Ellipse exithit;
	private Ellipse bigplayhit;
	private Ellipse bigexithit;
	private StartMenu menu;
	private boolean isPlayHovering = false;
	private boolean isExitHovering = false;

	public StartMenuController(StartMenu menu) {
		this.menu = menu;
		
		playhit = menu.getPlayHit();
		bigplayhit = new Ellipse((int) (playhit.getX() + playhit.getWidth() / 2),
				(int) (playhit.getY()  + playhit.getWidth() / 2), (int) (playhit.getWidth() * 0.5),
				(int) (playhit.getWidth() * 0.5));
		
		exithit = menu.getExitHit();
		bigexithit = new Ellipse((int) (exithit.getX() + exithit.getWidth() / 2),
				(int) (exithit.getY()  + exithit.getWidth() / 2), (int) (exithit.getWidth() * 0.5),
				(int) (exithit.getWidth() * 0.5));
	}

	/**
	 * Method that handles the inputs from the user inside the start menu.
	 * 
	 * @param input
	 * @param StateBasedGame
	 */
	public void control(Input input, StateBasedGame sbg) {
		playControl(input, sbg);
		exitControl(input, sbg);
	}

	/**
	 * Handles the playbutton inputs.
	 * 
	 * @param input
	 * @param sbg
	 */
	private void playControl(Input input, StateBasedGame sbg) {
		if (playhit.contains(input.getMouseX(), input.getMouseY())) {
			if (!isPlayHovering) {
				menu.playHover();
				isPlayHovering = true;
			}
			if (input.isMousePressed(0)) {
				sbg.enterState(1);
			}
		} else if (isPlayHovering && !bigplayhit.contains(input.getMouseX(), input.getMouseY())) {
			menu.playNotHover();
			isPlayHovering = false;
		}
	}

	/**
	 * Handles the exitbutton inputs.
	 * 
	 * @param input
	 * @param sbg
	 */
	private void exitControl(Input input, StateBasedGame sbg) {
		if (exithit.contains(input.getMouseX(), input.getMouseY())) {
			if (!isExitHovering) {
				menu.exitHover();
				isExitHovering = true;
			}
			if (input.isMousePressed(0)) {
				System.exit(0);
			}
		} else if (isExitHovering && !bigexithit.contains(input.getMouseX(), input.getMouseY())) {
			menu.exitNotHover();
			isExitHovering = false;
		}
	}
}
