package tatakae.graphics;

import org.newdawn.slick.Image;

/**
 * Class representing a hit image. A hit image is an image that appears after a
 * hit object has disappeared. The hit object can have 4 different images
 * telling the player how accurately they clicked the circle. The 4 different
 * outcomes are: miss, 50, 100 and 300.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name HitImage.java
 */
public class HitImage {

	private Image image;
	private int x;
	private int y;
	private long duration = 0;
	private long startTime;

	/**
	 * Initiates HitImage by setting the image, x position, y position and start
	 * time.
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param startTime
	 */
	public HitImage(Image image, int x, int y, long startTime) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.startTime = startTime;
	}

	/**
	 * Sets the start time for the hit image.
	 * 
	 * @param startTime
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Returns the start time of the hit image.
	 * 
	 * @return start time
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Sets the Image for the hit image.
	 * 
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Returns the Image of the hit image.
	 * 
	 * @return image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Sets the x position of the hit image.
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the x position of the hit image.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the y position of the hit image.
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the y position of the hit image.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the duration of the hit image.
	 * 
	 * @param time
	 */
	public void setDuration(long time) {
		duration = time - startTime;
	}

	/**
	 * Returns the duration of the hit image.
	 * 
	 * @return duration
	 */
	public long getDuration() {
		return duration;
	}
}
