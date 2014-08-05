<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Service Overview</title>
    <s:head theme="ajax"/>

	<%@ include file="../common/imports.html" %>
	
	<script type="text/javascript" src="service/js/service_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="service/css/service_detials.css" media="screen" />
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

function subscribe(serviceid,servicename)
{
    
    AjaxSubmit("","/repository/service/subscribe.jsp?serviceid="+serviceid+"&serviceName="+servicename);
	

}



function AddResponse(msg)
    {
       alert(msg);
	       
	   
	} 
    </script>
  </head>
  
  <body>

<!-- Logo图片 -->
<div class="logo"></div>

<!-- 顶部菜单 -->
<div id="menu">
	<ul class="nav current-services">
		<%@ include file="../common/menu.html" %>
	</ul>
</div>

<div class="corner">
	<div class="corner_top"></div>
	<div class="corner_middle">
	<table border="0" cellspacing="0" cellpadding="0"><tr><td>
		<!-- 左边 -->
		<%@ include file="../common/left.jsp" %>
		<!-- 中间 -->
		<div class="middle_div">
			<!-- 搜索部分 -->
			<%@ include file="../common/search.jsp" %>
			<!-- 正文部分 -->
			<div class="outer_div">
				<div class="outer_title"><table width="100%"><tr><td><strong>Web Service Details: <s:property value="serviceBean.name"/></strong></td><td align="right"><span><a href="javascript:subscribe(<s:property value="serviceBean.id"/>,'<s:property value="serviceBean.name"/>');" ><img border="0" src="service/img/subscribe_button.gif"></a></span></td></tr></table>
				</div>
				<!-- 服务菜单 -->
				<div class="inner_menu">
					<span id="current" class="menu_botton"><a href="service/ServiceOverView.action?serviceId=<s:property value="serviceId"/>">Overview</a></span>
					<span class="menu_botton"><a href="service/ServiceOperations.action?serviceId=<s:property value="serviceId" />">Details</a></span>
					<span class="menu_botton"><a href="service/ServiceGraph.action?serviceId=<s:property value="serviceId" />">Connectability</a></span>
					<span class="menu_botton"><a href="service/GetServiceInput.action?serviceId=<s:property value="serviceId" />">Try it</a></span>
					<span class="menu_botton"><a href="service/GetQos.action?serviceId=<s:property value="serviceId" />">Preference QoS</a></span>
					<span class="menu_botton_last"><a href="comments/GetComments.action?serviceId=<s:property value="serviceId" />">Comments</a></span>
				</div>
				<!-- 服务详细信息 -->
				<div class="inner_div">
					<div class="lines">
						<div><span class="small_title">Service Id: </span><s:property value="serviceBean.id"/></div>
						<div><span class="small_title">Name: </span><s:property value="serviceBean.name"/></div>
						<div><span class="small_title">Description: </span><s:property value="serviceBean.description"/></div>
						<div><span class="small_title">Url: </span><s:property value="serviceBean.url"/></div>
						<div><span class="small_title">Wsdl Url: </span><p><a href="<s:property value="serviceBean.wsdlUrl"/>"><s:property value="serviceBean.wsdlUrl"/></a></p></div>
					</div>
					<div class="inner_title">
						<table width="100%"><tr>
							<td><strong>Description from users</strong></td>
							<td align="right"><span class="edit_button"><input name="edit_button" type="image" src="service/img/edit_button.gif"></span></td>
						</tr></table>
					</div>
					<div class="gap_line"></div>
					<div class="additionalInfo"><s:property value="serviceBean.additionalInfo"/></div>
					<div class="additionalInfoEdit">
						<div><form>
							<input type="hidden" name="serviceId" value="<s:property value="serviceBean.id"/>"/>
							<textarea rows="3" cols="50" name="additionalInfo"><s:property value="serviceBean.additionalInfo"/></textarea>
						</form></div>
						<div>
							<input name="save_edit" type="image" src="service/img/save_button.gif">
							<input name="reset_edit" type="image" src="service/img/reset_button.gif">
							<input name="cancel_edit" type="image" src="service/img/cancel_button.gif">
						</div>
					</div>
					<div class="inner_title">
						<table width="100%"><tr>
							<td><strong>User Tags</strong></td>
							<td align="right"><span class="add_button"><input name="add_button" type="image" src="service/img/add_button.gif"></span></td>
						</tr></table>
					</div>
					<div class="gap_line"></div>
					<div class="user_tags">
						<s:iterator value="tags" status="st">
							<a href="service/ServicesByTag.action?tagName=<s:property value="name" />"><s:property value="name" /></a>&nbsp;
						</s:iterator>
					</div>
					<div class="user_tags_add">
						<span style="float:left">
							<form>
								<input type="hidden" name="serviceId" value="<s:property value="serviceBean.id"/>"/>
								<input type="text" name="tagName" />
							</form>
						</span>
						<span>
							<input name="save_add" type="image" src="service/img/save_button.gif">
							<input name="cancel_add" type="image" src="service/img/cancel_button.gif">
						</span>
					</div>
				</div>
			</div>
		</div>
		<!-- 右边 -->
		<jsp:include page="../common/right.jsp"></jsp:include>
	</td></tr></table>
	</div>
	<%@ include file="../common/footer.jsp" %>
</div>

  </body>
</html>
