package tatakae.audio;

import java.io.File;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

/**
 * Class that handles the audio in the game.
 * 
 * @author Jesper Bergstrom
 * @version 0.00.00
 * @name AudioManager.java
 */
public class AudioManager {

	private Sound hitSound;
	private Sound missSound;
	private Music song;
	private Converter converter;

	public AudioManager(String songPath) throws SlickException {
		hitSound = new Sound("./resources/drum-hitnormal.wav");
		missSound = new Sound("./resources/combobreak.wav");
		converter = new Converter();
		if (new File("./songs/song.wav").exists()) {
			new File("./songs/song.wav").delete();
		}
		try {
			converter.convert(songPath, "./songs/song.wav");
			song = new Music("./songs/song.wav");
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
	public void stopSong(){
		song.stop();
	}

	/**
	 * Plays the miss sound.
	 */
	public void playMissSound() {
		missSound.play((float) 1, (float) 0.1);
	}

	/**
	 * Plays the hit sound.
	 */
	public void playHitSound() {
		hitSound.play((float) 1, (float) 0.1);
	}

	/**
	 * Plays the song.
	 */
	public void playSong() {
		song.play((float) 1, (float) 0.1);
	}
}
