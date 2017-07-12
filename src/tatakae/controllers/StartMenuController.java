package tatakae.controllers;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.menu.StartMenu;

public class StartMenuController {

	private Ellipse playhit;
	private Ellipse exithit;
	private StartMenu menu;
	private boolean isPlayHovering = false;
	private boolean isExitHovering = false;

	public StartMenuController(StartMenu menu) {
		this.menu = menu;
		playhit = menu.getPlayHit();
		exithit = menu.getExitHit();
	}

	public void control(Input input, StateBasedGame sbg) {
		playControl(input, sbg);
		exitControl(input, sbg);
	}

	private void playControl(Input input, StateBasedGame sbg) {
		if (playhit.contains(input.getMouseX(), input.getMouseY())) {
			if (!isPlayHovering) {
				menu.playHover();
				isPlayHovering = true;
			}
			if (input.isMousePressed(0)) {
				sbg.enterState(1);
			}
		} else if (isPlayHovering) {
			menu.playNotHover();
			isPlayHovering = false;
		}
	}

	private void exitControl(Input input, StateBasedGame sbg) {
		if (exithit.contains(input.getMouseX(), input.getMouseY())) {
			if (!isExitHovering) {
				menu.exitHover();
				isExitHovering = true;
			}
			if (input.isMousePressed(0)) {
				System.exit(0);
			}
		} else if (isExitHovering) {
			menu.exitNotHover();
			isExitHovering = false;
		}
	}
}
