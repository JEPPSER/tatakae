package tatakae;

import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.game.Game;
import tatakae.menu.SongMenu;
import tatakae.menu.StartMenu;

/**
 * Main class that starts the game.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name Tatakae.java
 */
public class Tatakae extends StateBasedGame{
	
	public Tatakae(String name) {
		super(name);
		this.addState(new StartMenu());
		this.addState(new SongMenu());
		this.addState(new Game());
	}

	private static int width = 1600;
	private static int height = 900;

	public static void main(String[] args) throws FileNotFoundException {
		AppGameContainer screen;
		try {
			screen = new AppGameContainer(new Tatakae("Tatakae"));
			screen.setDisplayMode(width, height, false);
			screen.setAlwaysRender(true);
			screen.start();
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(0).init(container, this);
		this.getState(1).init(container, this);
		this.getState(2).init(container, this);
		this.enterState(0);
	}
}
