package tatakae.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import tatakae.entities.Beatmap;
import tatakae.entities.Circle;
import tatakae.entities.Slider;
import tatakae.entities.Spinner;
import tatakae.entities.TimingPoint;

/**
 * Class for reading a beatmap file and converting it to a Beatmap object.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name BeatmapBuilder.java
 */
public class BeatmapBuilder {

	private double resConverter;
	private String[] prevParts;
	private int stackCount = 0;
	private int width;
	private int height;

	private static final String[] GENERAL_FIELDS = { "AudioFilename: ", "AudioLeadIn: ", "PreviewTime: ", "Countdown: ",
			"SampleSet: ", "StackLeniency: ", "Mode: ", "LetterboxInBreaks: ", "WidescreenStoryboard: " };

	private static final String[] EDITOR_FIELDS = { "Bookmarks: ", "DistanceSpacing: ", "BeatDivisor: ", "GridSize: ",
			"TimelineZoom: " };

	private static final String[] METADATA_FIELDS = { "Title:", "TitleUnicode:", "Artist:", "ArtistUnicode:",
			"Creator:", "Version:", "Source:", "Tags:", "BeatmapID:", "BeatmapSetID:" };

	private static final String[] DIFFICULTY_FIELDS = { "HPDrainRate:", "CircleSize:", "OverallDifficulty:",
			"ApproachRate:", "SliderMultiplier:", "SliderTickRate:" };

	/**
	 * Constructs the beatmap builder with information about the width and
	 * height of the playingfield.
	 *
	 * @param resConverter
	 * @param width
	 * @param height
	 */
	public BeatmapBuilder(double resConverter, int width, int height) {
		this.resConverter = resConverter;
		this.width = width;
		this.height = height;
	}

	/**
	 * Method that reads a beatmap file and converts it to a Beatmap object.
	 * 
	 * @param file
	 * @return Beatmap
	 * @throws FileNotFoundException
	 */
	public Beatmap readFile(File file) throws FileNotFoundException {

		Beatmap beatmap = new Beatmap();
		Scanner scan = new Scanner(file, "UTF-8");

		while (scan.hasNextLine()) {
			String temp = scan.nextLine();

			switch (temp) {
			case "[General]":
				readGeneral(scan, beatmap);
				break;
			case "[Editor]":
				readEditor(scan, beatmap);
				break;
			case "[Metadata]":
				readMetadata(scan, beatmap);
				break;
			case "[Difficulty]":
				readDifficulty(scan, beatmap);
				break;
			case "[Events]":
				break;
			case "[TimingPoints]":
				readTimingPoints(scan, beatmap);
				break;
			case "[HitObjects]":
				readHitObjects(scan, beatmap);
				break;
			default:
				break;
			}
		}

		scan.close();
		return beatmap;
	}

	/**
	 * Reads the general fields from a beatmap file. The general section is
	 * structured in the following way: 1. AudioFilename 2. AudioLeadIn 3.
	 * PreviewTime 4. Countdown 5. SampleSet 6. StackLeniency 7. Mode 8.
	 * LetterboxInBreaks 9. WidescreenStoryboard
	 * 
	 * @param scan
	 *            - Scanner object that reads the file for data.
	 * @param beatmap
	 *            - Beatmap object to populate with data.
	 */
	private void readGeneral(Scanner scan, Beatmap beatmap) {
		int counter = 0;
		Map<String, String> content = new HashMap<String, String>();

		while (scan.hasNextLine() && counter < GENERAL_FIELDS.length) {
			String temp = scan.nextLine();
			if (temp.equals("")) {
				break;
			}

			for (int i = 0; i < GENERAL_FIELDS.length; i++) {
				if (temp.contains(GENERAL_FIELDS[i])) {
					content.put(GENERAL_FIELDS[i], temp.substring(GENERAL_FIELDS[i].length()));
					counter++;
					break;
				}
			}
		}
		beatmap.setStackLeniency(Double.parseDouble(content.get(GENERAL_FIELDS[5])));
	}

