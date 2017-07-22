package tatakae.menu;

import java.io.File;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class SongBox extends Rectangle {

	private String title;
	private String artist;
	private String mapper;
	private String path;
	private Image image;

	public SongBox(float x, float y, float width, float height, String path) {
		super(x, y, width, height);
		this.path = path;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Image getImage() {
		try {
			File[] files = new File(path).listFiles();
			File file = null;
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().endsWith(".jpg") || files[i].getName().endsWith(".png")) {
					file = files[i];
					break;
				}
			}
			if (file != null) {
				image = new Image(file.getAbsolutePath());
				return image;
			} else {
				return null;
			}
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getArtist() {
		return artist;
	}

	public void setMapper(String mapper) {
		this.mapper = mapper;
	}

	public String getMapper() {
		return mapper;
	}
}
