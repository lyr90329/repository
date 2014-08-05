package db.dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.Bpmn;
import db.entity.UploadBpmn;
//import db.entity.Service;
//import db.entity.User;
//import db.entity.App;
import db.entity.UploadService;

/**
 * Data access object (DAO) for domain model class User.
 * 
 * @see hibernateDao.User
 * @author MyEclipse Persistence Tools
 */

public class BpmnDao extends BaseDao {
	// property constants
	public static final String BPMN_ID = "bpmnId";
	
	public static final String USER_NAME = "userName";
	
	public static final String BPMN_NAME = "bpmnName";
	
	public static final String DEPLOY_STATUS = "deployStatus";
	
	public static final String RUN_STATUS = "runStatus";
	
	public static final String JOB_ID = "jobId";
	
	public static final String TABLE = "cloud_bpmntrial";
	
	public static final String UPLOADBPMNTABLE = "cloud_bpmnupload";
	
	public static final String BPMN_URL = "bpmnUrl";
	

	public BpmnDao() {
		super();
	}
	
	public int delete(Bpmn persistentInstance) {
		try {
			String sql = "DELETE FROM " + TABLE + " WHERE " + BPMN_ID + " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, persistentInstance.getBpmnId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	
	public int deleteUploadBpmn(int id) {
		try {
			String sql = "DELETE FROM " + UPLOADBPMNTABLE + " WHERE " + BPMN_ID
					+ " = ?";
			
			UploadBpmn bpmn=this.findUploadBpmnById(id);
			String url=bpmn.getBpmnUrl();//得到文件在文件系统中的路径
			
			//删除文件系统中相应的文件
			 File deletefile = new File(url);
			   if(deletefile.exists())
			   {
			    boolean d = deletefile.delete();
			    if(d)
			    {
			     System.out.println(url+"删除成功！");
			    }else
			    {
			     System.out.println(url+" 删除失败！");
			    }
			   } 
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate();//删除数据库中的记录
			  
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	
	public List<Bpmn> findAll() {
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

	public Bpmn findById(int id) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + BPMN_ID
					+ " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			List<Bpmn> instanceList = this
					.getListByResultSet(ps.executeQuery());
			if (instanceList == null) {
				return null;
			}
			Bpmn instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	public UploadBpmn findUploadBpmnById(int id) {
		try {
			String sql = "SELECT * FROM " + UPLOADBPMNTABLE + " WHERE " + BPMN_ID
					+ " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			List<UploadBpmn> instanceList = this
					.getListByResultSet2(ps.executeQuery());
			if (instanceList == null) {
				return null;
			}
			UploadBpmn instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Bpmn findRunningByBPMNIdAndUserName(int id, String name) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + BPMN_ID
					+ " = ? and " + USER_NAME + "=?  and runStatus = 'true'" ;
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			List<Bpmn> instanceList;
			while(true)
			{
				instanceList= this.getListByResultSet(ps
						.executeQuery());
				if (instanceList != null) {
					break;
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
			Bpmn instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	
	public Bpmn findByBPMNIdAndUserName(int id, String name) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + BPMN_ID
					+ " = ? and " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			List<Bpmn> instanceList = this.getListByResultSet(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			Bpmn instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	//用于上传BPMN文件
	public UploadBpmn findByUpBPMNIdAndUserName(int id, String name) {
		try {
			String sql = "SELECT * FROM " + UPLOADBPMNTABLE + " WHERE " + BPMN_ID
					+ " = ? and " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			List<UploadBpmn> instanceList = this.getListByResultSet2(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			UploadBpmn instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	
	public UploadBpmn findByBPMNIdAndUserName2(int id, String name) {
		try {
			String sql = "SELECT * FROM " + UPLOADBPMNTABLE + " WHERE " + BPMN_ID
					+ " = ? and " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			List<UploadBpmn> instanceList = this.getListByResultSet2(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			UploadBpmn instance = instanceList.get(0);
			return instance;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	
//	public List<Bpmn> findByProperty(String propertyName, Object value) {
//		try {
//			String sql = "SELECT * FROM " + TABLE + " WHERE " + propertyName
//					+ " = ? ";
//			PreparedStatement ps = this.getPreparedStatement(sql);
//			if (propertyName.equals(USER_NAME)) {
//				ps.setInt(1, ((Integer) value).intValue());
//			}
//			
//			return this.getListByResultSet(ps.executeQuery(sql));
//		} catch (RuntimeException re) {
//			return null;
//		} catch (SQLException e) {
//			return null;
//		}
//	}
	
	public List<Bpmn> findByProperty(String propertyName, Object value) {
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

	public List<Bpmn> findByUserName(String name) {
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
	protected List<Bpmn> getListByResultSet(ResultSet rs) {
		List<Bpmn> list = new ArrayList<Bpmn>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				Bpmn instance = new Bpmn();
				instance.setBpmnId(rs.getInt(BPMN_ID));
				instance.setUserName(rs.getString(USER_NAME));
				instance.setBpmnName(rs.getString(BPMN_NAME));
				instance.setDeployStatus(rs.getString(DEPLOY_STATUS));
				instance.setRunStatus(rs.getString(RUN_STATUS));
				instance.setJobId(rs.getInt(JOB_ID));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	
	protected List<UploadBpmn> getListByResultSet2(ResultSet rs) {
		List<UploadBpmn> list = new ArrayList<UploadBpmn>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				UploadBpmn instance = new UploadBpmn();
				instance.setBpmnId(rs.getInt(BPMN_ID));
				instance.setUserName(rs.getString(USER_NAME));
				instance.setBpmnName(rs.getString(BPMN_NAME));
				instance.setBpmnUrl(rs.getString(BPMN_URL));
				instance.setDeployStatus(rs.getString(DEPLOY_STATUS));
				instance.setRunStatus(rs.getString(RUN_STATUS));
				instance.setJobId(rs.getInt(JOB_ID));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	
	public List<UploadBpmn> findUploadBpmn(String propertyName, Object value) {
		try {
			String sql = "SELECT * FROM " + UPLOADBPMNTABLE + " WHERE " + propertyName
					+ "='" + value + "'";
			PreparedStatement ps = this.getPreparedStatement(sql);
			return this.getListByResultSet2(ps.executeQuery(sql));
		} catch (RuntimeException re) {
			return null;
		} catch (SQLException e) {
			return null;
		}
	}

	
	
	
	public int save(Bpmn transientInstance) {
		try {
			String sql = "INSERT INTO " + TABLE + "(" + BPMN_ID + "," + USER_NAME + ","
					+ BPMN_NAME + "," + DEPLOY_STATUS + "," + RUN_STATUS + ") VALUES(?, ?, ?, ?, ?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, transientInstance.getBpmnId());
			ps.setString(2, transientInstance.getUserName());
			ps.setString(3, transientInstance.getBpmnName());
			ps.setString(4, transientInstance.getDeployStatus());
			ps.setString(5, transientInstance.getRunStatus());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	
	
	public int saveUploadBpmn(UploadBpmn transientInstance) {
		try {
			String sql = "INSERT INTO " + UPLOADBPMNTABLE + "(" +  USER_NAME + "," + BPMN_NAME + "," +BPMN_URL+ "," + DEPLOY_STATUS + "," + RUN_STATUS + "," + JOB_ID + ") VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, transientInstance.getUserName());
			ps.setString(2, transientInstance.getBpmnName());
			ps.setString(3, transientInstance.getBpmnUrl());
			ps.setString(4, transientInstance.getDeployStatus());
			ps.setString(5, transientInstance.getRunStatus());
			ps.setInt(6, transientInstance.getJobId());
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

//	public int update(Bpmn instance) {
//		try {
//			String sql = "UPDATE " + TABLE + " SET " + USER_NAME + "=? , " + BPMN_NAME + "=? , " + DEPLOY_STATUS + "=? , " + RUN_STATUS + "=? WHERE " + BPMN_ID + "=?";
//			PreparedStatement ps = this.getPreparedStatement(sql);
//			ps.setString(1, instance.getUserName());
//			ps.setString(2, instance.getBpmnName());
//			ps.setInt(3, instance.getBpmnId());
//			return ps.executeUpdate();
//		} catch (RuntimeException re) {
//			return -1;
//		} catch (SQLException e) {
//			return -1;
//		}
//	}

	public int update(Bpmn instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + DEPLOY_STATUS + "=?, "+RUN_STATUS+"=? WHERE "
					+ USER_NAME + "=? and "+BPMN_ID+"=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getDeployStatus());
			ps.setString(2, instance.getRunStatus());
			ps.setString(3, instance.getUserName());
			ps.setInt(4, instance.getBpmnId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	//用于 上传的BPMN
	public int update2(UploadBpmn instance) {
		try {
			String sql = "UPDATE " + UPLOADBPMNTABLE + " SET " + DEPLOY_STATUS + "=?, "+RUN_STATUS+"=? WHERE "
					+ USER_NAME + "=? and "+BPMN_ID+"=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getDeployStatus());
			ps.setString(2, instance.getRunStatus());
			ps.setString(3, instance.getUserName());
			ps.setInt(4, instance.getBpmnId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int setJobIdFalse(Bpmn instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + JOB_ID + "=? , runStatus = 'false'  WHERE "
					+ USER_NAME + "=? and "+BPMN_ID+"=?";
			System.out.println("JobId置为过期:"+sql);
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, instance.getJobId());
//			ps.setString(2, instance.getRunStatus());
			ps.setString(2, instance.getUserName());
			ps.setInt(3, instance.getBpmnId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	public int updateJobId(Bpmn instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + JOB_ID + "=? , runStatus = 'true'  WHERE "
					+ USER_NAME + "=? and "+BPMN_ID+"=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, instance.getJobId());
//			ps.setString(2, instance.getRunStatus());
			ps.setString(2, instance.getUserName());
			ps.setInt(3, instance.getBpmnId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	//用于上传BPMN
	public int updateJobId2(UploadBpmn instance) {
		try {
			String sql = "UPDATE " + UPLOADBPMNTABLE + " SET " + JOB_ID + "=? WHERE "
					+ USER_NAME + "=? and "+BPMN_ID+"=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, instance.getJobId());
//			ps.setString(2, instance.getRunStatus());
			ps.setString(2, instance.getUserName());
			ps.setInt(3, instance.getBpmnId());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	

	public int updateDeployStatus(Bpmn instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + DEPLOY_STATUS
					+ "=? WHERE " + BPMN_ID + "=? AND " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getDeployStatus());
			ps.setInt(2, instance.getBpmnId());
			ps.setString(3, instance.getUserName());
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	
}