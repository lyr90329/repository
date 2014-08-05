package repository.entity;

import java.util.List;

import cn.org.act.sdp.repository.entity.ServiceTBean;

public class Category {
	// Cluster 的 id
	private long clusterId = -1;
	//属于本cluster的serviceTBean的集合
	private List<ServiceTBean> services;

	private int size;

	public Category() {
	}

	public long getClusterId() {
		return clusterId;
	}

	public void setClusterId(long clusterId) {
		this.clusterId = clusterId;
	}

	public List<ServiceTBean> getServices() {
		return services;
	}

	public void setServices(List<ServiceTBean> services) {
		this.services = services;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
