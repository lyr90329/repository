package cn.org.act.sdp.bpmnRepository.search.model;

import java.io.Serializable;

public class DirectedEdge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8611942967491241927L;

	private String label = "";

	private int weight = -1;

	private Node sourceNode = null;

	private Node destNode = null;

	private boolean isPathStart = false;

	public DirectedEdge(Node sNode, Node dNode) {
		this.sourceNode = sNode;
		this.destNode = dNode;
	}

	public boolean isPathStart() {
		return isPathStart;
	}

	public void setPathStart(boolean isPathStart) {
		this.isPathStart = isPathStart;
	}

	/*
	 * Type in the palm tree: 0-tree art; 1-frond.
	 */
	private int typeOfPalmTree = -1;

	public int getTypeOfPalmTree() {
		return typeOfPalmTree;
	}

	public void setTypeOfPalmTree(int typeOfPalmTree) {
		this.typeOfPalmTree = typeOfPalmTree;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Node getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(Node sourceNode) {
		this.sourceNode = sourceNode;
	}

	public Node getDestNode() {
		return destNode;
	}

	public void setDestNode(Node destNode) {
		this.destNode = destNode;
	}
}
