package tatakae.menu.loader;

import java.io.File;
import java.util.ArrayList;

public class BeatmapLoader {

	private String path;
	private ArrayList<File> songList;

	public BeatmapLoader(String path) {
		this.path = path;
		songList = new ArrayList<File>();
	}

	public BeatmapLoader() {
		songList = new ArrayList<File>();
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public ArrayList<File> loadSongs() {
		File[] fileList = new File(path).listFiles();
		for (int i = 0; i < fileList.length; i++) {
			songList.add(fileList[i]);
		}
		return songList;
	}
}
