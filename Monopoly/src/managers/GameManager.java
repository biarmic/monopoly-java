package managers;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import gamestuff.Chance;
import gamestuff.Chest;
import gamestuff.Land;
import gamestuff.Player;
import gamestuff.PlayerColor;
import gamestuff.Property;
import gamestuff.PropertyType;
import gamestuff.Railroad;
import gamestuff.TileType;
import gamestuff.Utility;
import windows.DiceWindow;
import windows.GameScene;
import windows.ManageWindow;
import windows.Screen;
import windows.TradeWindow;

public class GameManager {
	private static Screen screen;
	private static AudioManager audioManager;
	private static GameScene gameScene;
	private static Font font;
	private DiceWindow diceWindow;
	private ManageWindow manageWindow;
	private Player[] players = new Player[4];
	private int turnIndex = 0;
	private ArrayList<Property> propertyList = new ArrayList<Property>();
	private ArrayList<Chance> chanceList = new ArrayList<Chance>();
	private ArrayList<Chest> chestList = new ArrayList<Chest>();
	private int lastDieTotal = 0;
	public GameManager(Screen screen, AudioManager audioManager, GameScene gameScene, Font font) {
		GameManager.screen = screen;
		GameManager.audioManager = audioManager;
		GameManager.gameScene = gameScene;
		GameManager.font = font;
		diceWindow = new DiceWindow(audioManager,this);
		propertyList.add(new Land("MEDITERRANEAN AVENUE",60,2,10,30,90,160,250,50,PropertyType.brown,0,1,gameScene,font));
		propertyList.add(new Land("BALTIC AVENUE",60,2,10,30,90,160,250,50,PropertyType.brown,0,3,gameScene,font));
		propertyList.add(new Land("ORIENTAL AVENUE",100,6,30,90,270,400,550,50,PropertyType.lightblue,0,6,gameScene,font));
		propertyList.add(new Land("VERMONT AVENUE",100,6,30,90,270,400,550,50,PropertyType.lightblue,0,8,gameScene,font));
		propertyList.add(new Land("CONNECTICUT AVENUE",120,8,40,100,300,450,600,50,PropertyType.lightblue,0,9,gameScene,font));
		propertyList.add(new Land("ST. CHARLES PLACE",140,10,50,150,450,625,750,100,PropertyType.pink,1,1,gameScene,font));
		propertyList.add(new Land("STATES AVENUE",140,10,50,150,450,625,750,100,PropertyType.pink,1,3,gameScene,font));
		propertyList.add(new Land("VIRGINIA AVENUE",160,12,60,180,500,700,900,100,PropertyType.pink,1,4,gameScene,font));
		propertyList.add(new Land("ST. JAMES PLACE",180,14,70,200,550,750,950,100,PropertyType.orange,1,6,gameScene,font));
		propertyList.add(new Land("TENNESSEE AVENUE",180,14,70,200,550,750,950,100,PropertyType.orange,1,8,gameScene,font));
		propertyList.add(new Land("NEW YORK AVENUE",200,16,80,220,600,800,1000,100,PropertyType.orange,1,9,gameScene,font));
		propertyList.add(new Land("KENTUCKY AVENUE",220,18,90,250,700,875,1050,150,PropertyType.red,2,1,gameScene,font));
		propertyList.add(new Land("INDIANA AVENUE",220,18,90,250,700,875,1050,150,PropertyType.red,2,3,gameScene,font));
		propertyList.add(new Land("ILLINOIS AVENUE",240,20,100,300,750,925,1100,150,PropertyType.red,2,4,gameScene,font));
		propertyList.add(new Land("ATLANTIC AVENUE",260,22,110,330,800,975,1150,150,PropertyType.yellow,2,6,gameScene,font));
		propertyList.add(new Land("VENTNOR AVENUE",260,22,110,330,800,975,1150,150,PropertyType.yellow,2,7,gameScene,font));
		propertyList.add(new Land("MARVIN GARDENS",280,24,120,360,850,1025,1200,150,PropertyType.yellow,2,9,gameScene,font));
		propertyList.add(new Land("PACIFIC AVENUE",300,26,130,390,900,1100,1275,200,PropertyType.green,3,1,gameScene,font));
		propertyList.add(new Land("NORTH CAROLINA AVENUE",300,26,130,390,900,1100,1275,200,PropertyType.green,3,2,gameScene,font));
		propertyList.add(new Land("PENNSYLVANIA AVENUE",230,28,150,450,1000,1200,1400,200,PropertyType.green,3,4,gameScene,font));
		propertyList.add(new Land("PARK PLACE",350,35,175,500,1100,1300,1500,200,PropertyType.darkblue,3,7,gameScene,font));
		propertyList.add(new Land("BOARDWALK",400,50,200,600,1400,1700,2000,200,PropertyType.darkblue,3,9,gameScene,font));
		propertyList.add(new Railroad("READING RAILROAD",0,5,gameScene,font));
		propertyList.add(new Railroad("PENNSYLVANIA RAILROAD",1,5,gameScene,font));
		propertyList.add(new Railroad("B. & O. RAILROAD",2,5,gameScene,font));
		propertyList.add(new Railroad("SHORT LINE",3,5,gameScene,font));
		propertyList.add(new Utility("ELECTRIC WORKS",1,2,gameScene,font));
		propertyList.add(new Utility("WATER WORKS",2,8,gameScene,font));
		for(int i = 0; i < 16; i++) {
			chanceList.add(new Chance(i,chanceList));
			chestList.add(new Chest(i,chestList));
		}
		Collections.shuffle(chanceList);
		Collections.shuffle(chestList);
	}
	public int getLastDieTotal() {
		return lastDieTotal;
	}
	public ArrayList<Chance> getChanceList() {
		return chanceList;
	}
	public ArrayList<Chest> getChestList() {
		return chestList;
	}
	public Player getTurnPlayer() {
		return players[turnIndex];
	}
	public Player getHumanPlayer() {
		for(Player player : players)
			if(player.isHuman())
				return player;
		return null;
	}
	public Player getPlayer(PlayerColor color) {
		for(Player player : players)
			if(player.getColor()==color)
				return player;
		return null;
	}
	public void receiveColor(Player[] players, PlayerColor humanColor) {
		this.players = players;
		for(Player player : players) {
			if(player.getColor()==humanColor) {
				manageWindow = new ManageWindow(player,screen,audioManager,font);
				player.setHumanPlayer();
			}
		}
		rollDice();
 	}
	public void receiveDiceNumber(int dice1, int dice2) {
		screen.removeFromGlassPane(diceWindow);
		lastDieTotal = dice1+dice2;
		players[turnIndex].move(dice1+dice2,dice1==dice2);
	}
	public void rollDice() {
		screen.addToGlassPane(diceWindow);
		diceWindow.roll();
	}
	public Property findProperty(int line, int tile) {
		for(Property prop : propertyList)
			if(tile==prop.getTile() && line==prop.getLine())
				return prop;
		return null;
	}
	public void endTurn() {
		turnIndex = (turnIndex+1)%4;
		rollDice();
	}
	public void showManageWindow() {
		manageWindow.updateList();
		screen.addToGlassPane(manageWindow);
	}
	public void showBuyWindow() {
		TileType type = players[turnIndex].getPosition().getTileType();
		if(type==TileType.property) {
			Property prop = findProperty(players[turnIndex].getPosition().getLine(),players[turnIndex].getPosition().getTile());
			if(prop.getOwner()==null)
				gameScene.showBuyWindow(players[turnIndex],prop);
		}
	}
	public void showTradeWindow() {
		screen.addToGlassPane(new TradeWindow(players,screen,audioManager,this,font));
	}
}
