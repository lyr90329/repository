package cn.org.act.sdp.bpmnRepository.tool;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import cn.org.act.sdp.bpmnRepository.config.Config;
import cn.org.act.sdp.bpmnRepository.constants.FileType;
import cn.org.act.sdp.bpmnRepository.crust.impl.RepositoryConf;
import cn.org.act.sdp.bpmnRepository.entity.BpmnBean;
import cn.org.act.sdp.bpmnRepository.entity.MetaBean;
import cn.org.act.sdp.bpmnRepository.listener.ListenerContainer;
import cn.org.act.sdp.bpmnRepository.session.BpmnSession;
import cn.org.act.sdp.bpmnRepository.session.SessionFactory;
import cn.org.act.sdp.bpmnRepository.session.SessionType;

public class BpmnTool {
	private BpmnSession session = null;

	/**
	 * @return
	 * @throws Exception
	 */
	private BpmnSession init() {

		RepositoryConf.poolInit();
		RepositoryConf.managerInit();

		try {
			session = (BpmnSession) (SessionFactory
					.openSession(SessionType.Bpmn));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return session;

	}

	public int save(Object o) {
		BpmnBean bean = (BpmnBean) o;
		init();
		int id = session.save(bean);
		session.close();
		return id;
	}

	public int saveAll(BpmnBean bpmn) {
		init();
		int id = session.save(bpmn);
		session.close();
		bpmn.setId(id);
		init();
		id = session.updateContent(bpmn, FileType.BPMN) ? id : 0;
		session.close();
		init();
		id = session.updateContent(bpmn, FileType.DIAGRAM) ? id : 0;
		session.close();
		init();
		id = session.updateContent(bpmn, FileType.FLEX) ? id : 0;
		session.close();
		init();
		id = session.updateContent(bpmn, FileType.FLEXIMG) ? id : 0;
		session.close();
		return id;

	}

	public int save(String BPMNLocation, String DiagramLocation,
			String flexLocation, String BPMNName, String DiagramName,
			InputStream bpmnStream, InputStream diagramStream, long bpmnSize,
			long diagramSize) {
		BpmnBean bean = new BpmnBean();
		// bean.setAnotationID(AnotationID);
		bean.setBpmnLocation(BPMNLocation);
		bean.setFlexLocation(flexLocation);
		bean.setDiagramLocation(DiagramLocation);
		// bean.setBPMNName(BPMNName);
		// bean.setDiagramName(DiagramName);
		// bean.setBpmnStream(bpmnStream);
		// bean.setBpmnSize((int) bpmnSize);
		// bean.setDiagramStream(diagramStream);
		// bean.setDiagramSize((int) diagramSize);
		init();
		int id = session.save(bean);
		session.close();
		return id;
	}

	/**
	 * �Ӳ�ѯ��ID�еõ���Ҫ��bpmn
	 * 
	 * @param string
	 * @return null if not existed
	 */
	public BpmnBean getBpmnByID(int ID) {
		init();
		BpmnBean bean = session.getByID(ID);
		session.close();
		return bean;
	}

	public boolean update(Object o) {
		init();
		boolean b = session.update(o);
		session.close();
		return b;
	}

	public boolean updateAll(BpmnBean bpmn) {
		init();
		boolean b = session.update(bpmn);
		b &= session.updateContent(bpmn, FileType.BPMN);
		b &= session.updateContent(bpmn, FileType.DIAGRAM);
		b &= session.updateContent(bpmn, FileType.FLEX);
		b &= session.updateContent(bpmn, FileType.FLEXIMG);
		session.close();
		return b;
	}

	public boolean getByMeta(MetaBean meta) {
		if (meta == null)
			return false;
		init();
		BpmnBean bean = session.getByMeta(meta);
		session.close();
		meta.setBpmn(bean);
		if (bean != null)
			bean.setMeta(meta);
		return true;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public File getBpmnFile(String path) {
		File file = new File(path);
		return file;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public File getDiagramFile(String path) {
		File file = new File(path);
		return file;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public File getFlexFile(String path) {
		File file = new File(path);
		return file;
	}

	/**
	 * 
	 * @param bpmn
	 * @param type
	 *            "bpmn","diagram","flex","flexImg"
	 * @return
	 */
	public boolean getContentByBpmn(BpmnBean bpmn, String type) {
		if (bpmn == null) {
			return false;
		}
		init();
		boolean b = session.getContentByBpmn(bpmn, type);
		session.close();
		return b;
	}

	public boolean saveContent(BpmnBean bpmn, String type) {
		if (bpmn == null)
			return false;
		init();
		boolean b = session.updateContent(bpmn, type);
		session.close();
		return b;
	}

	public boolean remove(int id) {
		init();
		boolean b = session.remove(id);
		session.close();
		return b;
	}

	public boolean remove(BpmnBean bpmn) {
		init();
		boolean b = session.remove(bpmn);
		session.close();
		return b;
	}

	public ArrayList<BpmnBean> getByName(String name) {
		init();
		ArrayList<BpmnBean> list = session.getByName(name);
		session.close();
		return list;
	}

	/**
	 * 
	 * @param id
	 * @return false if the file is not existed or delete failed.
	 */
	public boolean removeImg(int id) {
		BpmnBean bpmn = this.getBpmnByID(id);
		String imgPath = bpmn.getFlexLocation();
		File img = new File(Config.getPath(Config.imgFolderPath) + imgPath);
		if (img.exists()) {
			img.delete();
		} else
			return false;
		return true;
	}

	public boolean removeImg(BpmnBean bpmn) {
		return this.removeImg(bpmn.getId());
	}
}
