package repository.loadTester;

public class EngineResponse {

	private String jobId;
	private boolean issuccessful;
	private String lastTime;
	private String url;
	private String remarks;

	public EngineResponse() {

	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public boolean isIssuccessful() {
		return issuccessful;
	}

	public void setIssuccessful(boolean issuccessful) {
		this.issuccessful = issuccessful;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}