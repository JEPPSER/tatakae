package tatakae.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import tatakae.entities.HitObject;

/**
 * Class that creates and draws an approach circle.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name ApproachCircle.java
 */
public class ApproachCircle {

	/**
	 * Method for drawing an approach circle around a Circle hitobject. A
	 * position, radius and thickness for the approach circle gets calculated
	 * based on the circle size and the duration of the hitobject.
	 * 
	 * @param Graphics
	 * @param hitobject
	 * @param circlesize          
	 */
	public static void drawApproachCircle(Graphics g, HitObject hitobject, int circleSize, double ar) {
		int radius = (int) (circleSize * 4 - circleSize * 3.2 * (hitobject.getDuration() / ar));
		int x = (int) (hitobject.getHitBox().getX() + circleSize / 2 - radius / 2);
		int y = (int) (hitobject.getHitBox().getY() + circleSize / 2 - radius / 2);
		g.setColor(new Color(255, 255, 255, 150));
		g.setLineWidth(7);
		if (radius >= hitobject.getHitBox().getWidth()) {
			g.drawOval(x, y, radius, radius);
		}
		g.setColor(Color.white);
	}
}
