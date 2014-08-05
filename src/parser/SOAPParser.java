package parser;

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SOAPParser{
	private static int idIndex;
	private static Document doc;
	private String content;
	
	public SOAPParser(String soapContent){
		super();
		initDom(soapContent);
		idIndex=2;
		content="<div id=\"1\" style=\"padding-left:0px; width:630px; height:10px; background-color:#FFCC99\">\n";
	}
	
	private static void initDom(String soapContent){
		try {
			File file = new File("tmpSoap");
			FileWriter fw = new FileWriter(file);
			
			//去掉特殊标记
			if(soapContent.contains("<![CDATA[") && soapContent.contains("]]>")){
				soapContent = soapContent.replaceAll("<!\\Q[\\ECDATA\\Q[\\E", "");
				soapContent = soapContent.replaceAll("]]>", "");
			}
			fw.write(soapContent);
			fw.close();
			idIndex = 2;
			// 创建XML文档解析器
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();	
			// Create a DocumentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
			// 解析XML文档
			doc = builder.parse(file);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<ParamInfo> getParamInfoList(NodeList nodeList){
		ArrayList<ParamInfo> paramInfoList = null;
		
		if(nodeList.getLength()>0){
			paramInfoList = new ArrayList<ParamInfo>();
		}
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node tmpNode = nodeList.item(i);
			if(tmpNode.getNodeType()==Node.ELEMENT_NODE){
				String nodeName = tmpNode.getNodeName();
				ParamInfo tmpParamInfo = new ParamInfo(nodeName.substring(nodeName.indexOf(':')+1));
				ArrayList<ParamInfo> tmpParamInfoList = getParamInfoList(tmpNode.getChildNodes());
				tmpParamInfo.setParamList(tmpParamInfoList);
				if(tmpParamInfoList==null || tmpParamInfoList.size()==0){//若为简单类型,则获取节点的值
					tmpParamInfo.setValue(tmpNode.getFirstChild().getNodeValue());					

					tmpParamInfo.setSimpleType(true);
				}
				else{//复杂类型
					tmpParamInfo.setSimpleType(false);
				}
				paramInfoList.add(tmpParamInfo);
			}			
		}
		return paramInfoList;
	}
	
	private static ParamInfo getSoapMessage(){
		ParamInfo paramInfo = null;
		ArrayList<ParamInfo> paramList = new ArrayList<ParamInfo>();
		
		//initDom(fileName);
		try {
			// dom解析,仅仅考虑了soap:Body和soapenv:Body两种情况
			NodeList bodyList = doc.getElementsByTagName("soap:Body");
			if(bodyList==null || bodyList.getLength()==0){
				bodyList = doc.getElementsByTagName("soapenv:Body");
			}
						
			for(int i=0;i<bodyList.getLength();i++){
				Node node = bodyList.item(i);
				NodeList valueList = node.getChildNodes();				
				
				for(int j = 0;j < valueList.getLength();j++){
					Node tmpNode = valueList.item(j);
					if(tmpNode.getNodeType()==Node.ELEMENT_NODE){
						String nodeName = tmpNode.getNodeName();
						paramInfo = new ParamInfo(nodeName.substring(nodeName.indexOf(':')+1));
						NodeList tmpList = tmpNode.getChildNodes();
						paramList = getParamInfoList(tmpList);
					}
				}
			}
		}
		catch(Exception e){
			
		}
		if(paramList!=null && paramList.size()!=0){			
			paramInfo.setParamList(paramList);
		}
		return paramInfo;
	}
	
	
	private static ParamInfo getUpSoapMessage(){
		ParamInfo paramInfo = null;
		ArrayList<ParamInfo> paramList = new ArrayList<ParamInfo>();
		
		//initDom(fileName);
		try {
			// dom解析,仅仅考虑了soap:Body和soapenv:Body两种情况
			NodeList bodyList = doc.getElementsByTagName("soap:Body");
			if(bodyList==null || bodyList.getLength()==0){
				bodyList = doc.getElementsByTagName("soapenv:Body");
			}
						
			for(int i=0;i<bodyList.getLength();i++){
				Node node = bodyList.item(i);
				NodeList valueList = node.getChildNodes();				
				
				for(int j = 0;j < valueList.getLength();j++){
					Node tmpNode = valueList.item(j);
					if(tmpNode.getNodeType()==Node.ELEMENT_NODE){
						String nodeName = tmpNode.getNodeName();
						paramInfo = new ParamInfo(nodeName.substring(nodeName.indexOf(':')+1));
						NodeList tmpList = tmpNode.getChildNodes();
						paramList = getParamInfoList(tmpList);
					}
				}
			}
		}
		catch(Exception e){
			
		}
		if(paramList!=null && paramList.size()!=0){			
			paramInfo.setParamList(paramList);
		}
		return paramInfo;
	}
	
	
	public void initContent(ParamInfo paramInfo){		
		ArrayList<ParamInfo> paramInfoList = paramInfo.getParamList();
		if(paramInfoList == null || paramInfoList.size()==0){//无子节点			
			content += (paramInfo.getName()+ "=" + paramInfo.getValue() +"<br />\n");
			return ;
		}
		content += ("<div id=\""+idIndex+"\"  style=\"background-color:#CCCCCC\"><a href=\"javascript:onclick=showDiv('"+(++idIndex)+"')\">"+paramInfo.getName()+"</a></div>\n");
		content += ("<div id=\""+(idIndex++)+"\" style=\" display:none;padding-left:5px; background-color:#FFCC99\">\n");
		for(int i=0;i<paramInfoList.size();i++){
			initContent(paramInfoList.get(i));
		}
		content += ("</div>");
	}
	
	public String getHTMLContent(){
		try{
			initContent(getSoapMessage());
			content += ("</div>");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(content);
		return this.content;
	}
	
	public String getUpHTMLContent(){
		try{
			initContent(getUpSoapMessage());
			content += ("</div>");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(content);
		return this.content;
	}
	
	public static void main(String[] args) throws IOException{
		
		FileReader fr = new FileReader(new File("soap2.txt"));
		BufferedReader br = new BufferedReader(fr);
		String soapContent="";
		String tmpStr;
		while((tmpStr=br.readLine())!=null){
			soapContent += tmpStr;
		}
		//System.out.println(soapContent);
		SOAPParser parser = new SOAPParser(soapContent);
		System.out.println(parser.getHTMLContent());
		/*ParamInfo paramInfo2 = parser.getSoapMessage("soap3.txt");
		
		ArrayList<ParamInfo> list = paramInfo2.getParamList();
		if(list==null){
			System.out.println("null");
			return ;
		}
		System.out.println("*************************************");*/
		//content = "";		
		
		/*System.out.println(paramInfo2.getName()+"("+paramInfo2.getType()+")");
		for (int i = 0; i < list.size(); i++) {
			ParamInfo paramInfo = list.get(i);
			//System.out.println(paramInfo.getName()+" \t"+list.get(i).getType());
			//System.out.println(paramInfo.isSimpleType());
			if(!paramInfo.isSimpleType()){
				ArrayList<ParamInfo> typeList = paramInfo.getParamList();
				System.out.println("|-"+paramInfo.getName()+":"+paramInfo.isSimpleType()+":"+paramInfo.getValue());				
				for(int j = 0; j < typeList.size(); j++){
					ParamInfo tmpParamInfo = typeList.get(j);
					System.out.println(" |-"+typeList.get(j).getName()+":"+typeList.get(j).isSimpleType()+":"+typeList.get(j).getValue());
					if(!tmpParamInfo.isSimpleType()){
						ArrayList<ParamInfo> typeList2 = tmpParamInfo.getParamList();
						for(int k = 0; k < typeList2.size(); k++){
							ParamInfo tmpParamInfo2 = typeList2.get(k);
							System.out.println("   |-"+tmpParamInfo2.getName()+":"+tmpParamInfo2.isSimpleType()+":"+tmpParamInfo2.getValue());
							if(!tmpParamInfo2.isSimpleType()){
								ArrayList<ParamInfo> typeList3 = tmpParamInfo2.getParamList();
								for(int m = 0; m < typeList3.size(); m++){									
									ParamInfo tmpParamInfo3 = typeList3.get(m);
									System.out.println("    |-"+tmpParamInfo3.getName()+":"+tmpParamInfo3.isSimpleType()+":"+tmpParamInfo3.getValue());
									if(!tmpParamInfo3.isSimpleType()){
										ArrayList<ParamInfo> typeList4 = tmpParamInfo3.getParamList();
										for(int n = 0; n < typeList4.size(); n++){									
											ParamInfo tmpParamInfo4 = typeList4.get(n);
											System.out.println("     |-"+tmpParamInfo4.getName()+":"+tmpParamInfo4.isSimpleType()+":"+tmpParamInfo4.getValue());
										}
									}
								}
							}
						}
					}					
				}
			}
			else{
				System.out.println("|-"+paramInfo.getName()+"\t"+paramInfo.getType());
			}
		}*/
	}
}