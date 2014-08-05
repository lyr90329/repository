package repository.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import cn.org.act.sdp.repository.cleavage.entity.FeedBackTBean;

public class UserFeedbackClient {
	

	public String getResourceDBUrl() throws IOException {
		String resourceDBUrl=null;
		Properties p = new Properties();
		InputStream in = this.getClass().getResourceAsStream("/config.properties");
		p.load(in); 
		in.close();
  
		String url = p.getProperty("url");

		resourceDBUrl = new String("http://" + url
				+ ":8088/axis2/services/UserFeedback");
		

		return resourceDBUrl;
	}

	public boolean userAddIntoDB(FeedBackTBean fdbean) throws IOException {
		// 使用RPC方式调用WebService
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		// 指定调用userAddInfo的URL

		String resourceDBUrl = getResourceDBUrl();

		System.out.println("resourceDBUrl is " + resourceDBUrl);
		EndpointReference targetEPR = new EndpointReference(resourceDBUrl);
		options.setTo(targetEPR);
		// 指定userAddInfo方法的参数
		Object[] opAddEntryArgs = new Object[] { fdbean.getJobId(),
				fdbean.getServiceId(), fdbean.getCorrectness(),
				fdbean.getExecuteTime(), fdbean.getRespondVelocity(),
				fdbean.getPrice(), fdbean.getReputation(),
				fdbean.getReliability(), fdbean.getStability(),
				fdbean.getCompatibilty() ,fdbean.isLogIn()};

		// 指定userAddInfo方法返回值的数据类型的Class对象
		Class[] classes = new Class[] { boolean.class };
		// 指定要调用的userAddInfo方法及WSDL文件的命名空间
		QName opAddEntry = new QName("http://sdp.act.org.cn", "userAddInfo");
		// 调用userAddInfo方法并保存该方法的返回值
		// if(serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs,
		// classes)[0].equals(true))
		boolean result = (Boolean) serviceClient.invokeBlocking(opAddEntry,
				opAddEntryArgs, classes)[0];
		if (result)
			return true;
		return false;
	}
}
