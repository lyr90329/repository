package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.entity.TestResult;

;

public class TestResultDao extends BaseDao {
	public static final String USER_NAME = "userName";
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String AVERAGE = "average";
	public static final String TABLE = "cloud_testresult";

	public static final String SERVICENAME = "serviceName";
	public static final String TESTOPERATION = "testOperation";
	public static final String URL = "URL";
	public static final String TYPE = "type";
	public static final String STRATEGY = "strategy";
	public static final String TESTNUM = "testNum";
	public static final String TIMEOUT = "timeout";

	public TestResultDao() {
		super();
	}

	public List<TestResult> findByUserName(String name) {
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE " + USER_NAME
					+ " = ?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, name);
			List<TestResult> instanceList = this.getListByResultSet(ps
					.executeQuery());
			if (instanceList == null) {
				return null;
			}
			return instanceList;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected List<TestResult> getListByResultSet(ResultSet rs) {
		List<TestResult> list = new ArrayList<TestResult>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				TestResult instance = new TestResult();
				instance.setUserName(rs.getString(USER_NAME));
				instance.setSuccess(rs.getString(SUCCESS));
				instance.setFail(rs.getString(FAIL));
				instance.setAverage(rs.getString(AVERAGE));
				instance.setServiceName(rs.getString(SERVICENAME));
				instance.setTestOperation(rs.getString(TESTOPERATION));
				instance.setUrl(rs.getString(URL));
				instance.setType(rs.getString(TYPE));
				instance.setStrategy(rs.getString(STRATEGY));
				instance.setTestNum(rs.getString(TESTNUM));
				instance.setTimeout(rs.getString(TIMEOUT));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	public int save(TestResult transientInstance) {
		try {
			String sql = "INSERT INTO " + TABLE + "(" + USER_NAME + ","
					+ SUCCESS + "," + FAIL + "," + AVERAGE + "," + SERVICENAME
					+ "," + TESTOPERATION + "," + URL + "," + TYPE + ","
					+ STRATEGY + "," + TESTNUM + "," + TIMEOUT
					+ ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, transientInstance.getUserName());
			ps.setString(2, transientInstance.getSuccess());
			ps.setString(3, transientInstance.getFail());
			ps.setString(4, transientInstance.getAverage());
			ps.setString(5, transientInstance.getServiceName());
			ps.setString(6, transientInstance.getTestOperation());
			ps.setString(7, transientInstance.getUrl());
			ps.setString(8, transientInstance.getType());
			ps.setString(9, transientInstance.getStrategy());
			ps.setString(10, transientInstance.getTestNum());
			ps.setString(11, transientInstance.getTimeout());
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

	public int update(TestResult instance) {
		try {
			String sql = "UPDATE " + TABLE + " SET " + SUCCESS + "=? ," + FAIL
					+ "=? ," + AVERAGE + "=? " + "WHERE " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getSuccess());
			ps.setString(2, instance.getFail());
			ps.setString(3, instance.getAverage());
			ps.setString(4, instance.getUserName());
			System.out.println(sql);
			return ps.executeUpdate();
		} catch (RuntimeException re) {
			return -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	public static void main(String[] args) {

	}
}