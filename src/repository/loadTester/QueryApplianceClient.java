package repository.loadTester;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import config.Config;

public class QueryApplianceClient {
	private String queryAddr;			//主机查询的地址
	
	public QueryApplianceClient(String queryAddr){
		this.queryAddr = queryAddr;
	}
	
	public QueryApplianceClient(){}
	
	public String getQueryAddr() {
		return queryAddr;
	}


	public void setQueryAddr(String queryAddr) {
		this.queryAddr = queryAddr;
	}

	public ArrayList<String> queryAppliance(){
		ArrayList<String> appliances = new ArrayList<String>();
		Document requestDoc = createRequestDoc(Constants.HOST);
		Document result = getResult(requestDoc, this.queryAddr);
		appliances = parseQueryResult(result);
		return appliances;
	}
	
	public ArrayList<Host> queryHost(){
		ArrayList<Host> hosts = new ArrayList<Host>();
		Document requestDoc = createRequestDoc(Constants.ALL);
		Document result = getResult(requestDoc, this.queryAddr);
		hosts = parseQueryResultDetails(result);
		return hosts;
	}
	
	private  Document createRequestDoc(String type){
		Document document = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.newDocument();
			
			Element root = document.createElement(Constants.QUERY_APPLIANCE_REQUEST);
			root.setAttribute("type", type);		//查询所有的主机，提供了查询各种设备信息的方法
			document.appendChild(root);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return document;
		
	}

	private  Document getResult(Document document,String url)
	{
		File tmp=null;
		String tmpresult = null;
		Document result=null;
		
		tmp=saveXmlToFile(document);
		if(tmp==null)
			return null;
		
		tmpresult = sendAndGetResult(tmp,url);
		if(tmpresult==null)
			return null;
		
		result=StringToDoc(tmpresult);
		
		return result;
	}
	
	
	private String sendAndGetResult(File tmp,String url)
	{
		PostMethod post = new PostMethod(url);
		
		RequestEntity entity = new FileRequestEntity(tmp,Constants.CODE);
    	post.setRequestEntity(entity);
		HttpClient client = new HttpClient();
		String result=null;
		
		try 
		{
			int resultCode = client.executeMethod(post);
			result = post.getResponseBodyAsString();
			System.out.println("resultCode: "+resultCode);
			System.out.println("Response body:\n " + result);
			// remove the tmp file
			tmp.delete();
			post.releaseConnection();
		} 
		catch (HttpException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
		return result;		
	}
	
	
	private File saveXmlToFile(Document doc)
	{
		File tmp = new File(Constants.TMP_FILE_PATH + Constants.FILE_PERFIX + Calendar.getInstance().getTimeInMillis()+ Constants.FILE_SUFFIX);
		
		if (!tmp.exists())
		{
			try {
				tmp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		TransformerFactory transFac = TransformerFactory.newInstance();
		try {
			Transformer trans = transFac.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(tmp);
			trans.transform(source, result);
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	
		
		return tmp;
	}
	
	private ArrayList<Host> parseQueryResultDetails(Document doc){
		ArrayList<Host> hostsList=new ArrayList<Host>();
		Element root = doc.getDocumentElement();
		Element hosts = (Element)root.getElementsByTagName("hosts").item(0);
		NodeList hostList = (NodeList) hosts.getElementsByTagName(Constants.HOST);
		
		for(int i = 0;i<hostList.getLength();i++)
		{
			String host_id = ((Element)hostList.item(i)).getAttribute(Constants.ID);
			String host_childNum = ((Element)hostList.item(i)).getAttribute(Constants.CHILDAPPLIANCENUM);
			String host_cpu = ((Element)hostList.item(i)).getAttribute(Constants.CPU);
			String host_memory = ((Element)hostList.item(i)).getAttribute(Constants.MEMORY);
			String host_timestamp = ((Element)hostList.item(i)).getAttribute(Constants.TIMESTAMP);
			
			Host host = new Host();
			host.setChildApplianceNum(host_childNum);
			host.setCpu(host_cpu);
			host.setId(host_id);
			host.setMemory(host_memory);
			host.setTimestamp(host_timestamp);
			NodeList applianceList=(NodeList) ((Element)hostList.item(i)).getElementsByTagName(Constants.SUBAPPLIANCE);
			for(int j=0;j<applianceList.getLength();j++)
			{
				SubAppliance subAppliance = new SubAppliance(); 
				subAppliance.setAppNum(((Element)applianceList.item(j)).getAttribute(Constants.APPNUM));
				subAppliance.setId(((Element)applianceList.item(j)).getAttribute(Constants.ID));
				subAppliance.setTimestamp(((Element)applianceList.item(j)).getAttribute(Constants.TIMESTAMP));
				subAppliance.setType(((Element)applianceList.item(j)).getAttribute(Constants.TYPE));
				host.getHost().add(subAppliance);
			}
			hostsList.add(host);
		}
		return hostsList;
	}
	
	private ArrayList<String> parseQueryResult(Document doc){
		ArrayList<String> appliances = new ArrayList<String>();
		
		Element root = doc.getDocumentElement();
		Element hosts = (Element)root.getElementsByTagName("hosts").item(0);
		NodeList hostList = (NodeList) hosts.getElementsByTagName(Constants.HOST);
		
		for(int i = 0;i<hostList.getLength();i++){
			String applianceId = ((Element)hostList.item(i)).getAttribute(Constants.ID);
			appliances.add(parseApplianceId(applianceId));
		}
		return appliances;
	}
	
	private String parseApplianceId(String applianceId){
		if((applianceId != null) && (applianceId != "")){
			return applianceId.substring((Constants.HOST_PREFIX + Constants.HTTP).length(), applianceId.length());
		}
		return "";
	}
	
	private static String docToString(Document doc){
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			transformer = tf.newTransformer();
			transformer.setOutputProperty("encoding", "utf8");
			DOMSource domSource = new DOMSource(doc);
			Result result = new StreamResult(bos);
			transformer.transform(domSource,result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return bos.toString();
	}
	
	private static Document StringToDoc(String str){
		StringReader sr = new StringReader(str);
		InputSource is = new InputSource(sr);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = dbf.newDocumentBuilder();
			doc = builder.parse(is);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public static void main(String[] args) {
		QueryApplianceClient query = new QueryApplianceClient(Config.getApplianceQueryUrl());
		ArrayList<Host> hosts = query.queryHost();
		for (Host host : hosts) {
			System.out.println("getChildApplianceNum:  "+host.getChildApplianceNum());
			System.out.println("getCpu:  "+host.getCpu());
			System.out.println("getId:  "+host.getId());
			System.out.println("getMemory:  "+host.getMemory());
			System.out.println("host-->getType:  "+host.getHost().get(0).getType());
		}
	}
}