package parser;

import java.util.*;

import javax.wsdl.*;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.factory.*;
import javax.wsdl.xml.*;

import java.io.*;

import org.w3c.dom.*;

public class ParameterParse{	
	public final static int PARAM_INPUT = 0; 
	public final static int PARAM_OUTPUT = 1;
	public final  ArrayList<ParamInfo>  endNodeList=new ArrayList<ParamInfo>();
	
	//判断是否是简单类型
	private static boolean isSimpleType(String type) {
		type = type.substring(type.indexOf(':')+1);
		String[] simpleType = {"int","integer","string","float","double","char","long","boolean"};
		
		for(int i=0;i<simpleType.length;i++){
			if(type.toLowerCase().equals(simpleType[i]))
				return true;
		}
		return false;
	}
	
	
	
	public void searchForEndNode(ParamInfo paramInfo){
		if(paramInfo.isSimpleType()){
			endNodeList.add(paramInfo);
		}else{
			for(int i=0;i<paramInfo.getParamList().size();i++){
				searchForEndNode(paramInfo.getParamList().get(i));
			}
		}
	}
	
	//
	public ArrayList<ParamInfo> getEndNodes(ParamInfo paramInfo){
		searchForEndNode(paramInfo);
		return endNodeList;
	}
	
	
	
	//获得最小出现次数
	private static int getMin(NamedNodeMap namedNodeMap){
		//默认为1
		if (namedNodeMap.getNamedItem("minOccurs") == null)
			return 1;	
		
		try{
			return Integer.parseInt(namedNodeMap.getNamedItem("minOccurs").getNodeValue());
		}
		catch(Exception e){
			return 1;
		}
	}
	
	//获得最大出现次数
	private static int getMax(NamedNodeMap namedNodeMap){
		Node node = namedNodeMap.getNamedItem("maxOccurs");
		//默认为1
		if (node == null)
			return 1;		
		//System.out.println(namedNodeMap.getNamedItem("minOccurs").getNodeValue().equals("0"));
		try{
			//System.out.println(node.getNodeValue());
			//无线次数的记录为-1
			if(node.getNodeValue().equals("unbounded")){				
				return -1;
			}
			else{
				return Integer.parseInt(node.getNodeValue());
			}
		}
		catch(Exception e){
			return 1;
		}
	}
	
