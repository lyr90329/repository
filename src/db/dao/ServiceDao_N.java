package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.Service_N;

/**
 * Data access object (DAO) for domain model class User.
 * 
 * @see hibernateDao.User_N
 * @author MyEclipse Persistence Tools
 */

public class ServiceDao_N extends BaseDao {
	// property constants
	public static final String TABLE = "services_1";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String URL = "url";

	public ServiceDao_N() {
		super();
	}

	// public int delete(Service persistentInstance) {
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

	// public List<Service> findAll() {
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

	public Service_N findById(int id) {
		try {
			String sql = "select * from services_1 where id=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			List<Service_N> instanceList = this.getListByResultSet(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			Service_N instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// public List<Service> findByProperty(String propertyName, Object value) {
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
	protected List<Service_N> getListByResultSet(ResultSet rs) {
		List<Service_N> list = new ArrayList<Service_N>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				Service_N instance = new Service_N();
				instance.setId(rs.getInt(ID));
				instance.setName(rs.getString(NAME));
				instance.setDescription(rs.getString(DESCRIPTION));
				instance.setUrl(rs.getString(URL));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	// public int save(Service transientInstance) {
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

	// public int update(Service instance) {
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

	public List<Service_N> findServicesByUserName(String name) {
		String sql = "select services_1.* from services_1,cloud_servicesubscription "
				+ "where cloud_servicesubscription.userName="
				+ "\""
				+ name
				+ "\""
				+ " and cloud_servicesubscription.serviceId=services_1.id";

		return getListByResultSet(super.executeQuery(sql));
	}

}