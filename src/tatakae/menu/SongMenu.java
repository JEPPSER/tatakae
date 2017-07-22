package tatakae.menu;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
	private ArrayList<SongBox> songRects;
	private ArrayList<DiffBox> diffs;
	private int cursorSize = 70;

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		cursor = new Cursor(cursorSize);
		container.setMouseCursor(new Image(32, 32), 0, 0);
		loader = new BeatmapLoader("C:/Users/JeSpEr/Documents/osu!/Songs");
		songList = loader.loadSongs();
		backImage = ImageHandler.buildBackButton(container.getHeight());
		visibleSongs = new ArrayList<File>();
		songRects = new ArrayList<SongBox>();
		diffs = new ArrayList<DiffBox>();
		for (int i = 0; i < 12; i++) {
			visibleSongs.add(songList.get(i));
			songRects.add(new SongBox((int) (container.getWidth() * 0.6), (int) (i * container.getHeight() * 0.1),
					(int) (container.getWidth() * 0.4), (int) (container.getHeight() * 0.1),
					songList.get(i).getAbsolutePath()));
		}
		controller = new SongMenuController(songRects, visibleSongs, songList, diffs, this);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		Input input = container.getInput();
		if(songImage != null){
			g.drawImage(songImage, 0, 0);
		}
		// Draw song boxes
		for (int i = 0; i < visibleSongs.size(); i++) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fill(songRects.get(i));
			g.setColor(Color.white);
			g.draw(songRects.get(i));
			g.drawString(visibleSongs.get(i).getName(), songRects.get(i).getX() + 10, songRects.get(i).getCenterY());
		}
		// Draw diff boxes
		for (int i = 0; i < diffs.size(); i++) {
			g.setColor(Color.black);
			g.fill(diffs.get(i));
			g.setColor(Color.white);
			g.draw(diffs.get(i));
			g.drawString(diffs.get(i).getDiff(), diffs.get(i).getX() + 10,
					diffs.get(i).getY() + diffs.get(i).getHeight() / 2);
		}
		drawMenuBar(g, container);
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
	
	public void setImage(Image songImage){
		this.songImage = songImage;
	}

	private void drawMenuBar(Graphics g, GameContainer container) {
		g.setColor(Color.black);
		g.fillRect((float) 0, (float) (container.getHeight() * 0.9), (float) container.getWidth(),
				(float) (container.getHeight() * 0.1));
		g.setColor(Color.white);
		g.drawRect((float) 0, (float) (container.getHeight() * 0.9), (float) container.getWidth(),
				(float) (container.getHeight() * 0.1));
		g.drawImage(backImage, 0, (float) (container.getHeight() * 0.9));
	}
}
