package training.adv.bowling.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	
	private static final String url = "jdbc:h2:mem:test";
	private static final String user = "sa";
	private static final String pwd = "sa";
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(url, user, pwd);
			connection.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	
}
