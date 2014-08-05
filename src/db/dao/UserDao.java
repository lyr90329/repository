package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.User;

/**
 * Data access object (DAO) for domain model class User.
 * 
 * @see hibernateDao.User
 * @author MyEclipse Persistence Tools
 */

public class UserDao extends BaseDao {
	// property constants
	public static final String USER_NAME = "userName";
	public static final String CONF = "conf";
	public static final String TABLE = "cloud_user";

	public UserDao() {
		super();
	}

	public int delete(User persistentInstance) {
		try {
			String sql = "DELETE FROM " + TABLE + " WHERE " + USER_NAME
					+ " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, persistentInstance.getUserName());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	public List<User> findAll() {
		try {
			String sql = "SELECT * FROM " + TABLE;
			PreparedStatement ps = this.getPreparedStatement(sql);
			return this.getListByResultSet(ps.executeQuery(sql));
		} catch (RuntimeException re) {
			return null;
		} catch (SQLException e) {
			return null;
		}
	}

	public User findByUserName(String name) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + USER_NAME
					+ " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, name);
			List<User> instanceList = this
					.getListByResultSet(ps.executeQuery());
			if (instanceList == null) {
				return null;
			}
			User instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> findByProperty(String propertyName, Object value) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + propertyName
					+ "='" + value + "'";
			PreparedStatement ps = this.getPreparedStatement(sql);
			return this.getListByResultSet(ps.executeQuery(sql));
		} catch (RuntimeException re) {
			return null;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	protected List<User> getListByResultSet(ResultSet rs) {
		List<User> list = new ArrayList<User>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				User instance = new User();
				instance.setUserName(rs.getString(USER_NAME));
				instance.setConf(rs.getInt(CONF));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	public int save(User transientInstance) {
		try {
			String sql = "INSERT INTO " + TABLE + "(" + USER_NAME + "," + CONF
					+ ") VALUES(?, ?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, transientInstance.getUserName());
			ps.setInt(2, transientInstance.getConf());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	@Override
	protected void setSavePreparedStatement(PreparedStatement ps, Object obj)
			throws SQLException {

	}

	public int update(User instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + CONF + "=? WHERE "
					+ USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, instance.getConf());
			ps.setString(2, instance.getUserName());
			System.out.println(sql);
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	public static void main(String[] args) {
		User user = new UserDao().findByUserName("wangsact.buaa.edu.cn");
		System.out.println(user.getUserName());
	}
}