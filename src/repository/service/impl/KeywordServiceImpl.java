package repository.service.impl;

import repository.service.KeywordService;
import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.KeywordTBean;
import cn.org.act.sdp.repository.cleavage.session.KeywordSession;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

public class KeywordServiceImpl implements KeywordService {

	@Override
	public KeywordTBean getByValue(Long jobId, String value) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		KeywordSession session = null;

		KeywordTBean bean = null;
		Object[] paras = new String[2];
		paras[0] = jobId.toString();
		paras[1] = value;
		try {
			session = (KeywordSession) SessionFactory
					.openSession(SessionType.Keyword);
			bean = (KeywordTBean) session.getByValue(paras);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			return bean;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
