package tatakae.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.controllers.StartMenuController;
import tatakae.graphics.Cursor;

public class StartMenu extends BasicGameState{
	
	private StartMenuController controller;
	private Cursor cursor;
	private int cursorSize = 70;

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		controller = new StartMenuController();
		cursor = new Cursor(cursorSize);
		container.setMouseCursor(new Image(32, 32), 0, 0);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		Input input = container.getInput();
		g.drawString("Start Menu", container.getWidth() / 2, container.getHeight() / 2);
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
}
