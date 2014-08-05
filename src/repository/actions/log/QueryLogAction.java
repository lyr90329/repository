package repository.actions.log;

import java.util.LinkedList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;

import cn.org.act.sdp.repository.cloud.entity.QueryLogTBean;

import repository.actions.BaseAction;
import repository.entity.QueryLogBean;
import repository.service.QueryLogService;
import repository.service.ServiceService;

public class QueryLogAction extends BaseAction {

	private static final long serialVersionUID = 4724617604433388857L;

	private List<QueryLogTBean> queryLogTList;
	private List<QueryLogBean> queryLogList;

	private ServiceService serviceService;
	private QueryLogService queryLogService;

	public String getQueryLog() throws Exception {
		String userName = null;
		QueryLogBean queryLogBean;
		ActionContext ctx = ActionContext.getContext();
		userName = (String) ctx.getSession().get("userName");
		queryLogTList = queryLogService.get(userName);
		queryLogList = new LinkedList<QueryLogBean>();
		for (int i = 0; i < queryLogTList.size(); i++) {
			queryLogBean = new QueryLogBean();
			queryLogBean.setUserName(userName);
			queryLogBean.setKeyword(queryLogTList.get(i).getKeyword());
			queryLogBean.setServiceId(queryLogTList.get(i).getServiceId());
			queryLogBean.setServiceName(serviceService.getById(new Long(1),
					queryLogTList.get(i).getServiceId()).getName());
			queryLogBean.setTimestamp(queryLogTList.get(i).getTimestamp());
			queryLogList.add(queryLogBean);
		}
		return SUCCESS;
	}

	public List<QueryLogTBean> getQueryLogTList() {
		return queryLogTList;
	}

	public void setQueryLogTList(List<QueryLogTBean> queryLogTList) {
		this.queryLogTList = queryLogTList;
	}

	public List<QueryLogBean> getQueryLogList() {
		return queryLogList;
	}

	public void setQueryLogList(List<QueryLogBean> queryLogList) {
		this.queryLogList = queryLogList;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public QueryLogService getQueryLogService() {
		return queryLogService;
	}

	public void setQueryLogService(QueryLogService queryLogService) {
		this.queryLogService = queryLogService;
	}
}
