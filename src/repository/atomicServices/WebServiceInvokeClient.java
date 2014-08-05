package repository.atomicServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class WebServiceInvokeClient {
	File requestFile = null;
	String endpointReference = null;

	public WebServiceInvokeClient() {}
	
	
	public WebServiceInvokeClient(String filePath, String endpointReference) {
		requestFile = new File(filePath);
		this.endpointReference = endpointReference;
	}

	/**
	 * 
	 * @param file
	 *            传入的xml文件
	 * @param endpointReference
	 *            执行组件所在的位置
	 * @throws AxisFault
	 * @return response 获得的应答报文
	 * @throws FileNotFoundException 
	 */
	public OMElement invoke(InvokeType type, String serviceUrl) throws AxisFault, FileNotFoundException {
		FileInputStream inStream = new FileInputStream(requestFile);
		Document inputFileDoc = TransformerUtils.getDocumentFromFile(inStream);
		
		System.out.println("输入的文件：");
		System.out.println(TransformerUtils.getStringFromDocument(inputFileDoc));
		
		//OMElement inputFileElm = XML2OMElement(requestFile);
		if (inputFileDoc != null) {
			//改造inputFileDoc 
			if(type.equals(InvokeType.WSDL)){
				System.out.println("以wsdl的方式调用");
				Element rootElement = inputFileDoc.getDocumentElement();
				
				rootElement.setAttribute("type", "wsdl");
				Element wsdlElement = inputFileDoc.createElement("wsdl");
				wsdlElement.setTextContent(serviceUrl);
				rootElement.appendChild(wsdlElement);
				//inputFileDoc.appendChild(rootElement);
			}else if(type.equals(InvokeType.SERVICEID)){
				System.out.println("以serviceID的方式调用");
				Element rootElement = inputFileDoc.getDocumentElement();
				System.out.println("rootElement::"+rootElement);
				rootElement.setAttribute("type", "serviceID");
				Element serviceIDElement = inputFileDoc.createElement("serviceID");
				serviceIDElement.setTextContent(serviceUrl);
				rootElement.appendChild(serviceIDElement);
				//inputFileDoc.appendChild(rootElement);
			}else {
				System.err.println("调用类型出错。请选择wsdl或者serviceID");
				return null;
			}
			
			System.out.println("改造后的file:");
			System.out.println(TransformerUtils.getStringFromDocument(inputFileDoc));
		OMElement inputFileElm;
		try {
			inputFileElm = TransformerUtils.docToOM(inputFileDoc);
			Options options = new Options();
			EndpointReference epr = new EndpointReference(endpointReference);
			options.setTo(epr);
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);

			OMElement response = sender.sendReceive(inputFileElm);
			return response;
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}			
		}
		return null;
	}
	
	/**
	 *构造调用服务的报文，并使用服务的deployID调用服务
	 * @param deployID  服务ID
	 * @param insoap  调用服务的soap报文
	 * @return OMElement  返回的结果报文
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * 
	 */
	public String invokeByID(String insoap,String deployID,String portType,String operation, String userName) throws ParserConfigurationException, SAXException, IOException {
		
		String responseSoap="";
		FileInputStream inStream = new FileInputStream(requestFile);
		Document baseFileDoc = TransformerUtils.getDocumentFromFile(inStream);
		
		System.out.println("输入的文件：");
		System.out.println(TransformerUtils.getStringFromDocument(baseFileDoc));
		
		Document doc = null;  
		insoap = new String(insoap.getBytes(),"UTF-8");
		StringReader sr = new StringReader(insoap);
		InputSource is = new InputSource(sr);  
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder=factory.newDocumentBuilder();  
		doc = builder.parse(is);  
		System.out.println("输入的soap报文：");
		System.out.println(TransformerUtils.getStringFromDocument(doc));
		if(doc!= null)
		{
			
			//doc为传入的调用服务的soap报文，在soap:Body中加入userID和userType
			Element docRootElement = doc.getDocumentElement();
			
			System.out.println("soap:Envelope:");
			System.out.println(docRootElement.getNodeName());
			
			NodeList list=docRootElement.getElementsByTagName("soap:Body");
			if(list.getLength()==0)
			{
				list=docRootElement.getElementsByTagName("soapenv:Body");
			}
			Element bodyElement=(Element)list.item(0);
			bodyElement.setAttribute("userID" , userName);
			bodyElement.setAttribute("userType" , "owner");
			
			System.out.println("输出的soap报文1：");
			System.out.println(TransformerUtils.getStringFromDocument(doc));
			
			System.out.println("以serviceID的方式调用");
			Element baseRootElement = baseFileDoc.getDocumentElement();//baseRootElement为invokeRequest
			//根节点invokeRequest添加type=serviceID属性
			baseRootElement.setAttribute("type", "serviceID");
			
			
			System.out.println("输出的soap报文2：");
			System.out.println(TransformerUtils.getStringFromDocument(baseFileDoc));
			
			//添加serviceID节点
			Element serviceIDElement = baseFileDoc.createElement("serviceID");
			serviceIDElement.setTextContent(deployID);
			baseRootElement.appendChild(serviceIDElement);
			
			System.out.println("输出的soap报文3：");
			System.out.println(TransformerUtils.getStringFromDocument(baseFileDoc));
			
			//添加protType节点
			Element protTypeElement = baseFileDoc.createElement("protType");
			protTypeElement.setTextContent(portType);
			baseRootElement.appendChild(protTypeElement);
			
			System.out.println("输出的soap报文4：");
			System.out.println(TransformerUtils.getStringFromDocument(baseFileDoc));
			
			//添加operation节点
			Element operationElement = baseFileDoc.createElement("operation");
			operationElement.setTextContent(operation);
			baseRootElement.appendChild(operationElement);
			
			System.out.println("输出的soap报文5：");
			System.out.println(TransformerUtils.getStringFromDocument(baseFileDoc));
			
			//替换requestSoap节点的内容,该节点中内容即为调用的报文soap:Envelope
			NodeList requestSoapList= baseFileDoc.getElementsByTagName("requestSoap");
			Element requestSoapElement=(Element)requestSoapList.item(0);
			String docText=TransformerUtils.getStringFromDocument(doc);
			requestSoapElement.setTextContent(docText);
			
			String response=TransformerUtils.getStringFromDocument(baseFileDoc);
			System.out.println("改造后的调用报文:");
			System.out.println(response);
		}
		OMElement inputFileElm;
		try {
			inputFileElm = TransformerUtils.docToOM(baseFileDoc);
			Options options = new Options();
			EndpointReference epr = new EndpointReference(endpointReference);
			options.setTo(epr);
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			OMElement responseOME = sender.sendReceive(inputFileElm);
			responseSoap=responseOME.toString();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
		return responseSoap;
	}

	private static void showResult(OMElement response) {
		System.out.println("response is :");
		try {
			response.serialize(System.out);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		FileInputStream inStream = new FileInputStream(new File("/home/sdp/testData/soap.xml"));
		Document baseFileDoc = TransformerUtils.getDocumentFromFile(inStream);
		System.out.println("insoap：");
		String insoap=TransformerUtils.getStringFromDocument(baseFileDoc);
		System.out.println(insoap);
		WebServiceInvokeClient wsInvokeClient=new WebServiceInvokeClient("/home/sdp/testData/localServiceRequest.xml","http://192.168.104.115:8192/WebServiceInvoke/");
		System.out.println("111");
		String rs=wsInvokeClient.invokeByID(insoap,"WS_1575","CaculatePriceSoap11Binding","caculatePrice","user1");
		System.out.println("222");
		System.out.println(rs);
	}



}