package tatakae.entities;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Ellipse;

import tatakae.graphics.SliderShape;

/**
 * Class representing a slider. A slider holds a list of points.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name Slider.java
 */
public class Slider extends Circle {

	private ArrayList<ArrayList<Point>> points;
	private ArrayList<Point> sliderTrack;
	private ArrayList<Double> pointLength;
	private ArrayList<Point> newTrack;
	private ArrayList<Point> endCircles;

	private SliderShape sliderShape;
	private long length;
	private long pointDistance = 20;
	private double sliderLength = 0;
	private int kicks;
	private boolean hitcircleVisible = true;
	private int pointCount;
	private int circleSize;
	private float angles[];

	public Slider(int width, int height) {
		points = new ArrayList<ArrayList<Point>>();
		sliderTrack = new ArrayList<Point>();
		pointLength = new ArrayList<Double>();
		newTrack = new ArrayList<Point>();
		endCircles = new ArrayList<Point>();
	}

	@Override
	public void removeEndCircle(int index) {
		endCircles.remove(index);
		float temp = angles[0];
		angles[0] = angles[1];
		angles[1] = temp;
	}

	@Override
	public void setHitcircleVisible(boolean visible) {
		this.hitcircleVisible = visible;
	}

	@Override
	public void setKicks(int kicks) {
		this.kicks = kicks;
	}

