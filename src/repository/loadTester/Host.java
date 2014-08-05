package repository.loadTester;

import java.util.ArrayList;
import java.util.Date;

public class Host {
	private ArrayList<SubAppliance> host = new ArrayList<SubAppliance>();
	private String childApplianceNum = "";
	private String cpu = "";
	private String id = "";
	private String memory = "";
	private String timestamp = "";

	public ArrayList<SubAppliance> getHost() {
		return host;
	}

	public void setHost(ArrayList<SubAppliance> host) {
		this.host = host;
	}

	public String getChildApplianceNum() {
		return childApplianceNum;
	}

	public void setChildApplianceNum(String childApplianceNum) {
		this.childApplianceNum = childApplianceNum;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getTimestamp() {
		Date date = new Date(Long.parseLong(timestamp));
		return date.toString();
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}