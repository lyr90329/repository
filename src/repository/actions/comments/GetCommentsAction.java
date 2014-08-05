package repository.actions.comments;

import java.util.LinkedList;
import java.util.List;

import cn.org.act.sdp.repository.cleavage.entity.CommentsTBean;
import cn.org.act.sdp.repository.entity.ServiceTBean;
import repository.actions.BaseAction;
import repository.service.CommentsService;
import repository.service.ServiceService;

public class GetCommentsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4467328079364204706L;
	private Long jobId = new Long(1);
	private long serviceId;
	private ServiceTBean serviceBean;
	private List<CommentsTBean> comments;
	private ServiceService serviceService;
	private CommentsService commentsService;

	public String execute() throws Exception {
		serviceBean = serviceService.getById(jobId, serviceId);
		List<CommentsTBean> commentsByService = commentsService.get(jobId,
				serviceId);
		this.getPage().setTotalLine(commentsByService.size());
		comments = new LinkedList<CommentsTBean>();
		for (int i = getPage().getBeginLine(); i <= getPage().getEndLine(); i++) {
			comments.add(commentsByService.get(i));
		}
		return SUCCESS;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public ServiceTBean getServiceBean() {
		return serviceBean;
	}

	public void setServiceBean(ServiceTBean serviceBean) {
		this.serviceBean = serviceBean;
	}

	public List<CommentsTBean> getComments() {
		return comments;
	}

	public void setComments(List<CommentsTBean> comments) {
		this.comments = comments;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public CommentsService getCommentsService() {
		return commentsService;
	}

	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}
}
