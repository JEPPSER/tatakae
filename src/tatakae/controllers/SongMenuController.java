package tatakae.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.game.Game;

public class SongMenuController {

	private ArrayList<Rectangle> songBoxes;
	private ArrayList<File> songList;
	private ArrayList<File> allSongs;
	private String path;
	private boolean isDown = false;
	private boolean moving = false;
	private int firstY;

	public SongMenuController(ArrayList<Rectangle> songBoxes, ArrayList<File> songList, ArrayList<File> allSongs) {
		this.songBoxes = songBoxes;
		this.songList = songList;
		this.allSongs = allSongs;
	}

	public void control(Input input, StateBasedGame sbg, GameContainer container) {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(1);
		}
		scrolling(input);
		songBoxLoading(container);
	}

	private void songBoxLoading(GameContainer container) {
		if (songBoxes.get(0).getMaxY() < 0) {
			int index = allSongs.indexOf(songList.get(songList.size() - 1));
			if (index != allSongs.size() - 1) {
				songBoxes.remove(0);
				songList.remove(0);
				songList.add(allSongs.get(index + 1));
				songBoxes.add(
						new Rectangle((int) (container.getWidth() * 0.6), songBoxes.get(songBoxes.size() - 1).getMaxY(),
								(int) (container.getWidth() * 0.4), (int) (container.getHeight() * 0.1)));
			}
		} else if (songBoxes.get(songBoxes.size() - 1).getY() > container.getHeight()) {
			int index = allSongs.indexOf(songList.get(0));
			if (index != 0) {
				songBoxes.remove(songBoxes.size() - 1);
				songList.remove(songList.size() - 1);
				songList.add(0, allSongs.get(index - 1));
				songBoxes.add(0,
						new Rectangle((int) (container.getWidth() * 0.6),
								songBoxes.get(0).getY() - songBoxes.get(0).getHeight(),
								(int) (container.getWidth() * 0.4), (int) (container.getHeight() * 0.1)));
			}
		}
	}

	private void scrolling(Input input) {
		if (input.isMouseButtonDown(0) && !isDown) {
			isDown = true;
			firstY = input.getMouseY();
		} else if (input.isMouseButtonDown(0)) {
			int distanceY = input.getMouseY() - firstY;
			if (!(distanceY == 0)) {
				moving = true;
			}
			for (int i = 0; i < songBoxes.size(); i++) {
				songBoxes.get(i).setY(songBoxes.get(i).getY() + distanceY);
			}
			firstY = input.getMouseY();
		} else if (!input.isMouseButtonDown(0)) {
			if (isDown && !moving) {
				for (int i = 0; i < songBoxes.size(); i++) {
					if (songBoxes.get(i).contains(input.getMouseX(), input.getMouseY())) {
						songSelected(songList.get(i));
					}
				}
			}
			isDown = false;
			moving = false;
		}
	}
	
	private void songSelected(File song){
		System.out.println(song.getAbsolutePath());
	}

	private void startPlay(StateBasedGame sbg) {
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
