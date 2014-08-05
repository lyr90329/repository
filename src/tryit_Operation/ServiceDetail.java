package tryit_Operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import WSDL_Factory.ServiceInvo;
import WSDL_Factory.WSDL_Parse;
import WSDL_Structure.WSDL_Operations;
import WSDL_Structure.WSDL_ParameterInfo;
import WSDL_Structure.WSDL_Service;

public class ServiceDetail {

	private WSDL_Service serviceInfo;

	private String wsdlAddress;

	private ServiceDetail serviceDetail;

	private static long time1;

	private static long time2;

	private String serviceNS = null;

	private String soapA = null;

	public void getserviceDetail(String wsdlAddress) throws Exception {

		this.serviceInfo = setContent(wsdlAddress);

	}

	public WSDL_Operations getOperation(String operationName) {
		WSDL_Operations operation = new WSDL_Operations();
		Iterator iter = this.serviceInfo.getOperations();
		while (iter.hasNext()) {
			operation = (WSDL_Operations) iter.next();
			if (operation.getName().equals(operationName))
				break;
		}
		return operation;
	}

	public List getInputParamrters(String opertionName) {
		WSDL_Operations oper = null;
		List inputPara = new ArrayList();
		List inps = new ArrayList();
		oper = getOperation(opertionName);
		inps = oper.getInparameters();
		Iterator iter = inps.iterator();
		while (iter.hasNext()) {
			WSDL_ParameterInfo para = (WSDL_ParameterInfo) iter.next();
			if (para.getName().equals(opertionName)) {
				while (iter.hasNext()) {
					WSDL_ParameterInfo para1 = (WSDL_ParameterInfo) iter.next();
					if (para1.getName().equals("END"))
						break;
					inputPara.add(para1);
				}
			}
		}
		return inputPara;
	}

	public List getOutputParamrters(String opertionName) {
		WSDL_Operations oper = null;
		List outputPara = new ArrayList();
		List outps = new ArrayList();
		oper = getOperation(opertionName);
		outps = oper.getOutparameters();
		Iterator iter = outps.iterator();
		while (iter.hasNext()) {
			WSDL_ParameterInfo para = (WSDL_ParameterInfo) iter.next();
			if (para.getName().equals(opertionName)) {
				while (iter.hasNext()) {
					WSDL_ParameterInfo para1 = (WSDL_ParameterInfo) iter.next();
					if (para1.getName().equals("END"))
						break;
					outputPara.add(para1);
				}
			}
		}
		return outputPara;
	}

	public static void main(String[] args) {
		ArrayList testInputParaList = new ArrayList();
		ArrayList testOutputParaList = new ArrayList();
		ServiceDetail test = new ServiceDetail();
		test
				.setWsdlAddress("http://localhost:8080/axis2/services/myFirstWS?wsdl");
		try {
			test.getserviceDetail(test.wsdlAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testInputParaList = (ArrayList) test.getInputParamrters("add");
		testOutputParaList = (ArrayList) test
				.getOutputParamrters("add");
		Iterator testIter = testInputParaList.iterator();
		WSDL_ParameterInfo para0 = null;
		System.out.println("此操作所需的输入参数为:");
		while (testIter.hasNext()) {
			para0 = (WSDL_ParameterInfo) testIter.next();
			System.out.println(para0.getName().toString());
			System.out.println(para0.getType().toString());
			para0.setValue("20");
			System.out.println(para0.getValue().toString());
		}
		test
				.runWebService(testInputParaList, testOutputParaList,
						"add");
	}

	public WSDL_Service setContent(String content) throws Exception {

		WSDL_Operations oper1 = new WSDL_Operations();
		WSDL_Service serviceInfo1;
		ArrayList paraList = new ArrayList();
		WSDL_Service serviceInfo = new WSDL_Service();
		WSDL_Parse parse = new WSDL_Parse(content);
		serviceInfo = parse.getServiceList();
		serviceInfo1 = serviceInfo;
		this.serviceNS = parse.getNS();
		this.soapA = parse.getSoapA();
		System.out.println("");
		Iterator iter = serviceInfo.getOperations();
		System.out.println("现在可以查看远端Web服务对象的有关情况了(对应本地Web服务类,ServiceInfo)");
		System.out.println(serviceInfo.getName() + "提供的操作有:");
		return serviceInfo;
	}

	public List runWebService(ArrayList inputParaList, List outputParaList,
			String operationName) {
		ServiceInvo serviceinvo = new ServiceInvo();
		List list1 = new ArrayList();
		WSDL_Operations oper1 = this.getOperation(operationName);
		list1 = serviceinvo.invokeWebService(this.soapA, oper1, this.serviceNS,
				inputParaList, outputParaList);
		return list1;
	}
	
	public List setParametersValue(ArrayList inputParaList,String parameterName,String value){
		Iterator iter = inputParaList.iterator();
		WSDL_ParameterInfo para = null;
		while (iter.hasNext()) {
			para = (WSDL_ParameterInfo) iter.next();
			if (para.getName().equals(parameterName))
			para.setValue(value);
		}	
		return null;
		
		
	}

	public WSDL_Service getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(WSDL_Service serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public String getWsdlAddress() {
		return wsdlAddress;
	}

	public void setWsdlAddress(String wsdlAddress) {
		this.wsdlAddress = wsdlAddress;
	}
}
