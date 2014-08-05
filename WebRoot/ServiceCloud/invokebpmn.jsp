<%@ page language="java" import="javax.xml.parsers.*,org.w3c.dom.*,config.Config,java.util.*,db.dao.*,db.entity.Bpmn" pageEncoding="GB18030"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String bpmnid=(String)request.getParameter("bpmnid");
String userName=(String)session.getAttribute("userName");
BpmnDao dao = new BpmnDao();
Bpmn trial;
trial = dao.findByBPMNIdAndUserName(Integer.parseInt(bpmnid), userName);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>BPMN:<%=trial.getBpmnName() %></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
	
	<link rel="stylesheet" type="text/css" href="css/logo.css" media="screen" />

	
 <script type="text/javascript">
    function AjaxSubmit(para,url)
    {
       
       
       var xmlhttp;
       try{
             xmlhttp = new XMLHttpRequest();
          }
          catch(e)
          {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
          }
          
       xmlhttp.onreadystatechange=function(){
           if(4==xmlhttp.readyState){
                if(200==xmlhttp.status){
                    var res =xmlhttp.responseText;
                    AddResponse(res);
                    }
                    else
                    {
                    alert("error");
                    }
            }
         }
         
         
         xmlhttp.open("post",url,true);
         
         xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
         
         xmlhttp.send(para);               
    }

function invoke(serviceid,soaptype,operation)
{
    
    var f = document.getElementById("insoap");
	var insoap = f.value;
	
	 var go= document.getElementById("goid");
	    go.disabled=true;
    AjaxSubmit("serviceid="+serviceid+"&soaptype="+soaptype+"&operation="+operation+"&insoap="+insoap,"/repository/ServiceCloud/invokeservice.jsp");


}

function setOpName(serviceid,soaptype,operation)
{
    
    window.location="/repository/ServiceCloud/service.jsp?"+"serviceid="+serviceid+"&soaptype="+soaptype+"&operation="+operation;


}

function setSoap(serviceid,soaptype,operation)
{
     var f = document.getElementById("bindingid");
	var selIdx = f.selectedIndex;
   var newSel = f.options[selIdx].text;

    window.location="/repository/ServiceCloud/service.jsp?"+"serviceid="+serviceid+"&soaptype="+newSel;


}

function AddResponse(msg)
    {
       var f = document.getElementById("outsoap");
	    f.value=msg; 
	    var go= document.getElementById("goid");  
	    go.disabled=false;
	} 
    </script>
  </head>
  
  <body>
   <table align="center" width="800px" height="40px" style="background-color:#EEE;
	
	border-width:1px;
	
	border-style:solid;
	border-color:#CCC;
	">
<tr>
<td width="600px"><font size="5">BPMN:<%=trial.getBpmnName() %></font></td>
<td width="200px"><table width="200px"><tr height="5px"><td></td></tr><tr align="center" height="35px"><td>



</td></tr></table></td></tr>
</table>
<table align="center" width="800px"><tr><td width="350px" valign="top">
<table width="350px" align="right">
<%
  

		
		
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc=builder.parse(Config.getPath("bpmn/"+trial.getBpmnName()+".bpmn"));
		doc.normalize();
		NodeList links =doc.getElementsByTagName("supportingElements");
		int flag=0,tempFlag=0;
		String argMessage=null;
		String[] argNameTemp;
		List<String> tempArgName=new ArrayList<String>( );
		List<String> tempArgType=new ArrayList<String>( );
		for (int i=0;i<links.getLength();i++){
		    Node aNode=links.item(i);//			
			NamedNodeMap attributes= aNode.getAttributes();
			for(int a=0;a<attributes.getLength();a++){
				Node theAttribute=attributes.item(a);
//				System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
				if(theAttribute.getNodeName().equals("xsi:type")&&theAttribute.getNodeValue().equals("bpmn:InputSet"))
				{	
					
					flag=i;
					
				}
			}
		
		
		}
		
		Node aNode=links.item(flag);
		NamedNodeMap attributes= aNode.getAttributes();
		for(int a=0;a<attributes.getLength();a++){
			Node theAttribute=attributes.item(a);
			if(theAttribute.getNodeName().equals("propertyInputs"))
				argMessage=theAttribute.getNodeValue();
		}
		argNameTemp=argMessage.split(" "); 
		
		for (int j=0;j<argNameTemp.length;j++){
			for (int i=0;i<links.getLength();i++){
				Node tempNode=links.item(i);
				NamedNodeMap tempAttributes= tempNode.getAttributes();
				for(int a=0;a<tempAttributes.getLength();a++){
					Node tempTheAttribute=tempAttributes.item(a);
	//				System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
					if(tempTheAttribute.getNodeName().equals("id")&&tempTheAttribute.getNodeValue().equals(argNameTemp[j]))
					{	
						
						tempFlag=i;
						
					}					
				}
			}
			Node tempNode=links.item(tempFlag);
			NamedNodeMap tempAttributes= tempNode.getAttributes();
			for(int a=0;a<tempAttributes.getLength();a++){
				Node tempTheAttribute=tempAttributes.item(a);
				if(tempTheAttribute.getNodeName().equals("name"))
					tempArgName.add(j, tempTheAttribute.getNodeValue());
				
				if(tempTheAttribute.getNodeName().equals("type"))
					tempArgType.add(j, tempTheAttribute.getNodeValue());
					
			}
		}
		for(int i=0;i<tempArgName.size();i++){
		    out.print("<tr><td>"+tempArgName.get(i)+"</td><td>"+tempArgType.get(i)+"</td><td></td></tr>");
			
		}
		
 %>
</table>

</td>
<td width="625px" style="background-color:#EEE;;border-width:1px;border-style:solid;border-right-width:0px;border-color:#CCC;">
<table width="625px">
<tr><td>Request:</td></tr>
<tr><td><textarea id="insoap" name="textarea2" style="width:600px;height:150px"><%

 %></textarea></td></tr>
<tr><td><input type="Button" id="goid" name="button1" value="Go" onclick="invoke('');"/></td></tr>
<tr><td>Response:</td></tr>
<tr><td><textarea id="outsoap" name="textarea3" style="width:600px;height:150px"></textarea></td></tr>
</table>
</td>
</tr></table> 
  </body>
</html>
