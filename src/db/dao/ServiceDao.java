package db.dao;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.Service;
import db.entity.UploadService;

/**
 * Data access object (DAO) for domain model class User.
 * 
 * @see hibernateDao.User
 * @author MyEclipse Persistence Tools
 */

public class ServiceDao extends BaseDao {
	// property constants
	public static final String SERVICE_ID = "serviceId";

	public static final String USER_NAME = "userName";

	public static final String SERVICE_NAME = "serviceName";

	public static final String WSDLURL = "wsdlurl";

	public static final String DEPLOY_STATUS = "deployStatus";

	public static final String DEPLOY_ID = "deployId";

	public static final String RUN_STATUS = "runStatus";

	public static final String TABLE = "cloud_servicetrial";

	public static final String UPLOADSERVICETABLE = "cloud_serviceupload";

	public static final String SERVICE_URL = "serviceUrl";

	public static final String SAND_BOX = "sandbox_info";

	public ServiceDao() {
		super();
	}

	public int delete(Service persistentInstance) {
		try {
			String sql = "DELETE FROM " + TABLE + " WHERE " + SERVICE_ID
					+ " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, persistentInstance.getServiceId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	// ɾ����ݿ��еļ�¼���ļ�ϵͳ�е��ļ�
	public int deleteUploadService(int id) {
		try {
			String sql = "DELETE FROM " + UPLOADSERVICETABLE + " WHERE "
					+ SERVICE_ID + " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate();// ɾ����ݿ��еļ�¼

		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	public List<Service> findAll() {
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

	public Service findById(int id) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + SERVICE_ID
					+ " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			List<Service> instanceList = this.getListByResultSet(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			Service instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public UploadService findUploadServiceById(int id) {
		try {
			String sql = "SELECT * FROM " + UPLOADSERVICETABLE + " WHERE "
					+ SERVICE_ID + " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			List<UploadService> instanceList = this.getListByResultSet2(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			UploadService instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Service findByServiceIdAndUserName(int id, String name) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + SERVICE_ID
					+ " = ? and " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			List<Service> instanceList = this.getListByResultSet(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			Service instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Service> findByProperty(String propertyName, Object value) {
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

	public List<UploadService> findUploadService(String propertyName,
			Object value) {
		try {
			String sql = "SELECT * FROM " + UPLOADSERVICETABLE + " WHERE "
					+ propertyName + "='" + value + "'";
			PreparedStatement ps = this.getPreparedStatement(sql);
			return this.getListByResultSet2(ps.executeQuery(sql));
		} catch (RuntimeException re) {
			return null;
		} catch (SQLException e) {
			return null;
		}
	}

	public List<Service> findByUserName(String name) {
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
	protected List<Service> getListByResultSet(ResultSet rs) {
		List<Service> list = new ArrayList<Service>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				Service instance = new Service();
				instance.setServiceId(rs.getInt(SERVICE_ID));
				instance.setUserName(rs.getString(USER_NAME));
				instance.setServiceName(rs.getString(SERVICE_NAME));
				instance.setWsdlurl(rs.getString(WSDLURL));
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

	protected List<UploadService> getListByResultSet2(ResultSet rs) {
		List<UploadService> list = new ArrayList<UploadService>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				UploadService instance = new UploadService();
				instance.setServiceId(rs.getInt(SERVICE_ID));
				instance.setUserName(rs.getString(USER_NAME));
				instance.setServiceName(rs.getString(SERVICE_NAME));
				instance.setServiceUrl(rs.getString(SERVICE_URL));
				instance.setDeployStatus(rs.getString(DEPLOY_STATUS));
				instance.setRunStatus(rs.getString(RUN_STATUS));
				instance.setDeployID(rs.getString(DEPLOY_ID));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	public int save(Service transientInstance) {
		try {
			String sql = "INSERT INTO " + TABLE + "(" + SERVICE_ID + ","
					+ USER_NAME + "," + SERVICE_NAME + "," + DEPLOY_STATUS
					+ "," + RUN_STATUS + "," + WSDLURL
					+ ") VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, transientInstance.getServiceId());
			ps.setString(2, transientInstance.getUserName());
			ps.setString(3, transientInstance.getServiceName());
			ps.setString(4, transientInstance.getDeployStatus());
			ps.setString(5, transientInstance.getRunStatus());
			ps.setString(6, transientInstance.getWsdlurl());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	public int saveUploadService(UploadService transientInstance) {
		try {
			String sql = "INSERT INTO " + UPLOADSERVICETABLE + "(" + USER_NAME
					+ "," + SERVICE_NAME + "," + SERVICE_URL + ","
					+ DEPLOY_STATUS + "," + RUN_STATUS + "," + DEPLOY_ID
					+ ") VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, transientInstance.getUserName());
			ps.setString(2, transientInstance.getServiceName());
			ps.setString(3, transientInstance.getServiceUrl());
			ps.setString(4, transientInstance.getDeployStatus());
			ps.setString(5, transientInstance.getRunStatus());
			ps.setString(6, transientInstance.getDeployID());

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

	public int update(Service instance) {
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

	public int updateSandBoxInfo(String info)
			throws UnsupportedEncodingException {
		try {
			String sql = "UPDATE " + SAND_BOX + " SET  info=? WHERE id=1";
			PreparedStatement ps = this.getPreparedStatement(sql);
			// info=new String(info.getBytes("gbk"),"UTF-8");
			info = new String(info.getBytes(), "gbk");
			ps.setString(1, info);
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public String getSandBoxInfo() {
		try {
			String sql = "SELECT * FROM " + SAND_BOX + " WHERE id=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, 1);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("info");
			}
			return "";

		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int updateDeployStatus(Service instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + DEPLOY_STATUS
					+ "=? WHERE " + SERVICE_ID + "=? AND " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getDeployStatus());
			ps.setInt(2, instance.getServiceId());
			ps.setString(3, instance.getUserName());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}
}