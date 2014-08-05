package cn.sdp.Service4all.data;

public class DataServiceUtil {
	public static String WEBROOT_PATH;
	
	static {
		WEBROOT_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		WEBROOT_PATH = WEBROOT_PATH.substring(0, WEBROOT_PATH.length()-16).replace("%20", " ");
	}
}
