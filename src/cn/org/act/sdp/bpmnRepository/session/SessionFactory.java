package cn.org.act.sdp.bpmnRepository.session;

import java.sql.Connection;

import cn.org.act.sdp.bpmnRepository.pool.ConnectionPool;

/**
 * This class manages the construction of sessions.
 * 
 * @author <a href=��mailto:xuxt@act.buaa.edu.cn��>Xu Xiaotian</a>
 * @version $Revision: 1.5 $ $Date: 2010-05-13 03:19:10 $
 */
public class SessionFactory {
	/**
	 * open a new session according to its session type.
	 * 
	 * @param type
	 *            the required type of the session to be constructed
	 * @return the new session
	 */
	public static DBSession openSession(SessionType type) throws Exception {
		DBSession session = null;
		switch (type) {
		case Bpmn:
			session = new BpmnSession();
			break;
		case Graph:
			session = new GraphModelSession();
			break;
		case Meta:
			session = new MetaSession();
			break;
		case Domain:
			session = new DomainSession();
			break;
		case Title:
			session = new TitleSession();
			break;
		case Petri:
			session = new PetriSession();
			break;
		case Tag:
			session = new TagSession();
			break;
		case User:
			session = new UserSession();
			break;
		case Annotation:
			session = new AnnotationSession();
			break;
		default:
			return null;
		}

		ConnectionPool pool = ConnectionPool.singleInstance();
		Connection conn = pool.getConnection();
		if (conn == null)
			return null;
		session.setConn(conn);

		return session;
	}

}
