package repository.actions.comments;

import cn.org.act.sdp.repository.cleavage.entity.CommentsTBean;
import repository.actions.BaseAction;
import repository.service.CommentsService;

/**
 * 对comments的支持或反对
 * @author Mandla
 *
 */
public class AppraiseCommentsAction extends BaseAction {

	private static final long serialVersionUID = -4191000627915005949L;
	
	private long id;
	private CommentsService commentsService;
	
	/**
	 * 支持
	 * @return
	 * @throws Exception
	 */
	public String up()throws Exception{
		CommentsTBean comment = null;
		comment = commentsService.get(id);
		if(comment!=null){
			comment.setUp(comment.getUp()+1);
			commentsService.update(comment);
		}
		return SUCCESS;
	}
	
	/**
	 * 反对
	 * @return
	 * @throws Exception
	 */
	public String down()throws Exception{
		CommentsTBean comment = null;
		comment = commentsService.get(id);
		if(comment!=null){
			comment.setDown(comment.getDown()+1);
			commentsService.update(comment);
		}
		return SUCCESS;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CommentsService getCommentsService() {
		return commentsService;
	}

	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}
}
