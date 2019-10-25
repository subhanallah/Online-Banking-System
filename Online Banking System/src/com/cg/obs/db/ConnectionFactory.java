package com.cg.obs.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	private static ConnectionFactory connectionFactory = null;
	private static Properties properties = new Properties();
	private String url;
	private String username;
	private String password;
	private String dbDriver;
	
	public ConnectionFactory() {
		try {
			try (InputStream inputStream = new FileInputStream(".//resources//jdbc.properties");){
				properties.load(inputStream);
				dbDriver = properties.getProperty("db.driver");
				Class.forName(dbDriver);
				}catch(IOException e) {
					e.printStackTrace();
				}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static ConnectionFactory getInstance() {
		if(connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
	public Connection getConnection() throws SQLException{
		Connection conn = null;
		url = properties.getProperty("db.url");
		username = properties.getProperty("db.username");
		password = properties.getProperty("db.password");
		conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
}
