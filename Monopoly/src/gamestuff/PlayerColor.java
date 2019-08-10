package gamestuff;

public enum PlayerColor {
	red, blue, green, yellow;
	public int indexOf() {
		for(int i = 0; i < 4; i++)
			if(this==values()[i])
				return i;
		return -1;
	}
}
