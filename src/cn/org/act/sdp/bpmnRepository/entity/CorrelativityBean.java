package cn.org.act.sdp.bpmnRepository.entity;

import cn.org.act.sdp.bpmnRepository.search.model.Correlativity;

public class CorrelativityBean {
	private int id;

	private Correlativity corr;

	public Correlativity getCorr() {
		if (corr == null)
			corr = new Correlativity();
		return corr;
	}

	public void setCorr(Correlativity corr) {
		this.corr = corr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
