package repository.service.impl;

import java.util.LinkedList;
import java.util.List;

import repository.service.ServiceKeywordService;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.ServiceKeywordTBean;
import cn.org.act.sdp.repository.cleavage.session.ServiceKeywordSession;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

public class ServiceKeywordServiceImpl implements ServiceKeywordService {

	@Override
	public List getByKeywodId(Long jobId, Long keywordId) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		ServiceKeywordSession session = null;

		List<ServiceKeywordTBean> serviceKeywords = new LinkedList<ServiceKeywordTBean>();
		String[] parameters = new String[2];
		parameters[0] = jobId.toString();
		parameters[1] = keywordId.toString();
		try {
			session = (ServiceKeywordSession) SessionFactory
					.openSession(SessionType.ServiceKeyword);
			serviceKeywords = session.getByKeywordId(parameters);
			return serviceKeywords;
		} catch (Exception e) {
			e.printStackTrace();
			return serviceKeywords;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
