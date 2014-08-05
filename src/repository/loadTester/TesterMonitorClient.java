package repository.loadTester;

import java.util.Iterator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class TesterMonitorClient {

	// private MonitorResponse monitorResponse =new MonitorResponse();

	public TesterMonitorClient() {

	}

	public MonitorResponse invoke(String jobId, String url) throws AxisFault {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMElement monitorRequest = fac.createOMElement("monitorRequest", null);
		OMElement jobIdOM = fac.createOMElement("jobId", null);
		jobIdOM.setText(jobId);
		monitorRequest.addChild(jobIdOM);

		// System.out.println("monitorRequest Soap:"+monitorRequest.toString());

		Options options = new Options();
		EndpointReference epr = new EndpointReference(url);
		options.setTo(epr);
		options.setTimeOutInMilliSeconds(20000);
		ServiceClient sender = new ServiceClient();
		sender.setOptions(options);
		OMElement responseOME = sender.sendReceive(monitorRequest);
		// System.out.println("monitor response: "+responseOME);
		MonitorResponse monitorResponse = null;
		try {
			monitorResponse = parseMonitorResponse(responseOME);
		} catch (Exception e) {
			System.out
					.println("something wrong happeded when parsing monitorResponse!");
			e.printStackTrace();
		}

		return monitorResponse;
	}

	private MonitorResponse parseMonitorResponse(OMElement response)
			throws Exception {
		MonitorResponse monitor = new MonitorResponse();
		monitor.getSuccessList().clear();
		monitor.getFailList().clear();

		Iterator iter = response.getChildElements();
		while (iter.hasNext()) {
			OMElement elem = (OMElement) iter.next();
			if (elem.getLocalName().equals("jobId")) {
				monitor.setJobId(elem.getText());
				Iterator attrs = elem.getAllAttributes();
				while (attrs.hasNext()) {
					OMAttribute attr = (OMAttribute) attrs.next();
					if (attr.getLocalName().equals("status"))
						monitor.setStatus(attr.getAttributeValue());
					else if (attr.getLocalName().equals("subId"))
						monitor.setSubId(attr.getAttributeValue());
					else
						throw new Exception("parsing monitorResponse error!");
				}
			} else if (elem.getLocalName().equals("total")) {
				monitor.setTotal(elem.getText());
			} else if (elem.getLocalName().equals("averageTime")) {
				monitor.setAverageTime(elem.getText());
			} else if (elem.getLocalName().equals("completed")) {
				Iterator subOM = elem.getChildElements();
				while (subOM.hasNext()) {
					OMElement succ_fail = (OMElement) subOM.next();
					if (succ_fail.getLocalName().equals("success")) {
						Iterator testEngineResponse = succ_fail
								.getChildElements();// testEngineResponse
						OMElement succ_res = null;
						OMElement testEngineResponse_node = null;
						if (testEngineResponse != null
								&& testEngineResponse.hasNext())
							testEngineResponse_node = (OMElement) testEngineResponse
									.next();
						if (testEngineResponse_node != null) {
							Iterator succ_responses = testEngineResponse_node
									.getChildElements();//
							while (succ_responses.hasNext()) {
								EngineResponse engine_res = new EngineResponse();
								OMElement succ_executeResult = (OMElement) succ_responses
										.next();
								if (succ_executeResult.getLocalName().equals(
										"executeResult"))// executeResult
								{
									Iterator executeResult_attrs = succ_executeResult
											.getAllAttributes();
									while (executeResult_attrs.hasNext()) {
										OMAttribute executeResult_attr = (OMAttribute) executeResult_attrs
												.next();
										if (executeResult_attr.getLocalName()
												.equals("jobId")) {
											engine_res
													.setJobId(executeResult_attr
															.getAttributeValue());
										} else if (executeResult_attr
												.getLocalName().equals(
														"issuccessful")) {
											engine_res
													.setIssuccessful(Boolean
															.valueOf(executeResult_attr
																	.getAttributeValue()));
										} else if (executeResult_attr
												.getLocalName().equals(
														"lastTime")) {
											engine_res
													.setLastTime(executeResult_attr
															.getAttributeValue());
										} else if (executeResult_attr
												.getLocalName().equals("url")) {
											engine_res
													.setUrl(executeResult_attr
															.getAttributeValue());
										} else {
											throw new Exception(
													"parsing monitorResponse error!");
										}
									}
								} else if (succ_res.getLocalName().equals(
										"remarks"))// remarks
								{
									engine_res.setRemarks(succ_res.getText());
								}
								monitor.getSuccessList().add(engine_res);
								// System.out.println("Sucess Num:"+monitor.getSuccessList().size());
							}
						}

					} else if (succ_fail.getLocalName().equals("fail")) {
						Iterator testEngineResponse = succ_fail
								.getChildElements();// testEngineResponse
						OMElement succ_res = null;
						OMElement testEngineResponse_node = null;
						if (testEngineResponse != null
								&& testEngineResponse.hasNext())
							testEngineResponse_node = (OMElement) testEngineResponse
									.next();
						if (testEngineResponse_node != null) {
							Iterator fail_responses = testEngineResponse_node
									.getChildElements();// testEngineResponse
							while (fail_responses.hasNext()) {
								EngineResponse engine_res = new EngineResponse();
								OMElement fail_res = (OMElement) fail_responses
										.next();
								if (fail_res.getLocalName().equals(
										"executeResult"))// executeResult
								{
									Iterator executeResult_attrs = fail_res
											.getAllAttributes();
									while (executeResult_attrs.hasNext()) {
										OMAttribute executeResult_attr = (OMAttribute) executeResult_attrs
												.next();
										if (executeResult_attr.getLocalName()
												.equals("jobId")) {
											engine_res
													.setJobId(executeResult_attr
															.getAttributeValue());
										} else if (executeResult_attr
												.getLocalName().equals(
														"issuccessful")) {
											engine_res
													.setIssuccessful(Boolean
															.valueOf(executeResult_attr
																	.getAttributeValue()));
										} else if (executeResult_attr
												.getLocalName().equals(
														"lastTime")) {
											engine_res
													.setLastTime(executeResult_attr
															.getAttributeValue());
										} else if (executeResult_attr
												.getLocalName().equals("url")) {
											engine_res
													.setUrl(executeResult_attr
															.getAttributeValue());
										} else {
											throw new Exception(
													"parsing monitorResponse error!");
										}
									}
								} else if (fail_res.getLocalName().equals(
										"remarks"))// remarks
								{
									engine_res.setRemarks(fail_res.getText());
								}
								monitor.getFailList().add(engine_res);
								// System.out.println("Fail Num:"+monitor.getFailList().size());
							}
						}
					} else {
						throw new Exception("parsing monitorResponse error!");
					}
				}
			} else {
				throw new Exception("parsing monitorResponse error!");
			}

		}

		return monitor;
	}

	public static void main(String[] args) {
		TesterMonitorClient client = new TesterMonitorClient();
		try {

			MonitorResponse monitorResponse = client.invoke("430",
					"http://124.205.18.169:8192/cloudtest/TestJobMonitor/");
			System.out.println("AverageTime:"
					+ monitorResponse.getAverageTime());
			System.out.println("Status:" + monitorResponse.getStatus());
			System.out.println("SuccessListNum:"
					+ monitorResponse.getSuccessList().size());
			System.out.println("FailListNum:"
					+ monitorResponse.getFailList().size());

		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
}