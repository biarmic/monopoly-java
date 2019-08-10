package gamestuff;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import managers.AudioManager;

public class Dice extends JLabel {
	private static AudioManager audioManager;
	private static ImageIcon[] faces = new ImageIcon[6];
	private int topFace;
	public Dice(int x, int y, AudioManager audioManager) {
		Dice.audioManager = audioManager;
		try {
			for(int i = 0; i < 6; i++)
				faces[i] = new ImageIcon(ImageIO.read(getClass().getResource("/pieces/dice-"+(i+1)+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setIcon(faces[(int) (Math.random()*6)]);
		setBounds(x,y,getIcon().getIconWidth(),getIcon().getIconHeight());
		
	}
	public Dice(int x, int y) {
		setIcon(faces[(int) (Math.random()*6)]);
		setBounds(x,y,getIcon().getIconWidth(),getIcon().getIconHeight());
	}
	private void setTopFace() {
		for(int i = 0; i < 6; i++) {
			if(getIcon()==faces[i]) {
				topFace = i+1;
				return;
			}
		}
	}
	public int getTopFace() {
		return topFace;
	}
	public void roll() {
		audioManager.playSound("dice");
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			private int rollCount = 0;
			@Override
			public void run() {
				rollCount++;
				setIcon(faces[(int) (Math.random()*6)]);
				if(rollCount>=10) {
					setTopFace();
					timer.cancel();
				}
			}
		};
		timer.schedule(task,0,100);
	}
}
