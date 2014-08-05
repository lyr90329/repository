package cn.org.act.sdp.bpmnRepository.search.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class Path implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6412557760886328536L;

	String label = "";

	ArrayList<DirectedEdge> path = new ArrayList<DirectedEdge>();

	HashSet<Node> activities;

	public HashSet<Node> getActivities() {
		if (activities == null) {
			activities = new HashSet<Node>();
			for (DirectedEdge edge : path) {
				activities.add(edge.getDestNode());
				activities.add(edge.getSourceNode());
			}
		}
		return activities;
	}

	public void setActivities(HashSet<Node> activities) {
		this.activities = activities;
	}

	public void addEdge(DirectedEdge tmpEdge) {
		path.add(tmpEdge);
	}

	public ArrayList<DirectedEdge> getPaths() {
		return path;
	}

	private String initPathLabel() {
		for (Iterator iterator = path.iterator(); iterator.hasNext();) {
			DirectedEdge element1 = (DirectedEdge) iterator.next();
			label += element1.getSourceNode().getLabel() + "->";
		}
		label += "end";
		return label;
	}

	public String getLabel() {
		if (label.equals(""))
			return initPathLabel();
		return label;
	}

}
