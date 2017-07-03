package tatakae.entities;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Ellipse;

/**
 * Class representing a Spinner in the game.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name Spinner.java
 */
public class Spinner implements HitObject {

	private long time;
	private long length;
	private long duration;
	private int x;
	private int y;
	private int size;

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
		this.x = xPosition;
	}

	@Override
	public int getXPosition() {
		return x;
	}

	@Override
	public void setYPosition(int yPosition) {
		this.y = yPosition;
	}

	@Override
	public int getYPosition() {
		return y;
	}

	@Override
	public void setLength(long length) {
		this.length = length;
	}

	@Override
	public long getLength() {
		return length;
	}

	@Override
	public ArrayList<Ellipse> getEndCircles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKicks(int kicks) {
		// TODO Auto-generated method stub

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
	public void createSlider(double circleSize, double resConverter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawImage(int circleSize, double resConverter) {
		// TODO Auto-generated method stub

	}

	@Override
	public Point getCurrentSliderPoint(long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHitBox(double circleSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public Ellipse getHitBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDuration(long time) {
		this.duration = time - this.time;
	}

	@Override
	public long getDuration() {
		return duration;
	}

	@Override
	public void setStartTime(long startTime) {

	}

	@Override
	public long getStartTime() {
		return 0;
	}

	@Override
	public void render(Graphics g, int circleSize, Image hitcircle, Image hitcircleoverlay, Image reverseArrow) {
		g.drawImage(hitcircle, x - hitcircle.getWidth() / 2, 0);
		this.drawSpinnerApproarchCircle(g, hitcircleoverlay);
	}

	@Override
	public void setSpinnerSize(int height) {
		this.size = height;
	}

	@Override
	public int getSpinnerSize() {
		return size;
	}

	private void drawSpinnerApproarchCircle(Graphics g, Image image) {
		double percent = (double) duration / (double) length;
		int width = (int) ((size - 100) * (1 - percent));
		int x = this.x - width / 2;
		int y = (size - width) / 2;
		g.drawImage(image.getScaledCopy(width, width), x, y);
	}
}
