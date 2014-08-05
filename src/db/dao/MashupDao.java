package db.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class MashupDao {
	private Connection con = null;
	private static final String TABLE = "accountinfo";
	private static final String USERNAME="username";
	private static final String PASSWORD="password";
	private static final String AUTHTOKEN="authToken";

	public MashupDao() {
		con = this.getConnection();
	}

	public Connection getConnection() {
		try {
			String driverClass = "com.mysql.jdbc.Driver";
			String user = "root";
			String url = "jdbc:mysql://localhost:3306/mashupdb";
			String password = "sdp123";
			Class.forName(driverClass);
			DriverManager.setLoginTimeout(3000);
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void saveUser(String username, String password) {
		try {
			String sql="insert into "+TABLE+"("+USERNAME+","+PASSWORD+","+AUTHTOKEN+") values(?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, 4);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
