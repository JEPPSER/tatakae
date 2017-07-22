package tatakae.menu;

import org.newdawn.slick.geom.Rectangle;

public class DiffBox extends Rectangle {
	
	private String title;
	private String artist;
	private String diff;
	private String mapper;
	
	private String path;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DiffBox(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setArtist(String artist){
		this.artist = artist;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public void setDiff(String diff){
		this.diff = diff;
	}
	
	public String getDiff(){
		return diff;
	}
	
	public void setMapper(String mapper){
		this.mapper = mapper;
	}
	
	public String getMapper(){
		return mapper;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
}
