package cn.org.act.sdp.bpmnRepository.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.org.act.sdp.bpmnRepository.search.model.DLGraph;
import cn.org.act.sdp.bpmnRepository.search.model.DirectedEdge;
import cn.org.act.sdp.bpmnRepository.search.model.Node;

/**
 * This class convert the data of bpmn file into abstract data used by
 * visualization tool.
 * 
 * @author <a href=”mailto:kangxt@act.buaa.edu.cn”>kangxt</a>
 * @version 0.1 2008/5/30 18:00
 */
public class BPMNReader {

	/**
	 * @return 
	 * @see prefuse.data.io.GraphReader#readGraph(java.io.InputStream)
	 */
	public DLGraph readGraph(InputStream is) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			BPMNHandler handler = new BPMNHandler();
			saxParser.parse(is, handler);// pass handler as a parameter
			return handler.getGrap();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * String tokens used in the GraphML format.
	 */
	public interface Tokens {// **
		// public static final String ID = "id";
		public static final String ID = "id";

		public static final String NODE = "vertices";

		public static final String TASK = "task";

		public static final String NONETASK = "NoneTask";// added by zjn

		public static final String SENDTASK = "SendTask";// added by zjn

		public static final String RECEIVETASK = "ReceiveTask";// added by zjn

		public static final String SERVICETASK = "ServiceTask";// added by zjn

		public static final String STARTEVENT = "startEvent";

		public static final String ENDEVENT = "endEvent";

		public static final String DATABASEDEXCLUSIVEGATEWAY = "dataBasedExclusiveGateway";

		public static final String EVENTBASEDEXCLUSIVEGATEWAY = "eventBasedExclusiveGateway";

		public static final String INCLUSIVEGATEWAY = "inclusiveGateway";

		public static final String INTERMEDIATEEVENT = "intermediateEvent";

		public static final String PARALLELGATEWAY = "parallelGateway";

		public static final String EDGE = "sequenceFlows";// modified by zjn

		public static final String MESSAGEFLOW = "messageFlows";// //modified by

		// zjn

		public static final String PROPERTY = "property";

		public static final String MESSAGE = "message";

		public static final String NAME = "name";

		public static final String TYPE = "type";

		public static final String KIND = "kind";

		public static final String POOL = "pools";

		public static final String X = "x";

		public static final String Y = "y";
	}

	/**
	 * A SAX Parser for bpmn data files.
	 */
	public static class BPMNHandler extends DefaultHandler implements Tokens {
		// protected ParserFactory m_pf = ParserFactory.getDefaultFactory();
		private DLGraph dLGrap = null;

		// private WorkflowGraph workflow = null;

		private ArrayList<Node> nodeL = new ArrayList<Node>();

		private ArrayList<DirectedEdge> edgeL = new ArrayList<DirectedEdge>();

		// private HashSet<String> nodesSet=new HashSet<String>();
		/* 假设每个流程中，一个活动之出现一次，好像也没有很好的解决，目前的流程中就是这样的，没有问题 */
		private HashMap<String, Node> nodesMap = new HashMap<String, Node>();

		/* 消除重复边以及双向边 */
		private HashSet<String> edgeSet = new HashSet<String>();

//		protected static final String SRC = "source";

//		protected static final String TRG = "target";

//		protected static final String SRCID = SRC + '_' + ID;

//		protected static final String TRGID = TRG + '_' + ID;

		protected static final String UniEnd = "UniversEnd";

		protected static final String UniSta = "UniversStart";

		protected String m_graphid;

//		protected Map<String, String> message_node = new HashMap<String, String>();

//		protected Map<String, String> pools = new HashMap<String, String>();

//		protected String currentPool = null;

		protected Map<String, String> id_nodeMap = new HashMap<String, String>();

//		private int poolCounter = 0;

		public void startDocument() {
			// m_nodeMap.clear();
			// message_node.clear();
			addNode(UniSta);
			addNode(UniEnd);
		}

		public void endDocument() throws SAXException {
			// time to actually set up the edges
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
		}

		/*
		 * This method is used for adapting to the new BPMN schema added by zjn
		 */
		public void startElement(String namespaceURI, String localName,
				String qName, Attributes atts) {
			if (qName.equals("bpmn:BusinessProcessDiagram")) {
				// System.out.println();
			} else if (qName.equals(POOL)) {// "pools" instead of "pool"
			// poolCounter++;
			// currentPool = atts.getValue(NAME);
			// System.out.println(currentPool);
			} else if (qName.equals("graphicalElements")) {
				String type = atts.getValue("xsi:type").substring(5);
				if (type.toLowerCase().contains(TASK)) {
					String name = atts.getValue(NAME);
					String id = atts.getValue(ID);
					addNode(name);//默认不存在相同的task
					id_nodeMap.put(id, name);
				}
				if (type.equalsIgnoreCase(STARTEVENT)// **
						|| type.equalsIgnoreCase(ENDEVENT)
						|| type.equalsIgnoreCase(DATABASEDEXCLUSIVEGATEWAY)
						|| type.equalsIgnoreCase(EVENTBASEDEXCLUSIVEGATEWAY)
						|| type.equalsIgnoreCase(INCLUSIVEGATEWAY)
						|| type.equalsIgnoreCase(INTERMEDIATEEVENT)
						|| type.equalsIgnoreCase(PARALLELGATEWAY)) {
					String id = atts.getValue(ID);// **id; atts;
                    String name=atts.getValue(NAME);
                    name=((name==null)?id:name);
                    addNode(name);
                    id_nodeMap.put(id, name);
//					String message;
//					if ((message = atts.getValue("inMessageRef")) != null) {
//						message_node.put(message, id);
//					}
//					if ((message = atts.getValue("outMessageRef")) != null) {
//						message_node.put(message, "#" + id);
//					}
//					if ((message = atts.getValue("messageRef")) != null) {
//						if (atts.getValue("xsi:type")
//								.equals("bpmn:ReceiveTask"))
//							message_node.put(message, "@" + id);
//						else
//							message_node.put(message, "$" + id);
//					}
//					System.out.println(type + "  " + atts.getValue("name"));
				}
				// System.out.println();
			} else if (qName.equals(EDGE)) {// **sequence flow
				String sid = atts.getValue("sourceRef");
				String tid = atts.getValue("targetRef");
				addEdge(id_nodeMap.get(sid), id_nodeMap.get(tid));
//				System.out.println(EDGE + ":" + sor + "->" + tar);
			} else if (qName.equals(MESSAGEFLOW)) {
//				String sor = atts.getValue("sourceRef");
//				String tar = atts.getValue("targetRef");
//				System.out.println(MESSAGEFLOW + ":" + sor + "->" + tar);
			} else if (qName.equals("supportingElements")) {
//				String type = atts.getValue("xsi:type").substring(5);
				// System.out.println();
//				if (type.equalsIgnoreCase("Property")) {// property
//					atts.getValue(ID);
//					atts.getValue(NAME);
//					atts.getValue(TYPE);
//				}
			} else if (qName.equals(MESSAGE)) {
				// System.out.println();
			}
		}

		protected void error(String s) {
			throw new RuntimeException(s);
		}

		protected void error(Exception e) {
			throw new RuntimeException(e);
		}

//		public int getPoolCounter() {
//			return poolCounter;
//		}
//
//		public void setPoolCounter(int poolCounter) {
//			this.poolCounter = poolCounter;
//		}

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

		public DLGraph getGrap() {
			return dLGrap;
		}

		public void setGrap(DLGraph grap) {
			this.dLGrap = grap;
		}
	} // end of inner class GraphMLHandler

} // end of class XMLGraphReader