	@Override
	public int getKicks() {
		return kicks;
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
	public Point getCurrentSliderPoint(long time) {
		long currentSpot = time - this.getTime();
		if (currentSpot < this.getLength()) {
			double percent = (double) currentSpot / (double) this.getLength();
			return newTrack.get((int) (newTrack.size() * percent));
		} else {
			return newTrack.get(newTrack.size() - 1);
		}
	}

	@Override
	public ArrayList<Ellipse> getEndCircles() {
		ArrayList<Ellipse> list = new ArrayList<Ellipse>();
		for (int i = 0; i < endCircles.size(); i++) {
			Ellipse el = new Ellipse((float) (endCircles.get(i).getX()), (float) (endCircles.get(i).getY()), circleSize,
					circleSize);
			list.add(el);
		}
		return list;
	}

	@Override
	public void render(Graphics g, int circleSize, Image hitcircle, Image hitcircleoverlay, Image reverseArrow) {
		g.setColor(Color.white);
		drawSlider(g, circleSize);

		if (hitcircleVisible) {
			g.drawImage(hitcircle, super.getXPosition() - circleSize / 2, super.getYPosition() - circleSize / 2);
			g.drawImage(hitcircleoverlay, super.getXPosition() - circleSize / 2, super.getYPosition() - circleSize / 2);
		}

		for (int j = 0; j < endCircles.size() - 1; j++) {
			if (!(j == 1 && hitcircleVisible)) {
				reverseArrow.setRotation(angles[j % 2]);
				g.drawImage(reverseArrow, (int) endCircles.get(j).x - reverseArrow.getWidth() / 2,
						(int) endCircles.get(j).y - reverseArrow.getHeight() / 2);
			}
		}
		g.setColor(Color.white);
	}

	@Override
	public void createSlider(double circleSize, double resConverter) {
		this.circleSize = (int) ((109 - 9 * circleSize) * resConverter);
		length = length * kicks;
		this.calculateCurve();
		this.repositionPoints();
		this.calculateKicks();
		this.createShape();
		this.calculateAngles();
	}

	/**
	 * Adds a point to the slider.
	 * 
	 * @param pointX
	 * @param pointY
	 */
	public void addPoint(int pointX, int pointY) {
		Point point = new Point(pointX, pointY);
		if (points.isEmpty()) {
			points.add(new ArrayList<Point>());
		} else {
			Point last = points.get(points.size() - 1).get(points.get(points.size() - 1).size() - 1);
			if (point.x == last.x && point.y == last.y) {
				points.add(new ArrayList<Point>());
			}
		}
		this.points.get(points.size() - 1).add(new Point(pointX, pointY));
	}

	/**
	 * Method that calculates the angles for the reverse arrows.
	 */
	private void calculateAngles() {
		angles = new float[2];
		// Getting back angle.
		Point one = newTrack.get(newTrack.size() / kicks - 1);
		Point two = newTrack.get(newTrack.size() / kicks - 2);
		angles[0] = (float) Math.toDegrees(Math.atan2(two.y - one.y, two.x - one.x));
		// Getting forward angle.
		one = newTrack.get(0);
		two = newTrack.get(2);
		angles[1] = (float) Math.toDegrees(Math.atan2(two.y - one.y, two.x - one.x));
	}

	/**
	 * Method that creates a shape for the slider to be stored. When the render method
	 * gets called, this shape will get drawn.
	 */
	private void createShape() {
		int size = circleSize - circleSize / 10;
		ArrayList<Point> track = new ArrayList<Point>();
		for (int i = 0; i < newTrack.size() / kicks; i++) {
			track.add(newTrack.get(i));
		}
		sliderShape = new SliderShape(newTrack.get(0).x, newTrack.get(0).y, size / 2, size / 2, size, track);
		for (int i = 1; i < track.size(); i++) {
			sliderShape = sliderShape.add(new Ellipse(track.get(i).x, track.get(i).y, size / 2, size / 2));
		}
		sliderShape = sliderShape.onlyBorder();
	}

	/**
	 * Method that draws the slider.
	 * 
	 * @param g
	 * @param circleSize
	 */
	private void drawSlider(Graphics g, int circleSize) {
		int size = circleSize - circleSize / 10;
		int smallSize = size - 10;
		g.setAntiAlias(true);
		g.setLineWidth(7);
		g.setColor(Color.white);
		g.draw(sliderShape);
	}

	/**
	 * Method that calculates the curve for the slider. A slider curve can
	 * be a combination of different curves added together and the curves
	 * can either be a bezier curve or a circular curve.
	 */
	private void calculateCurve() {
		for (int i = 0; i < points.size(); i++) {
			pointCount = points.get(i).size();
			if (pointCount == 3) {
				this.circularCurve(points.get(i));
			} else if (pointCount > 1) {
				float t = 0;
				while (t <= this.getLength()) {
					this.besierCurvePixel(t, points.get(i));
					t += 3;
				}
			}
		}
	}

	/*
	 * Method that extends the slider curve so it takes all kicks
	 * in to account.
	 */
	private void calculateKicks() {
		endCircles.add(newTrack.get(newTrack.size() - 1));
		if (kicks > 1) {
			int initLength = newTrack.size();
			for (int i = 1; i < kicks; i++) {
				ArrayList<Point> pTemp = new ArrayList<Point>();
				for (int j = 0; j < initLength; j++) {
					Point p = newTrack.get(newTrack.size() - 1 - j);
					pTemp.add(p);
					if (j == initLength - 1) {
						endCircles.add(p);
					}
				}
				for (int j = 0; j < pTemp.size(); j++) {
					pointLength.add(pointLength.get(pointLength.size() - 1)
							+ pTemp.get(j).distance(newTrack.get(newTrack.size() - 1)));
					newTrack.add(pTemp.get(j));
				}
			}
		}
	}

	// Factorial
	private static int fact(int n) {
		int fact = 1;
		for (int i = 1; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}

	// Bernstein polynomial
	private double bernstein(float t, int n, int i) {
		t = t / getLength();
		return (fact(n) / (fact(i) * fact(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t, i);
	}

	/** 
	 * Calculates the middle of a circle, given three points.
	 * 
	 * @param points
	 * @return middle point
	 */
	private Point middle(ArrayList<Point> points) {
		float yDelta_a = points.get(1).y - points.get(0).y;
		float xDelta_a = points.get(1).x - points.get(0).x;
		float yDelta_b = points.get(2).y - points.get(1).y;
		float xDelta_b = points.get(2).x - points.get(1).x;
		float[] arr = { yDelta_a, xDelta_a, yDelta_b, xDelta_b };
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 0) {
				arr[i] = 1;
			}
		}
		Point center = new Point();
		float aSlope = arr[0] / arr[1];
		float bSlope = arr[2] / arr[3];
		center.x = (int) ((aSlope * bSlope * (points.get(0).y - points.get(2).y)
				+ bSlope * (points.get(0).x + points.get(1).x) - aSlope * (points.get(1).x + points.get(2).x))
				/ (2 * (bSlope - aSlope)));
		center.y = (int) (-1 * (center.x - (points.get(0).x + points.get(1).x) / 2) / aSlope
				+ (points.get(0).y + points.get(1).y) / 2);
		return center;
	}

	/**
	 * returns true if direction of the circular slider is right. false if left.
	 * 
	 * @param big
	 * @param small
	 * @return direction
	 */
	private boolean direction(float big, float small) {
		big *= -1;
		small *= -1;
		if (big < 0 && small > 0 || big > 0 && small < 0) {
			big += 90;
			small += 90;
			if (big < 0) {
				big += 360;
			}
			if (small < 0) {
				small += 360;
			}
		}
		big = big % 360;
		small = small % 360;
		if (small < big) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculates a circular curve from 3 points within the curve.
	 * 
	 * @param points
	 */
	private void circularCurve(ArrayList<Point> points) {
		Point center = middle(points);
		double r = points.get(0).distance(center.x, center.y);
		if (r > 800 || this.points.size() > 1) {
			float t = 0;
			while (t <= this.getLength()) {
				this.besierCurvePixel(t, points);
				t += 3;
			}
		} else {
			float big = (float) Math.toDegrees(Math.atan2(center.y - points.get(0).y, center.x - points.get(0).x));
			float small = (float) Math
					.toDegrees(Math.atan2(points.get(1).y - points.get(0).y, points.get(1).x - points.get(0).x));

			// get slider direction.
			boolean right = direction(big, small);

			// set pointLength
			sliderTrack.add(points.get(0));
			if (pointLength.isEmpty()) {
				pointLength.add((double) 0);
			} else {
				pointLength.add(pointLength.get(pointLength.size() - 1));
			}

			// Length between points.
			double s = (length / kicks) / ((length / kicks) / 10);
			float v = (float) Math.toDegrees(Math.acos((r * r + s * s - r * r) / (2 * r * s)));

			// Place points.
			for (int i = 0; i <= (length / kicks) / 10; i++) {
				pointLength.add(pointLength.get(pointLength.size() - 1) + s);
				Point a = sliderTrack.get(sliderTrack.size() - 1);
				float u = (float) Math.toDegrees(Math.atan2(center.y - a.y, center.x - a.x));
				u *= -1;
				float w = v + u;
				w *= -1;
				double y = s * Math.sin(Math.toRadians(w));
				double x = s * Math.cos(Math.toRadians(w));
				Point res = new Point();

				if (right) {
					res.setLocation(a.x - x, a.y - y);
				} else {
					res.setLocation(a.x + x, a.y + y);
				}

				sliderTrack.add(res);
			}
		}
	}

	/**
	 * Calculates a besier curve from the points given in the slider.
	 * 
	 * @param t
	 * @param points
	 */
	private void besierCurvePixel(float t, ArrayList<Point> points) {

		double bPoly[] = new double[pointCount];

		for (int i = 0; i < pointCount; i++) {
			bPoly[i] = bernstein(t, pointCount - 1, i);
		}

		double sumX = 0;
		double sumY = 0;

		for (int i = 0; i < pointCount; i++) {
			sumX += bPoly[i] * points.get(i).x;
			sumY += bPoly[i] * points.get(i).y;
		}

		int x, y;
		x = (int) Math.round(sumX);
		y = (int) Math.round(sumY);

		Point p = new Point(x, y);
		if (sliderTrack.size() == 0) {
			pointLength.add((double) 0);
		} else {
			sliderLength += sliderTrack.get(sliderTrack.size() - 1).distance(p);
			pointLength.add(sliderLength);
		}
		sliderTrack.add(p);
	}

	/*
	 * Evens out the distances between the points in the slider curve. We need
	 * this for the sliderball and rendering of the slider.
	 */
	private void repositionPoints() {
		double totLength = 0;
		for (int i = 0; i < sliderTrack.size() - 1; i++) {
			totLength += sliderTrack.get(i).distance(sliderTrack.get(i + 1));
		}
		double curLength = 0;
		newTrack.add(sliderTrack.get(0));
		while (curLength <= totLength) {
			curLength += pointDistance;
			for (int j = 1; j < pointLength.size() - 1; j++) {
				if (pointLength.get(j) >= curLength) {
					double diff1 = pointLength.get(j) - curLength;
					double diff2 = curLength - pointLength.get(j - 1);
					if (diff1 < diff2) {
						newTrack.add(sliderTrack.get(j));
					} else {
						newTrack.add(sliderTrack.get(j - 1));
					}
					break;
				}
			}
		}
	}
}