	//若参数为复杂类型，则调用此方法(该方法从types标签中查询)
	private static ArrayList<ParamInfo> getTypesByElementName(Definition def,Document doc,String elementName){
		ArrayList<ParamInfo> paramList = new ArrayList<ParamInfo>();
		// 标签头部
		String preTag = "";
		// dom解析
		Types types = def.getTypes();
		// 若types内容非空则表示包含complexType
		List list = types.getExtensibilityElements();
		
		//System.out.println("elementName:"+elementName);
		// 默认types标签中只包含一个直接子标签
		ExtensibilityElement extensibilityElement = (ExtensibilityElement)list.get(0);
		Schema schema = (Schema)extensibilityElement;
		preTag = schema.getElement().getPrefix()+":";
		NodeList elementList = doc.getElementsByTagName(preTag + "element");
		//System.out.println(elementList.getLength());
		for (int i = 0; i < elementList.getLength(); i++){
			Node elementNode = elementList.item(i);
			NamedNodeMap map = elementNode.getAttributes();
			if(map.getNamedItem("name")==null){
				continue;
			}
			//System.out.println(map.getNamedItem("name").getNodeValue());
			if (map.getNamedItem("name").getNodeValue().equals(elementName)) {
				//System.out.println("element name:" + map.getNamedItem("name").getNodeValue());
				NodeList complexTypeNodeList = elementNode.getChildNodes();
				for (int j = 0; j < complexTypeNodeList.getLength(); j++) {					
					NodeList sequenceList = complexTypeNodeList.item(j)
							.getChildNodes();
					for (int k = 0; k < sequenceList.getLength(); k++) {
						Node sequenceOrComplexContentNode = sequenceList
								.item(k);
						if (sequenceOrComplexContentNode.getNodeName().equals(
								preTag+"sequence")) {
							// pojo
							NodeList elementsList = sequenceOrComplexContentNode
									.getChildNodes();
							for (int m = 0; m < elementsList.getLength(); m++) {								
								Node element = elementsList.item(m);
								if (element.getNodeName().equals(
										preTag + "element")) {
									NamedNodeMap elementNode2 = element
											.getAttributes();
																		
									if (elementNode2.getNamedItem("ref") == null) {// 无ref属性值
										String typeString = elementNode2
												.getNamedItem("type")
												.getNodeValue();
										String nameString = elementNode2
												.getNamedItem("name")
												.getNodeValue();										
										typeString = typeString
												.substring(typeString
														.indexOf(':') + 1);
										ParamInfo tmpParam = new ParamInfo(
												nameString,typeString);
										
										tmpParam.setMinOccurs(getMin(elementNode2));
										tmpParam.setMaxOccurs(getMax(elementNode2));
										System.out.println(tmpParam.isOptional());
										//System.out.println("typeString:"+typeString);
										if (isSimpleType(typeString)) {											
											paramList.add(tmpParam);
										} else {
											/**/											
											tmpParam.setSimpleType(false);
											tmpParam
													.setParamList(getTypesByElementName(
															def,
															doc,
															typeString
																	.substring(typeString
																			.indexOf(':') + 1)));
											paramList.add(tmpParam);
											/**/											
										}
									} else {// 若有ref属性值,则递归查询
										/**/
										String refName = elementNode2
												.getNamedItem("ref")
												.getNodeValue();
										refName = refName.substring(refName
												.indexOf(':') + 1);

										ParamInfo tmpParam = new ParamInfo("",
												refName);
										tmpParam.setSimpleType(false);
										tmpParam.setMinOccurs(getMin(elementNode2));
										tmpParam.setMaxOccurs(getMax(elementNode2));
										tmpParam
												.setParamList(getTypesByElementName(
														def, doc, refName));
										paramList.add(tmpParam);
										/**/										
									}
								}
							}
						}
					}
				}
			}
		}
		//若paramList不为空，表示已经获取到了参数，则返回获取的参数,否则，继续查找complexType标签
		if(paramList != null && paramList.size() != 0){
			return paramList;
		}
		//System.out.println("||||||||||||||||||||||");
		NodeList complexTypeList = doc.getElementsByTagName(preTag + "complexType");
		for (int i = 0; i < complexTypeList.getLength(); i++){
			Node complexTypeNode = complexTypeList.item(i);
			NamedNodeMap map = complexTypeNode.getAttributes();
			if(map.getNamedItem("name")==null){
				continue;
			}
			//System.out.println(map.getNamedItem("name").getNodeValue()+"&"+elementName);
			//找到匹配的类型
			if (map.getNamedItem("name").getNodeValue().equals(elementName)) {
				//System.out.println("element name:" + map.getNamedItem("name").getNodeValue());
				NodeList sequenceNodeList = complexTypeNode.getChildNodes();
				for (int j = 0; j < sequenceNodeList.getLength(); j++) {
					//System.out.println(sequenceNodeList.item(j).getChildNodes());
					NodeList sequenceList = sequenceNodeList.item(j)
							.getChildNodes();
					for(int k=0;k<sequenceList.getLength();k++){
						Node elementNode = sequenceList.item(k);
						//System.out.println(sequenceList.getLength());						
						//找到sequence标签
						if (elementNode.getNodeName().equals(
								preTag + "element")) {							
							// pojo							
							NamedNodeMap elementNode2 = elementNode.getAttributes();
							
							if (elementNode2.getNamedItem("ref") == null) {// 无ref属性值
								String typeString = elementNode2.getNamedItem(
										"type").getNodeValue();
								String nameString = elementNode2.getNamedItem(
										"name").getNodeValue();
								typeString = typeString.substring(typeString
										.indexOf(':') + 1);
								ParamInfo tmpParam = new ParamInfo(nameString,
										typeString);
								
								tmpParam.setMinOccurs(getMin(elementNode2));
								tmpParam.setMaxOccurs(getMax(elementNode2));
						
								//System.out.println("typeString:" + typeString);
								if (isSimpleType(typeString)) {
									paramList.add(tmpParam);
								} else {
									/**/									
									tmpParam.setSimpleType(false);
									tmpParam
											.setParamList(getTypesByElementName(
													def,
													doc,
													typeString
															.substring(typeString
																	.indexOf(':') + 1)));
									paramList.add(tmpParam);
									/**/									
								}
							} else {// 若有ref属性值,则递归查询
								/**/
								String refName = elementNode2
										.getNamedItem("ref")
										.getNodeValue();
								refName = refName
								.substring(refName
										.indexOf(':') + 1);
								
								ParamInfo tmpParam = new ParamInfo("",refName);
								tmpParam.setSimpleType(false);
								tmpParam.setMinOccurs(getMin(elementNode2));
								tmpParam.setMaxOccurs(getMax(elementNode2));
								tmpParam.setParamList(getTypesByElementName(
										def, doc, refName));
								paramList.add(tmpParam);
								/**/								
							}							
						}
					}
				}
			}
		}
		return paramList;
	} 
	
