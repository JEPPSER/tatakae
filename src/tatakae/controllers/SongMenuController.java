package tatakae.controllers;

import java.io.File;
import java.io.FileNotFoundException;

import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.game.Game;

public class SongMenuController {

	public void control(Input input,  StateBasedGame sbg){
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(1);
		} else if(input.isMousePressed(0)){
			sbg.enterState(2);
			Game game = (Game) sbg.getState(2);
			try {
				game.playMap(new File("./resources/9mm Parabellum Bullet - Inferno (Monstrata) [Excruciating Extra].osu"),
						sbg.getContainer().getWidth(), sbg.getContainer().getHeight());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
