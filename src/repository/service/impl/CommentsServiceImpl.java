package repository.service.impl;

import java.util.LinkedList;
import java.util.List;

import repository.service.CommentsService;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.CommentsTBean;
import cn.org.act.sdp.repository.cleavage.session.CommentsSession;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

public class CommentsServiceImpl implements CommentsService {

	public CommentsServiceImpl() {
		RepositoryConf.poolInit();
		SqlManagerConf.init();
	}

	@Override
	public List get(Long jobId, Long serviceId) {

		CommentsSession session = null;

		List<CommentsTBean> comments = new LinkedList<CommentsTBean>();
		Object[] paras = new String[2];
		paras[0] = jobId.toString();
		paras[1] = serviceId.toString();
		try {
			session = (CommentsSession) SessionFactory
					.openSession(SessionType.Comments);
			comments = session.get(paras);
			return comments;
		} catch (Exception e) {
			e.printStackTrace();
			return comments;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public CommentsTBean get(long id) {

		CommentsSession session = null;

		try {
			session = (CommentsSession) SessionFactory
					.openSession(SessionType.Comments);
			return session.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List getAll(Long jobId) {

		CommentsSession session = null;

		List<CommentsTBean> comments = new LinkedList<CommentsTBean>();
		try {
			session = (CommentsSession) SessionFactory
					.openSession(SessionType.Comments);
			comments = session.getAll(jobId);
			return comments;
		} catch (Exception e) {
			e.printStackTrace();
			return comments;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean save(Long jobId, Long serviceId, String comment,
			String fromUser) {

		CommentsSession session = null;

		if (fromUser == null) {
			fromUser = "-1";
		}

		CommentsTBean commentBean = new CommentsTBean();
		commentBean.setJobId(jobId);
		commentBean.setServiceId(serviceId);
		commentBean.setComment(comment);
		commentBean.setFromUser(fromUser);

		try {
			session = (CommentsSession) SessionFactory
					.openSession(SessionType.Comments);
			return session.save(commentBean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean update(Object o) {

		CommentsSession session = null;
		try {
			session = (CommentsSession) SessionFactory
					.openSession(SessionType.Comments);
			return session.update(o);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
