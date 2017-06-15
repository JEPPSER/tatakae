package tatakae.entities;

/**
 * Class representing a timing point in the beatmap.
 * A timing point can change the slider speed, the offset
 * of the bpm.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name TimingPoint.java
 */
public class TimingPoint {
	
	private double bpm;
	private long time;
	private int offset;
	private double sliderSpeed;
	
	/**
	 * Sets the bpm of the timing point.
	 * 
	 * @param bpm
	 */
	public void setBPM(double bpm){
		this.bpm = bpm;
	}
	
	/**
	 * Returns the bpm of the timing point.
	 * 
	 * @return bpm
	 */
	public double getBPM(){
		return bpm;
	}
	
	/**
	 * Sets the time of the timing point.
	 * 
	 * @param time
	 */
	public void setTime(long time){
		this.time = time;
	}
	
	/**
	 * Returns the time of the timing point.
	 * 
	 * @return time
	 */
	public long getTime(){
		return time;
	}
	
	/**
	 * Sets the offset of the timing point.
	 * 
	 * @param offset
	 */
	public void setOffset(int offset){
		this.offset = offset;
	}
	
	/**
	 * Returns the offset of the timing point.
	 * 
	 * @return offset
	 */
	public int getOffset(){
		return offset;
	}
	
	/**
	 * Sets the slider speed of the timing point.
	 * 
	 * @param sliderSpeed
	 */
	public void setSliderSpeed(double sliderSpeed){
		this.sliderSpeed = sliderSpeed;
	}
	
	/**
	 * Returns the slider speed.
	 * 
	 * @return slider speed
	 */
	public double getSliderSpeed(){
		return sliderSpeed;
	}
}
