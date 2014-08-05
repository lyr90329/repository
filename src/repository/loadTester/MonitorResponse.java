package repository.loadTester;

import java.util.ArrayList;

public class MonitorResponse {
	private String jobId;
	private String status;
	private String subId;
	private String total;
	private String averageTime;

	private ArrayList<EngineResponse> successList = new ArrayList<EngineResponse>();
	private ArrayList<EngineResponse> failList = new ArrayList<EngineResponse>();

	public MonitorResponse() {

	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String bubId) {
		this.subId = bubId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(String averageTime) {
		this.averageTime = averageTime;
	}

	public ArrayList<EngineResponse> getSuccessList() {
		return successList;
	}

	public void setSuccessList(ArrayList<EngineResponse> successList) {
		this.successList = successList;
	}

	public ArrayList<EngineResponse> getFailList() {
		return failList;
	}

	public void setFailList(ArrayList<EngineResponse> failList) {
		this.failList = failList;
	}

}