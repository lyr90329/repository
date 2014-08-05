package repository.actions.comments;

import repository.actions.BaseAction;
import repository.service.CommentsService;

import com.opensymphony.xwork2.ActionContext;

public class SaveCommentsAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4287188826039383147L;
	private long serviceId;
	private String comment;
	private CommentsService commentsService;

	public String execute() throws Exception {
		String fromUser = null;
		Long jobId = new Long(1);
		ActionContext ctx = ActionContext.getContext();
		if (ctx.getSession().get("userName") != null)
			fromUser = (String) ctx.getSession().get("userName");
		commentsService.save(jobId, serviceId, comment, fromUser);
		return SUCCESS;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public CommentsService getCommentsService() {
		return commentsService;
	}

	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}

}
