package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import config.DBConfig;

public class DBConnection {
	
	
	
	public static Connection getConnection() {
		DBConfig db=new DBConfig();
		Connection con=null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection(db.getUrl(),db.getUsername(),db.getPassword());
//		System.out.println("Connection Establish Successfully...");
		}catch(SQLException e) {
			System.out.println(e);
		}catch(ClassNotFoundException e) {
			System.out.println(e);
		}
		return con;
	}

}
