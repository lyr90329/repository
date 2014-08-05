package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.ServiceTrial;

/**
 * Data access object (DAO) for domain model class User.
 * 
 * @see hibernateDao.User_N
 * @author MyEclipse Persistence Tools
 */

public class ServiceTrialDao extends BaseDao {
	// property constants
	public static final String SERVICE_ID = "serviceId";
	public static final String USER_NAME = "userName";
	public static final String SERVICE_NAME = "serviceName";
	public static final String DEPLOY_STATUS = "deployStatus";
	public static final String RUN_STATUS = "runStatus";
	public static final String TABLE = "cloud_servicetrial";

	public ServiceTrialDao() {
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

	public ServiceTrial findByServiceIdAndUserName(int id, String name) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + SERVICE_ID
					+ " = ? and " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			List<ServiceTrial> instanceList = this.getListByResultSet(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			ServiceTrial instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ServiceTrial> findByProperty(String propertyName, Object value) {
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
	protected List<ServiceTrial> getListByResultSet(ResultSet rs) {
		List<ServiceTrial> list = new ArrayList<ServiceTrial>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				ServiceTrial instance = new ServiceTrial();
				instance.setDeployStatus(rs.getString(DEPLOY_STATUS));
				instance.setRunStatus(rs.getString(RUN_STATUS));
				instance.setServiceId(rs.getInt(SERVICE_ID));
				instance.setServiceName(rs.getString(SERVICE_NAME));
				instance.setUserName(rs.getString(USER_NAME));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	public int save(ServiceTrial instance) {
		try {
			String sql = "insert into cloud_servicetrial "
					+ "(serviceId,username,serviceName,deployStatus,runStatus)"
					+ " values(?,?,?,?,?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, instance.getServiceId());
			ps.setString(2, instance.getUserName());
			ps.setString(3, instance.getServiceName());
			ps.setString(4, instance.getDeployStatus());
			ps.setString(5, instance.getRunStatus());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		} catch (SQLException e) {
			// e.getErrorCode() == 1062 means duplicate value
			if (e.getErrorCode() == 1062)
				return 0;
			else
				return -1;
		}
	}

	@Override
	protected void setSavePreparedStatement(PreparedStatement ps, Object obj)
			throws SQLException {

	}

	public int update(ServiceTrial instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + DEPLOY_STATUS + "=?, "
					+ RUN_STATUS + "=? WHERE " + USER_NAME + "=? and "
					+ SERVICE_ID + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getDeployStatus());
			ps.setString(2, instance.getRunStatus());
			ps.setString(3, instance.getUserName());
			ps.setInt(4, instance.getServiceId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public ServiceTrial findById(int id) {
		return null;
	}
}