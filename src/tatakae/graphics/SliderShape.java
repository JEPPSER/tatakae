package tatakae.graphics;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * Class that extends polygon and represents the shape for a slider.
 * This shape will be drawn in the render method in the Slider class.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name SliderShape.java
 */
public class SliderShape extends Polygon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int circleSize;
	private ArrayList<Point> track = new ArrayList<Point>();

	public SliderShape(float centerPointX, float centerPointY, float radius1, float radius2, int circleSize,
			ArrayList<Point> track) {
		this.circleSize = circleSize;
		this.track = track;
		Ellipse e = new Ellipse(centerPointX, centerPointY, radius1, radius2);
		float[] arr = e.getPoints();
		for (int i = 0; i < arr.length - 1; i += 2) {
			super.addPoint(arr[i], arr[i + 1]);
		}
	}

	public SliderShape(ArrayList<Point> track, int circleSize) {
		this.track = track;
		this.circleSize = circleSize;
	}

	@Override
	protected void createPoints() {

	}

	@Override
	public Shape transform(Transform arg0) {
		return null;
	}

	public SliderShape add(Shape shape) {
		if (super.intersects(shape)) {
			SliderShape newShape = new SliderShape(track, circleSize);
			float[] points = super.points;
			ArrayList<Point> newPoints = new ArrayList<Point>();
			// Put all current points in the new points list.
			for (int i = 0; i < points.length - 1; i += 2) {
				newPoints.add(new Point((int) points[i], (int) points[i + 1]));
			}
			// Add all points from the added circle.
			for (int i = 0; i < shape.getPoints().length - 1; i += 2) {
				newPoints.add(new Point((int) shape.getPoints()[i], (int) shape.getPoints()[i + 1]));
			}

			// Add the new points.
			for (int i = 0; i < newPoints.size(); i++) {
				newShape.addPoint(newPoints.get(i).x, newPoints.get(i).y);
			}
			return newShape;
		}
		return null;
	}

	/*
	 * Removes all points in the slider shape that are not in the border.
	 */
	public SliderShape onlyBorder() {
		SliderShape newShape = new SliderShape(track, circleSize);
		ArrayList<Point> newPoints = new ArrayList<Point>();
		for (int i = 0; i < getPoints().length; i += 2) {
			newPoints.add(new Point((int) getPoints()[i], (int) getPoints()[i + 1]));
		}
		// Remove all non-border points from the sliderShape.
		for (int i = 0; i < newPoints.size(); i++) {
			Point p = newPoints.get(i);
			for (int j = 0; j < track.size(); j++) {
				if (p.distance(track.get(j)) < circleSize / 2 - 1) {
					newPoints.remove(p);
					i--;
					break;
				}
			}
		}
		
		// Fix the indexes of the points.
		newPoints = reIndex(newPoints);

		// Add the new points.
		for (int i = 0; i < newPoints.size(); i++) {
			newShape.addPoint(newPoints.get(i).x, newPoints.get(i).y);
		}
		return newShape;
	}

	/**
	 * Creates a new list of points with the same points as in the slider shape
	 * but the indexes are different so that the next point is always the one
	 * that is the closest.
	 */
	private ArrayList<Point> reIndex(ArrayList<Point> points) {
		ArrayList<Point> result = new ArrayList<Point>();
		result.add(points.get(0));
		while (result.size() < points.size()) {
			double shortest = 1000;
			Point one = result.get(result.size() - 1);
			int resPoint = 0;
			for (int j = 0; j < points.size(); j++) {
				if (j != points.size() - 1) {
					Point two = points.get(j);
					if (one.distance(two) < shortest && one.distance(two) != 0 && !result.contains(points.get(j))) {
						shortest = one.distance(two);
						resPoint = j;
					}
				}
			}
			result.add(points.get(resPoint));
		}
		return result;
	}
}
