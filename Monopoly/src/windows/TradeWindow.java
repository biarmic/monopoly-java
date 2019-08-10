package windows;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import gamestuff.Card;
import gamestuff.OfferItem;
import gamestuff.Player;
import gamestuff.PlayerColor;
import gamestuff.Property;
import managers.AudioManager;
import managers.GameManager;

public class TradeWindow extends Window {
	private static Screen screen;
	private static AudioManager audioManager;
	private static GameManager gameManager;
	private static Font font;
	private static Player[] players;
	private OfferWindow offer1;
	private OfferWindow offer2;
	private OfferableWindow offerable1;
	private OfferableWindow offerable2;
	public TradeWindow(Player[] players, Screen screen, AudioManager audioManager, GameManager gameManager, Font font) {
		super("trade-main");
		TradeWindow.screen = screen;
		TradeWindow.audioManager = audioManager;
		TradeWindow.gameManager = gameManager;
		TradeWindow.font = font;
		TradeWindow.players = players;
		showColors();
	}
	private void showColors() {
		for(Component comp : getComponentsInLayer(1))
			remove(comp);
		for(int i = 0; i < 4; i++) {
			if(!players[i].isHuman()) {
				Button button = null;
				if(players[i].getColor()==PlayerColor.red) {
					button = new Button("red-player",365+335*getComponentsInLayer(2).length,358);
					button.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent event) {
							audioManager.playSound("click");
							chooseColor(PlayerColor.red);
						}
					});
				}else if(players[i].getColor()==PlayerColor.blue) {
					button = new Button("blue-player",365+335*getComponentsInLayer(2).length,358);
					button.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent event) {
							audioManager.playSound("click");
							chooseColor(PlayerColor.blue);
						}
					});
				}else if(players[i].getColor()==PlayerColor.green) {
					button = new Button("green-player",365+335*getComponentsInLayer(2).length,358);
					button.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent event) {
							audioManager.playSound("click");
							chooseColor(PlayerColor.green);
						}
					});
				}else if(players[i].getColor()==PlayerColor.yellow) {
					button = new Button("yellow-player",365+335*getComponentsInLayer(2).length,358);
					button.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent event) {
							audioManager.playSound("click");
							chooseColor(PlayerColor.yellow);
						}
					});
				}
				add(button,new Integer(2));
			}
			repaint();
		}
		JLabel background = new JLabel();
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/backgrounds/trade-color.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setBounds(0,0,1536,940);
		add(background,new Integer(1));
	}
	private void chooseColor(PlayerColor color) {
		for(Component comp : getComponentsInLayer(1))
			remove(comp);
		for(Component comp : getComponentsInLayer(2))
			remove(comp);
		offer1 = new OfferWindow(382,62,this,font);
		offer2 = new OfferWindow(766,62,this,font);
		offerable1 = new OfferableWindow(4,62,gameManager.getHumanPlayer(),screen,audioManager,this,font);
		offerable2 = new OfferableWindow(1154,62,gameManager.getPlayer(color),screen,audioManager,this,font);
		Button offer = new Button("offer",235,826);
		Button close = new Button("close-2",1001,826);
		Button players = new Button("players",618,826);
		offer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				offer();
			}
		});
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				screen.removeFromGlassPane(TradeWindow.this);
			}
		});
		players.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				audioManager.playSound("click");
				showColors();
			}
		});
		add(offer1,new Integer(1));
		add(offer2,new Integer(1));
		add(offerable1,new Integer(1));
		add(offerable2,new Integer(1));
		add(offer,new Integer(1));
		add(close,new Integer(1));
		add(players,new Integer(1));
		repaint();
	}
	public void addToOfferWindow(Property property, boolean isHuman) {
		if(isHuman)
			offer1.add(property);
		else
			offer2.add(property);
		repaint();
	}
	public void addToOfferWindow(int cash, boolean isHuman) {
		if(isHuman)
			offer1.add(cash);
		else
			offer2.add(cash);
		repaint();
	}
	public void addToOfferWindow(Card card, boolean isHuman) {
		if(isHuman)
			offer1.add(card);
		else
			offer2.add(card);
		repaint();
	}
	public void removeFromOfferWindow(Property property, boolean isHuman) {
		if(isHuman)
			offer1.remove(property);
		else
			offer2.remove(property);
	}
	public void showCashWindow(Player player) {
		screen.addToGlassPane(new CashWindow(player,screen,audioManager,this,font));
	}
	public void removeOfferItem(boolean isCash, boolean isHuman) {
		if(isHuman)
			offer1.remove(isCash);
		else
			offer2.remove(isCash);
	}
	public void offer() {
		if(offerable2.getPlayer().doesAccept(offer2.getComponents(),offer1.getComponents())) {
			exchangeItems();
			screen.removeFromGlassPane(this);
			screen.addToGlassPane(new MessageWindow("YOUR TRADE OFFER HAS BEEN ACCEPTED",screen,audioManager,font));
		}else
			screen.addToGlassPane(new MessageWindow("YOUR TRADE OFFER HAS BEEN REJECTED",screen,audioManager,font));
	}
	private void exchangeItems() {
		Player human = offerable1.getPlayer();
		Player computer = offerable2.getPlayer();
		for(Component comp : offer2.getComponents()) {
			if(comp instanceof Property) {
				human.tradeProperty((Property)comp);
				computer.removeProperty((Property)comp);
			}else if(comp instanceof OfferItem && ((OfferItem)comp).getCard()!=null) {
				human.addJailCard(((OfferItem)comp).getCard());
				computer.removeJailCard(((OfferItem)comp).getCard());
			}else if(comp instanceof OfferItem) {
				human.transaction(((OfferItem)comp).getAmount());
				computer.transaction(-((OfferItem)comp).getAmount());
			}
		}
		for(Component comp : offer1.getComponents()) {
			if(comp instanceof Property) {
				computer.tradeProperty((Property)comp);
				human.removeProperty((Property)comp);
			}else if(comp instanceof OfferItem && ((OfferItem)comp).getCard()!=null) {
				computer.addJailCard(((OfferItem)comp).getCard());
				human.removeJailCard(((OfferItem)comp).getCard());
			}else if(comp instanceof OfferItem) {
				computer.transaction(((OfferItem)comp).getAmount());
				human.transaction(-((OfferItem)comp).getAmount());
			}
		}
	}
	public void untick(boolean isHuman) {
		if(isHuman)
			offerable1.untick();
		else
			offerable2.untick();
	}
}
