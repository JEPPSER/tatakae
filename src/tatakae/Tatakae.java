package tatakae;

import java.io.File;
import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import tatakae.game.Game;

/**
 * Main class that starts the game.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name Tatakae.java
 */
public class Tatakae {
	
	private static int width = 1600;
	private static int height = 900;

	public static void main(String[] args) throws FileNotFoundException {
		AppGameContainer screen = null;
		Game game = new Game("Game");
		game.playMap(new File("./resources/Ayase Rie - Yuima-ruWorld TVver. (Fycho) [Ultimate].osu"), width, height);
		try {
			screen = new AppGameContainer(game);
			screen.setDisplayMode(width, height, false);
			screen.setAlwaysRender(true);
			screen.start();
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
	}
}
