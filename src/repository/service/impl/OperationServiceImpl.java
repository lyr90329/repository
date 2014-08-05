package repository.service.impl;

import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;

import repository.service.OperationService;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.OperationTBean;
import cn.org.act.sdp.repository.cleavage.entity.UserTBean;
import cn.org.act.sdp.repository.cleavage.query.IQuery;
import cn.org.act.sdp.repository.cleavage.query.impl.IQueryImpl;
import cn.org.act.sdp.repository.cleavage.session.OperationSession;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.cleavage.session.UserSession;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;
import cn.org.act.sdp.repository.pool.ConnectionPool;

public class OperationServiceImpl implements OperationService {

	// private OperationSession session;

	public List<OperationTBean> getByServiceId(Long jobId, Long serviceId) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		OperationSession session = null;

		List<OperationTBean> operations = new LinkedList<OperationTBean>();
		String[] parameters = new String[2];
		parameters[0] = jobId.toString();
		parameters[1] = serviceId.toString();
		try {
			session = (OperationSession) SessionFactory
					.openSession(SessionType.Operation);
			operations = session.getByServiceId(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return operations;
	}

	public OperationTBean getByName(Long jobId, String operationName) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		OperationSession session = null;

		OperationTBean operationBean = new OperationTBean();
		String[] parameters = new String[2];
		parameters[0] = jobId.toString();
		parameters[1] = operationName;
		try {
			session = (OperationSession) SessionFactory
					.openSession(SessionType.Operation);
			operationBean = (OperationTBean) session.get(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return operationBean;
	}

	public OperationTBean getById(Long jobId, Long operationId) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		OperationSession session = null;

		OperationTBean operationBean = new OperationTBean();
		String[] parameters = new String[2];
		parameters[0] = jobId.toString();
		parameters[1] = operationId.toString();
		try {
			session = (OperationSession) SessionFactory
					.openSession(SessionType.Operation);
			operationBean = (OperationTBean) session.getById(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return operationBean;
	}
}
