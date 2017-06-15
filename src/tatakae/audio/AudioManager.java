package tatakae.audio;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

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
	
	public AudioManager() throws SlickException{
		hitSound = new Sound("./resources/drum-hitnormal.wav");
		missSound = new Sound("./resources/combobreak.wav");
	}
	
	/**
	 * Plays the miss sound.
	 */
	public void playMissSound(){
		missSound.play((float) 1, (float) 0.1);
	}
	
	/**
	 * Plays the hit sound.
	 */
	public void playHitSound(){
		hitSound.play((float) 1, (float) 0.1);
	}
	
	/**
	 * Plays the song.
	 */
	public void playSong(){
		
	}
}
