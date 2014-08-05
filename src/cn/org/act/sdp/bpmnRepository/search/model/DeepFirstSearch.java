package cn.org.act.sdp.bpmnRepository.search.model;

import java.util.Stack;

/**
 * 建议改名为figurePrint
 * @author dell
 *
 */
public class DeepFirstSearch {
	//输入无向图
	private DLGraph inputGraph = null;
	
	private Path curPath=new Path();
	private Stack<DirectedEdge> stack=new Stack<DirectedEdge>();
	
//	private ArrayList arcArray = new ArrayList();
//	private ArrayList frondArray = new ArrayList();
//	
//	private HashMap historyMap = new HashMap();
//	
//	private HashMap dfNumberMap = new HashMap();
//	
//	public ArrayList getBiComponents() {
//		return biComponents;
//	}
//
//	private HashMap lowMap = new HashMap();
//	
//	private HashMap fatherMap = new HashMap();
//	
//	private int count = 1;
//	
//	private ArrayList biComponents = new ArrayList();
	
	public DeepFirstSearch( DLGraph testGraph ){
		this.inputGraph = testGraph;
	}
	
	public void advancedDFS( Node sourceNode ){
//		Node[] allNodes = this.inputGraph.vertices();
//		for( int i = 0; i < allNodes.length; i++ ){
//			//0-new; 1-old;
//			this.historyMap.put( allNodes[i], 0 );
//		}
		
		search( sourceNode );
	}
	
//	public HashMap getDfNumberMap() {
//		return dfNumberMap;
//	}
//
//	public HashMap getLowMap() {
//		return lowMap;
//	}
     /**
      * 避免回路
      */
	private void search( Node currentNode ){
		if(currentNode.getLabel().equals("UniversEnd")){
			curPath.getPaths().addAll(stack);
			inputGraph.addPath(curPath);
			curPath=new Path();
		}
		DirectedEdge[] outEdges = inputGraph.outIncidentEdges( currentNode );//这一步比较耗时，仔细一想也不耗时
		for( int i = 0; i < outEdges.length; i++ ){
			DirectedEdge tmpEdge = outEdges[i];
			Node outNode = tmpEdge.getDestNode();
			if( !outNode.isVisited() ){
				//if w is marked "new"
				stack.push(tmpEdge);
				outNode.setVisited(true);
				search( outNode );
				stack.pop();
				outNode.setVisited(false);
			}else {
				//这里遇上了回路
			}
		}
	}
	
//	public ArrayList getArcArray() {
//		return arcArray;
//	}
//
//	public ArrayList getFrondArray() {
//		return frondArray;
//	}
//
//	private Node getOtherNodeOfEdge( DirectedEdge edge, Node node ){
//		Node[] adjNodes = edge.getAdjacentNodes();
//		if( adjNodes.length != 2 ){
//			return null;
//		}
//		
//		if( adjNodes[0].equals( node ) ){
//			return adjNodes[1];
//		}else{
//			return adjNodes[0];
//		}
//	}
//
//	public HashMap getFatherMap() {
//		return fatherMap;
//	}
//
//	public void setFatherMap(HashMap fatherMap) {
//		this.fatherMap = fatherMap;
//	}
}
