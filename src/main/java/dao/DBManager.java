package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
	public static Connection getConnection() {
		
		String jdbcDriver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした。");
		}
		
		return "jdbc:mysql://localhost:3306/あなたのデータベース名"
	}
}
