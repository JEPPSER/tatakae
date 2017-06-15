package tatakae.entities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Ellipse;

import java.awt.Point;

/**
 * Interface that all hitobjects will implement.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name HitObject.java
 */
public interface HitObject {

	/**
	 * Method that sets the time for a hitobject.
	 * 
	 * @param time
	 */
	public void setTime(long time);

	/**
	 * Method for getting the time of a hitobject.
	 * 
	 * @return time
	 */
	public long getTime();

	/**
	 * Method that sets the x-position for a hitobject.
	 * 
	 * @param xPosition
	 */
	public void setXPosition(int xPosition);

	/**
	 * Method for getting the x-position of a hitobject.
	 * 
	 * @return xPosition
	 */
	public int getXPosition();

	/**
	 * Method that sets the y-position for a hitobject.
	 * 
	 * @param yPosition
	 */
	public void setYPosition(int yPosition);

	/**
	 * Method for getting the y-position of a hitobject.
	 * 
	 * @return yPosition
	 */
	public int getYPosition();

	/**
	 * Method for setting the length of a hitobject. This is relevant for
	 * sliders and spinners.
	 * 
	 * @param length
	 */
	public void setLength(long length);

	/**
	 * Method that returns the length of a hitobject.
	 * 
	 * @return length
	 */
	public long getLength();

	/**
	 * Method that returns the endcircles for a slider hitobject. The endcircles
	 * are where the reverse arrows will appear.
	 * 
	 * @return endcircle
	 */
	public ArrayList<Ellipse> getEndCircles();
	
	/**
	 * Sets the number of kicks in a slider.
	 * 
	 * @param kicks
	 */
	public void setKicks(int kicks);

	/**
	 * Method that returns the number of kicks in a slider.
	 * 
	 * @return kicks
	 */
	public int getKicks();

	/**
	 * Method that sets the visibility of a hitcircle. This will be set to false
	 * after the hitcircle in a slider has been clicked.
	 * 
	 * @param visible
	 */
	public void setHitcircleVisible(boolean visible);

	/**
	 * Removes the endcircle att index.
	 * 
	 * @param index
	 */
	public void removeEndCircle(int index);

	/**
	 * Method that creates the logic for a slider.
	 * 
	 * @param circleSize
	 * @param resConverter
	 */
	public void createSlider(double circleSize, double resConverter);

	/**
	 * Method that draws an image held by a hitobject.
	 * 
	 * @param circleSize
	 * @param resConverter
	 */
	public void drawImage(int circleSize, double resConverter);

	/**
	 * Returns the sliderpoint that the slider is currently at. This describes
	 * where the sliderball should be drawn.
	 * 
	 * @param time
	 * @return sliderpoint
	 */
	public Point getCurrentSliderPoint(long time);

	/**
	 * Method that sets the hitbox.
	 * 
	 * @param circleSize
	 */
	public void setHitBox(double circleSize);

	/**
	 * Method that returns the hitbox.
	 * 
	 * @return hitbox
	 */
	public Ellipse getHitBox();

	/**
	 * Method that sets the current duration of the hitobject. This means how
	 * long the hitobject has been visible.
	 * 
	 * @param time
	 */
	public void setDuration(long time);

	/**
	 * Method that returns the duration of the hitobject. This means how long
	 * the hitobject has been visible.
	 * 
	 * @return duration
	 */
	public long getDuration();

	/**
	 * Method that sets the start time of a hitobject. This means when the
	 * hitobject is first displayed on screen.
	 * 
	 * @param start
	 *            time
	 */
	public void setStartTime(long startTime);

	/**
	 * Method that returns the start time of a hitobject. This means when the
	 * hitobject is first displayed on screen.
	 * 
	 * @return start time
	 */
	public long getStartTime();

	/**
	 * Method for rendering the HitObject.
	 * 
	 * @param Graphics
	 * @param circleSize
	 * @param hitcircle
	 * @param hitcircleoverlay
	 */
	public void render(Graphics g, int circleSize, Image hitcircle, Image hitcircleoverlay, Image reverseArrow);
}
