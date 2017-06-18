package tatakae.graphics;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 * Class that represents the cursor used in the game. This cursor has
 * a custom image provided in a skin folder. A cursor trail is also
 * created and drawn.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name Cursor.java
 */
public class Cursor {

	private Image cursor;
	private Image cursorTrail;
	private ArrayList<Point> trail;
	private ArrayList<Long> duration;
	private int cursorSize;

	public Cursor(int cursorSize) {
		this.cursorSize = cursorSize;
		cursor = ImageHandler.buildCursor(cursorSize);
		cursorTrail = ImageHandler.buildCursorTrail(cursorSize);
		trail = new ArrayList<Point>();
		duration = new ArrayList<Long>();
	}

	/**
	 * Renders the cursor and cursor trail.
	 * 
	 * @param Graphics
	 * @param fps
	 * @param input
	 */
	public void render(Graphics g, int fps, Input input) {
		int x = input.getMouseX();
		int y = input.getMouseY();
		Point point = new Point(x, y);
		if (fps == 0) {
			fps = 3000;
		}
		if (!trail.isEmpty() && point.distance(trail.get(trail.size() - 1)) > cursorSize / 3) {
			trail.add(point);
			duration.add(System.currentTimeMillis());
		} else if (trail.isEmpty()) {
			trail.add(point);
			duration.add(System.currentTimeMillis());
		}
		if (System.currentTimeMillis() - duration.get(0) > 100) {
			trail.remove(0);
			duration.remove(0);
		}
		// Draw cursor trail.
		for (int i = 0; i < trail.size(); i++) {
			g.drawImage(cursorTrail, trail.get(i).x - cursorTrail.getWidth() / 2, trail.get(i).y - cursorTrail.getHeight() / 2);
		}
		g.drawImage(cursor, x - cursorSize / 2, y - cursorSize / 2);
	}
}
