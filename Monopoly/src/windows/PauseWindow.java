package windows;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import managers.AudioManager;

public class PauseWindow extends Window {
	public PauseWindow(Screen screen, AudioManager audioManager) {
		super("pause");
		Button home = new Button("home",72,126);
		Button sound = new Button(audioManager.isSoundOn() ? "sound-on" : "sound-off",audioManager.isSoundOn() ? "sound-off" : "sound-on",418,126);
		Button music = new Button(audioManager.isMusicOn() ? "music-on" : "music-off",audioManager.isMusicOn() ? "music-off" : "music-off",245,126);
		add(home,new Integer(1));
		add(sound,new Integer(1));
		add(music,new Integer(1));
		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				screen.openMainMenu();
			}
		});
		sound.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.switchSound();
			}
		});
		music.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.switchMusic();
			}
		});
		audioManager.addSoundButton(sound);
		audioManager.addMusicButton(music);
	}
}
