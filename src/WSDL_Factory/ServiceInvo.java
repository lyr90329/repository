package WSDL_Factory;


import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;   
import org.apache.axiom.om.OMElement;   
import org.apache.axiom.om.OMFactory;   
import org.apache.axiom.om.OMNamespace;   
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.impl.llom.factory.OMXMLBuilderFactory;
import org.apache.axis2.AxisFault;   
import org.apache.axis2.addressing.EndpointReference;   
import org.apache.axis2.client.Options;   
import org.apache.axis2.client.ServiceClient;  
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.util.StreamWrapper;

import WSDL_Structure.WSDL_Operations;
import WSDL_Structure.WSDL_ParameterInfo;

public class ServiceInvo {
	public String returnnum;
	public String returnN;
	private List list1 = new ArrayList();
	 public List invokeWebService(String SoapBingdingA,WSDL_Operations Operation,String serviceNS,ArrayList list,List output)   
	    {   
	        try  
	        {   
	            String soapBindingAddress = SoapBingdingA;   
	               
	            EndpointReference endpointReference = new EndpointReference(soapBindingAddress);   
	               
	            //创建一个OMFactory，下面的namespace、方法与参数均需由它创建   
	            OMFactory factory = OMAbstractFactory.getOMFactory();   
	               
	            //下面创建命名空间，如果你的WebService指定了targetNamespace属性的话，就要用这个   
	            //对应于@WebService(targetNamespace = "http://www.mycompany.com")   
	            OMNamespace namespace = factory.createOMNamespace(serviceNS, "xsd");   
	  
	           //下面创建一个method对象，"test"为方法名 
	            String op = Operation.getName();
	            OMElement method = factory.createOMElement(op, namespace);   
	            
	            //下面创建的是参数对数，对应于@WebParam(name = "name")   
	            //由于@WebParam没有指定targetNamespace，所以下面创建name参数时，用了null，否则你得赋一个namespace给它   
	           Iterator inputv = list.iterator();
				while (inputv.hasNext()) {
					WSDL_ParameterInfo p = new WSDL_ParameterInfo();

					p = (WSDL_ParameterInfo)inputv.next();
					String name = p.getName();
					String type = p.getType();
					if(type.equals("int")||type.equals("float")||type.equals("double")||type.equals("long")||type.equals("string")
							||type.equals("anyType")||type.equals("base64Binary")||type.equals("dateTime")||type.equals("boolean")){

						WSDL_ParameterInfo paraN = new WSDL_ParameterInfo(name);
						WSDL_ParameterInfo paraV = new WSDL_ParameterInfo(type+"....."+p.getValue());
						paraN.addChildren(paraV);
						//listO.add(paraN);
						OMElement nameElement = factory.createOMElement(p.getName(), null);   
			            nameElement.addChild(factory.createOMText(nameElement, p.getValue()));
			            method.addChild(nameElement); 
					}
					else{
 
						OMElement nameElement = factory.createOMElement(p.getName(), null);   
						OMElement  p0= this.DrawTree(p,factory,nameElement);

						//nameElement.addChild(p0); 
						method.addChild(p0);

					}
		 
				}
				
				
	            Options options = new Options(); 
	            options.setAction(Operation.getSoapActionURI());  //此处对应于@WebMethod(action = "http://www.mycompany.com/test")   
	            options.setTo(endpointReference);   
	  
	            ServiceClient sender = new ServiceClient();   
	            sender.setOptions(options);   
	  
	            //下面的输出结果为<xsd:test xmlns:xsd="http://www.mycompany.com"><name>java</name></xsd:test>   
	            System.out.println(method.toString());   
	  
	            //发送并得到结果，至此，调用成功，并得到了结果   
	            OMElement result = sender.sendReceive(method);   
	  
	            //下面的输出结果为<ns2:testResponse xmlns:ns2="http://www.mycompany.com"><greeting>hello java</greeting></ns2:testResponse>   
	            System.out.println(result.toString()); 
	            Iterator cre1 = result.getChildElements();
	           if(cre1.hasNext()){
	            
	            list1 = ReturnR(result,output);
	            System.out.println(list1.toString());
	            
	           }
	           else{
	        	   Iterator it = output.iterator();
	        	   WSDL_ParameterInfo p0 = new WSDL_ParameterInfo();
	        	   if(it.hasNext()){
	        		   p0 = (WSDL_ParameterInfo)it.next();
	        	   }
	        	   System.out.println(result.getText());
	        	   WSDL_ParameterInfo p = new WSDL_ParameterInfo();
	        	   p.setValue(result.getText());
	        	   p.setName(result.getLocalName());
	        	   p.setType(p0.getType());
					list1.add(p);
	           }

	            	
	        }   
	      catch (AxisFault ex)   
	        {   
	            ex.printStackTrace();   
	        }
	      return list1;
	    }
	 private OMElement DrawTree(WSDL_ParameterInfo p,OMFactory factory,OMElement nameElement){
			
			String name = p.getName();
			String type = p.getType();
			List list0 = p.getList();
			Iterator in = list0.iterator();
			
			while(in.hasNext()){
				WSDL_ParameterInfo  input0 = new WSDL_ParameterInfo();
				input0 = (WSDL_ParameterInfo)in.next();

				String name0 = input0.getName();
				String type0 = input0.getType();
				String value0 = input0.getValue();
				System.out.println("######"+name0+type0);
				System.out.println(name0);
				if(type0.equals("int")||type0.equals("float")||type0.equals("double")||type0.equals("long")||type0.equals("string")
						||type0.equals("decimal")||type0.equals("anyType")||type0.equals("dateTime")||type0.equals("base64Binary")||type0.equals("boolean")||type0.equals("token")){
				
					OMElement nameElement1= factory.createOMElement(name0, null);   
		            nameElement1.addChild(factory.createOMText(nameElement, value0));
		            nameElement.addChild(nameElement1);
				}
				else{
					OMElement nameElement1= factory.createOMElement(name0, null); 
					nameElement.addChild(nameElement1);
					this.DrawTree(input0,factory,nameElement1);
				}
			}
			return nameElement;
		}
	 
