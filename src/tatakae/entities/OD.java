package tatakae.entities;

/**
 * Class representing Overall Difficulty. This is the timing window
 * where the user can click the circle, it is used to calculate accuracy.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name OD.java
 */
public class OD {
	
	private long hit300;
	private long hit100;
	private long hit50;
	
	/**
	 * Converts an od value from a beatmap to longs representing milliseconds.
	 * 
	 * @param od
	 */
	public OD(double od){
		hit300 = (long) (79.5 - od * 6);
		hit100 = (long) (139.5 - od * 8);
		hit50 = (long) (199.5 - od * 10);
	}
	
	/**
	 * Returns the 300 point hit window.
	 * 
	 * @return hit300
	 */
	public long getHit300(){
		return hit300;
	}
	
	/**
	 * Returns the 100 point hit window.
	 * 
	 * @return hit100
	 */
	public long getHit100(){
		return hit100;
	}
	
	/**
	 * Returns the 50 point hit window.
	 * 
	 * @return hit50
	 */
	public long getHit50(){
		return hit50;
	}
}
