package tatakae.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import tatakae.game.Game;
import tatakae.menu.DiffBox;
import tatakae.menu.SongBox;

public class SongMenuController {

	private ArrayList<SongBox> songBoxes;
	private ArrayList<File> songList;
	private ArrayList<File> allSongs;
	private ArrayList<DiffBox> diffs;
	private int selectedIndex;
	private String path;
	private boolean isDown = false;
	private boolean moving = false;
	private int firstY;
	private int diffCount;

	public SongMenuController(ArrayList<SongBox> songBoxes, ArrayList<File> songList, ArrayList<File> allSongs,
			ArrayList<DiffBox> diffs) {
		this.songBoxes = songBoxes;
		this.songList = songList;
		this.allSongs = allSongs;
		this.diffs = diffs;
	}

	public void control(Input input, StateBasedGame sbg, GameContainer container) {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(1);
		}
		scrolling(input, container);
		songBoxLoading(container);
		setXPositions(container);
		setYPositions(container);
	}

	private void songBoxLoading(GameContainer container) {
		if (songBoxes.get(0).getMaxY() < 0) {
			int index = allSongs.indexOf(songList.get(songList.size() - 1));
			if (index != allSongs.size() - 1) {
				songBoxes.remove(0);
				songList.remove(0);
				songList.add(allSongs.get(index + 1));
				if (songList.get(songList.size() - 1) == allSongs.get(selectedIndex)) {
					songBoxes.add(new SongBox((int) (container.getWidth() * 0.6),
							(float) (songBoxes.get(songBoxes.size() - 1).getMaxY()
									+ container.getHeight() * 0.1 * (diffCount - 1)),
							(int) (container.getWidth() * 0.4), (int) (container.getHeight() * 0.1)));
				} else {
					songBoxes.add(new SongBox((int) (container.getWidth() * 0.6),
							songBoxes.get(songBoxes.size() - 1).getMaxY(), (int) (container.getWidth() * 0.4),
							(int) (container.getHeight() * 0.1)));
				}
			}
		}
		if (songBoxes.get(songBoxes.size() - 1).getY() > container.getHeight()) {
			int index = allSongs.indexOf(songList.get(0));
			if (index != 0) {
				songBoxes.remove(songBoxes.size() - 1);
				songList.remove(songList.size() - 1);
				songList.add(0, allSongs.get(index - 1));
				songBoxes.add(0,
						new SongBox((int) (container.getWidth() * 0.6),
								songBoxes.get(0).getY() - songBoxes.get(0).getHeight(),
								(int) (container.getWidth() * 0.4), (int) (container.getHeight() * 0.1)));
			}
		}
	}

	private void scrolling(Input input, GameContainer container) {
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
			for (int i = 0; i < diffs.size(); i++) {
				diffs.get(i).setY(diffs.get(i).getY() + distanceY);
			}
			firstY = input.getMouseY();
		} else if (!input.isMouseButtonDown(0)) {
			if (isDown && !moving) {
				for (int i = 0; i < songBoxes.size(); i++) {
					if (songBoxes.get(i).contains(input.getMouseX(), input.getMouseY())) {
						diffCount = 0;
						songSelected(songList.get(i), songBoxes.get(i), container);
						break;
					}
				}
			}
			isDown = false;
			moving = false;
		}
	}

	private void setXPositions(GameContainer container) {
		for (int i = 0; i < songBoxes.size(); i++) {
			if (allSongs.indexOf(songList.get(i)) == selectedIndex) {
				songBoxes.get(i).setX(container.getWidth());
			} else {
				songBoxes.get(i).setX((float) (container.getWidth() * 0.6));
				songBoxes.get(i).setWidth((float) (container.getWidth() * 0.4));
			}
		}
	}

	private void setYPositions(GameContainer container) {
		File file = allSongs.get(selectedIndex);
		if (songList.contains(file)) {
			int index = songList.indexOf(file);
			for (int i = 0; i < index; i++) {
				songBoxes.get(i).setY((float) (songBoxes.get(index).getY()
						- container.getHeight() * 0.1 * (index - i + diffCount - 1)));
			}
		}
	}

	private void songSelected(File song, Rectangle box, GameContainer container) {
		File[] files = song.listFiles();
		for (int i = 1; i < songBoxes.size(); i++) {
			songBoxes.get(i).setY(songBoxes.get(i - 1).getMaxY());
		}
		selectedIndex = allSongs.indexOf(songList.get(songBoxes.indexOf(box)));
		getDiffs(files, container);
	}

	private void getDiffs(File[] files, GameContainer container) {
		diffs.clear();
		int j = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getAbsolutePath().endsWith(".osu")) {
				try {
					Scanner scan = new Scanner(files[i], "utf-8");
					String line = "";
					String artist = "";
					String title = "";
					String mapper = "";
					String diff = files[i].getName().split("\\[")[1].replaceAll("].osu", "");
					boolean isStandard = true;
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						if (line.contains("Mode:")) {
							if (line.endsWith("0")) {
								diffCount++;
								continue;
							} else {
								isStandard = false;
								break;
							}
						}
						if (line.startsWith("Title:") && line.split(":").length > 1) {
							title = line.split(":")[1];
							line = scan.nextLine();
						} else if (line.startsWith("Artist:") && line.split(":").length > 1) {
							artist = line.split(":")[1];
							line = scan.nextLine();
						} else if (line.startsWith("Creator:") && line.split(":").length > 1) {
							mapper = line.split(":")[1];
							line = scan.nextLine();
						}
					}
					scan.close();
					if (isStandard) {
						int index = songList.indexOf(allSongs.get(selectedIndex));
						DiffBox temp = new DiffBox((float) (container.getWidth() / 2),
								(float) (songBoxes.get(index).getY() - container.getHeight() * 0.1 * j),
								(float) (container.getWidth() / 2), (float) (container.getHeight() * 0.1));
						temp.setArtist(artist);
						temp.setTitle(title);
						temp.setDiff(diff);
						temp.setMapper(mapper);
						diffs.add(temp);
						j++;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
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