	 public List ReturnR(OMElement result,List output)
	 {
		 List returnlist = new ArrayList();
		 Iterator cre1 = result.getChildElements();
         while(cre1.hasNext()){
         	 OMElement cre2 ;
         	 cre2 =  (OMElement)cre1.next();
         	 returnnum = cre2.getText();
         	 returnN = cre2.getLocalName();
         	 WSDL_ParameterInfo pp = new WSDL_ParameterInfo();
         	 pp.setName(returnN);
         	 pp.setValue(returnnum);
         	 boolean bo = false;
         	 bo = FindP(output,bo,pp);
         	 System.out.println(cre2.getLocalName()+":"+cre2.getText());
         	Iterator cre3 = cre2.getChildElements();
         	if(cre3.hasNext()){
         		 System.out.println("********");
         		 ReturnR(cre2,output);
         	}
         	
         }
         return output;
	 }
	 
		public boolean FindP(List output,boolean exist,WSDL_ParameterInfo input){
		    System.out.println("*****"+input.getName());
	
			Iterator in = output.iterator();
			while(in.hasNext()){
				WSDL_ParameterInfo p2 = new WSDL_ParameterInfo();
				
				p2 = (WSDL_ParameterInfo)in.next();
				System.out.println(">>>>"+p2.getName());
				if(p2.getName().equals(input.getName())){
					p2.setValue(input.getValue());
					exist = true;
					break;
				}
				else{
					List it = p2.getList();
					Iterator iter = it.iterator();
					if(iter.hasNext()){
						exist = FindP(it,exist,input);
					}
				}
			}
			return exist;
	}
}
