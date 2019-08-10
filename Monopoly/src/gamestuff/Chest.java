package gamestuff;
import java.util.ArrayList;

public class Chest extends Card {
	private static ArrayList<Chest> list;
	public Chest(int id, ArrayList<Chest> list) {
		super(id,"chest");
		Chest.list = list;
	}
	@Override
	public void performAction(Player player) {
		switch(getId()) {
		case 0:
			player.advanceTo(0,0);
			break;
		case 1:
			player.transaction(200);
			break;
		case 2:
			player.transaction(-50);
			break;
		case 3:
			player.transaction(50);
			break;
		case 4:
			player.addJailCard(this);
			break;
		case 5:
			player.advanceTo(-1,-1);
			break;
		case 6:
			player.getMoneyFromOthers(50);
			break;
		case 7:
			player.transaction(100);
			break;
		case 8:
			player.transaction(20);
			break;
		case 9:
			player.transaction(100);
			break;
		case 10:
			player.transaction(-50);
			break;
		case 11:
			player.transaction(-50);
			break;
		case 12:
			player.transaction(25);
			break;
		case 13:
			player.repair(40,115);
			break;
		case 14:
			player.transaction(10);
			break;
		default:
			player.transaction(100);
		}
		if(getId()!=4)
			list.add(this);
	}
}
