package repository.atomicServices;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * @author Linjj Date:2010-07-26
 */
public class TransformerUtils {
	private final static String internal = " ";
	private final static String tag = "  ";
	private static Logger logger = Logger.getLogger("TransformerUtils");

	/**
	 * get xml string for document object
	 * 
	 * @param doc
	 * @return XML as string
	 */
	public static String getStringFromDocument(Document doc) {// 是否有更好的方法，如直接获得DocumentElement
																// 。目前的方法，可能出现空行
		NodeList nodeList = doc.getChildNodes();
		StringBuffer sb = new StringBuffer(1024);
		int i;
		for (i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			sb.append(getStringFromNode(node, 0));
		}
		return sb.toString();
	}

	/**
	 * write the content of the Document doc to the OutputStream output
	 * 
	 * @param doc
	 * @param output
	 */
	public  static void getOutputStreamFromDocument(Document doc, OutputStream output) {
		String nodeStr = getStringFromDocument(doc);
		try {
			output.write(nodeStr.getBytes());
			output.close();
		} catch (IOException ioe) {
			logger.warning("There happens some error when reading the document");
		}
	}

	/**
	 * get xml string for the first element of the given tag name in the
	 * document
	 * 
	 * @param inDoc
	 * @param tagName
	 * @return context string for the first element of the given tag name in the
	 *         document
	 */
	public static String getFirstElementBodyStringByTagName(Document inDoc,
			String tagName) {
		NodeList nodeList = inDoc.getElementsByTagName(tagName);
		Node node = (Node) nodeList.item(0);// The first element
		/*
		 * 若这个element只有一个值，那么它只有一个孩子，并且这个孩子是Text类型的；
		 * 若这个element下面还有很多标签,这些标签可能是Text，可能是Element类型的。
		 * 且第一个Text和最后一个Text的内容均为："\n" 在这里，我们不需要第一个和最后一个Text。
		 */
		NodeList nodeChildren = node.getChildNodes();
		StringBuffer sb = new StringBuffer(1024);

		int length = nodeChildren.getLength();
		if (length == 1) {
			sb.append(getStringFromNode(nodeChildren.item(0), 0));
		} else {
			for (int i = 1; i < length - 1; i++) {
				Node nodeTmp = nodeChildren.item(i);
				sb.append(getStringFromNode(nodeTmp, 0));
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param doc
	 * @param tagName
	 * @return the first element by the tag name
	 */
	public static OMElement getFirstOMElementByTagName(Document doc,
			String tagName) {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer;
		StreamResult result = new StreamResult();

		NodeList nodeList = doc.getElementsByTagName(tagName);

		Node node = (Node) nodeList.item(0);// The first element requestSoap
		if (node == null) {
			throw new NullPointerException("The node is null");
		}

		// NodeList nodeChildren = node.getChildNodes();//soap消息
		// int length = nodeChildren.getLength();
		// for (int i = 0; i < length; i++) {
		// Node nodeTmp = nodeChildren.item(i);

		try {
			transformer = factory.newTransformer();
			// DOMSource source = new DOMSource(nodeTmp);
			DOMSource source = new DOMSource(node);

			File file = new File("tmp.xml");
			if (!file.exists()) {
				file.createNewFile();
			}

			result.setOutputStream(new FileOutputStream(file));

			transformer.transform(source, result);

			javax.xml.stream.XMLStreamReader parser = XMLInputFactory
					.newInstance().createXMLStreamReader(
							new FileInputStream(file));

			StAXOMBuilder builder = new StAXOMBuilder(parser);

			OMElement e = builder.getDocumentElement();

			// e.serialize(System.out); // cache on
			// e.serializeAndConsume(System.out); // cache off

			// will NOT build the OMTree in the memory.
			// So you are at your own risk of losing information.
			// String creStr = e.toStringWithConsume();
			// call toString, will build the OMTree in the memory.
			// System.out.println(e);

			file.delete();

			return e;
		} catch (TransformerConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// }

		}
		return null;

	}

	/**
	 * get xml string from the given node in the given depth.
	 */
	private static String getStringFromNode(Node node, int depth) {
		int i;
		StringBuffer strBuffer = new StringBuffer(1024);
		String interStr = "";
		for (i = 0; i < depth; i++)
			interStr += tag;

		if (node instanceof Element) {
			strBuffer.append(interStr + "<" + node.getNodeName());
			/*
			 * add attribute pairs to the strBuffer
			 */
			if (node.hasAttributes()) {
				NamedNodeMap attrs = node.getAttributes();
				for (i = 0; i < attrs.getLength(); i++) {
					Node a = attrs.item(i);
					strBuffer.append(internal + a.getNodeName() + "=\""
							+ a.getNodeValue() + "\"");
				}
			}
			/*
			 * add child nodes to the strBuffer
			 */
			if (node.hasChildNodes()) {
				strBuffer.append(">");
				NodeList ns = node.getChildNodes();
				for (i = 0; i < ns.getLength(); i++) {
					Node nodeChild = ns.item(i);
					strBuffer.append(getStringFromNode(nodeChild, depth + 1));
				}
				if (i == 1) {// node没有子标签
					strBuffer.append("</" + node.getNodeName() + ">");
				} else {// node有子标签
					strBuffer
							.append(interStr + "</" + node.getNodeName() + ">");
				}
			} else {
				strBuffer.append("/>");
			}
		} else if (node instanceof CDATASection) {
			strBuffer.append(interStr + "<![CDATA[\n");
			strBuffer.append(interStr + tag + node.getNodeValue());
			strBuffer.append(interStr + "]]>");
		} else if (node instanceof Comment) {
			strBuffer.append(interStr + "<!--" + node.getNodeValue() + "-->");
		} else if (node instanceof Document) {
			strBuffer.append(getStringFromNode(
					((Document) node).getDocumentElement(), depth + 1));// 待考证
		} else if (node instanceof DocumentFragment) {
			// TODO
		} else if (node instanceof Entity) {
			// TODO
		} else if (node instanceof EntityReference) {
			// TODO
		} else if (node instanceof Notation) {
			strBuffer.append(interStr + "<!--" + node.getNodeValue() + "-->");
		} else if (node instanceof Text) {
			strBuffer.append(node.getNodeValue());
		}// 要把Text放在后面！！！！CDATASection是Text类型的
		else {// ProcessInstuction
			strBuffer.append(interStr + "<?" + node.getNodeValue() + "?>");
		}

		return strBuffer.toString();
	}

	/**
	 * 加载xml文件，返回Docuemnt对象
	 * 
	 * @param fileName
	 * @return Document
	 */
	@SuppressWarnings("finally")
	public static Document getDocumentFromFile(FileInputStream inStream) {
		Document doc = null;
		try {
			DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = (DocumentBuilder) docBF
					.newDocumentBuilder();
			doc = docBuilder.parse(inStream);
		} catch (Exception e) {
			logger.warning("readDOM throw err: " + e.getClass().getName()
					+ e.getMessage());
		} finally {
			try {
				if (inStream != null)
					inStream.close();
			} catch (Exception e) {
				logger.warning("Fail to close input stream: " + e.getMessage());
			}
			return doc;
		}
	}

	/**
	 * 加载xml字符串，返回Document对象
	 * 
	 * @param xml
	 * @return Document
	 */
	@SuppressWarnings("finally")
	public static Document getDocumentFromString(String xml) {

		ByteArrayInputStream inStream = null;
		try {
			inStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException ue) {
			logger.warning("Fail to create byte input stream from string:"
					+ ue.getMessage());
		}
		Document doc = null;
		try {
			DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = (DocumentBuilder) docBF
					.newDocumentBuilder();
			doc = docBuilder.parse(inStream);
		} catch (Exception e) {
			logger.warning("readDOM throw err: " + e.getClass().getName()
					+ e.getMessage());
		} finally {
			try {
				if (inStream != null)
					inStream.close();
			} catch (Exception e) {
				logger.warning("Fail to close input stream: " + e.getMessage());
			}
			return doc;
		}
	}

	/**
	 * @param response
	 * @return Document @
	 *         目前，这个函数会创建一个临时文件，以完成Document到OMElement的转换。这个函数有更好的实现方法，以后应当改进
	 */
	public static Document getDocumentFromOMElement(OMElement response) {

		File tempFile = new File("temp.xml");
		if (!tempFile.exists()) {
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileOutputStream outputStream;
		Document document = null;
		try {
			outputStream = new FileOutputStream(tempFile);
			response.serialize(outputStream);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = null;
			builder = factory.newDocumentBuilder();
			document = builder.parse(tempFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;

	}

	
	/**
	 * 通过一个临时文件实现Document到OMElement的转换
	 * @param in
	 * @return
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationError
	 */
		public static OMElement docToOM(Document in) throws XMLStreamException,
				FactoryConfigurationError {

			TransformerFactory factory = TransformerFactory.newInstance();
			try {
				Transformer transformer = factory.newTransformer();
				try {
					StreamResult result;
					result = new StreamResult(new FileOutputStream(new File(
							"temp.xml")));
					transformer.transform(new DOMSource(in), result);
					XMLStreamReader parser = XMLInputFactory.newInstance()
							.createXMLStreamReader(new FileInputStream("temp.xml"));
					StAXOMBuilder builder = new StAXOMBuilder(parser);
					OMElement out = builder.getDocumentElement();

					return out;
				} catch (TransformerException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} catch (TransformerConfigurationException e1) {
				e1.printStackTrace();
			}
			return null;
		}
//该方法需要优化
	/**
	 * 将OMElement序列化到一个临时文件，再将此临时文件构建为Document
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static Document omToDoc(OMElement in) throws Exception {
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMDocument doc = factory.createOMDocument();
		doc.addChild(in);
		doc.serializeAndConsume(new FileOutputStream("temp.xml"));
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document out = builder.parse(new File("temp.xml"));
		return out;

	}
	public OMElement XML2OMElement(File file) {
		if (file == null) {
			System.err.println("Request file does not exit");
		} else {
			try {
				XMLStreamReader parser = XMLInputFactory.newInstance()
						.createXMLStreamReader(new FileInputStream(file));
				StAXOMBuilder builder = new StAXOMBuilder(parser);
				OMDocument doc = builder.getDocument();
				OMElement creElement = doc.getOMDocumentElement();
				return creElement;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static void main(String[] args) throws Exception,
			ParserConfigurationException, SAXException {

		Document testDocument = null;
		String testString = null;
		Node textNode = null;
		File testFile = null;
		File resultFile = null;
		OMElement testOMElement = null;

		String tagName1 = "operation";
		String tagName2 = "requestSoap";

//		// test for getDocumentFromFile
//		System.out.println("test for getDocumentFromFile");
//		testFile = new File("testData/localServiceRequest4ID.xml");
//		FileInputStream inStream = null;
//		try {
//			inStream = new FileInputStream(testFile);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Document resultDocumentFromFile = getDocumentFromFile(inStream);
//		System.out.println("resultDocumentFromFile.toString():\n"
//				+ resultDocumentFromFile.toString());
//		System.out.println();
//
//		// test for getStringFromDocument
//		System.out.println("test for getStringFromDocument");
//		System.out.println("the string in the Document is :\n"
//				+ getStringFromDocument(resultDocumentFromFile));
//		System.out.println();
//
//		// test for getOutputStreamFromDocument
//
//		// test for getFirstElementBodyStringByTagName
//		System.out.println("test for getFirstElementBodyStringByTagName");
//		String tagName1ElementString = getFirstElementBodyStringByTagName(
//				resultDocumentFromFile, tagName1);
//		String tagName2ElementString = getFirstElementBodyStringByTagName(
//				resultDocumentFromFile, tagName2);
//		System.out.println("operation is:\n" + tagName1ElementString);
//		System.out.println("requestSoap is:\n" + tagName2ElementString);
//		System.out.println();
//
//		// test for getFirstOMElementByTagName
//		System.out.println("test for getFirstOMElementByTagName");
//		OMElement tagName1OMElement = getFirstOMElementByTagName(
//				resultDocumentFromFile, tagName1);
//		OMElement tagName2OMElement = getFirstOMElementByTagName(
//				resultDocumentFromFile, tagName2);
//		System.out.println("operation is:\n" + tagName1OMElement);
//		System.out.println("requestSoap is:\n" + tagName2OMElement);
//		System.out.println();
//
//		// test for getDocumentFormString
//		System.out.println("test for getDocumentFormString");
//		testString = getStringFromDocument(resultDocumentFromFile);
//		Document document = getDocumentFromString(testString);
//		System.out.println("the string in the transformed document is:");
//		System.out.println(getStringFromDocument(document));
//		System.out.println();
//
//		// test for getDocumentFromOMElement
//		System.out.println("test for getDocumentFromOMElement");

		 OMFactory fac = OMAbstractFactory.getOMFactory();
		 OMNamespace omNs = fac.createOMNamespace(
		 "http://service.bpmnengine.sdp.act.org.cn", "ebf");
		 testOMElement = fac.createOMElement("response", omNs);
		 testOMElement.setText("哈哈哈");
		 OMElement sElement = testOMElement.cloneOMElement();
		 OMElement parentOmElement = OMAbstractFactory.getOMFactory().createOMElement("parent", null);
		 
		 parentOmElement.addAttribute("a1", "value", omNs);
		 parentOmElement.addChild(sElement);
		 parentOmElement.addChild(testOMElement);
		 
		 System.out.println(parentOmElement);
		 
		 
		 OMElement element = OMAbstractFactory.getOMFactory().createOMElement("response", null);
			OMElement infoOmElement = OMAbstractFactory.getOMFactory().createOMElement("info", null);
//			infoOmElement.addAttribute(WSInvokerReceiver.ISSUCCESSFUL, "true", null);
//			infoOmElement.addAttribute(WSInvokerReceiver.STARTDATE, "20101217", null);
//			infoOmElement.addAttribute(WSInvokerReceiver.ENDTIME, "500", null);
			
			element.addChild(infoOmElement);
		 QName qName = new  QName("info");
		 System.out.println("测试QName");
		 OMElement cElement = element.getFirstChildWithName(qName);
		 if(cElement == null){
			 System.err.println("为空");
		 }
		 System.out.println(omToString(cElement));
		 
//		 Document resultDocumentFromOMElement =
//		 getDocumentFromOMElement(testOMElement);
//		 System.out.println("the string in the transformed Document is :\n"+
//		 getStringFromDocument(resultDocumentFromOMElement));
//		 System.out.println("another exmple for getDocumentFormOMElement");

//		System.out.println("测试：omToString");
//		System.out.println(omToString(testOMElement));
//System.out.println(omToString(parentOmElement));
		 
//		resultDocumentFromOMElement = getDocumentFromOMElement(tagName2OMElement);
//		System.out.println("the string in the transformed document is :\n"
//				+ getStringFromDocument(resultDocumentFromOMElement));
//		System.out.println();
		
//		System.out.println("测试：将文档写入一个文件中");
//		File file = new File("doc4show","file4document");
//		getFileFromDocument(document,file);

	}

	public static void getFileFromDocument(Document document, File file) {
		//String s = getStringFromDocument(document);
		
		try {
			FileOutputStream out = new FileOutputStream(file);
			getOutputStreamFromDocument(document, out);
//			DataOutputStream dataOutputStream = new  DataOutputStream(out);
//			dataOutputStream.writeChars(s);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static String omToString(OMElement in) {
		String string = null;
		string = getStringFromDocument(getDocumentFromOMElement(in));
		return string;
	}
	
//	public static Element omToElement(Document doc,OMElement in) {
//		Element element = doc;
//	}
}
