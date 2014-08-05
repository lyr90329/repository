package org.cn.act.sdp.app;

public class AppInfo {
	private String appID;
	private String appName;
	private String url;

	public AppInfo(String appID, String appName, String url) {
		this.appID = appID;
		this.appName = appName;
		this.url = url;
	}

	public String getAppID() {
		return appID;
	}

	public String getAppName() {
		return appName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return String.format("[AppInfo]appID:%s\nappName:%s\nurl:%s", appID,
				appName, url);
	}
}
