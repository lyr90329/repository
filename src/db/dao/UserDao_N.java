package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.User_N;

/**
 * Data access object (DAO) for domain model class User.
 * 
 * @see hibernateDao.User_N
 * @author MyEclipse Persistence Tools
 */

public class UserDao_N extends BaseDao {
	// property constants
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String MENU_CONF = "conf";
	public static final String TABLE = "cloud_user";

	public UserDao_N() {
		super();
	}

	// public int delete(User persistentInstance) {
	// try {
	// String sql = "DELETE FROM " + TABLE + " WHERE " + USER_NAME + " = ?";
	// PreparedStatement ps = this.getPreparedStatement(sql);
	// ps.setInt(1, persistentInstance.getId());
	// return ps.executeUpdate();
	// } catch (RuntimeException re) {
	// return -1;
	// } catch (SQLException e) {
	// return -1;
	// }
	// return 0;
	// }

	// public List<User> findAll() {
	// try {
	// String sql = "SELECT * FROM " + TABLE;
	// PreparedStatement ps = this.getPreparedStatement(sql);
	// return this.getListByResultSet(ps.executeQuery(sql));
	// } catch (RuntimeException re) {
	// return null;
	// } catch (SQLException e) {
	// return null;
	// }
	// }

	public User_N findById(int id) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + USER_ID
					+ " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			List<User_N> instanceList = this.getListByResultSet(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			User_N instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User_N> findByProperty(String propertyName, Object value) {
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
	protected List<User_N> getListByResultSet(ResultSet rs) {
		List<User_N> list = new ArrayList<User_N>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				User_N instance = new User_N();
				instance.setName(rs.getString(USER_NAME));
				instance.setMenuConf(rs.getInt(MENU_CONF));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	// public int save(User transientInstance) {
	// try {
	// String sql = "INSERT INTO " + TABLE + "(" + USER_NAME + ","
	// + MENU_CONF + ") VALUES(?, ?)";
	// PreparedStatement ps = this.getPreparedStatement(sql);
	// ps.setString(1, transientInstance.getName());
	// ps.setInt(2, transientInstance.getMenuConf());
	// return ps.executeUpdate();
	// } catch (RuntimeException re) {
	// return -1;
	// } catch (SQLException e) {
	// return -1;
	// }
	// }

	@Override
	protected void setSavePreparedStatement(PreparedStatement ps, Object obj)
			throws SQLException {

	}

	public int update(User_N instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + MENU_CONF + "=? WHERE "
					+ USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, instance.getMenuConf());
			ps.setString(2, instance.getName());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}
}