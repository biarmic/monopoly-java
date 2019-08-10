package gamestuff;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import windows.GameScene;

public class Land extends Property {
	private static Font font;
	private JLabel currentRent;
	private int rent;
	private int[] rentWithHouse = new int[4];
	private int rentWithHotel;
	private int houseCost;
	private ArrayList<Building> buildings = new ArrayList<Building>();
	public Land(String name, int price, int rent, int house1, int house2, int house3, int house4, int hotel, int houseCost, PropertyType color, int line, int tile, GameScene gameScene, Font font) {
		super(color,name,price,line,tile,gameScene);
		this.rent = rent;
		rentWithHouse[0] = house1;
		rentWithHouse[1] = house2;
		rentWithHouse[2] = house3;
		rentWithHouse[3] = house4;
		rentWithHotel = hotel;
		this.houseCost = houseCost;
		JLabel background = new JLabel();
		JLabel nameLabel = new JLabel("<html><div align=\"center\">"+name+"</div></html>",JLabel.CENTER);
		JLabel rentLabel = new JLabel(""+rent,JLabel.RIGHT);
		JLabel rentMonopolyLabel = new JLabel(""+rent*2,JLabel.RIGHT);
		JLabel[] rentWithHouseLabel = new JLabel[4];
		rentWithHouseLabel[0] = new JLabel(""+house1,JLabel.RIGHT);
		rentWithHouseLabel[1] = new JLabel(""+house2,JLabel.RIGHT);
		rentWithHouseLabel[2] = new JLabel(""+house3,JLabel.RIGHT);
		rentWithHouseLabel[3] = new JLabel(""+house4,JLabel.RIGHT);
		JLabel rentWithHotelLabel = new JLabel(""+hotel,JLabel.RIGHT);
		JLabel houseCostLabel = new JLabel(""+houseCost,JLabel.RIGHT);
		JLabel hotelCostLabel = new JLabel(""+houseCost*5,JLabel.RIGHT);
		currentRent = new JLabel(""+rent,JLabel.RIGHT);
		Land.font = new Font(font.getName(),Font.BOLD,15);
		try {
			background.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/properties/"+color+"-property.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setBounds(0,0,300,500);
		nameLabel.setBounds(27,25,250,100);
		currentRent.setBounds(238,144,36,15);
		rentLabel.setBounds(238,191,36,15);
		rentMonopolyLabel.setBounds(238,223,36,15);
		rentWithHouseLabel[0].setBounds(238,256,36,15);
		rentWithHouseLabel[1].setBounds(238,291,36,15);
		rentWithHouseLabel[2].setBounds(238,323,36,15);
		rentWithHouseLabel[3].setBounds(238,357,36,15);
		rentWithHotelLabel.setBounds(238,393,36,15);
		houseCostLabel.setBounds(238,440,36,15);
		hotelCostLabel.setBounds(238,472,36,15);
		nameLabel.setFont(new Font(font.getName(),Font.PLAIN,27));
		currentRent.setFont(Land.font);
		rentLabel.setFont(Land.font);
		rentMonopolyLabel.setFont(Land.font);
		rentWithHouseLabel[0].setFont(Land.font);
		rentWithHouseLabel[1].setFont(Land.font);
		rentWithHouseLabel[2].setFont(Land.font);
		rentWithHouseLabel[3].setFont(Land.font);
		rentWithHotelLabel.setFont(Land.font);
		houseCostLabel.setFont(Land.font);
		hotelCostLabel.setFont(Land.font);
		nameLabel.setForeground(Color.white);
		currentRent.setForeground(Color.black);
		rentLabel.setForeground(Color.black);
		rentMonopolyLabel.setForeground(Color.black);
		rentWithHouseLabel[0].setForeground(Color.black);
		rentWithHouseLabel[1].setForeground(Color.black);
		rentWithHouseLabel[2].setForeground(Color.black);
		rentWithHouseLabel[3].setForeground(Color.black);
		rentWithHotelLabel.setForeground(Color.black);
		houseCostLabel.setForeground(Color.black);
		hotelCostLabel.setForeground(Color.black);
		add(background,JLayeredPane.DEFAULT_LAYER);
		add(nameLabel,new Integer(1));
		add(currentRent,new Integer(1));
		add(rentLabel,new Integer(1));
		add(rentMonopolyLabel,new Integer(1));
		add(rentWithHouseLabel[0],new Integer(1));
		add(rentWithHouseLabel[1],new Integer(1));
		add(rentWithHouseLabel[2],new Integer(1));
		add(rentWithHouseLabel[3],new Integer(1));
		add(rentWithHotelLabel,new Integer(1));
		add(houseCostLabel,new Integer(1));
		add(hotelCostLabel,new Integer(1));
		setBounds(500,0,300,500);
	}
	public int getHousePrice() {
		return houseCost;
	}
	public int getBuildingsNumber() {
		return buildings.size();
	}
	public boolean canMortgage() {
		return buildings.size()==0;
	}
	public boolean canUpgrade() {
		ArrayList<Property> same = getOwner().getSameTypes(this);
		if(isMortgaged())
			return false;
		if(getOwner().getCash()<houseCost)
			return false;
		if((getType()==PropertyType.brown || getType()==PropertyType.darkblue) && same.size()<2)
			return false;
		if(getType()!=PropertyType.brown && getType()!=PropertyType.darkblue && same.size()<3)
			return false;
		if(buildings.size()==5)
			return false;
		for(Property prop : same)
			if(((Land) prop).buildings.size()-buildings.size()<0)
				return false;
		return true;
	}
	public void upgrade() {
		getOwner().transaction(-houseCost);
		if(buildings.size()<4) {
			int x = 0;
			int y = 0;
			int numHouses = buildings.size();
			if(getLine()==0) {
				x = 940-getTile()*68+numHouses*14;
				y = 886-getTile()*39+numHouses*8;
			}else if(getLine()==1) {
				x = 285+getTile()*68+(numHouses%2)*14-20*(numHouses/2);
				y = 514-getTile()*39+(numHouses%2)*8+12*(numHouses/2);
			}else if(getLine()==2) {
				x = 910+getTile()*68+numHouses*14;
				y = 122+getTile()*39+numHouses*8;
			}else {
				x = 1618-getTile()*68+(numHouses%2)*14-20*(numHouses/2);
				y = 501+getTile()*39+(numHouses%2)*8+12*(numHouses/2);
			}
			Building house = new House(x,y);
			buildings.add(house);
			getOwner().getGameScene().add(house,new Integer(2+numHouses+getTile()*4));
		}else {
			for(Building build : buildings)
				getOwner().getGameScene().remove(build);
			int x = 0;
			int y = 0;
			if(getLine()==0) {
				x = 961-getTile()*68;
				y = 898-getTile()*39;
			}else if(getLine()==1) {
				x = 282+getTile()*68;
				y = 525-getTile()*39;
			}else if(getLine()==2) {
				x = 932+getTile()*68;
				y = 136+getTile()*39;
			}else {
				x = 1614-getTile()*68;
				y = 513+getTile()*39;
			}
			Building hotel = new Hotel(x,y);
			buildings.add(hotel);
			getOwner().getGameScene().add(hotel,new Integer(2+getTile()*4));
		}
		updateRent(getOwner().getSameTypes(this).size());
	}
	public boolean canDowngrade() {
		if(buildings.size()==0)
			return false;
		ArrayList<Property> same = getOwner().getSameTypes(this);
		for(Property prop : same)
			if(((Land) prop).buildings.size()-buildings.size()>0)
				return false;
		return true;
	}
	public void downgrade() {
		getOwner().transaction(houseCost/2);
		if(buildings.size()==5) {
			getOwner().getGameScene().remove(buildings.remove(4));
			for(int i = 0; i < 4; i++) {
				getOwner().getGameScene().add(buildings.get(i),new Integer(2+i+getTile()*4));
			}
		}else
			getOwner().getGameScene().remove(buildings.remove(buildings.size()-1));
		updateRent(getOwner().getSameTypes(this).size());
	}
	@Override
	public int calculateRent(int numberSameType) {
		return Integer.valueOf(currentRent.getText());
	}
	public void updateRent(int numberSameType) {
		int rent = 0;
		if(!isMortgaged()) {
			if(buildings.size()==0) {
				if((getType()==PropertyType.brown || getType()==PropertyType.darkblue) && numberSameType==2)
					rent = this.rent*2;
				else if(getType()!=PropertyType.brown && getType()!=PropertyType.darkblue && numberSameType==3)
					rent = this.rent*2;
				else
					rent = this.rent;
			}else if(buildings.size()<5)
				rent = rentWithHouse[buildings.size()-1];
			else 
				rent = rentWithHotel;
		}
		currentRent.setText(""+rent);
	}
}
