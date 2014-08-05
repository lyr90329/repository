package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.App;

/**
 * Data access object (DAO) for domain model class User.
 * 
 * @see hibernateDao.User
 * @author MyEclipse Persistence Tools
 */

public class AppDao extends BaseDao {
	// property constants
	public static final String APP_ID = "appId";

	public static final String USER_NAME = "userName";

	public static final String APP_NAME = "appName";

	public static final String DEPLOY_STATUS = "deployStatus";

	public static final String RUN_STATUS = "runStatus";

	public static final String TABLE = "cloud_apptrial";

	public AppDao() {
		super();
	}

	public int delete(App persistentInstance) {
		try {
			String sql = "DELETE FROM " + TABLE + " WHERE " + APP_ID + " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, persistentInstance.getAppId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	public List<App> findAll() {
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

	public App findById(int id) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + APP_ID + " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			List<App> instanceList = this.getListByResultSet(ps.executeQuery());
			if (instanceList == null) {
				return null;
			}
			App instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public App findByAppIdAndUserName(int id, String name) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + APP_ID
					+ " = ? and " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			List<App> instanceList = this.getListByResultSet(ps.executeQuery());
			if (instanceList == null) {
				return null;
			}
			App instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// public List<App> findByProperty(String propertyName, Object value) {
	// try {
	// String sql = "SELECT * FROM " + TABLE + " WHERE " + propertyName
	// + " = ? ";
	// PreparedStatement ps = this.getPreparedStatement(sql);
	// if (propertyName.equals(USER_NAME)) {
	// ps.setString(1, (String)value);
	// }
	// // sql="select * from app where userid=1";
	// return this.getListByResultSet(ps.executeQuery(sql));
	// } catch (RuntimeException re) {
	// re.printStackTrace();
	// return null;
	// } catch (SQLException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	public List<App> findByProperty(String propertyName, Object value) {
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

	public List<App> findByUserName(String name) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + USER_NAME
					+ " = " + "'" + name + "'";
			PreparedStatement ps = this.getPreparedStatement(sql);
			return this.getListByResultSet(ps.executeQuery(sql));
		} catch (RuntimeException re) {
			return null;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	protected List<App> getListByResultSet(ResultSet rs) {
		List<App> list = new ArrayList<App>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				App instance = new App();
				instance.setAppId(rs.getInt(APP_ID));
				instance.setUserName(rs.getString(USER_NAME));
				instance.setAppName(rs.getString(APP_NAME));
				instance.setDeployStatus(rs.getString(DEPLOY_STATUS));
				instance.setRunStatus(rs.getString(RUN_STATUS));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	public int save(App transientInstance) {
		try {
			String sql = "INSERT INTO " + TABLE + "(" + APP_ID + ","
					+ USER_NAME + "," + APP_NAME + "," + DEPLOY_STATUS + ","
					+ RUN_STATUS + ") VALUES(?, ?, ?, ?, ?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, transientInstance.getAppId());
			ps.setString(2, transientInstance.getUserName());
			ps.setString(3, transientInstance.getAppName());
			ps.setString(4, transientInstance.getDeployStatus());
			ps.setString(5, transientInstance.getRunStatus());
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

	// public int update(App instance) {
	// try {
	// String sql = "UPDATE " + TABLE + " SET " + USER_NAME + "=? , "
	// + APP_NAME + "=? WHERE " + APP_ID + "=?";
	// PreparedStatement ps = this.getPreparedStatement(sql);
	// ps.setString(1, instance.getUserName());
	// ps.setString(2, instance.getAppName());
	// ps.setInt(3, instance.getAppId());
	// return ps.executeUpdate();
	// } catch (RuntimeException re) {
	// return -1;
	// } catch (SQLException e) {
	// return -1;
	// }
	// }

	public int update(App instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + DEPLOY_STATUS + "=?, "
					+ RUN_STATUS + "=? WHERE " + USER_NAME + "=? and " + APP_ID
					+ "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getDeployStatus());
			ps.setString(2, instance.getRunStatus());
			ps.setString(3, instance.getUserName());
			ps.setInt(4, instance.getAppId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int updateDeployStatus(App instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + DEPLOY_STATUS
					+ "=? WHERE " + APP_ID + "=? AND " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getDeployStatus());
			ps.setInt(2, instance.getAppId());
			ps.setString(3, instance.getUserName());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}
}