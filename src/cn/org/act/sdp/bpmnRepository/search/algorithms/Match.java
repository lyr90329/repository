package cn.org.act.sdp.bpmnRepository.search.algorithms;

import cn.org.act.sdp.bpmnRepository.search.model.DLGraph;

public interface Match {
	public float match(DLGraph s, DLGraph query);
}
