package repository.service.impl;

import java.util.List;

import repository.service.ServiceGraphService;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.session.RelatedType;
import cn.org.act.sdp.repository.cleavage.session.ServiceGraphSession;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

public class ServiceGraphServiceImpl implements ServiceGraphService {

	@Override
	public List<Long> getRelatedOperationIds(long operationId, RelatedType type) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		ServiceGraphSession session = null;

		List<Long> operationIds = null;

		try {
			session = (ServiceGraphSession) SessionFactory
					.openSession(SessionType.ServiceGraph);
			operationIds = session.getRelatedOperationIds(operationId, type);
			return operationIds;
		} catch (Exception e) {
			e.printStackTrace();
			return operationIds;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
