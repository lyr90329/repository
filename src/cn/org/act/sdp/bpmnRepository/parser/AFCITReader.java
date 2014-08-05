package cn.org.act.sdp.bpmnRepository.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.org.act.sdp.bpmnRepository.search.model.DLGraph;
import cn.org.act.sdp.bpmnRepository.search.model.DirectedEdge;
import cn.org.act.sdp.bpmnRepository.search.model.Node;

public class AFCITReader {
	/**
	 * @see prefuse.data.io.GraphReader#readGraph(java.io.InputStream)
	 */
	public DLGraph readGraph(InputStream is) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			BioWorkflowHandler handler = new BioWorkflowHandler();
			saxParser.parse(is, handler);// pass handler as a parameter
			return handler.getGrap();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * A SAX Parser for bpmn data files.
	 */
	public static class BioWorkflowHandler extends DefaultHandler {
		// protected ParserFactory m_pf = ParserFactory.getDefaultFactory();

		protected static final String Processer = "s:processor";

		protected static final String SinkEnd = "s:sink";// 结束点

		protected static final String SourceEnd = "s:source";// 开始点

		protected static final String SequenceFlow = "s:link";

		protected static final String Source = "source";

		protected static final String Target = "sink";

		protected static final String Name = "name";

		protected static final String UniEnd = "UniversEnd";

		protected static final String UniSta = "UniversStart";

		// private Graph g = new Graph(true);

		private DLGraph dLGrap = null;

//		private WorkflowGraph workflow = null;

		private ArrayList<Node> nodeL = new ArrayList<Node>();

		private ArrayList<DirectedEdge> edgeL = new ArrayList<DirectedEdge>();

		// private HashSet<String> nodesSet=new HashSet<String>();
		/* 假设每个流程中，一个活动之出现一次，好像也没有很好的解决，目前的流程中就是这样的，没有问题 */
		private HashMap<String, Node> nodesMap = new HashMap<String, Node>();

		/* 消除重复边以及双向边 */
		private HashSet<String> edgeSet = new HashSet<String>();

		public void startDocument() {
			// g.getNodeTable().addColumn("name", String.class);
			/* 为了避免枚举的错误，先建立两个节点，即开始和结束 */
			addNode(UniSta);
			addNode(UniEnd);

		}

		public void endDocument() throws SAXException {
			// time to actually set up the edges
			/* 先将所有的出度为0的边连向结束点，再将所有的入度为0的边连向开始点 */
			for (Iterator iter = nodeL.iterator(); iter.hasNext();) {
				Node element = (Node) iter.next();
				if (element.getLabel().equals(UniEnd)
						|| element.getLabel().equals(UniSta))
					continue;
				if (!element.isHasInEdge()) {
					addEdge(UniSta, element.getLabel());
				}
				if (!element.isHasOutEdge()) {
					addEdge(element.getLabel(), UniEnd);
				}
			}
			DirectedEdge[] edges = new DirectedEdge[edgeL.size()];
			Node[] nodes = new Node[nodeL.size()];
			edgeL.toArray(edges);
			nodeL.toArray(nodes);
			dLGrap = new DLGraph(nodes, edges);
			dLGrap.setStart(addNode(UniSta));
//			workflow = new WorkflowGraph(dLGrap);
//			workflow.getActivities().addAll(nodesMap.keySet());// 统计所有流程中包含的活动节点的数目
		}

		public void startElement(String namespaceURI, String localName,
				String qName, Attributes atts) {
			// BioVisit.set.add(qName);
			if (qName.equals(Processer)) {
				addNode(atts.getValue(Name));
				// System.out.println("Node::" + atts.getValue("name"));
			} else if (qName.equals(SourceEnd)) {
				addNode(atts.getValue(Name));
				// System.out.println("Node::" + atts.getValue("name"));
			} else if (qName.equals(SequenceFlow)) {
				String source = atts.getValue(Source);
				String target = atts.getValue(Target);
				addEdge(source, target);
				// System.out.println(source + "--->" + target);
			} else if (qName.equals(SinkEnd)) {
				addNode(atts.getValue(Name));
				// addEdge(atts.getValue(Name), UniEnd);
				// System.out.println("Node::" + atts.getValue("name"));
			}
		}

		private void addEdge(String source, String target) {
			if (source.contains(":"))
				source = source.substring(0, source.indexOf(':'));
			if (target.contains(":"))
				target = target.substring(0, target.indexOf(':'));
			if (edgeSet.contains(source + target)
					|| edgeSet.contains(target + source))// 消除重复边和双向边
				return;
			Node sor = addNode(source);
			Node tar = addNode(target);
			DirectedEdge edge = new DirectedEdge(sor, tar);
			sor.addOutEdge(edge);
			tar.addInEdge(edge);
			edgeL.add(edge);
			edgeSet.add(source + target);
			edgeSet.add(target + source);
		}

		private Node addNode(String value) {
			if (!nodesMap.containsKey(value)) {
				Node node = new Node();
				node.setLabel(value);
				nodeL.add(node);
				nodesMap.put(value, node);
				return node;
			}
			return nodesMap.get(value);
		}

		protected void error(String s) {
			throw new RuntimeException(s);
		}

		protected void error(Exception e) {
			throw new RuntimeException(e);
		}

		public DLGraph getGrap() {
			return dLGrap;
		}

		public void setGrap(DLGraph grap) {
			this.dLGrap = grap;
		}

//		public WorkflowGraph getWorkflow() {
//			return workflow;
//		}

//		public void setWorkflow(WorkflowGraph workflow) {
//			this.workflow = workflow;
//		}

	} // end of inner class GraphMLHandler
}
