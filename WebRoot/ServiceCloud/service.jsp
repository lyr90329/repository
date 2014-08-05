<%@ page language="java" import="java.util.*,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.Data,cn.org.act.sdp.repository.entity.ServiceTBean,com.eviware.soapui.impl.wsdl.*" pageEncoding="GB18030"%>
<%@ page import="parser.*"%>
<jsp:directive.page import="cn.org.act.sdp.wsdl.WebServiceRunner;"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String serviceid=(String)request.getParameter("serviceid");
String soaptype=(String)request.getParameter("soaptype"); 
String operation=(String)request.getParameter("operation"); 
String wsdlurl=(String)request.getParameter("wsdlurl"); 
String servicename=(String)request.getParameter("servicename");
ServiceTBean stbean=Data.getInstance().getwebservicebyId(Long.parseLong(serviceid));
WsdlInterface wsdliface=null;
WsdlOperation wsdlop=null;
WebServiceRunner wsRunner=null; 
String soapContent = "";
	
	try
	{
		//WSDL初始化，wsRunner会加载、解析WSDL文件
		wsRunner = new WebServiceRunner(wsdlurl);
		System.out.println(wsdlurl);
		//获取所有Binding的协议
		WsdlInterface[] results = wsRunner.getWSDLInterfaces();
	
		//遍历所有的协议
		WsdlInterface myiface = null;	
		boolean isfirst=true;
		for( WsdlInterface iface : results )
		{
	    	String binding=iface.getBindingName().getLocalPart();
	if(binding!=null)
	{
		if(isfirst&&(soaptype==null||soaptype.equals("")))
		{
			soaptype=binding;
			wsdliface=iface;
		}
		else if(binding.equals(soaptype))
		{
			wsdliface=iface;
		}
		isfirst=false;
	}
		}
	}
	catch(Exception e)
	{
		out.print("ParseException: Please Try again!1");
		return ;
	}
		
	try
	{
		boolean opfirst=true;
		List oplist=wsdliface.getOperationList();
		for(int i=0;i<oplist.size();i++)
		{
		    WsdlOperation iop=(WsdlOperation)oplist.get(i);
		    String opname=iop.getName();
	
	if(opname!=null)
	{
	    		if(opfirst&&(operation==null||operation.equals("")))
	    		{
	    			operation=opname;
	    			wsdlop=iop;
	    		}
	    		else if(opname.equals(operation))
	    		{
	    			wsdlop=iop;
	    		}			    
		opfirst=false;
	}			
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	soapContent = wsRunner.getRequestFormat(wsdlop);

	ArrayList<ParamInfo> list = ParameterParse.getResult(wsdlurl,
		'?', 0, operation).getParamList();
	ArrayList<ParamInfo> paramInfoList = new ArrayList<ParamInfo>();

	for(int i=0;list!=null && i<list.size();i++){
		ParamInfo paramInfo = list.get(i);
		if(!paramInfo.isSimpleType()){
	ArrayList<ParamInfo> typeList = paramInfo.getParamList();
	for(int j = 0; j < typeList.size(); j++){
		paramInfoList.add(typeList.get(j));		
	}
		}
		else{
	paramInfoList.add(paramInfo);
		}
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Web Service:<%=servicename %></title>
    
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

	function invoke(serviceid,soaptype,operation,wsdlurl,servicename)
	{
		var go= document.getElementById("goid");
		go.disabled=true;
		var content=document.getElementById("insoap").value;
	    AjaxSubmit("serviceid="+serviceid+"&soaptype="+soaptype+"&wsdlurl="+wsdlurl+"&servicename="+servicename+"&operation="+operation+"&soapContent=" +content,"/repository/ServiceCloud/invokeservice2.jsp");
	}


	function setOpName(serviceid,soaptype,operation,wsdlurl,servicename)
	{ 
	    window.location="/repository/ServiceCloud/service.jsp?"+"serviceid="+serviceid+"&wsdlurl="+wsdlurl+"&servicename="+servicename+"&soaptype="+soaptype+"&operation="+operation;
	}
	
	function setSoap(serviceid,soaptype,operation,wsdlurl,servicename)
	{
		var f = document.getElementById("bindingid");
		var selIdx = f.selectedIndex;
	   	var newSel = f.options[selIdx].text;
	
	    window.location="/repository/ServiceCloud/service.jsp?"+"serviceid="+serviceid+"&wsdlurl="+wsdlurl+"&servicename="+servicename+"&soaptype="+newSel;
	}
	
	
	function setShowType(serviceid,soaptype,operation,wsdlurl,servicename)
{
     var f = document.getElementById("showid");
	var selIdx = f.selectedIndex;
   var newSel = f.options[selIdx].text;
	if("Soap"==newSel)
	{
		window.location="/repository/ServiceCloud/service.jsp?"+"serviceid="+serviceid+"&wsdlurl="+wsdlurl+"&servicename="+servicename+"&soaptype="+soaptype+"&operation="+operation+"&showtype="+newSel;
	}
	else
	{
		window.location="/repository/ServiceCloud/serviceform.jsp?"+"serviceid="+serviceid+"&wsdlurl="+wsdlurl+"&servicename="+servicename+"&soaptype="+soaptype+"&operation="+operation+"&showtype="+newSel;
	}
		

}
	
	
	
	function AddResponse(msg)
    {
       var f = document.getElementById("tree");
	    f.innerHTML=msg; 
	    var go= document.getElementById("goid");  
	    go.disabled=false;
	} 
	
	function replace()
	{
		var objLenth = <%=paramInfoList.size() %>;
		var arr = new Array(objLenth);
		var xmlhttp;
		
	    try{
	    	xmlhttp = new XMLHttpRequest();
	    }
		catch(e)
		{
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		//获得输入框中的对应的值
		for(i=0;i<objLenth;i++)    
		{    
			arr[i] = document.getElementById("value"+i).value;
		}
		var b = arr.join(",");
		var url = "/repository/ServiceCloud/ReplaceSoapParam.jsp"; 
		var para = "soapContent=" + <%=soapContent %> + "&paramList="+b;
		
	    xmlhttp.onreadystatechange = function()
	   	{
		   	if(xmlhttp.readyState == 4) 
		   	{
		   		if(xmlhttp.status == 200)
		   		{
		   			var message = xmlhttp.responseText;
		     		var soapText = document.getElementById("soap");
		  			soapText.value = message; 
		  			alert("OK");
		   		}
		   		else{
		   			alert(xmlhttp.status);
		   		}
		  	}
	   	}
	   	
	    xmlhttp.open("post",url,true);
	    xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');         
	    xmlhttp.send(para);
	}	
	
	function showDiv(op){
		if(document.getElementById(op).style.display=='none'){
			document.getElementById(op).style.display='block';
		}
		else
		{
			document.getElementById(op).style.display="none";
		}
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
<td width="600px"><font size="5">Web Service:<%=servicename %></font></td>
<td width="200px"><table width="200px"><tr height="5px"><td></td></tr><tr align="center" height="35px"><td>
<% 
      try
		{
		//获取所有Binding的协议
		WsdlInterface[] results = wsRunner.getWSDLInterfaces();
		
		//遍历所有的协议
		WsdlInterface myiface = null;	
		boolean isfirst=true;
		out.print("<select id=\"bindingid\" name=\"combobox\" onChange=\"javascript:setSoap('"+serviceid+"','"+soaptype+"','"+operation+"','"+wsdlurl+"','"+servicename+"')\">");	
		for( WsdlInterface iface : results )
		{
		    String binding=iface.getBindingName().getLocalPart();
			System.out.println(binding);
			
			if(binding!=null)
			{
			    if(isfirst&&(soaptype==null||soaptype.equals("")))
			    {
				    out.print("<option value=\""+binding+"\" selected>"+binding+"</option>");
				    soaptype=binding;
				    wsdliface=iface;
			    }
			    else if(binding.equals(soaptype))
			    {
				    out.print("<option value=\""+binding+"\" selected>"+binding+"</option>");
				    wsdliface=iface;
			    }
			    else
			    {
					out.print("<option value=\""+binding+"\">"+binding+"</option>");
				}
				isfirst=false;				
			}
		}
		
		out.print("</select>");	
		}
		catch(Exception e)
		{			
			out.print("ParseException: Please Try again!2");
			return ;
		}
%>


</td></tr></table></td></tr>
</table>
<table align="center" width="800px"><tr><td width="175px" valign="top">
<table width="170px" align="right">
<%
	try
	{
		boolean opfirst=true;
		List oplist=wsdliface.getOperationList();
		for(int i=0;i<oplist.size();i++)
		{
		    WsdlOperation iop=(WsdlOperation)oplist.get(i);
		    String opname=iop.getName();
			System.out.println(opname);
			
			if(opname!=null)
			{
			    if(opfirst&&(operation==null||operation.equals("")))
			    {
			    	out.print("<tr><td><div style=\"background-color:#5162f4;;border-width:1px;border-style:solid;border-right-width:0px;border-color:#3142e4;\"><a href=\"javascript:setOpName('"+serviceid+"','"+soaptype+"','"+opname+"','"+wsdlurl+"','"+servicename+"')\">"+opname+"</a></div></td></tr>");
			    	operation=opname;
			    	wsdlop=iop;
			    }
			    else if(opname.equals(operation))
			    {
			    	out.print("<tr><td><div style=\"background-color:#5162f4;;border-width:1px;border-style:solid;border-right-width:0px;border-color:#3142e4;\"><a href=\"javascript:setOpName('"+serviceid+"','"+soaptype+"','"+opname+"','"+wsdlurl+"','"+servicename+"')\">"+opname+"</a></div></td></tr>");
			    	wsdlop=iop;
			    }
			    else
			    {
				 	out.print("<tr><td><div style=\"background-color:#EEE;;border-width:1px;border-style:solid;border-right-width:0px;border-color:#CCC;\"><a href=\"javascript:setOpName('"+serviceid+"','"+soaptype+"','"+opname+"','"+wsdlurl+"','"+servicename+"')\">"+opname+"</a></div></td></tr>");
				}
					opfirst=false;
			}
			
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
 %>
</table>

</td>
<td valign="top"><br></td>
<td width="625px" style="background-color:#EEE;;border-width:1px;border-style:solid;border-right-width:0px;border-color:#CCC;">
	<table width="625px">
		<tr><td colspan="2">Request:</td></tr>
		
			<!-- ******************************** -->
			
				<tr><td colspan="2"><textarea id="insoap" name="textarea2" style="width:600px;height:150px"><%
					if(wsdlop!=null)
					{
					  out.print(soapContent);
					}
					%>
						</textarea>
					</td>
				</tr>
			
			<!-- ******************************** -->
			
				<tr><td><input type="Button" id="goid" name="button1" value="Go" onclick="invoke('<%=serviceid%>','<%=soaptype%>','<%=operation%>','<%=wsdlurl%>','<%=servicename%>');"/></td>
					<td align="right">
						<table width="100px" height="10px">
							<tr align="center" height="10px">
								<td>
								<select id="showid" name="combobox" onChange="javascript:setShowType('<%=serviceid%>','<%=soaptype%>','<%=operation%>','<%=wsdlurl%>','<%=servicename%>')">
									<option value="Soap" selected>Soap</option>
									<option value="Form"` >Form</option>
								</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td colspan="2">Response:</td></tr>
					<% 
						ParamInfo[] paramInfo = new ParamInfo[paramInfoList.size()];
						Object[] objects = paramInfoList.toArray();
						for(int i=0;i<objects.length;i++){
							paramInfo[i] = (ParamInfo)objects[i];
						}
					%>
			
	
				<tr><td colspan="2"><div id="tree" name="tree"></div></td></tr>


</table> 
  </body>
</html>
