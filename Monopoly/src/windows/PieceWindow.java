package windows;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import gamestuff.PlayerColor;
import managers.AudioManager;

public class PieceWindow extends Window {
	public PieceWindow(AudioManager audioManager, GameScene gameScene) {
		super("choose-a-piece");
		Button red = new Button("red-player",152,161);
		Button blue = new Button("blue-player",360,161);
		Button green = new Button("green-player",568,161);
		Button yellow = new Button("yellow-player",776,161);
		add(red,new Integer(1));
		add(blue,new Integer(1));
		add(green,new Integer(1));
		add(yellow,new Integer(1));
		red.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				gameScene.receiveColor(PlayerColor.red);
			}
		});
		blue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				gameScene.receiveColor(PlayerColor.blue);
			}
		});
		green.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				gameScene.receiveColor(PlayerColor.green);
			}
		});
		yellow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				gameScene.receiveColor(PlayerColor.yellow);
			}
		});
	}
}
