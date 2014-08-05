package cn.org.act.sdp.bpmnRepository.tool;

import java.util.List;

import cn.org.act.sdp.bpmnRepository.crust.impl.RepositoryConf;
import cn.org.act.sdp.bpmnRepository.entity.UserBean;
import cn.org.act.sdp.bpmnRepository.session.SessionFactory;
import cn.org.act.sdp.bpmnRepository.session.SessionType;
import cn.org.act.sdp.bpmnRepository.session.UserSession;

public class UserTool {
	private UserSession session = null;
	public Integer errorCode;

	/**
	 * @return
	 * @throws Exception
	 */
	private UserSession init() {

		RepositoryConf.poolInit();
		RepositoryConf.managerInit();

		try {
			session = (UserSession) (SessionFactory
					.openSession(SessionType.User));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;

	}

	public int newUser(Object o) {
		init();
		int id = session.save(o);
		((UserBean) o).setId(id);
		this.errorCode = session.errorCode;
		session.close();
		return id;
	}

	/**
	 * 
	 * @return return null if empty or error
	 */
	public UserBean getUserById(int id) {
		init();
		UserBean bean = session.getById(id);
		session.close();
		return bean;
	}

	public List<UserBean> getAllUser() {
		init();
		List<UserBean> list = session.getAll();
		session.close();
		return list;
	}

	public boolean confirm(UserBean user) {
		init();
		UserBean bean = session.getByName(user.getName());
		this.errorCode = session.errorCode;
		session.close();
		if (bean == null) {
			return false;
		}
		if (!bean.getPwd().equals(user.getPwd())) {
			return false;
		}
		user.setId(bean.getId());
		return true;
	}
}
