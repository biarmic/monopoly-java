package managers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import windows.Button;
import windows.Screen;

public class AudioManager {
	private static Screen screen;
	private ArrayList<Clip> clips = new ArrayList<Clip>();
	private HashMap<String,File> soundList = new HashMap<>();
	private ArrayList<Button> soundButtons = new ArrayList<Button>();
	private ArrayList<Button> musicButtons = new ArrayList<Button>();
	private boolean isSoundOn = true;
	private boolean isMusicOn = true;
	public AudioManager(Screen screen) {
		AudioManager.screen = screen;
		soundList.put("main-menu",(new File("src/sounds/main-menu.wav")));
		for(int i = 1; i < 8; i++)
			soundList.put("theme-"+i,(new File("src/sounds/theme-"+i+".wav")));
		soundList.put("click",(new File("src/sounds/click.wav")));
		soundList.put("dice",(new File("src/sounds/dice.wav")));
		soundList.put("move",new File("src/sounds/move.wav"));
		soundList.put("coin",new File("src/sounds/coin.wav"));
		soundList.put("buy",new File("src/sounds/buy.wav"));
	}
	public boolean isSoundOn() {
		return isSoundOn;
	}
	public boolean isMusicOn() {
		return isMusicOn;
	}
	public void addSoundButton(Button button) {
		soundButtons.add(button);
	}
	public void addMusicButton(Button button) {
		musicButtons.add(button);
	}
	public void switchSound() {
		isSoundOn = !isSoundOn;
		if(isSoundOn && isMusicOn)
			playSound(screen.getActiveScene());
		else
			stopClips();
		for(Button button : soundButtons)
			button.switchImages();
	}
	public void switchMusic() {
		isMusicOn = !isMusicOn;
		if(isMusicOn)
			playSound(screen.getActiveScene());
		else
			stopMusicClips();
		for(Button button : musicButtons)
			button.switchImages();
	}
	public void playSound(String key) {
		if(isSoundOn) {
			try {
				Clip clip = AudioSystem.getClip();
				if(key.equals("game-scene") || key.equals("main-menu"))
					clips.add(0,clip);
				else
					clips.add(clip);
				clip.open(AudioSystem.getAudioInputStream(soundList.get(key.equals("game-scene") ? "theme-"+(int)(Math.random()*7+1) : key)));
				clip.addLineListener(new LineListener() {
					@Override
					public void update(LineEvent event) {
						if(event.getType()==LineEvent.Type.STOP) {
							if(((Clip)event.getSource()).getMicrosecondLength()/1000000>=60 && isMusicOn)
								playSound(screen.getActiveScene());
							clips.remove(((Clip)event.getSource()));
						}
					}
				});
				clip.start();
			}catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	private void stopClips() {
		while(clips.size()>0)
			clips.remove(0).stop();
	}
	private void stopMusicClips() {
		if(clips.get(0).getMicrosecondLength()/1000000>=60)
			clips.remove(0).stop();
	}
	public void changeBackgroundSound() {
		if(clips.size()>0)
			clips.remove(0).stop();
	}
}
