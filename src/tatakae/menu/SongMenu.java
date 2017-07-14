package tatakae.menu;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.controllers.SongMenuController;
import tatakae.graphics.Cursor;
import tatakae.graphics.ImageHandler;
import tatakae.menu.loader.BeatmapLoader;

public class SongMenu extends BasicGameState {

	private SongMenuController controller;
	private Cursor cursor;
	private BeatmapLoader loader;
	private Image songImage;
	private Image backImage;
	private ArrayList<File> songList;
	private ArrayList<File> visibleSongs;
	private ArrayList<Rectangle> songRects;
	private int cursorSize = 70;

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		cursor = new Cursor(cursorSize);
		container.setMouseCursor(new Image(32, 32), 0, 0);
		loader = new BeatmapLoader("C:/Users/JeSpEr/Documents/osu!/Songs");
		songList = loader.loadSongs();
		backImage = ImageHandler.buildBackButton(container.getHeight());
		visibleSongs = new ArrayList<File>();
		songRects = new ArrayList<Rectangle>();
		for (int i = 0; i < 12; i++) {
			visibleSongs.add(songList.get(i));
			songRects.add(new Rectangle((int) (container.getWidth() * 0.5), (int) (i * container.getHeight() * 0.1),
					(int) (container.getWidth() * 0.5), (int) (container.getHeight() * 0.1)));
		}
		controller = new SongMenuController(songRects, visibleSongs, songList);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		Input input = container.getInput();
		drawMenuBar(g, container);
		g.setColor(Color.white);
		for (int i = 0; i < visibleSongs.size(); i++) {
			g.draw(songRects.get(i));
			g.drawString(visibleSongs.get(i).getName(), songRects.get(i).getX() + 10, songRects.get(i).getCenterY());
		}
		cursor.render(g, input);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		controller.control(input, sbg, container);
	}

	@Override
	public int getID() {
		return 1;
	}

	private void drawMenuBar(Graphics g, GameContainer container) {
		g.setColor(Color.black);
		g.fillRect((float) 0, (float) (container.getHeight() * 0.9), (float) container.getWidth(),
				(float) (container.getHeight() * 0.1));
		g.drawImage(backImage, 0, (float) (container.getHeight() * 0.9));
	}
}
