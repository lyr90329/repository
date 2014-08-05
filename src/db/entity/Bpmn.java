package db.entity;

public class Bpmn {
	private int bpmnId;
	private String userName;
	private String bpmnName;
	private String deployStatus;
	private String runStatus;
	private int jobId;

	public Bpmn() {
	}

	public Bpmn(int bpmnId, String userName, String bpmnName,
			String deployStatus, String runStatus, int jobId) {
		this.bpmnId = bpmnId;
		this.userName = userName;
		this.bpmnName = bpmnName;
		this.deployStatus = deployStatus;
		this.runStatus = runStatus;
		this.jobId = jobId;
	}

	public int getBpmnId() {
		return bpmnId;
	}

	public void setBpmnId(int bpmnId) {
		this.bpmnId = bpmnId;
	}

	public String getBpmnName() {
		return bpmnName;
	}

	public void setBpmnName(String bpmnName) {
		this.bpmnName = bpmnName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

}