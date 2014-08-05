package cn.org.act.sdp.bpmnRepository.tool.cloud;

import cn.org.act.sdp.bpmnRepository.entity.BpmnBean;
import cn.org.act.sdp.bpmnRepository.entity.MetaBean;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;
import cn.org.act.sdp.bpmnRepository.tool.MetaTool;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;

public class BpmnPublish {

	public int publish(String bpmn, String flex, String diagram, String title,
			String author) {
		// save title
		TitleTool titleTool = new TitleTool();
		TitleBean titleBean = new TitleBean();
		titleBean.setDomainId(0);
		titleBean.setTitle(title);
		titleBean.setVersions(1);
		titleBean.setLatest(1);
		int titleId = titleTool.save(titleBean);

		// save bpmn
		BpmnTool bpmnTool = new BpmnTool();
		BpmnBean bpmnBean = new BpmnBean();
		bpmnBean.setBpmnContent(bpmn);
		bpmnBean.setFlexContent(flex);
		bpmnBean.setDiagramContent(diagram);
		int bpmnId = bpmnTool.saveAll(bpmnBean);

		// save metadata
		MetaTool metaTool = new MetaTool();
		MetaBean meta = new MetaBean();
		meta.setId(bpmnId);
		meta.setTitleId(titleId);
		meta.setAuthor(author);
		metaTool.save(meta);

		return bpmnId;
	}
}
