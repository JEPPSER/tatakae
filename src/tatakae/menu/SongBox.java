package tatakae.menu;

import org.newdawn.slick.geom.Rectangle;

public class SongBox extends Rectangle {

	private String title;
	private String artist;
	private String mapper;

	public SongBox(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
