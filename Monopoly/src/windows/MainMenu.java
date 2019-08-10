package windows;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import managers.AudioManager;

public class MainMenu extends JLayeredPane {
	public MainMenu(Screen screen, AudioManager audioManager) {
		JLabel background = new JLabel();
		JLabel logo = new JLabel();
		Button play = new Button("play",810,650);
		Button sound = new Button("sound-on","sound-off",1680,20);
		Button music = new Button("music-on","music-off",1560,20);
		Button exit = new Button("exit",1800,20);
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/background.png"))));
			logo.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/logo.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setBounds(0,0,1920,1080);
		logo.setBounds(417,300,1085,202);
		add(background,JLayeredPane.DEFAULT_LAYER);
		add(logo,new Integer(1));
		add(play,new Integer(1));
		add(sound,new Integer(1));
		add(music,new Integer(1));
		add(exit,new Integer(1));
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
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				System.exit(0);
			}
		});
		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				screen.openGameScene();
			}
		});
		audioManager.addSoundButton(sound);
		audioManager.addMusicButton(music);
	}
}
