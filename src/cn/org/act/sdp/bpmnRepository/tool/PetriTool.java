package cn.org.act.sdp.bpmnRepository.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import cn.org.act.sdp.bpmnRepository.crust.impl.RepositoryConf;
import cn.org.act.sdp.bpmnRepository.entity.DomainBean;
import cn.org.act.sdp.bpmnRepository.entity.PetriBean;
import cn.org.act.sdp.bpmnRepository.session.PetriSession;
import cn.org.act.sdp.bpmnRepository.session.SessionFactory;
import cn.org.act.sdp.bpmnRepository.session.SessionType;

public class PetriTool {
	private PetriSession session = null;
	public Integer errorCode;

	/**
	 * @return
	 * @throws Exception
	 */
	private PetriSession init() {

		RepositoryConf.poolInit();
		RepositoryConf.managerInit();

		try {
			session = (PetriSession) (SessionFactory
					.openSession(SessionType.Petri));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;

	}

	public int save(PetriBean bean) {
		if (bean.getPetriIs() == null && bean.getPetri() != null) {
			byte[] bytes = null;
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4096);
			ObjectOutputStream os;
			try {
				os = new ObjectOutputStream(byteStream);
				os.writeObject(bean.getPetri());
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
			// retrieves byte array
			bytes = byteStream.toByteArray();
			bean.setPetriIs(new ByteArrayInputStream(bytes));
		}
		init();
		int id = session.save(bean);
		this.errorCode = session.errorCode;
		session.close();
		return id;
	}

	/**
	 * 
	 * @return return null if empty or error
	 */
	public PetriBean getById(int id) {
		init();
		PetriBean bean = session.getById(id);
		session.close();
		return bean;
	}

	public boolean update(PetriBean bean) {
		if (bean.getPetriIs() == null && bean.getPetri() != null) {
			byte[] bytes = null;
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4096);
			ObjectOutputStream os;
			try {
				os = new ObjectOutputStream(byteStream);
				os.writeObject(bean.getPetri());
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			// retrieves byte array
			bytes = byteStream.toByteArray();
			bean.setPetriIs(new ByteArrayInputStream(bytes));
		}
		init();
		boolean b = session.update(bean);
		session.close();
		return b;
	}

	public List<PetriBean> getAll() {
		init();
		List<PetriBean> list = session.getAll();
		session.close();
		return list;
	}

	public boolean remove(int id) {
		init();
		boolean b = session.remove(id);
		session.close();
		return b;
	}

	public boolean remove(PetriBean petri) {
		init();
		boolean b = session.remove(petri);
		session.close();
		return b;
	}

	public List<PetriBean> getByDomainId(int domainId) {
		init();
		List<PetriBean> list = session.getByDomainId(domainId);
		session.close();
		return list;
	}

	public List<PetriBean> getByDomain(DomainBean domain) {
		return this.getByDomainId(domain.getId());
	}
}
