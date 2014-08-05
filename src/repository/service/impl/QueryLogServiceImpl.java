package repository.service.impl;

import java.util.LinkedList;
import java.util.List;

import cn.org.act.sdp.repository.cloud.entity.QueryLogTBean;
import cn.org.act.sdp.repository.cloud.entity.ServiceLogTBean;
import cn.org.act.sdp.repository.cloud.session.QueryLogSession;
import cn.org.act.sdp.repository.cloud.session.ServiceLogSession;
import cn.org.act.sdp.repository.cloud.session.SessionFactory;
import cn.org.act.sdp.repository.cloud.session.SessionType;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

import repository.service.QueryLogService;

public class QueryLogServiceImpl implements QueryLogService {

	public QueryLogServiceImpl() {
		RepositoryConf.managerInit();
		RepositoryConf.poolInit();
	}

	@Deprecated
	@Override
	public boolean delete(Object[] parameters) {
		// TODO Auto-generated method stub
		return false;
	}

	@Deprecated
	@Override
	public Object get(Object[] parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List get(String name) {

		QueryLogSession queryLogSession = null;
		List<QueryLogTBean> list = new LinkedList<QueryLogTBean>();

		try {
			queryLogSession = (QueryLogSession) SessionFactory
					.openSession(SessionType.queryLog);
			list = queryLogSession.get(name);
			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return list;
		} finally {
			if (queryLogSession != null)
				queryLogSession.close();
		}
	}

	@Override
	public boolean save(Object o) {
		QueryLogSession queryLogSession = null;

		try {
			queryLogSession = (QueryLogSession) SessionFactory
					.openSession(SessionType.queryLog);
			return queryLogSession.save(o);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return false;
		} finally {
			if (queryLogSession != null)
				queryLogSession.close();
		}
	}

	@Deprecated
	@Override
	public boolean update(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

}
