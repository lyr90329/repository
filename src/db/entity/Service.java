package db.entity;

public class Service {
	private int serviceId;
	private String userName;
	private String serviceName;
	private String deployStatus;
	private String runStatus;
	private String wsdlurl;

	public Service() {
	}

	public Service(int serviceId, String userName, String serviceName,
			String wsdlurl, String deployStatus, String runStatus) {
		this.serviceId = serviceId;
		this.userName = userName;
		this.serviceName = serviceName;
		this.deployStatus = deployStatus;
		this.runStatus = runStatus;
		this.wsdlurl = wsdlurl;
	}

	public String getWsdlurl() {
		return wsdlurl;
	}

	public void setWsdlurl(String wsdlurl) {
		this.wsdlurl = wsdlurl;
	}

	public String getDeployStatus() {
		return deployStatus;
	}

	public void setDeployStatus(String deployStatus) {
		this.deployStatus = deployStatus;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
