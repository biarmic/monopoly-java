package gamestuff;

public enum PropertyType {
	brown, lightblue, pink, orange, red, yellow, green, darkblue, railroad, utility;
	public static PropertyType findType(int line, int tile) {
		if(line==0 && (tile==1 || tile==3))
			return brown;
		if(line==0 && (tile==6 || tile==8 || tile==9))
			return lightblue;
		if(line==1 && (tile==1 || tile==3 || tile==4))
			return pink;
		if(line==1 && (tile==6 || tile==8 || tile==9))
			return orange;
		if(line==2 && (tile==1 || tile==3 || tile==4))
			return red;
		if(line==2 && (tile==6 ||tile==7 || tile==9))
			return yellow;
		if(line==3 && (tile==1 || tile==2 || tile==4))
			return green;
		if(line==3 && (tile==7 || tile==9))
			return darkblue;
		if(tile==5)
			return railroad;
		return utility;
	}
	public int indexOf() {
		for(int i = 0; i < 10; i++)
			if(this==values()[i])
				return i;
		return -1;
	}
}
