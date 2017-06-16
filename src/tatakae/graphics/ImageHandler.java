package tatakae.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Class that handles and builds all Images that will be used in the game.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name ImageHandler.java
 */
public class ImageHandler {
	
	/**
	 * Builds and returns an image for the reverse arrow.
	 * 
	 * @param circleSize
	 * @return reverse arrow
	 */
	public static Image buildReverseArrow(int circleSize){
		try {
			return new Image("./resources/reversearrow.png").getScaledCopy(circleSize - circleSize / 10, circleSize - circleSize / 10);
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method that builds and returns the Image for the cursor.
	 * 
	 * @param cursorSize
	 * @return cursor
	 */
	public static Image buildCursor(int cursorSize) {
		try {
			return new Image("./resources/cursor.png").getScaledCopy(cursorSize, cursorSize);
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method that builds the cursor trail image.
	 * 
	 * @param cursorSize
	 * @return cursorTrail
	 */
	public static Image buildCursorTrail(int cursorSize) {
		try {
			return new Image("./resources/cursortrail.png").getScaledCopy(cursorSize / 4, cursorSize / 4);
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method that builds and returns the Image for the hitcircle.
	 * 
	 * @param circleSize
	 * @return hitcircle
	 */
	public static Image buildHitcircle(int circleSize) {
		try {
			return new Image("./resources/hitcircle.png").getScaledCopy(circleSize, circleSize);
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method that builds and returns the Image for the hitcircleoverlay.
	 * 
	 * @param circleSize
	 * @return hitcircleoverlay
	 */
	public static Image buildHitcircleOverlay(int circleSize) {
		try {
			return new Image("./resources/hitcircleoverlay.png").getScaledCopy(circleSize, circleSize);
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns a hit0 (miss) image.
	 * 
	 * @return image
	 */
	public static Image buildHit0() {
		try {
			return new Image("./resources/hit0.png");
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Rerurns a hit300 image.
	 * 
	 * @return hit300
	 */
	public static Image buildHit300() {
		try {
			return new Image("./resources/hit300.png");
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Rerurns a hit100 image.
	 * 
	 * @return hit100
	 */
	public static Image buildHit100() {
		try {
			return new Image("./resources/hit100.png");
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Rerurns a hit50 image.
	 * 
	 * @return hit50
	 */
	public static Image buildHit50() {
		try {
			return new Image("./resources/hit50.png");
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}
}
