package tatakae.entities;

import java.util.ArrayList;

/**
 * Class that represents a beatmap. The class holds a list of HitObjects as well
 * as a number of properties for necessary information about a beatmap.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name Beatmap.java
 */
public class Beatmap {

	private ArrayList<HitObject> list;
	private ArrayList<TimingPoint> timingPoints;
	private String title;
	private String artist;
	private String creator;
	private String version;
	private double hpDrain;
	private double circleSize;
	private double od;
	private double ar;
	private double sliderMultiplier;
	private double sliderTickRate;
	private double stackLeniency;
	private int beatDivisor;

	public Beatmap() {
		list = new ArrayList<HitObject>();
		timingPoints = new ArrayList<TimingPoint>();
	}

	/**
	 * Method for getting the list of HitObjects.
	 * 
	 * @return list of HitObjects
	 */
	public ArrayList<HitObject> getList() {
		return list;
	}

	/**
	 * Method for adding a HitObject to the beatmap.
	 * 
	 * @param object
	 */
	public void add(HitObject object) {
		list.add(object);
	}

	/**
	 * Method that sets the title of the beatmap.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method for getting the title of the beatmap.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method that sets the artist of the beatmap.
	 * 
	 * @param artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Method for getting the artist of the beatmap.
	 * 
	 * @return artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Method that sets the creator of the beatmap.
	 * 
	 * @param creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * Method for getting the creator of the beatmap.
	 * 
	 * @return creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Method that sets the version of the beatmap.
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Method for getting the version of the beatmap.
	 * 
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Method that sets the hp drain of the beatmap.
	 * 
	 * @param hp
	 *            drain
	 */
	public void setHpDrain(double hpDrain) {
		this.hpDrain = hpDrain;
	}

	/**
	 * Method for getting the hp drain of the beatmap.
	 * 
	 * @return hp drain
	 */
	public double getHpDrain() {
		return hpDrain;
	}

	/**
	 * Method that sets the circle size of the beatmap.
	 * 
	 * @param circle
	 *            size
	 */
	public void setCircleSize(double circleSize) {
		this.circleSize = circleSize;
	}

	/**
	 * Method for getting the circle size of the beatmap.
	 * 
	 * @return circle size
	 */
	public double getCircleSize() {
		return circleSize;
	}

	/**
	 * Method that sets the od of the beatmap.
	 * 
	 * @param od
	 */
	public void setOd(double od) {
		this.od = od;
	}

	/**
	 * Method for getting the od of the beatmap.
	 * 
	 * @return od
	 */
	public double getOd() {
		return od;
	}

	/**
	 * Method that sets the ar of the beatmap.
	 * 
	 * @param ar
	 */
	public void setAr(double ar) {
		this.ar = ar;
	}

	/**
	 * Method for getting the ar of the beatmap.
	 * 
	 * @return ar
	 */
	public double getAr() {
		return ar;
	}

	/**
	 * Method that sets the slider multiplier of the beatmap.
	 * 
	 * @param slider
	 *            multiplier
	 */
	public void setSliderMultiplier(double sliderMultiplier) {
		this.sliderMultiplier = sliderMultiplier;
	}

	/**
	 * Method for getting the slider multiplier of the beatmap.
	 * 
	 * @return slider multiplier
	 */
	public double getSliderMultiplier() {
		return sliderMultiplier;
	}

	/**
	 * Method that sets the slider tick rate of the beatmap.
	 * 
	 * @param slider
	 *            tick rate
	 */
	public void setSliderTickRate(double sliderTickRate) {
		this.sliderTickRate = sliderTickRate;
	}

	/**
	 * Method for getting the slider tick rate of the beatmap.
	 * 
	 * @return slider tick rate
	 */
	public double getSliderTickRate() {
		return sliderTickRate;
	}

	/**
	 * Sets the stack leniency.
	 * 
	 * @param stackLeniency
	 */
	public void setStackLeniency(double stackLeniency) {
		this.stackLeniency = stackLeniency;
	}

	/**
	 * Returns the stack leniancy.
	 * 
	 * @return stack leniancy
	 */
	public double getStackLeniency() {
		return stackLeniency;
	}
	
	/**
	 * Returns list of all timing points
	 * 
	 * @return timing points
	 */
	public ArrayList<TimingPoint> getTimingPoints(){
		return timingPoints;
	}
	
	/**
	 * Sets the beat divisor
	 * 
	 * @param beatDivisor
	 */
	public void setBeatDivisor(int beatDivisor){
		this.beatDivisor = beatDivisor;
	}
	
	/**
	 * Returns the beat divisor
	 * 
	 * @return beatDivisor
	 */
	public int getBeatDivisor(){
		return beatDivisor;
	}
}
