package windows;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import gamestuff.Card;
import gamestuff.Chance;
import gamestuff.Chest;
import managers.AudioManager;
import managers.GameManager;

public class GetOutWindow extends Window{
	public GetOutWindow(Screen screen, AudioManager audioManager, GameManager gameManager, Font font) {
		super("get-out");
		Button payButton = new Button("pay-50",31,65);
		Button useCardButton = new Button("use-card",295,65);
		Button closeButton = new Button("close",516,10);
		add(payButton,new Integer(1));
		add(useCardButton,new Integer(1));
		add(closeButton,new Integer(1));
		payButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if(gameManager.getTurnPlayer().getCash()>=50) {
					Thread thread = new Thread() {
						@Override
						public void run() {
							audioManager.playSound("click");
							screen.removeFromGlassPane(GetOutWindow.this);
							gameManager.getTurnPlayer().transaction(-50);
							gameManager.getTurnPlayer().getOutJail(gameManager.getLastDieTotal());
						}
					};
					thread.start();
				}else
					screen.addToGlassPane(new MessageWindow("YOU DO NOT HAVE ENOUGH CASH",screen,audioManager,font));
			}
		});
		useCardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if(gameManager.getTurnPlayer().getJailCards().size()>0) {
					Thread thread = new Thread() {
						@Override
						public void run() {
							audioManager.playSound("click");
							screen.removeFromGlassPane(GetOutWindow.this);
							Card card = gameManager.getTurnPlayer().getJailCards().remove(0);
							if(card instanceof Chance) {
								gameManager.getChanceList().add((Chance)card);
							}else {
								gameManager.getChestList().add((Chest)card);
							}
							gameManager.getTurnPlayer().getOutJail(gameManager.getLastDieTotal());
						}
					};
					thread.start();
				}else
					screen.addToGlassPane(new MessageWindow("YOU DO NOT HAVE A JAIL CARD",screen,audioManager,font));
			}
		});
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				screen.removeFromGlassPane(GetOutWindow.this);
			}
		});
	}
}
