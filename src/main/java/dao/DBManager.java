package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	public static Connection getConnection() {
		
		String jdbcDriver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/s3a1_system_dev";
		String user = "root";
		String password = "password";
		
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした。");
		}
		
		Connection conn;
		
		try{
			conn = DriverManager.getConnection(url, user, password);
				
			
		} catch (SQLException e) {
			throw new IllegalStateException("sqlにアクセスできませんでした。");
		}
		
		return conn;
	}
}