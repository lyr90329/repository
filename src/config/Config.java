package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

public class Config {
	static private Properties properties = null;
	// bpmn file folder path
	static public String bpmnFolderPath = null;
	// web project root path
	static public String rootPath = null;

	static public String deployService = null;

	static public String busService = null;

	static public String monitor = null;

	static public String resultService = null;

	static public String flexmonitor = null;

	static public String flexmonitorinfo = null;
	
	static public String WSNameQueryUrl=null;
	
	static public String WebAppQueryUrl=null;
	
	static public String applianceQueryUrl=null;
	
	static public String applianceForLoadTest=null;
	
	static public String userControlUrl=null;
	
	static public String WebAppDeployUrl=null;
	
	static public String WebAppUndeployUrl=null;
	
	static public String sendDocUrl=null;
	
	static public String WebServiceDeployUrl=null;
	
	static public String WebServiceUndeployUrl=null;
	
	static public String WebServiceInvokeUrl=null;
	
	static public String BPMNMonitorUrl=null;
	
	static public String cloudTestUrl=null;
	
	static public String LogFileUrl=null;
	
	static public String BPMNMonitorInfoFeedbackReceiverUrl=null;
	
	static public String BPMNExecuteUrl=null;
	
	static {
		properties = new Properties();
		try {
			String urlPath = Config.class.getResource("conf.properties")
					.getFile();
			String path = URLDecoder.decode(urlPath);
			properties.load(new FileInputStream(path));

			init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static private void init() {
		rootPath=System.getProperty("user.dir");
		if(rootPath.endsWith("bin"))
			rootPath = rootPath.substring(0,rootPath.lastIndexOf("bin"))+"webapps/repository/";
		bpmnFolderPath = properties.getProperty("bpmnFolderPath", null);
		deployService = properties.getProperty("deployService", null);
		busService = properties.getProperty("busService", null);
		monitor = properties.getProperty("monitor", null);
		resultService = properties.getProperty("resultService", null);
		flexmonitor = properties.getProperty("flexmonitor", null);
		flexmonitorinfo = properties.getProperty("flexmonitorinfo", null);
		WSNameQueryUrl=properties.getProperty("WSNameQueryUrl",null);
		WebAppQueryUrl=properties.getProperty("WebAppQueryUrl",null);
		applianceQueryUrl=properties.getProperty("applianceQueryUrl",null);
		applianceForLoadTest=properties.getProperty("applianceForLoadTest",null);
		userControlUrl=properties.getProperty("userControlUrl",null);
		WebAppDeployUrl=properties.getProperty("WebAppDeployUrl",null);
		WebAppUndeployUrl=properties.getProperty("WebAppUndeployUrl",null);
		sendDocUrl=properties.getProperty("sendDocUrl",null);
		WebServiceDeployUrl=properties.getProperty("WebServiceDeployUrl",null);
		WebServiceUndeployUrl=properties.getProperty("WebServiceUndeployUrl",null);
		WebServiceInvokeUrl=properties.getProperty("WebServiceInvokeUrl",null);
		BPMNMonitorUrl=properties.getProperty("BPMNMonitorUrl",null);
		cloudTestUrl=properties.getProperty("cloudTestUrl",null);
		LogFileUrl=properties.getProperty("LogFileUrl",null);
		BPMNMonitorInfoFeedbackReceiverUrl=properties.getProperty("BPMNMonitorInfoFeedbackReceiverUrl",null);
		BPMNExecuteUrl=properties.getProperty("BPMNExecuteUrl",null);
		// release the properties file
		properties = null;
	}

	/**
	 * getPath("conf/a.txt") as %rootPath%/conf/a.txt,
	 * 
	 * @param path
	 * @return
	 */
	static public String getPath(String path) {
		System.out.println("rootPath+path=" + rootPath + path);
		return rootPath + path;
	}

	public static String getBusService() {
		return busService;
	}

	public static String getDeployService() {
		return deployService;
	}

	public static String getMonitor() {
		return monitor;
	}

	public static String getResultService() {
		return resultService;
	}

	public static String getflexmonitor() {
		return flexmonitor;
	}

	public static String getflexmonitorinfo() {
		return flexmonitorinfo;
	}
	public static String getWSNameQueryUrl(){
		return WSNameQueryUrl;
	}
	public static String getWebAppQueryUrl(){
		return WebAppQueryUrl;
	}
	public static String getApplianceQueryUrl(){
		return applianceQueryUrl;
	}
	public static String getApplianceForLoadTest() {
		return applianceForLoadTest;
	}
	public static String getUserControlUrl(){
		return userControlUrl;
	}
	public static String getWebAppDeployUrl(){
		return WebAppDeployUrl;
	}
	
	public static String getWebAppUndeployUrl(){
		return WebAppUndeployUrl;
	}
	public static String getSendDocUrl(){
		return sendDocUrl;
	}
	public static String getWebServiceDeployUrl(){
		return WebServiceDeployUrl;
	}
	public static String getWebServiceUndeployUrl(){
		return WebServiceUndeployUrl;
	}
	public static String getWebServiceInvokeUrl(){
		return WebServiceInvokeUrl;
	}
	public static String getBPMNMonitorUrl(){
		return BPMNMonitorUrl;
	}
	public static String getCloudTestUrl(){
		return cloudTestUrl;
	}
	public static String getLogFileUrl(){
		return LogFileUrl;
	}
	public static String getBPMNMonitorInfoFeedbackReceiverUrl(){
		return BPMNMonitorInfoFeedbackReceiverUrl;
	}
	public static String getBPMNExecuteUrl(){
		return BPMNExecuteUrl;
	}
}
