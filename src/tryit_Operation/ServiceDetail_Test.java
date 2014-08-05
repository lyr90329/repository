package tryit_Operation;

import java.util.ArrayList;

public class ServiceDetail_Test {

	public static void main(String arg[]) {

		ServiceDetail test = new ServiceDetail();
		test
				.setWsdlAddress("http://192.168.3.252:8080/axis2/services/Sayhello?wsdl");
		try {
			test.getserviceDetail(test.getWsdlAddress());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList testInputParaList = new ArrayList();
		ArrayList testOutputParaList = new ArrayList();
		testInputParaList = (ArrayList) test.getInputParamrters("getGreeting");
		testOutputParaList = (ArrayList) test
				.getOutputParamrters("getGreeting");
		test.setParametersValue(testInputParaList, "a", "10");
		test.setParametersValue(testInputParaList, "b", "230.5");
		test.runWebService(testInputParaList, testOutputParaList, "add");
	}

}
