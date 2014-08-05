package repository.AppStore;

import java.sql.Date;

public class App {
	private int appId;
	private String userName;
	private String appName;

	private String description;
	private Date Date;
	private int userCount;
	private String ext;

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public App(int appId, String userName, String appName, String description,
			Date date, String ext) {
		this.appId = appId;
		this.userName = userName;
		this.appName = appName;
		this.description = description;
		this.Date = date;
		this.ext = ext;

	}

	public App() {
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		this.Date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
