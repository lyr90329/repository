package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.Application;

/**
 * Data access object (DAO) for domain model class User.
 * 
 * @see hibernateDao.User_N
 * @author MyEclipse Persistence Tools
 */

public class ApplicationDao extends BaseDao {
	// property constants
	public static final String TABLE = "cloud_app";
	public static final String ID = "app_id";
	public static final String PAGES_AMOUNT = "pages_amount";
	public static final String ACCESS_PAGE = "access_page";
	public static final String USER_ID = "user_id";
	public static final String DESCRIPTION = "description";
	public static final String NAME = "appname";

	public ApplicationDao() {
		super();
	}

	// public int delete(Application persistentInstance) {
	// try {
	// String sql = "DELETE FROM " + TABLE + " WHERE " + ID + " = ?";
	// PreparedStatement ps = this.getPreparedStatement(sql);
	// ps.setInt(1, persistentInstance.getId());
	// return ps.executeUpdate();
	// } catch (RuntimeException re) {
	// return -1;
	// } catch (SQLException e) {
	// return -1;
	// }
	// }

	public List<Application> findAll() {
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

	public Application findById(int id) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			List<Application> instanceList = this.getListByResultSet(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			Application instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// public List<Application> findByProperty(String propertyName, Object
	// value) {
	// try {
	// String sql = "SELECT * FROM " + TABLE + " WHERE " + propertyName
	// + "='" + value + "'";
	// PreparedStatement ps = this.getPreparedStatement(sql);
	// return this.getListByResultSet(ps.executeQuery(sql));
	// } catch (RuntimeException re) {
	// return null;
	// } catch (SQLException e) {
	// return null;
	// }
	// }

	@Override
	protected List<Application> getListByResultSet(ResultSet rs) {
		List<Application> list = new ArrayList<Application>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				Application instance = new Application();
				instance.setId(rs.getInt(ID));
				instance.setAccessPage(rs.getString(ACCESS_PAGE));
				instance.setPagesAmount(rs.getInt(PAGES_AMOUNT));

				instance.setDescription(rs.getString(DESCRIPTION));
				instance.setName(rs.getString(NAME));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	// public int save(Application transientInstance) {
	// try {
	// String sql = "INSERT INTO " + TABLE + "(" + ID + ","
	// + MENU_CONF + ") VALUES(?, ?)";
	// PreparedStatement ps = this.getPreparedStatement(sql);
	// ps.setInt(1, transientInstance.getId());
	// ps.setInt(2, transientInstance.getMenuConf());
	// return ps.executeUpdate();
	// } catch (RuntimeException re) {
	// return -1;
	// } catch (SQLException e) {
	// return -1;
	// }
	// return 0;
	// }

	protected void setSavePreparedStatement(PreparedStatement ps, Object obj)
			throws SQLException {

	}

	// public int update(Application instance) {
	// try {
	// String sql = "UPDATE " + TABLE + " SET " + MENU_CONF + "=? WHERE "
	// + USER_ID + "=?";
	// PreparedStatement ps = this.getPreparedStatement(sql);
	// ps.setInt(1, instance.getMenuConf());
	// ps.setInt(2, instance.getId());
	// return ps.executeUpdate();
	// } catch (RuntimeException re) {
	// return -1;
	// } catch (SQLException e) {
	// return -1;
	// }
	// return 0;
	// }

	public List<Application> findApplicationsByUserName(String name) {
		String sql = "select a.* from cloud_app a,cloud_appsubscription s "
				+ "where s.userName=" + "\"" + name + "\""
				+ " and s.appId=a.app_id";

		return getListByResultSet(super.executeQuery(sql));
	}

	public int subscribe(int id, String username, String appname) {
		try {
			String sql = "insert into cloud_apptrial (appName,userName,appId,deployStatus,runStatus) values(?,?,?,?,?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, appname);
			ps.setString(2, username);
			ps.setInt(3, id);
			ps.setString(4, "false");
			ps.setString(5, "false");
			return ps.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				return 0;
			} else {
				e.printStackTrace();
				return -1;
			}
		}

	}

}