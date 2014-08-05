package db.dao.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetClientMySQL{
	private String userName;
	private String password;
	private String dataBaseUrl;
	private String driver ;
	private String rootUser ;
	private String rootPassword;
	private String mysqlIp;
	private String port;

	public GetClientMySQL(String userName, String password){
		this.userName = userName;
		this.password = password;
		try {
			config();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  String getMysqlUrl(){
		String database = getUser(userName);
			try {
				Class.forName(driver);
				Connection connect = DriverManager.getConnection("jdbc:mysql://"+mysqlIp+":"+port,
						rootUser, rootPassword);
				Statement str = connect.createStatement();
				ResultSet st=str.executeQuery("select count(*) from mysql.user where User='"
						+ database+"';");
				st.next();
				if(!st.getString(1).equals("0")){
					str.close();
					connect.close();
					return "This userName is exsist!";
				}
				else{
					str.execute("create database "+database+";");
					str.execute("GRANT CREATE,DROP,UPDATE,INSERT,SELECT,DELETE ON "+database+".* TO '"+userName
							+"'@'%' IDENTIFIED BY '"+password+"' WITH"
							+ " GRANT OPTION;");
					str.execute("FLUSH PRIVILEGES;");
				}
				str.close();
				connect.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		dataBaseUrl = mysqlIp+":"+port+"/"+database;
		return dataBaseUrl;
	}
	private String getUser(String user){
		if(user.contains("@")){
			Pattern pat = Pattern.compile(".*@");
			Matcher mat = pat.matcher(user);
			boolean rs = mat.find();
			if(rs){
				String re=mat.group();
				return re.substring(0, re.length()-1);
			}
		}
		return user;
	}
	private void config() throws IOException{
		Properties prop = new Properties();
		String path = this.getClass().getResource("MySQLtoClient.properties")
				.getFile();
		InputStream fis = new FileInputStream(path);
		prop.load(fis);
		driver = prop.getProperty("driver").trim();
		rootUser = prop.getProperty("rootUser").trim();
		rootPassword = prop.getProperty("rootPassword").trim();
		mysqlIp = prop.getProperty("mysqlIp").trim();
		port = prop.getProperty("port").trim();
	}
}
