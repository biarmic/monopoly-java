package gamestuff;

public enum TileType {
	property, chance, chest, tax, empty, jail, police;
	public static TileType findType(int line, int tile) {
		if(line==-1)
			return jail;
		if((line==0 && tile==7) || (line==2 && tile==2) || (line==3 && tile==6))
			return chance;
		if((line==0 && tile==2) || (line==1 && tile==7) || (line==3 && tile==3))
			return chest;
		if((line==0 && tile==4) || (line==3 && tile==8))
			return tax;
		if(line==3 && tile==0)
			return police;
		if(tile==0)
			return empty;
		return property;
	}
}
