package windows;
import java.util.Timer;
import java.util.TimerTask;
import gamestuff.Dice;
import managers.AudioManager;
import managers.GameManager;

public class DiceWindow extends Window {
	private static GameManager gameManager;
	private Dice[] die = new Dice[2];
	public DiceWindow(AudioManager audioManager, GameManager gameManager) {
		super("dice");
		DiceWindow.gameManager = gameManager;
		die[0] = new Dice(22,60,audioManager);
		die[1] = new Dice(167,0);
		for(Dice dice : die)
			add(dice,new Integer(1));
	}
	public void roll() {
		for(Dice dice : die)
			dice.roll();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				gameManager.receiveDiceNumber(die[0].getTopFace(),die[1].getTopFace());
				timer.cancel();
			}
		};
		timer.schedule(task,3000);
	}
}