	/***************************************************************************
	 * 根据url路径名获取参数及其对应的数据类型，为用户输入提供提示功能
	 * 
	 * @param urlString -
	 *            url路径名
	 * @return 一个包含参数名及对应数据类型的Map类型数据
	 */
	private static ParamInfo getParamType(int paramType,String urlString,
			 String operation) {
		ParamInfo paramInfo = null;
		String paramParseString = (paramType == PARAM_INPUT ? "wsdl:input"
				: "wsdl:output");
		String messageName = "";

		try {
			WSDLFactory factory = WSDLFactory.newInstance();
			WSDLReader reader = factory.newWSDLReader();
			reader.setFeature("javax.wsdl.verbose", true);
			reader.setFeature("javax.wsdl.importDocuments", true);
			WSDLWriter writer = factory.newWSDLWriter();
			Definition def = reader.readWSDL(urlString);
			Document doc = writer.getDocument(def);
			
			boolean isFound = false;
			//dom解析
			NodeList operationList = doc
					.getElementsByTagName("wsdl:operation");
			for (int i = 0; !isFound && i < operationList.getLength(); i++) {
				Node operationNode = operationList.item(i);				
				NamedNodeMap namedNodeMap = operationNode.getAttributes();
				//System.out.println(namedNodeMap.getLength());
				for(int j = 0; !isFound && j < namedNodeMap.getLength();j++){					
					Node tmpNode = namedNodeMap.item(j);
					
					if(tmpNode.getNodeName()!="name"){
						continue;
					}
					if(operation.equals(tmpNode.getNodeValue())){//得到要查找的节点
						NodeList operationChildList = operationNode.getChildNodes();
						//遍历wsdl:operation标签中内容的子节点，找到wsdl:input子节点
						for(int k = 0; !isFound && k < operationChildList.getLength();k++){
							Node inputNode = operationChildList.item(k);
							
							if(inputNode.getNodeName().equals(paramParseString)){								
								NamedNodeMap inputNodeMap = inputNode.getAttributes();
								//遍历wsdl标签中内容的属性，找到message属性的值								
								for(int m = 0; !isFound && m < inputNodeMap.getLength();m++){
									Node inputMessageNode = inputNodeMap
											.item(m);
									//得到message的名称
									if (inputMessageNode.getNodeName().equals(
											"message")) {
										messageName = inputMessageNode
												.getNodeValue();
										//当message包含SoapIn时,便直接获得而不考虑其他情况
										if(messageName.contains("SoapIn") || messageName.contains("SoapOut")){
											isFound = true;
										}
										//System.out.println(m+messageName);
									}
								}
							}
						}
						//System.out.println(tmpNode.getNodeValue());
					}					
				}				
			}
			//operation有误，则返回空
			if(messageName.equals("")){
				return null;
			}
			messageName = messageName.substring(messageName.indexOf(':')+1);
			//System.out.println("messageName:"+messageName);
			//wsdl4j解析
			Map map = def.getMessages();
			Collection valueColl = map.values();
			Iterator valueIte = valueColl.iterator();
			while (valueIte.hasNext()) {
				Message tmpMessage = (Message) valueIte.next();					
				//若message的name属性值不匹配，则遍历下一个message
				if (!tmpMessage.getQName().getLocalPart().equals(messageName)) {
					continue;
				}
				//*************************位置是否要变化,no
				//初始化paramInfo
				paramInfo = new ParamInfo(messageName);
				//paramInfo.setEndNode(false);
				paramInfo.setSimpleType(false);
				//paramInfo的paramList属性
				ArrayList<ParamInfo> paramInfoList = new ArrayList<ParamInfo>();
				//*************************
				
				Map map2 = tmpMessage.getParts();
				Collection coll2 = map2.values();
				Iterator ite2 = coll2.iterator();
				while (ite2.hasNext()) {
					Part part = (Part) ite2.next();
					//System.out.println("part.getName:"+part.getName());
					
					//设置第二级参数（根的下一级）
					if (part.getTypeName() != null) {// 参数为简单类型
						ParamInfo tmpParamInfo = new ParamInfo(part.getName(),part.getTypeName().getLocalPart());
						tmpParamInfo.setMinOccurs(1);
						tmpParamInfo.setMaxOccurs(1);
						//System.out.println("Simple"+"\t"+part.getName()+" \t"+part.getTypeName().getLocalPart());						
						paramInfoList.add(tmpParamInfo);
					}
					else if(part.getElementName() != null){// 参数为复杂类型
						ParamInfo tmpParamInfo = new ParamInfo(part.getName(),part.getElementName().getLocalPart());
						tmpParamInfo.setSimpleType(false);
						tmpParamInfo.setMinOccurs(1);
						tmpParamInfo.setMaxOccurs(1);
						//System.out.println("Complex"+"\t"+part.getName()+" \t"+part.getElementName().getLocalPart());
						tmpParamInfo.setParamList(getTypesByElementName(def,doc,part.getElementName().getLocalPart()));						
						paramInfoList.add(tmpParamInfo);
					}
				}
				//*************************位置是否要变化,no
				paramInfo.setParamList(paramInfoList);
				//*************************
				break;
			}		
			//System.out.println("-------------------------------------");
		} catch (WSDLException e) {
			e.printStackTrace();
		}
		return paramInfo;
	}