	/**
	 * Reads the editor fields from a beatmap file. The editor section is
	 * structured the following way: 1. Bookmarks 2. DistanceSpacing 3.
	 * BeatDivisor 4. GridSize 5. TimelineZoom
	 * 
	 * @param scan
	 *            - Scanner object that reads the file for data.
	 * @param beatmap
	 *            - Beatmap object to populate with data.
	 */
	private void readEditor(Scanner scan, Beatmap beatmap) {
		int counter = 0;
		Map<String, String> content = new HashMap<String, String>();

		while (scan.hasNextLine() && counter < EDITOR_FIELDS.length) {
			String temp = scan.nextLine();
			if (temp.equals("")) {
				break;
			}

			for (int i = 0; i < EDITOR_FIELDS.length; i++) {
				if (temp.contains(EDITOR_FIELDS[i])) {
					content.put(EDITOR_FIELDS[i], temp.substring(EDITOR_FIELDS[i].length()));
					counter++;
					break;
				}
			}
		}

		beatmap.setBeatDivisor(Integer.parseInt(content.get(EDITOR_FIELDS[2])));
	}

	/**
	 * Reads the metadata for a beatmap. The metadata for a beatmap is
	 * structured in the following way: 1. Title 2. TitleUnicode 3. Artist 4.
	 * ArtistUnicode 5. Creator 6. Version 7. Source 8. Tags 9. BeatmapID 10.
	 * BeatmapSetID
	 * 
	 * @param scan
	 *            - Scanner object that reads the file for data.
	 * @param beatmap
	 *            - Beatmap object to populate with data.
	 */
	private void readMetadata(Scanner scan, Beatmap beatmap) {
		int counter = 0;
		Map<String, String> content = new HashMap<String, String>();

		while (scan.hasNextLine() && counter < METADATA_FIELDS.length) {
			String temp = scan.nextLine();
			if (temp.equals("")) {
				break;
			}

			for (int i = 0; i < METADATA_FIELDS.length; i++) {
				if (temp.contains(METADATA_FIELDS[i])) {
					content.put(METADATA_FIELDS[i], temp.substring(METADATA_FIELDS[i].length()));
					counter++;
					break;
				}
			}
		}

		beatmap.setTitle(content.get(METADATA_FIELDS[0]));
		beatmap.setArtist(content.get(METADATA_FIELDS[2]));
		beatmap.setCreator(content.get(METADATA_FIELDS[4]));
		beatmap.setVersion(content.get(METADATA_FIELDS[5]));
	}

	/**
	 * Reads the difficulty for a beatmap. The difficulty for a beatmap is
	 * structured in the following way: 1. HPDrainRate 2. CircleSize 3.
	 * OverallDifficulty 4. ApproachRate 5. SliderMultiplier 6. SliderTickRate
	 * 
	 * @param scan
	 *            - Scanner object that reads the file for data.
	 * @param beatmap
	 *            - Beatmap object to populate with data.
	 */
	private void readDifficulty(Scanner scan, Beatmap beatmap) {
		int counter = 0;
		Map<String, String> content = new HashMap<String, String>();

		while (scan.hasNextLine() && counter < DIFFICULTY_FIELDS.length) {
			String temp = scan.nextLine();
			if (temp.equals("")) {
				break;
			}

			for (int i = 0; i < DIFFICULTY_FIELDS.length; i++) {
				if (temp.contains(DIFFICULTY_FIELDS[i])) {
					content.put(DIFFICULTY_FIELDS[i], temp.substring(DIFFICULTY_FIELDS[i].length()));
					counter++;
					break;
				}
			}
		}

		beatmap.setHpDrain(Double.parseDouble(content.get(DIFFICULTY_FIELDS[0])));
		beatmap.setCircleSize(Double.parseDouble(content.get(DIFFICULTY_FIELDS[1])));
		beatmap.setOd(Double.parseDouble(content.get(DIFFICULTY_FIELDS[2])));
		beatmap.setAr(Double.parseDouble(content.get(DIFFICULTY_FIELDS[3])));
		beatmap.setSliderMultiplier(Double.parseDouble(content.get(DIFFICULTY_FIELDS[4])));
		beatmap.setSliderTickRate(Double.parseDouble(content.get(DIFFICULTY_FIELDS[5])));
	}

	private void readTimingPoints(Scanner scan, Beatmap beatmap) {
		while (scan.hasNextLine()) {
			String temp = scan.nextLine();
			if (temp.equals("")) {
				break;
			} else {
				String[] split = temp.split(",");
				TimingPoint tp = new TimingPoint();
				if (Double.parseDouble(split[1]) > 0) {
					tp.setBPM(60000 / Double.parseDouble(split[1]));
					tp.setSliderSpeed(1);
				} else {
					tp.setBPM(beatmap.getTimingPoints().get(beatmap.getTimingPoints().size() - 1).getBPM());
					tp.setSliderSpeed(1 / (-1 * (Double.parseDouble(split[1]) / 100)));
				}
				tp.setTime(Long.parseLong(split[0]));
				beatmap.getTimingPoints().add(tp);
			}
		}
	}

