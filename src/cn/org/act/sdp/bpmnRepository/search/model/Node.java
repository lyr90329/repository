package cn.org.act.sdp.bpmnRepository.search.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 有向图节点模型
 * 
 * @author Administrator
 * 
 */
public class Node implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1097521652299264882L;

	private String label = "";

	private int type = -1;

	private ArrayList<DirectedEdge> inEdges = new ArrayList<DirectedEdge>();

	private ArrayList<DirectedEdge> outEdges = new ArrayList<DirectedEdge>();

	private int lowpt1 = -1;

	private int lowpt2 = -1;

	private int nd = -1;

	private int degree = -1;

	private int high = -1;

	private boolean hasInEdge = false;

	private boolean hasOutEdge = false;

	public boolean isHasInEdge() {
		return hasInEdge;
	}

	public void setHasInEdge(boolean hasInEdge) {
		this.hasInEdge = hasInEdge;
	}

	public boolean isHasOutEdge() {
		return hasOutEdge;
	}

	public void setHasOutEdge(boolean hasOutEdge) {
		this.hasOutEdge = hasOutEdge;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getLowpt1() {
		return lowpt1;
	}

	public void setLowpt1(int lowpt1) {
		this.lowpt1 = lowpt1;
	}

	public int getLowpt2() {
		return lowpt2;
	}

	public void setLowpt2(int lowpt2) {
		this.lowpt2 = lowpt2;
	}

	public int getNd() {
		return nd;
	}

	public void setNd(int nd) {
		this.nd = nd;
	}

	private boolean visited = false;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void addOutEdge(DirectedEdge edge) {
		// List<DirectedEdge> list = Arrays.asList(outEdges);
		// list.add(edge);
		// list.toArray(inEdges);
		outEdges.add(edge);
		setHasOutEdge(true);
	}

	public void addInEdge(DirectedEdge edge) {
		// List<DirectedEdge> list = Arrays.asList(inEdges);
		// list.add(edge);
		// list.toArray(inEdges);
		inEdges.add(edge);
		setHasInEdge(true);
	}

	public ArrayList<DirectedEdge> getInEdges() {
		return inEdges;
	}

	public void setInEdges(ArrayList<DirectedEdge> inEdges) {
		this.inEdges = inEdges;
	}

	public ArrayList<DirectedEdge> getOutEdges() {
		return outEdges;
	}

	public void setOutEdges(ArrayList<DirectedEdge> outEdges) {
		this.outEdges = outEdges;
	}

	// public Node getStart() {
	// return start;
	// }
	//
	// public void setStart(Node start) {
	// this.start = start;
	// }

	// public boolean hasInEdges() {
	// return false;
	// }
	//
	// public boolean hasOutEdges() {
	// return false;
	// }
}
