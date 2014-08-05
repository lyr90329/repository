package repository.service;

import java.util.List;

import repository.entity.Category;

import cn.org.act.sdp.repository.cleavage.entity.ServiceClusterTBean;

/**
 * ServiceCluster定义的接口
 * @author Mandula
 * 
 */
public interface ServiceClusterService {

	public List getDistinctClusterId();

	public List<ServiceClusterTBean> getServicesByClusterId(long clusterId);

	public List<Category> getCategory(int clusterNum);

	public List<Category> getSimpleCategory(int clusterNum);

	public List<Category> getCategory();
}
