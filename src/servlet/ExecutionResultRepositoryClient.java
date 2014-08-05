package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ExecutionResultRepositoryClient {

	private EndpointReference targetEPR;
	private Options options;
	private ServiceClient sender;
	private OMFactory omFactory;
	private OMNamespace omNs;

	static Logger logger = Logger
			.getLogger(ExecutionResultRepositoryClient.class.getName());

	public ExecutionResultRepositoryClient(String url) {
		try {
			PropertyConfigurator.configure("log4j.properties");
			targetEPR = new EndpointReference(url);
			options = new Options();
			options.setTo(targetEPR);
			sender = new ServiceClient();
			sender.setOptions(options);

			omFactory = OMAbstractFactory.getOMFactory();
			omNs = omFactory.createOMNamespace(
					"http://executeresultrepository.sdp.act.org.cn", "tns");

		} catch (Exception axisFault) {
			axisFault.printStackTrace();
			System.exit(0);
		}
	}

	public boolean insertExecuteResult(String compositeServiceName, long jobId,
			File resultFile) {

		boolean isSucceeded = false;
		OMElement data = omFactory.createOMElement("insertExecuteResult", omNs);

		OMElement composServiceName = omFactory.createOMElement(
				"compositeServiceName", omNs);
		composServiceName
				.addChild(omFactory.createOMText(compositeServiceName));
		data.addChild(composServiceName);

		OMElement jId = omFactory.createOMElement("jobId", omNs);
		jId.addChild(omFactory.createOMText(Long.toString(jobId)));
		data.addChild(jId);

		FileDataSource dataSource = new FileDataSource(resultFile);
		DataHandler dh = new DataHandler(dataSource);
		OMText textData = omFactory.createOMText(dh, true);
		OMElement result = omFactory.createOMElement("result", omNs);
		result.addChild(textData);
		data.addChild(result);

		logger.info(data);

		try {
			OMElement response = sender.sendReceive(data);
			logger.info(response);
			isSucceeded = Boolean.parseBoolean(response.getFirstElement().getText());
		} catch (Exception e) {
			e.printStackTrace();
			return isSucceeded;
		} 
		return isSucceeded;
	}

	/**
	 * get the job's result file's absolute path
	 * @param jobId the job's id
	 * @throws Exception
	 */
	public String getExecuteResult(long jobId) {
		// 将收到的result XML文件写入本地result文件夹下
		String resultFilePath = null;
		FileOutputStream resultFileOs = null;

		// construct getExecuteResult operation's input omelement
		OMElement data = omFactory.createOMElement("getExecuteResult", omNs);
		OMElement jId = omFactory.createOMElement("jobId", omNs);
		jId.addChild(omFactory.createOMText(jId, Long.toString(jobId)));
		data.addChild(jId);
		logger.info(data);

		// receive response OMELEMENT
		try {
			OMElement result = sender.sendReceive(data).getFirstElement();
			logger.info(result);
			if (result.getFirstElement() != null) {
				OMElement returnOm = result.getFirstElement();
				logger.info(returnOm);
				OMText resultText = (OMText) returnOm.getFirstOMChild();
				logger.info(resultText.getText());
				resultText.setOptimize(true);
				DataHandler dh = (DataHandler) resultText.getDataHandler();
				resultFileOs = new FileOutputStream(resultFilePath);
				dh.writeTo(resultFileOs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (resultFileOs != null)
					resultFileOs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultFilePath;
	}

	public boolean deleteExecuteResult(long jobId) {
		boolean isSucceeded = false;

		// construct deleteExecuteResult operation's input omelement
		OMElement data = omFactory.createOMElement("deleteExecuteResult", omNs);
		OMElement jId = omFactory.createOMElement("jobId", omNs);
		jId.addChild(omFactory.createOMText(Long.toString(jobId)));
		data.addChild(jId);
		logger.info(data);

		// receive response omelement
		try {
			OMElement response = sender.sendReceive(data);
			logger.info(response);
			isSucceeded = Boolean.parseBoolean((response.getFirstElement().getText()));
		} catch (Exception e) {
			e.printStackTrace();
			return isSucceeded;
		}
		return isSucceeded;
	}
}