	/***************************************************************************
	 * 从消息中获取未确定的参数(info长度小于0xffff)
	 * 
	 * @param urlString -
	 *            网站地址
	 * @param paramType -
	 *            要解析的参数类型，PARAM_INPUT表示解析输入的参数，PARAM_OUTPUT表示解析返回的参数
	 * @param c -
	 *            要替换的字符(默认为"?")
	 * @return 需要用户填写的参数的信息
	 */
	public static ParamInfo getResult(String urlString, 
			char c, int paramType ,String operation) {
		//ArrayList<ParamInfo> list = new ArrayList<ParamInfo>();
		//String tmpString = c + "";
		//int fromIndex = -1;
		ParamInfo paramInfo = getParamType(paramType ,urlString, operation);

		if(paramType == PARAM_INPUT || paramType == PARAM_OUTPUT){
			//paramInfo;
		}
		else{
			System.exit(0);
		}
		
		return paramInfo;
	}

	/***************************************************************************
	 * 用用户填写的内容替换原未定的参数值
	 * 
	 * @param info -
	 *            原字符串(soap报文)
	 * @param values -
	 *            存放值的数组
	 * @return 替换成填写内容后的字符串
	 */
	public static String replaceValues(String info, String[] values, char c) {
		int index;
		int i = 0;

		// 按序用输入的值替换指定的未知的值
		while ((index = info.indexOf(c)) != -1 && i < values.length) {
			String tmp = info.substring(index + 1);
			info = info.substring(0, index);
			info += values[i++];
			info += tmp;
		}
		return info;
	}
	
	public static void main(String[] args){
		try{
		String operation="GetWeatherByPlaceName";
		BufferedReader br2 = new BufferedReader(
				new FileReader(new File("1.txt")));
		String content = "", tmp;
		// 获得已解析的内容(师兄已经完成)
		while ((tmp = br2.readLine()) != null) {
			content += tmp;
			content += '\n';
		}
		//System.out.println(content);
		ParamInfo info = ParameterParse.getResult(
				"WeatherForecast.wsdl", '?', ParameterParse.PARAM_OUTPUT,
				operation);
		ArrayList<ParamInfo> list = info.getParamList();
		if(list==null){
			System.out.println("null");
			return ;
		}
		System.out.println("*************************************");
		System.out.println(info.getName()+"("+info.getType()+")");
		for (int i = 0; i < list.size(); i++) {
			ParamInfo paramInfo = list.get(i);
			//System.out.println(paramInfo.getName()+" \t"+list.get(i).getType());
			//System.out.println(paramInfo.isSimpleType());
			if(!paramInfo.isSimpleType()){
				ArrayList<ParamInfo> typeList = paramInfo.getParamList();
				System.out.println("|-"+paramInfo.getName()+"("+paramInfo.getType()+")"+paramInfo.isOptional()+","+paramInfo.getMaxOccurs());				
				for(int j = 0; j < typeList.size(); j++){
					ParamInfo tmpParamInfo = typeList.get(j);
					System.out.println(" |-"+typeList.get(j).getName()+"("+typeList.get(j).getType()+")"+typeList.get(j).isOptional()+","+typeList.get(j).getMaxOccurs());
					if(!tmpParamInfo.isSimpleType()){
						ArrayList<ParamInfo> typeList2 = tmpParamInfo.getParamList();
						for(int k = 0; k < typeList2.size(); k++){
							ParamInfo tmpParamInfo2 = typeList2.get(k);
							System.out.println("   |-"+tmpParamInfo2.getName()+"("+tmpParamInfo2.getType()+")"+tmpParamInfo2.isOptional()+","+tmpParamInfo2.getMaxOccurs());
							if(!tmpParamInfo2.isSimpleType()){
								ArrayList<ParamInfo> typeList3 = tmpParamInfo2.getParamList();
								for(int m = 0; m < typeList3.size(); m++){									
									ParamInfo tmpParamInfo3 = typeList3.get(m);
									System.out.println("    |-"+tmpParamInfo3.getName()+"("+tmpParamInfo3.getType()+")"+tmpParamInfo3.isOptional()+","+tmpParamInfo3.getMaxOccurs());
									if(!tmpParamInfo3.isSimpleType()){
										ArrayList<ParamInfo> typeList4 = tmpParamInfo3.getParamList();
										for(int n = 0; n < typeList4.size(); n++){									
											ParamInfo tmpParamInfo4 = typeList4.get(n);
											System.out.println("     |-"+tmpParamInfo4.getName()+"("+tmpParamInfo4.getType()+")"+tmpParamInfo4.isOptional()+","+tmpParamInfo4.getMaxOccurs());
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
		}
		}
		catch(IOException e){
			
		}
	}
}