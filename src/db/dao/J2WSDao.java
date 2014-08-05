package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.J2WSBean;
import db.entity.User;

public class J2WSDao extends BaseDao {
	
	public static final String TABLE = "j2ws";
	
	public static final String USER = "user";
	public static final String FILENAME = "file"; 
	public static final String DEPLOYED = "deployed"; 

	public J2WSDao(){
		super();
	}
	
	public int addWebservice(String userName, String fileName){
		try {
			String sql = "INSERT INTO " + TABLE + "(" + USER + "," + FILENAME
					+ ") VALUES(?, ?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, fileName);
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	public int deployWebservice(String userName, String fileName){
		
		try {
			String sql = "UPDATE " + TABLE + " SET " + DEPLOYED + "=1 WHERE "
					+ USER + "=? and " + FILENAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, fileName);
			System.out.println(sql);
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	public List<J2WSBean> getJ2wsListByUser(String name){
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + USER
					+ " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, name);
			
			return this.getListByResultSet(ps.executeQuery());
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected List<J2WSBean> getListByResultSet(ResultSet rs) {
		List<J2WSBean> wsList = new ArrayList<J2WSBean>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				J2WSBean instance = new J2WSBean();
				instance.setUser(rs.getString(USER));
				instance.setFile(rs.getString(FILENAME));
				int deployed = rs.getInt(DEPLOYED);
				if(deployed>0){
					instance.setDeployed(true);
				}
				else{
					instance.setDeployed(false);
				}
				wsList.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wsList;
	}

	@Override
	protected void setSavePreparedStatement(PreparedStatement ps, Object obj)
			throws SQLException {
		
	}
	
	public int delete(String userName, String fileName) {
		try {
			String sql = "DELETE FROM " + TABLE + " WHERE " + USER
					+ " = ? and " + FILENAME + " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, fileName);
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	public static void main(String[] args) {
		List<J2WSBean> list = new J2WSDao().getJ2wsListByUser("user1");
		for(J2WSBean bean : list){
			System.out.println(bean.getFile());
			System.out.println(bean.isDeployed());
		}
	}
}
