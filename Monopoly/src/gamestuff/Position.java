package gamestuff;

public class Position {
	private int line;
	private int tile;
	private TileType tileType;
	private PropertyType propertyType;
	private int order;
	private int x;
	private int y;
	public Position(int line, int tile, int order) {
		this.order = order;
		setPosition(line,tile);
		adjustLocation();
	}
	public int getLine() {
		return line;
	}
	public int getTile() {
		return tile;
	}
	public int getOrder() {
		return order;
	}
	public TileType getTileType() {
		return tileType;
	}
	public PropertyType getPropertyType() {
		return propertyType;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setPosition(int line, int tile) {
		this.line = line;
		this.tile = tile;
		adjustLocation();
	}
	public void move(int number) {
		if(tile+number>9) {
			tile += number-10;
			line = (line+1)%4;
		}else if(tile+number<0) {
			tile = 9;
			line = 1;
		}else
			tile += number;
		tileType = TileType.findType(line,tile);
		if(tileType==TileType.property)
			propertyType = PropertyType.findType(line,tile);
		else
			propertyType = null;
		adjustLocation();
	}
	public void goToJail() {
		line = -1;
		tile = -1;
		tileType = TileType.jail;
		adjustLocation();
	}
	public void getOutJail() {
		line = 1;
		tile = 0;
		tileType = TileType.empty;
		adjustLocation();
	}
	private void adjustLocation() {
		if(line==-1) {
			x = 237-order%2*44+(order/2)*44;
			y = 395+order%2*26+(order/2)*27;
		}else if(line==0) {
			if(tile==0) {
				x = 930-order%2*44+(order/2)*53;
				y = 830+order%2*26+(order/2)*30;
			}else {
				x = 909-tile*68-order*18;
				y = 829-tile*39+order*11;
			}
		}else if(line==1){
			if(tile==0) {
				if(order<2) {
					x = 185-order%2*44;
					y = 370+order%2*26;
				}else {
					x = 150+order%2*44;
					y = 450+order%2*26;
				}
			}else {
				x = 245+tile*68-order%2*24-(order/2)*44;
				y = 400-tile*39+order%2*14-(order/2)*25;
			}
		}else if(line==2) {
			if(tile==0) {
				x = 930+order%2*44-(order/2)*53;
				y = 30-order%2*26-(order/2)*30;
			}else {
				x = 950+tile*68+order*18;
				y = 18+tile*39-order*11;
			}
		}else {
			if(tile==0) {
				x = 1630+order%2*44+(order/2)*53;
				y = 430-order%2*26+(order/2)*30;
			}else {
				x = 1637-tile*68+order%2*24+(order/2)*44;
				y = 449+tile*39-order%2*14+(order/2)*25;
			}
		}
	}
}
