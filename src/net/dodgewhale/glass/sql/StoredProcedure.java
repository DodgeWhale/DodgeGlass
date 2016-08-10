package net.dodgewhale.glass.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class StoredProcedure {

	protected Statement statement;
	protected Table table;
	protected HashMap<String, Object> parameters;
	
	protected StoredProcedure(Statement statement, Table table, HashMap<String, Object> parameters) {
		this.statement = statement;
		this.table = table;
		this.parameters = parameters;
	}
	
	public void execute(MySQL sql) {
		try {
			PreparedStatement query = null;
			
			switch(this.statement) {
			case INSERT_INTO: {
				String columns = "", values = "";
				
				int i = 1, size = this.parameters.size();
				for(Entry<String, Object> entry : this.parameters.entrySet()) {
					columns += entry.getKey() + (i == size ? "" : ", ");
					values += entry.getKey() + (i == size ? "" : ", ");
				}
				
				query = sql.getConnection().prepareStatement(this.statement.format() + " " + this.table.getName() + " (" + columns + ") VALUES (" + values + ");");
			}
			default:
				break;
			}
			
			query.executeUpdate();
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
