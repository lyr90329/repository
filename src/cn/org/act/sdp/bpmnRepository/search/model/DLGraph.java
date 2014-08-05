package cn.org.act.sdp.bpmnRepository.search.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * 使用邻接表描述的有向标记图模型
 * 强制的每个模型都有一个开始和结束节点，所有路径中不包括回路
 * @author Administrator
 * 
 */
public class DLGraph implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7964345529593834983L;

	protected Node[] nodes = null;
	
	protected String name;

	protected DirectedEdge[] edges = null;
	
	private Node start;

//	protected Path[] paths = null;
	
	protected HashSet<Path> paths=new HashSet<Path>();

	public HashSet<Path> getPaths() {
		return paths;
	}

	public void setPaths(HashSet<Path> paths) {
		this.paths = paths;
	}

	public DLGraph(Node[] nodes, DirectedEdge[] edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	public int numVertices() {
		return nodes.length;
	}

	public int numEdges() {
		return edges.length;
	}

	public Node[] vertices() {
		return this.nodes;
	}

	public DirectedEdge[] edges() {
		return this.edges;
	}

	public int inDegree(Node node) {
		int result = 0;
		for (int i = 0; i < this.edges.length; i++) {
			DirectedEdge tmpEdge = this.edges[i];
			if (tmpEdge.getDestNode().getLabel().equals(node.getLabel())) {
				result++;
			}
		}
		return result;
	}

	public int outDegree(Node node) {
		int result = 0;
		for (int i = 0; i < this.edges.length; i++) {
			DirectedEdge tmpEdge = this.edges[i];
			if (tmpEdge.getSourceNode().getLabel().equals(node.getLabel())) {
				result++;
			}
		}
		return result;
	}

	/**
	 * 返回节点Node的所有入边的一个数组
	 * 
	 * @param node
	 * @return
	 */
	public DirectedEdge[] inIncidentEdges(Node node) {
		ArrayList result = new ArrayList();
		for (int i = 0; i < this.edges.length; i++) {
			DirectedEdge tmpEdge = this.edges[i];
			if (tmpEdge.getDestNode().getLabel().equals(node.getLabel())) {
				result.add(tmpEdge);
			}
		}
		return (DirectedEdge[]) result.toArray();
	}

	/**
	 * 返回节点Node的所有出边的一个数组
	 * 
	 * @param node
	 * @return
	 */
	public DirectedEdge[] outIncidentEdges(Node node) {
		ArrayList<DirectedEdge> result = new ArrayList<DirectedEdge>();
		DirectedEdge[] a=null;
		for (int i = 0; i < this.edges.length; i++) {
			DirectedEdge tmpEdge = this.edges[i];
			if (tmpEdge.getSourceNode().getLabel().equals(node.getLabel())) {
				result.add(tmpEdge);
			}
		}
		a=new DirectedEdge[result.size()];
		return result.toArray(a);
//		return (DirectedEdge[]) result.toArray();
	}

	/**
	 * 沿着给定节点的入边,返回其所有相邻顶点的一个数组
	 * 
	 * @param node
	 * @return
	 */
	public Node[] inAdjacentVertices(Node node) {
		ArrayList result = new ArrayList();
		for (int i = 0; i < this.edges.length; i++) {
			DirectedEdge tmpEdge = this.edges[i];
			if (tmpEdge.getDestNode().getLabel().equals(node.getLabel())) {
				result.add(tmpEdge.getSourceNode());
			}
		}
		return (Node[]) result.toArray();
	}

	/**
	 * 沿着给定节点的出边,返回其所有相邻顶点的一个数组
	 * 
	 * @param node
	 * @return
	 */
	public Node[] outAdjacentVertices(Node node) {
		ArrayList result = new ArrayList();
		for (int i = 0; i < this.edges.length; i++) {
			DirectedEdge tmpEdge = this.edges[i];
			if (tmpEdge.getSourceNode().getLabel().equals(node.getLabel())) {
				result.add(tmpEdge.getDestNode());
			}
		}
		return (Node[]) result.toArray();
	}

	/**
	 * 沿着给定节点的出边,返回其第一个子节点
	 * 
	 * @param node
	 * @return
	 */
	public Node firstChild(Node node) {
		Node result = this.edges[0].getDestNode();

		return result;
	}
    /**
     * 
     * @param curPath
     */
	public void addPath(Path curPath) {
		paths.add(curPath);
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
