package cn.org.act.sdp.bpmnRepository.portal.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Properties;

public class Config {
	static private String CONFIG_FILE_PATH = "conf/conf.properties";

	static private Properties properties = null;
	// bpmn file folder path
	static public String bpmnFolderPath = null;
	// image files folder path
	static public String imgFolderPath = null;
	// web project root path
	static public String rootPath = null;

	static {
		String path;
		try {
			// get the root path of web project and added with "BPMN-PR/"
			path = Config.class.getResource("/").toURI().getPath();
			path = path.substring(1, path.length() - 1);
			String str = "WEB-INF/classes";
			int i = path.indexOf(str);
			if (i > 0) {
				path = path.substring(0, i);
				path += "BPMN-PR/";
			} else
				path = null;
			rootPath = path;

			init();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	static private void init() {
		properties = new Properties();
		try {
			String path = getPath(CONFIG_FILE_PATH);
			properties.load(new FileInputStream(path));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		bpmnFolderPath = properties.getProperty("bpmnFolderPath", null);
		imgFolderPath = properties.getProperty("imgFolderPath", null);
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
		return rootPath + path;
	}
}
