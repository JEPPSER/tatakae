package tatakae.entities;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Ellipse;

/**
 * This class represents a circle object in the game. A circle can be clicked by
 * the player and give points.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name Circle.java
 */
public class Circle implements HitObject {

	private long time;
	private int xPosition;
	private int yPosition;
	private long duration;
	private long startTime;
	private Ellipse hitBox;

	public Circle() {
		duration = 0;
	}

	@Override
	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	@Override
	public int getXPosition() {
		return xPosition;
	}

	@Override
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	@Override
	public int getYPosition() {
		return yPosition;
	}

	/**
	 * Method for setting the hitbox of the circle.
	 * 
	 * @param hitbox
	 */
	public void setHitBox(double circleSize) {
		hitBox = new Ellipse((float) xPosition, (float) yPosition,
				(float) circleSize / 2, (float) circleSize / 2);
	}

	/**
	 * Method that returns the hitbox of the circles.
	 * 
	 * @return hitbox
	 */
	public Ellipse getHitBox() {
		return hitBox;
	}

	@Override
	public void render(Graphics g, int circleSize, Image hitcircle, Image hitcircleoverlay, Image reverseArrow) {
		g.drawImage(hitcircle, xPosition - circleSize / 2, yPosition - circleSize / 2, null);
		g.drawImage(hitcircleoverlay, xPosition - circleSize / 2, yPosition - circleSize / 2, null);
	}

	@Override
	public void setDuration(long time) {
		duration = time - startTime;
	}

	@Override
	public long getDuration() {
		return duration;
	}

	@Override
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public long getLength() {
		return 0;
	}

	@Override
	public Point getCurrentSliderPoint(long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Ellipse> getEndCircles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getKicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHitcircleVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEndCircle(int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(int circleSize, double resConverter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createSlider(double circleSize, double resConverter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLength(long length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setKicks(int kicks) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSpinnerSize(int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSpinnerSize() {
		// TODO Auto-generated method stub
		return 0;
	}
}
