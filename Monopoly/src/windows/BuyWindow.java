package windows;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import gamestuff.Player;
import gamestuff.Property;
import managers.AudioManager;
import managers.GameManager;

public class BuyWindow extends Window {
	public BuyWindow(Player player, Property property, Screen screen, AudioManager audioManager, GameManager gameManager, Font font) {
		super("buy");
		Button buyButton = new Button("buy-2",405,143);
		Button dontBuyButton = new Button("dont-buy-2",405,351);
		property.setLocation(42,47);
		setBounds(576,243,768,594);
		add(buyButton,new Integer(1));
		add(dontBuyButton,new Integer(1));
		add(property,new Integer(1));
		buyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(player.getCash()>=property.getPrice()) {
					audioManager.playSound("buy");
					player.buyProperty(property);
					screen.removeFromGlassPane(BuyWindow.this);
				}else
					screen.addToGlassPane(new MessageWindow("YOU DO NOT HAVE ENOUGH CASH",screen,audioManager,font));
			}
		});
		dontBuyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				screen.removeFromGlassPane(BuyWindow.this);
			}
		});
	}
}
