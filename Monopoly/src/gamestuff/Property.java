package gamestuff;
import javax.swing.JLayeredPane;

import windows.GameScene;

public abstract class Property extends JLayeredPane {
	private PropertyType type;
	private String name;
	private int price;
	private int line;
	private int tile;
	private boolean isMortgaged = false;
	private Player owner;
	private OwnerColor ownerColor;
	public Property(PropertyType type, String name, int price, int line, int tile, GameScene gameScene) {
		this.type = type;
		this.name = name;
		this.price = price;
		this.line = line;
		this.tile = tile;
		int x = 0;
		int y = 0;
		if(getLine()==0) {
			x = 793-getTile()*68;
			y = 965-getTile()*39;
		}else if(getLine()==1) {
			x = 112+getTile()*68;
			y = 433-getTile()*39;
		}else if(getLine()==2) {
			x = 1031+getTile()*68;
			y = 44+getTile()*39;
		}else {
			x = 1715-getTile()*68;
			y = 577+getTile()*39;
		}
		ownerColor = new OwnerColor(line%2,x,y);
		gameScene.add(ownerColor,new Integer(2));
	}
	public abstract int calculateRent(int numberSameType);
	public PropertyType getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public int getPrice() {
		return price;
	}
	public int getLine() {
		return line;
	}
	public int getTile() {
		return tile;
	}
	public boolean isMortgaged() {
		return isMortgaged;
	}
	public boolean canUnmortgage() {
		return owner.getCash()>=price/2 && isMortgaged;
	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
		ownerColor.changeIcon(owner!=null ? owner.getColor() : null);
	}
	public void setMortgaged(boolean isMortgaged) {
		this.isMortgaged = isMortgaged;
	}
	public void reset() {
		isMortgaged = false;
		setOwner(null);
	}
}
