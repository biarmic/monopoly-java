package gamestuff;
import java.util.ArrayList;

public class Hand {
	private ArrayList<Property> list = new ArrayList<Property>();
	private int[] indexes = new int[10];
	public ArrayList<Property> getList() {
		return list;
	}
	public void add(Property property) {
		int index = property.getType().indexOf();
		list.add(indexes[index],property);
		for(int i = index; i < 10; i++)
			indexes[i]++;
	}
	public void remove(Property property) {
		int index = property.getType().indexOf();
		list.remove(property);
		for(int i = index; i < 10; i++)
			indexes[i]--;
	}
	public ArrayList<Property> getSameTypes(Property search) {
		ArrayList<Property> same = new ArrayList<Property>();
		for(Property prop : list) {
			if(search.getType()==prop.getType())
				same.add(prop);
		}
		return same;
	}
}
