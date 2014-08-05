package db.entity;

public class UploadService {
	private int serviceId;
	private String userName;
	private String serviceName;
	private String serviceUrl;
	private String deployStatus;
	private String runStatus;
	private String deployId;

	public UploadService() {

	}

	public UploadService(int serviceId, String userName, String serviceName,
			String serviceUrl, String deployStatus, String runStatus,
			String deployId) {
		this.serviceId = serviceId;
		this.userName = userName;
		this.serviceName = serviceName;
		this.serviceUrl = serviceUrl;
		this.deployStatus = deployStatus;
		this.runStatus = runStatus;
		this.deployId = deployId;
	}

	public UploadService(String userName, String serviceName,
			String serviceUrl, String deployStatus, String runStatus,
			String deployId) {

		this.userName = userName;
		this.serviceName = serviceName;
		this.serviceUrl = serviceUrl;
		this.deployStatus = deployStatus;
		this.runStatus = runStatus;
		this.deployId = deployId;
	}

	public String getDeployID() {
		return deployId;
	}

	public void setDeployID(String deployStatus) {
		this.deployId = deployStatus;
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

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}
}
