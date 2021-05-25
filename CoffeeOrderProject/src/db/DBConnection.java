package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static void initConnection() {

		try {
			// 클래스가 있는지 확인하는 것 
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loading Success!");
			// 따로 연결하지 않으면 예외뜸

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	// Connect가 되었을 때 정보를 가지고 있는 클래스 생성
	public static Connection getConnection() {
		Connection conn = null;
		try {

			conn = DriverManager.getConnection("jdbc:mysql://localhost/javadb?serverTimezone=UTC","javabook","961102");
			System.out.println("DB Connection Success!");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;

	}
	
	
	
}
