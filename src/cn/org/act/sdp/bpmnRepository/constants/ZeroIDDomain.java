package cn.org.act.sdp.bpmnRepository.constants;

import cn.org.act.sdp.bpmnRepository.entity.DomainBean;

public class ZeroIDDomain extends DomainBean {
	public ZeroIDDomain() {
		super();
		super.setCreatedTime(null);
		super.setDescription("Processes included are belonged to no domains.");
		super.setId(0);
		super.setModifiedTime(null);
		super.setName("Processes Of No Domain");
	}
}
