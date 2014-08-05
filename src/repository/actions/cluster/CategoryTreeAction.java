package repository.actions.cluster;

import java.util.List;

import cn.org.act.sdp.repository.entity.ServiceTBean;

import repository.actions.BaseAction;
import repository.entity.Category;
import repository.service.ServiceClusterService;
import repository.service.ServiceService;

public class CategoryTreeAction extends BaseAction {

	private static final long serialVersionUID = 2327035601322916191L;

	private int clusterNum; // 默认获取Category的数量

	private List<Category> categories;
	private ServiceClusterService serviceClusterService;

	public String execute() {
		categories = serviceClusterService.getSimpleCategory(clusterNum);
		return SUCCESS;
	}

	public int getClusterNum() {
		return clusterNum;
	}

	public void setClusterNum(int clusterNum) {
		this.clusterNum = clusterNum;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public ServiceClusterService getServiceClusterService() {
		return serviceClusterService;
	}

	public void setServiceClusterService(
			ServiceClusterService serviceClusterService) {
		this.serviceClusterService = serviceClusterService;
	}
}