	/**
	 * Reads the hit objects for a beatmap. There are three types of hit
	 * objects, first is circle which is structured the following way:
	 * "x,y,time,type,hitSound,addition"
	 * 
	 * @param scan
	 *            - Scanner object that reads the file for data.
	 * @param beatmap
	 *            - Beatmap object to populate with data.
	 */
	private void readHitObjects(Scanner scan, Beatmap beatmap) {
		while (scan.hasNextLine()) {
			String temp = scan.nextLine();
			String parts[] = temp.split(",");

			int x = (int) ((Double.parseDouble(parts[0]) + 180) * resConverter);
			int y = (int) ((Double.parseDouble(parts[1]) + 82) * resConverter);

			if (!beatmap.getList().isEmpty() && x == (int) ((Double.parseDouble(prevParts[0]) + 180) * resConverter)
					&& y == (int) ((Double.parseDouble(prevParts[1]) + 82) * resConverter)) {

				stackCount++;
				x += (int) (resConverter * beatmap.getStackLeniency() * 6 * stackCount);
				y += (int) (resConverter * beatmap.getStackLeniency() * 6 * stackCount);
			} else {
				stackCount = 0;
			}
			long time = Long.parseLong(parts[2]);

			Circle circle = new Circle();
			circle.setXPosition(x);
			circle.setYPosition(y);
			circle.setTime(time);
			circle.setHitBox((int) ((109 - 9 * beatmap.getCircleSize()) * resConverter));
			prevParts = parts;

			if (parts.length <= 6) {
				beatmap.add(circle);
			} else if (parts.length == 7) {
				Spinner spinner = new Spinner();
				spinner.setTime(Long.parseLong(parts[2]));
				long length = Long.parseLong(parts[5]) - Long.parseLong(parts[2]);
				spinner.setLength(length);
				spinner.setXPosition(circle.getXPosition());
				spinner.setYPosition(circle.getYPosition());
				spinner.setSpinnerSize(height);
				beatmap.add(spinner);
			} else if (parts.length > 7) {
				Slider slider = new Slider(width, height);
				slider.setXPosition(circle.getXPosition());
				slider.setYPosition(circle.getYPosition());
				slider.addPoint(circle.getXPosition(), circle.getYPosition());
				slider.setTime(circle.getTime());
				double bpm = beatmap.getTimingPoints().get(0).getBPM();
				for (int i = 0; i < beatmap.getTimingPoints().size() - 1; i++) {
					if (slider.getTime() >= beatmap.getTimingPoints().get(i).getTime()
							&& slider.getTime() < beatmap.getTimingPoints().get(i + 1).getTime()) {
						bpm = beatmap.getTimingPoints().get(i).getBPM();
					}
				}
				// milliseconds of 1 beat
				double r = 60000 / bpm;
				double s = Double.parseDouble(parts[7]) / (beatmap.getSliderMultiplier() * 100);
				double multiplier = 1;
				for (int i = 0; i < beatmap.getTimingPoints().size() - 1; i++) {
					if (slider.getTime() >= beatmap.getTimingPoints().get(i).getTime()
							&& slider.getTime() < beatmap.getTimingPoints().get(i + 1).getTime()) {
						multiplier = beatmap.getTimingPoints().get(i).getSliderSpeed();
						break;
					}
				}
				slider.setLength((long) ((s * r) / multiplier));
				slider.setHitBox((int) ((109 - 9 * beatmap.getCircleSize()) * resConverter));
				String split = parts[5];
				String[] points = split.split("\\|");
				for (int i = 1; i < points.length; i++) {
					int pointX = (int) ((Double.parseDouble(points[i].split(":")[0]) + 180) * resConverter);
					int pointY = (int) ((Double.parseDouble(points[i].split(":")[1]) + 82) * resConverter);
					slider.addPoint(pointX, pointY);
				}
				slider.setKicks(Integer.parseInt(parts[6]));
				slider.createSlider(beatmap.getCircleSize(), resConverter);
				beatmap.add(slider);
			}
		}
	}
}
