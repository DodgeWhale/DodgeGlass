package net.dodgewhale.glass.sql;

public enum Table {

	PLAYER_DATA;
	
	public String getName() {
		return this.toString().toLowerCase();
	}
	
}