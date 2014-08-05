package repository.AppStore;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;

import repository.AppStore.App;

public class AppDB extends DBconnect {

	public static final String APP_ID = "appId";

	public static final String USER_NAME = "userName";

	public static final String APP_NAME = "appName";

	public static final String DES = "description";

	public static final String Date = "Date";

	public static final String Lev = "level";

	public static final String UC = "userCount";

	public static final String TABLE = "appstore_main";

	public AppDB() {
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

	public List<App> getAppList(int sorttype) {
		List<App> list;
		try {
			String sql = "select appstore_main.*, count(*)"
					+ UC
					+ " from appstore_user  right outer join appstore_main  on appstore_main.appID=appstore_user.appID group by appstore_main.appID order by ";
			switch (sorttype) {
			case 1:
				sql += Date;
				break;
			case 3:
				sql += UC;
				break;

			case 2:
				sql += "click";
				break;

			}
			sql += " DESC";
			PreparedStatement ps = this.getPreparedStatement(sql);
			list = getListByResultSet(ps.executeQuery(sql));
			return list;
		} catch (RuntimeException re) {
			return null;
		} catch (SQLException e) {

			return null;
		}
	}

	public List<App> getSerchList(int sorttype, String search) {
		List<App> list;

		try {
			String sql = "select appstore_main.*, count(*)"
					+ UC
					+ " from appstore_user, appstore_main where appstore_main.appID=appstore_user.appID and (description like '%"
					+ search + "%' or appstore_main.userName like '%" + search
					+ "%' or appName like '%" + search
					+ "%') group by appstore_user.appID order by ";
			sql = "select appstore_main.*, count(*)"
					+ UC
					+ " from appstore_user  right outer join appstore_main  on appstore_main.appID=appstore_user.appID where  (description like '%"
					+ search + "%' or appstore_main.userName like '%" + search
					+ "%' or appName like '%" + search
					+ "%') group by appstore_main.appID order by ";
			switch (sorttype) {
			case 1:
				sql += Date;
				break;
			case 3:
				sql += UC;
				break;

			case 2:
				sql += "click";
				break;

			}
			sql += " DESC";
			PreparedStatement ps = this.getPreparedStatement(sql);
			list = getListByResultSet(ps.executeQuery(sql));
			return list;
		} catch (RuntimeException re) {
			return null;
		} catch (SQLException e) {

			return null;
		}
	}

	public App findById(int id) {
		try {
			String sql = "select appstore_main.*, count(*)userCount from appstore_user  right outer join appstore_main  on appstore_main.appID=appstore_user.appID   where appstore_main.appID=?";
			// String sql = "SELECT * FROM " + TABLE + " WHERE " + APP_ID
			// + " = ?";
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

	public void addClick(int id) {
		try {
			String sql = "update  appstore_main set click=click+1 where appID="
					+ id;
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.executeUpdate();

		} catch (RuntimeException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public List<String> findTag(int id) {
		try {
			String sql = "select tagName from appstore_tag where appID=" + id;

			PreparedStatement ps = this.getPreparedStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			List<String> list = new ArrayList<String>();

			while (rs.next())
				list.add(rs.getString(1));

			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> finghotTag(int count)

	{
		try {
			String sql = "select tagName,count(*) tagcount from appstore_tag group by tagName order by tagcount DESc";

			PreparedStatement ps = this.getPreparedStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			List<String> list = new ArrayList<String>();

			while (rs.next() && count >= 0) {
				list.add(rs.getString(1));
				count--;
			}

			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Comment> getComment(int AppID) {
		try {
			String sql = "select userName,comment from appstore_comment where appID="
					+ AppID;

			PreparedStatement ps = this.getPreparedStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			List<Comment> list = new ArrayList<Comment>();

			Comment c;
			while (rs.next()) {
				c = new Comment(rs.getString(2), rs.getString(1));
				list.add(c);

			}

			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void addComment(int AppID, String userName, String comment) {
		try {
			String sql = "delete from appstore_comment where appID=" + AppID
					+ " and userName='" + userName + "'";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.executeUpdate();
			sql = "insert into appstore_comment(appID,userName,comment)  values("
					+ AppID + ",'" + userName + "','" + comment + "')";

			PreparedStatement ps2 = this.getPreparedStatement(sql);
			ps2.executeUpdate();

		} catch (RuntimeException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();

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

	public void addTag(int id, String tagName) {
		try {
			String sql = "insert into appstore_tag  values(" + id + ",'"
					+ tagName + "')";

			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.executeUpdate();

		} catch (RuntimeException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	// �ҵ����д˱�ǩ�µ�app
	public List<App> getAppfromtag(int sorttype, String tag) {
		List<App> list;
		try {
			String sql = "select appstore_main.*, count(*)"
					+ UC
					+ " from appstore_user  right outer join appstore_main  on appstore_main.appID=appstore_user.appID where  appstore_main.appID in ( select appID FROM  appstore_tag where tagName='"
					+ tag + "') group by appstore_main.appID order by ";
			switch (sorttype) {
			case 1:
				sql += Date;
				break;
			case 3:
				sql += UC;
				break;

			case 2:
				sql += "click";
				break;

			}
			sql += " DESC";
			PreparedStatement ps = this.getPreparedStatement(sql);
			list = getListByResultSet(ps.executeQuery(sql));
			return list;
		} catch (RuntimeException re) {
			return null;
		} catch (SQLException e) {

			return null;
		}
	}

	//得到所有类别
	public List<String> getCate() {
		try {
			String sql = "select cateName from appstore_cate order by cateID";

			PreparedStatement ps = this.getPreparedStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			List<String> list = new ArrayList<String>();

			while (rs.next())
				list.add(rs.getString(1));

			return list;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<App> getAppfromCate(int sorttype, int cateID) {
		List<App> list;
		try {
			String sql = "select appstore_main.*, count(*)"
					+ UC
					+ " from appstore_user, appstore_main  where appstore_main.appID=appstore_user.appID and  cateID="
					+ cateID + " group by appstore_user.appID order by ";
			sql = "select appstore_main.*, count(*)"
					+ UC
					+ " from appstore_user  right outer join appstore_main  on appstore_main.appID=appstore_user.appID  where   cateID="
					+ cateID + " group by appstore_main.appID order by ";
			switch (sorttype) {
			case 1:
				sql += Date;
				break;
			case 3:
				sql += UC;
				break;

			case 2:
				sql += "click";
				break;

			}
			sql += " DESC";
			PreparedStatement ps = this.getPreparedStatement(sql);
			list = getListByResultSet(ps.executeQuery(sql));
			return list;
		} catch (RuntimeException re) {
			return null;
		} catch (SQLException e) {

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
				instance.setDescription(rs.getString(DES));
				instance.setDate(rs.getDate(Date));

				instance.setUserCount(rs.getInt(UC));
				instance.setExt(rs.getString("ext"));
				list.add(instance);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	public int save(App transientInstance, int cateID) {
		try {
			String sql = "INSERT INTO " + TABLE + "(" + APP_ID + ","
					+ USER_NAME + "," + APP_NAME + "," + DES + "," + Date
					+ ",cateID,ext) VALUES(?, ?, ?, ?, ?,?,?)";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setInt(1, transientInstance.getAppId());
			ps.setString(2, transientInstance.getUserName());
			ps.setString(3, transientInstance.getAppName());
			ps.setString(4, transientInstance.getDescription());
			ps.setDate(5, transientInstance.getDate());
			ps.setInt(6, cateID);
			ps.setString(7, transientInstance.getExt());

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
			String sql = "UPDATE " + TABLE + " SET " + DES + "=?, " + Date
					+ "=? WHERE " + USER_NAME + "=? and " + APP_ID + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getDescription());
			ps.setDate(2, instance.getDate());
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
			String sql = "UPDATE " + TABLE + " SET " + DES + "=? WHERE "
					+ APP_ID + "=? AND " + USER_NAME + "=?";
			PreparedStatement ps = this.getPreparedStatement(sql);
			ps.setString(1, instance.getDescription());
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
