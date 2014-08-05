package db.entity;

public class UploadBpmn {
	private int bpmnId;
	private String userName;
	private String bpmnName;
	private String bpmnUrl;
	private String deployStatus;
	private String runStatus;
	private int jobId;

	public UploadBpmn() {
	}

	public UploadBpmn(int bpmnId, String userName, String bpmnName,
			String bpmnUrl, String deployStatus, String runStatus, int jobId) {

		this.setBpmnId(bpmnId);
		this.setUserName(userName);
		this.setBpmnName(bpmnName);
		this.setBpmnUrl(bpmnUrl);
		this.setDeployStatus(deployStatus);
		this.setRunStatus(runStatus);
		this.setJobId(jobId);

	}

	public UploadBpmn(String userName, String bpmnName, String bpmnUrl,
			String deployStatus, String runStatus, int jobId) {

		this.setUserName(userName);
		this.setBpmnName(bpmnName);
		this.setBpmnUrl(bpmnUrl);
		this.setDeployStatus(deployStatus);
		this.setRunStatus(runStatus);
		this.setJobId(jobId);

	}

	public UploadBpmn(String userName, String bpmnName, String bpmnUrl,
			String deployStatus, String runStatus) {

		this.setUserName(userName);
		this.setBpmnName(bpmnName);
		this.setBpmnUrl(bpmnUrl);
		this.setDeployStatus(deployStatus);
		this.setRunStatus(runStatus);

	}

	public void setBpmnId(int bpmnId) {
		this.bpmnId = bpmnId;
	}

	public int getBpmnId() {
		return bpmnId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setBpmnName(String bpmnName) {
		this.bpmnName = bpmnName;
	}

	public String getBpmnName() {
		return bpmnName;
	}

	public void setBpmnUrl(String bpmnUrl) {
		this.bpmnUrl = bpmnUrl;
	}

	public String getBpmnUrl() {
		return bpmnUrl;
	}

	public void setDeployStatus(String deployStatus) {
		this.deployStatus = deployStatus;
	}

	public String getDeployStatus() {
		return deployStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getJobId() {
		return jobId;
	}

}
