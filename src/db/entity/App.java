package db.entity;

public class App {
	private int appId;
	private String userName;
	private String appName;
	private String deployStatus;
	private String runStatus;

	public App() {
	}

	public App(int appId, String userName, String appName, String deployStatus,
			String runStatus) {
		this.appId = appId;
		this.userName = userName;
		this.appName = appName;
		this.deployStatus = deployStatus;
		this.runStatus = runStatus;
	}

	public String getDeployStatus() {
		return deployStatus;
	}

	public void setDeployStatus(String deployStatus) {
		this.deployStatus = deployStatus;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
