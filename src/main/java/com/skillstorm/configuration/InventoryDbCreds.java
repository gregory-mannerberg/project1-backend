package com.skillstorm.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class InventoryDbCreds {

	private static InventoryDbCreds instance;
	private String url;
	private String username;
	private String password;
	
	private InventoryDbCreds() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (InputStream input = InventoryDbCreds.class.getClassLoader()
					.getResourceAsStream("application.properties")) {
				Properties properties = new Properties();
				properties.load(input);
				this.url = properties.getProperty("db.url");
				this.username = properties.getProperty("db.username");
				this.password = properties.getProperty("db.password");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static InventoryDbCreds getInstance() {
		if (instance == null) {
			instance = new InventoryDbCreds();
		}
		return instance;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(getUrl(), getUsername(), getPassword());
	}
	
}
