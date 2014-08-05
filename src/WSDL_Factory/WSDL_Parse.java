package WSDL_Factory;

import WSDL_Structure.WSDL_Service;

public class WSDL_Parse {

	WSDL_Service serviceInfo = new WSDL_Service();
	String serviceNS = null;
	String soapA = null;

	public WSDL_Parse(String location) throws Exception {
		System.out.println("s-load");
		WSDL_Parse_Builder builder = new WSDL_Parse_Builder();

		String wsdllocation = location;
		serviceInfo.setWsdllocation(wsdllocation);
		// try {
		serviceInfo = builder.buildserviceinformation(serviceInfo);
		serviceNS = builder.ns;
		soapA = builder.SoapA;
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		System.out.println("f-load");
	}

	public WSDL_Service getServiceList() {
		return this.serviceInfo;
	}

	public String getNS() {
		return this.serviceNS;
	}

	public String getSoapA() {
		return this.soapA;
	}

}
