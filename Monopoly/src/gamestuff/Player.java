package gamestuff;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import managers.AudioManager;
import managers.GameManager;
import windows.GameScene;
import windows.PlayerWindow;

public class Player extends JLabel {
	private static AudioManager audioManager;
	private static GameManager gameManager;
	private static GameScene gameScene;
	private static Player[] players;
	private PlayerColor color;
	private Position position;
	private int cash = 1500;
	private Hand hand = new Hand();
	private boolean isHuman = false;
	private int reroll = 0;
	private int waitingInJail = 0;
	private PlayerWindow playerWindow;
	private ArrayList<Card> jailCards = new ArrayList<Card>();
	public Player(PlayerColor color, AudioManager audioManager, GameManager gameManager, GameScene gameScene, Player[] players) {
		Player.audioManager = audioManager;
		Player.gameManager = gameManager;
		Player.gameScene = gameScene;
		Player.players = players;
		this.color = color;
		position = new Position(0,0,color.indexOf());
		try {
			setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/pieces/"+color.toString()+"-player.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setBounds(position.getX(),position.getY(),48,118);
	}
	public GameManager getGameManager() {
		return gameManager;
	}
	public GameScene getGameScene() {
		return gameScene;
	}
	public PlayerColor getColor() {
		return color;
	}
	public Position getPosition() {
		return position;
	}
	public int getCash() {
		return cash;
	}
	public int getReroll() {
		return reroll;
	}
	public ArrayList<Card> getJailCards() {
		return jailCards;
	}
	public ArrayList<Property> getPropertyList() {
		return hand.getList();
	}
	public boolean isHuman() {
		return isHuman;
	}
	public ArrayList<Property> getSameTypes(Property search) {
		return hand.getSameTypes(search);
	}
	public void setHumanPlayer() {
		isHuman = true;
	}
	public void setPlayerWindow(PlayerWindow playerWindow) {
		this.playerWindow = playerWindow;
	}
	public void addProperty(Property property) {
		hand.add(property);
		playerWindow.getPropertyWindow().updateList();
	}
	public void transaction(int amount) {
		if(amount>0)
			audioManager.playSound("coin");
		cash += amount;
		playerWindow.updateCash();
		gameScene.getMenuWindow().updateMenu();
	}
	public void sendMoneyToOthers(int amount) {
		for(Player player : players) {
			if(player!=this) {
				transaction(-amount);
				player.transaction(amount);
			}
		}
	}
	public void getMoneyFromOthers(int amount) {
		for(Player player : players) {
			if(player!=this) {
				transaction(amount);
				player.transaction(-amount);
			}
		}
	}
	public void buyProperty(Property prop) {
		transaction(-prop.getPrice());
		hand.add(prop);
		prop.setOwner(this);
		for(Property property : hand.getList())
			if(property instanceof Land && ((Land) property).getType()==prop.getType())
				((Land) property).updateRent(hand.getSameTypes(prop).size());
	}
	public void tradeProperty(Property prop) {
		hand.add(prop);
		prop.setOwner(this);
		for(Property property : hand.getList())
			if(property instanceof Land && ((Land) property).getType()==prop.getType())
				((Land) property).updateRent(hand.getSameTypes(prop).size());
	}
	public void removeProperty(Property prop) {
		hand.remove(prop);
		for(Property property : hand.getList())
			if(property instanceof Land && ((Land) property).getType()==prop.getType())
				((Land) property).updateRent(hand.getSameTypes(prop).size());
	}
	public void addJailCard(Card card) {
		jailCards.add(card);
	}
	public void removeJailCard(Card card) {
		jailCards.remove(card);
	}
	public void move(int number, boolean isDouble) {
		gameScene.getMenuWindow().removeGetOut();
		if(reroll==2 && isDouble) {
			advanceTo(-1,-1);
			reroll = 0;
			gameScene.getMenuWindow().removeReroll();
			return;
		}
		if(position.getTileType()==TileType.jail) {
			gameScene.getMenuWindow().addGetOut();
			gameScene.getMenuWindow().removeReroll();
			if(isDouble)
				getOutJail(number);
			else {
				waitingInJail++;
				if(waitingInJail==3) {
					transaction(-50);
					getOutJail(number);
				}
			}
		}else {
			if(isDouble) {
				reroll++;
				gameScene.getMenuWindow().addReroll();
			}else {
				reroll = 0;
				gameScene.getMenuWindow().removeReroll();
			}
			for(int i = 0; i < Math.abs(number); i++) {
				position.move(1*(int)Math.signum(number));
				moveAnimation();
				if(position.getLine()==0 && position.getTile()==0) {
					transaction(200);
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			performAction(1);
		}
	}
	public void move(int number) {
		for(int i = 0; i < Math.abs(number); i++) {
			position.move(1*(int)Math.signum(number));
			moveAnimation();
			if(position.getLine()==0 && position.getTile()==0)
				transaction(200);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		performAction(1);
	}
	private void moveAnimation() {
		audioManager.playSound("move");
		updateLayer();
		int x = position.getX();
		int y = position.getY();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				int currentX = getX();
				int currentY = getY();
				int xDiff = x - currentX;
				int yDiff = y - currentY;
				int hypotenuse = (int) Math.hypot(xDiff,yDiff);
				if(hypotenuse>=10) {
					int xDist = 10 * xDiff / hypotenuse;
					int yDist = 10 * yDiff / hypotenuse;
					Player.this.setLocation(currentX+xDist,currentY+yDist);
				}else {
					Player.this.setLocation(x,y);
					timer.cancel();
				}
			}
		};
		timer.schedule(task,5,5);
	}
	private void updateLayer() {
		int layer = 0;
		if(position.getLine()==0) {
			if(position.getTile()==0) 
				layer = 600+position.getOrder();
			else
				layer = 600-position.getTile()*4+position.getOrder();
		}else if(position.getLine()==1) {
			if(position.getTile()==0)
				layer = 300+position.getOrder()-position.getOrder()%2;
			else
				layer = 300-position.getTile()*4+position.getOrder()%2-position.getOrder()/2;
		}else if(position.getLine()==2) {
			if(position.getTile()==0)
				layer = 400-position.getOrder();
			else
				layer = 400+position.getTile()*4-position.getOrder();
		}else if(position.getLine()==-1)
			layer = 500+position.getOrder();
		else
			layer = 500+position.getTile()*4+position.getOrder();
		gameScene.setLayer(this,new Integer(layer));
	}
	public void getOutJail(int number) {
		waitingInJail = 0;
		reroll = 0;
		gameScene.getMenuWindow().removeGetOut();
		position.getOutJail();
		moveAnimation();
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		move(number);
	}
	public void advanceTo(int line, int tile) {
		if(line==-1) {
			reroll = 0;
			gameScene.getMenuWindow().removeReroll();
			position.goToJail();
			moveAnimation();
		}else {
			while(position.getTile()!=tile || position.getLine()!=line) {
				position.move(1);
				moveAnimation();
				if(position.getLine()==0 && position.getTile()==0)
					transaction(200);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void advanceTo(PropertyType type, int multiplier) {
		while(position.getPropertyType()!=type) {
			position.move(1);
			moveAnimation();
			if(position.getLine()==0 && position.getTile()==0)
				transaction(200);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		performAction(multiplier);
	}
	public void repair(int house, int hotel) {
		int amount = 0;
		for(Property prop : hand.getList()) {
			if(prop instanceof Land) {
				if(((Land) prop).getBuildingsNumber()<5)
					amount += ((Land) prop).getBuildingsNumber()*house;
				else
					amount += hotel;
			}
		}
		transaction(-amount);
	}
	public void drawCard(boolean isChance) {
		Card card = isChance ? gameManager.getChanceList().remove(0) : gameManager.getChestList().remove(0);
		gameScene.add(card,new Integer(isChance ? 600 : 601));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		card.performAction(this);
		gameScene.remove(card);
		gameScene.repaint();
	}
	public void performAction(int multiplier) {
		if(position.getTileType()==TileType.chance)
			drawCard(true);
		if(position.getTileType()==TileType.chest)
			drawCard(false);
		if(position.getTileType()==TileType.tax) {
			if(position.getLine()==0)
				transaction(-200);
			else
				transaction(-150);
		}
		if(position.getTileType()==TileType.police)
			advanceTo(-1,-1);
		if(position.getTileType()==TileType.property) {
			Property prop = gameManager.findProperty(position.getLine(),position.getTile());
			if(prop.getOwner()!=null && prop.getOwner()!=this) {
				int rent = prop.calculateRent(prop.getOwner().hand.getSameTypes(prop).size())*multiplier;
				transaction(-rent);
				prop.getOwner().transaction(rent);
			}else if(prop.getOwner()==null && !isHuman)
				computerAction();
		}
		gameScene.getMenuWindow().updateMenu();
		if(multiplier!=1)
			return;
		if(!isHuman) {
			if(cash<0) {
				if(doesBankrupt())
					bankrupt();
			}
			if(position.getLine()!=-1) {
				if(reroll==0)
					gameManager.endTurn();
				else
					gameManager.rollDice();
			}else {
				if(waitingInJail==0)
					gameManager.endTurn();
				else {
					if(jailCards.size()!=0) {
						Card card = jailCards.remove(0);
						if(card instanceof Chance)
							gameManager.getChanceList().add((Chance)card);
						else
							gameManager.getChestList().add((Chest)card);
						getOutJail(gameManager.getLastDieTotal());
					}else if(cash>=50) {
						transaction(-50);
						getOutJail(gameManager.getLastDieTotal());
					}else
						gameManager.endTurn();
				}
			}
		}
	}
	private boolean doesBankrupt() {
		ArrayList<Property> props = hand.getList();
		for(int i = 0; i < props.size(); i++) {
			if(cash<0) {
				if((props.get(i) instanceof Land && ((Land)props.get(i)).canMortgage()) || !(props.get(i) instanceof Land)) {
					transaction(props.get(i).getPrice()/2);
					props.get(i).setMortgaged(true);
					i = -1;
				}else if(props.get(i) instanceof Land && ((Land)props.get(i)).canDowngrade()) {
					((Land)props.get(i)).downgrade();
					i = -1;
				}
			}else
				return false;
		}
		return true;
	}
	private void bankrupt() {
		ArrayList<Property> props = hand.getList();
		for(int i = 0; i < props.size(); i++) {
			props.get(i).reset();
			if(props.get(i) instanceof Land && ((Land)props.get(i)).canDowngrade()) {
				((Land)props.get(i)).downgrade();
				i = -1;
			}
		}
		while(props.size()!=0) {
			Property prop = props.remove(0);
			if(prop instanceof Land)
				((Land)prop).updateRent(1);
		}
		while(jailCards.size()!=0) {
			Card card = jailCards.remove(0);
			if(card instanceof Chance)
				gameManager.getChanceList().add((Chance)card);
			else
				gameManager.getChestList().add((Chest)card);
		}
		cash = 0;
		playerWindow.updateCash();
		gameScene.remove(this);
		gameScene.repaint();
	}
	public boolean doesAccept(Component[] give, Component[] take) {
		int giveValue = 0;
		for(Component comp : give) {
			if(comp instanceof Property)
				giveValue += (((Property)comp).isMortgaged() ? ((Property)comp).getPrice()/2 : ((Property)comp).getPrice());
			else if(comp instanceof OfferItem)
				giveValue += (((OfferItem)comp).getCard()!=null ? 50 : ((OfferItem)comp).getAmount());
		}
		int takeValue = 0;
		for(Component comp : take) {
			if(comp instanceof Property)
				takeValue += (((Property)comp).isMortgaged() ? ((Property)comp).getPrice()/2 : ((Property)comp).getPrice());
			else if(comp instanceof OfferItem)
				takeValue += (((OfferItem)comp).getCard()!=null ? 50 : ((OfferItem)comp).getAmount());
		}
		return takeValue>giveValue;
	}
	private void computerAction() {
		if(cash>=gameManager.findProperty(position.getLine(),position.getTile()).getPrice()) {
			audioManager.playSound("buy");
			buyProperty(gameManager.findProperty(position.getLine(),position.getTile()));
		}
		ArrayList<Property> props = hand.getList();
		for(int i = 0; i < props.size(); i++) {
			if(props.get(i).canUnmortgage()) {
				transaction(-props.get(i).getPrice()/2);
				props.get(i).setMortgaged(false);
				i = -1;
			}else if(props.get(i) instanceof Land && ((Land)props.get(i)).canUpgrade()) {
				((Land)props.get(i)).upgrade();
				i = -1;
			}
		}
	}
}
