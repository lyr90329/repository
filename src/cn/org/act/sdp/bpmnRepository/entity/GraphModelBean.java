package cn.org.act.sdp.bpmnRepository.entity;

import java.io.InputStream;

import cn.org.act.sdp.bpmnRepository.search.model.DLGraph;

public class GraphModelBean {
	private int ID;

	private int bpmnID;

	private DLGraph modelStream;

	public DLGraph getModelStream() {
		return modelStream;
	}

	public void setModelStream(DLGraph modelStream) {
		this.modelStream = modelStream;
	}

	public int getBpmnID() {
		return bpmnID;
	}

	public void setBpmnID(int bpmnID) {
		this.bpmnID = bpmnID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}
}
