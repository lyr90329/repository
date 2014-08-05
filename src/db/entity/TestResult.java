package db.entity;

public class TestResult {
	private String userName;
	private String success;
	private String fail;
	private String average;

	private String serviceName;
	private String testOperation;
	private String url;
	private String type;
	private String strategy;
	private String testNum;
	private String timeout;

	public TestResult(String userName, String success, String fail,
			String average) {
		this.userName = userName;
		this.success = success;
		this.fail = fail;
		this.average = average;
	}

	public TestResult() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getTestOperation() {
		return testOperation;
	}

	public void setTestOperation(String testOperation) {
		this.testOperation = testOperation;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getTestNum() {
		return testNum;
	}

	public void setTestNum(String testNum) {
		this.testNum = testNum;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

}