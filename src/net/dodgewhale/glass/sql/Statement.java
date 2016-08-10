package net.dodgewhale.glass.sql;

public enum Statement {

	INSERT_INTO, UPDATE;
	
	public String format() {
		return this.toString().replaceAll("_", " ");
	}
	
}