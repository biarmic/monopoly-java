package gamestuff;
import java.util.ArrayList;

public class Chance extends Card {
	private static ArrayList<Chance> list;
	public Chance(int id, ArrayList<Chance> list) {
		super(id,"chance");
		Chance.list = list;
	}
	@Override
	public void performAction(Player player) {
		switch(getId()) {
		case 0:
			player.advanceTo(0,0);
			break;
		case 1:
			player.advanceTo(2,4);
			break;
		case 2:
			player.advanceTo(1,1);
			break;
		case 3:
			player.advanceTo(PropertyType.utility,10);
			break;
		case 4:
			player.advanceTo(PropertyType.railroad,2);
			break;
		case 5:
			player.transaction(50);
			break;
		case 6:
			player.addJailCard(this);
			break;
		case 7:
			player.move(-3);
			break;
		case 8:
			player.advanceTo(-1,-1);
			break;
		case 9:
			player.repair(25,100);
			break;
		case 10:
			player.transaction(-15);
			break;
		case 11:
			player.advanceTo(0,5);
			break;
		case 12:
			player.advanceTo(3,9);
			break;
		case 13:
			player.sendMoneyToOthers(50);
			break;
		case 14:
			player.transaction(150);
			break;
		default:
			player.transaction(100);
		}
		if(getId()!=6)
			list.add(this);
	}
}
