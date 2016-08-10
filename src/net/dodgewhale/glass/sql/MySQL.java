package net.dodgewhale.glass.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.plugin.Plugin;

public class MySQL extends Database {
	
	private final String USER, DATABASE, PASSWORD, HOSTNAME, PORT;
	private Connection connection;

	/**
	 * Creates a new MySQL instance
	 */
	public MySQL(Plugin plugin, String hostname, String port, String database, String username, String password) {
		super(plugin);
		
		this.HOSTNAME = hostname;
		this.PORT = port;
		this.DATABASE = database;
		this.USER = username;
		this.PASSWORD = password;
		this.connection = null;
	}

	@Override
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		if (this.isOpen()) {
			return this.getConnection();
		}
		
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection("jdbc:mysql://" + this.HOSTNAME + ":" + this.PORT + "/" + this.DATABASE, this.USER, this.PASSWORD);
		return this.getConnection();
	}

	@Override
	public boolean isOpen() throws SQLException {
		return this.getConnection() != null && !this.getConnection().isClosed();
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public boolean closeConnection() throws SQLException {
		if (this.getConnection() == null || this.getConnection().isClosed()) {
			return false;
		}
		
		this.getConnection().close();
		return true;
	}

	@Override
	public ResultSet query(String query) throws SQLException, ClassNotFoundException {
		if (this.isOpen()) {
			this.openConnection();
		}
		
		Statement statement = this.getConnection().createStatement();
		return statement.executeQuery(query);
	}

	@Override
	public int update(String query) throws SQLException, ClassNotFoundException {
		if (!this.isOpen()) {
			this.openConnection();
		}

		Statement statement = this.getConnection().createStatement(); 
		return statement.executeUpdate(query);
	}

}