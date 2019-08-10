package windows;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;
import gamestuff.Card;
import gamestuff.Land;
import gamestuff.Player;
import gamestuff.Property;
import managers.AudioManager;

public class OfferableWindow extends JPanel {
	private static Screen screen;
	private static AudioManager audioManager;
	private static Font font;
	private TradeWindow tradeWindow;
	private Player player;
	public OfferableWindow(int x, int y, Player player, Screen screen, AudioManager audioManager, TradeWindow tradeWindow, Font font) {
		super(null);
		setBackground(new Color(0,0,0,0));
		OfferableWindow.screen = screen;
		OfferableWindow.audioManager = audioManager;
		OfferableWindow.font = font;
		this.tradeWindow = tradeWindow;
		this.player = player;
		addBoxes();
		addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent event) {
				int scroll = -event.getWheelRotation()*50;
				Component[] comps = getComponents();
				if(comps.length>5 && ((scroll<0 && comps[comps.length-1].getY()+comps[comps.length-1].getHeight()>750) || (scroll>0 && comps[0].getY()<36))) {
					for(Component comp : comps) {
						comp.setLocation(comp.getX(),comp.getY()+scroll);
						tradeWindow.repaint();
					}
				}
			}
		});
		setBounds(x,y,378,750);
	}
	public Player getPlayer() {
		return player;
	}
	private void addBoxes() {
		TickBox cash = new TickBox(14,36+136*getComponents().length,"CASH",font);
		cash.addMouseListener(new MouseAdapter() {
			private boolean isAdded = false;
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				if(!isAdded) {
					tradeWindow.showCashWindow(player);
					isAdded = true;
				}else {
					tradeWindow.removeOfferItem(true,player.isHuman());
					isAdded = false;
				}
			}
		});
		add(cash);
		for(Card card : player.getJailCards()) {
			TickBox box = new TickBox(14,36+136*getComponents().length,"JAIL CARD",font);
			add(box);
			box.addMouseListener(new MouseAdapter() {
				private boolean isAdded = false;
				@Override
				public void mouseClicked(MouseEvent event) {
					audioManager.playSound("click");
					if(!isAdded) {
						tradeWindow.addToOfferWindow(card,player.isHuman());
						isAdded = true;
					}else {
						tradeWindow.removeOfferItem(false,player.isHuman());
						isAdded = false;
					}
				}
			});
		}
		for(Property prop : player.getPropertyList()) {
			TickBox box = new TickBox(14,36+136*getComponents().length,prop,font);
			add(box);
			box.addMouseListener(new MouseAdapter() {
				private boolean isAdded = false;
				@Override
				public void mouseClicked(MouseEvent event) {
					audioManager.playSound("click");
					if(!isAdded && ((prop instanceof Land && ((Land) prop).getBuildingsNumber()==0) || !(prop instanceof Land))) {
						tradeWindow.addToOfferWindow(prop,prop.getOwner().isHuman());
						isAdded = true;
					}else if(!isAdded)
						screen.addToGlassPane(new MessageWindow("YOU CANNOT ADD PROPERTY WHICH IS UPGRADED",screen,audioManager,font));
					else if(isAdded) {
						tradeWindow.removeFromOfferWindow(prop,prop.getOwner().isHuman());
						isAdded = false;
					}
				}
			});
		}
	}
	public void untick() {
		for(Component comp : getComponents())
			if(((TickBox)comp).isCash())
				((TickBox)comp).untick();
	}
}
