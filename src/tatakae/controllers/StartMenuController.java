package tatakae.controllers;

import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class StartMenuController {
	
	public void control(Input input,  StateBasedGame sbg){
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(1);
		} else if(input.isMousePressed(0)){
			sbg.enterState(1);
		}
	}
}
