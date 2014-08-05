package repository.service.impl;

import java.util.List;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cloud.entity.UserTBean;
import cn.org.act.sdp.repository.cloud.session.SessionFactory;
import cn.org.act.sdp.repository.cloud.session.SessionType;
import cn.org.act.sdp.repository.cloud.session.UserSession;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;
import repository.service.UserService;

public class UserServiceImpl implements UserService {

	public UserServiceImpl() {
		RepositoryConf.managerInit();
		RepositoryConf.poolInit();
	}

	@Override
	public boolean add(UserTBean bean) {

		UserSession userSession = null;

		try {
			userSession = (UserSession) SessionFactory
					.openSession(SessionType.user);
			userSession.save(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return false;
		} finally {
			if (userSession != null)
				userSession.close();
		}
		return true;
	}

	@Override
	public boolean delete(String userName) {

		UserSession userSession = null;

		Object[] para = null;
		para = new Object[1];
		para[0] = userName;

		try {
			userSession = (UserSession) SessionFactory
					.openSession(SessionType.user);
			userSession.delete(para);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return false;
		} finally {
			if (userSession != null)
				userSession.close();
		}
		return true;
	}

	@Override
	public UserTBean get(String userName) {

		UserSession userSession = null;

		Object[] para = null;
		para = new Object[1];
		para[0] = userName;

		UserTBean bean = null;
		try {
			userSession = (UserSession) SessionFactory
					.openSession(SessionType.user);
			bean = (UserTBean) userSession.get(para);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return null;
		} finally {
			if (userSession != null)
				userSession.close();
		}
		return bean;
	}

	@Override
	public List getAll() {

		UserSession userSession = null;

		List<UserTBean> users = null;

		try {
			userSession = (UserSession) SessionFactory
					.openSession(SessionType.user);
			users = userSession.getAll();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return null;
		} finally {
			if (userSession != null)
				userSession.close();
		}
		return users;
	}

	@Override
	public boolean update(UserTBean bean) {

		UserSession userSession = null;

		try {
			userSession = (UserSession) SessionFactory
					.openSession(SessionType.user);
			userSession.update(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return false;
		} finally {
			if (userSession != null)
				userSession.close();
		}
		return true;
	}

}
