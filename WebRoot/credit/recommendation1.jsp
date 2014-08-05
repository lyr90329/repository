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
    
    <title>Rate these services</title>
    <s:head theme="ajax"/>

	<!-- css样式 -->
	<link rel="stylesheet" type="text/css" href="css/logo.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/menu.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/style.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/style++.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="service/css/services.css" media="screen" />
	
	
	<!-- 菜单js函数 -->
	<script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="js/menu.js" type="text/javascript"></script>
	
	<!-- js导入 -->
	<script src="service/js/special.js" type="text/javascript"></script>
	
	<!-- ajax导入 -->
	<script src="common/news.js" type="text/javascript"></script>
	<script src="common/tags.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="common/tags.css" media="screen" />
	
	
	<!-- dtree导入 -->
	<link rel="stylesheet" type="text/css" href="dtree/dtree.css" media="screen" />
	<script src="dtree/dtree.js" type="text/javascript"></script>
	
	<style type="text/css"> 
		.starWrapper{padding:5px;width:70px;} 
		.starWrapper img{cursor:pointer;} 
	</style> 
	
	<script type="text/javascript"> 
	function rate(obj,oEvent){ 
		var imgSrc = 'credit/images/star1.gif'; 
		var imgSrc_2 = 'credit/images/star2.gif'; 
		//--------------------------------------------------------------------------- 
		var e = oEvent || window.event; 
		var target = e.target || e.srcElement;  
		var imgArray = obj.getElementsByTagName("img"); 
		for(var i=0;i<imgArray.length;i++){ 
		   imgArray[i]._num = i; 
		   imgArray[i].onclick=function(){ 
		    if(target.tagName=="IMG"){ 
			   for(var j=0;j<imgArray.length;j++){ 
			    if(j<=target._num){ 
			     imgArray[j].src=imgSrc_2; 
			    } else { 
			     imgArray[j].src=imgSrc; 
			    } 
			   } 
			} else { 
			   for(var k=0;k<imgArray.length;k++){ 
			    imgArray[k].src=imgSrc; 
			   } 
			} 
		   }; 
		} 
		
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
				<div class="outer_title"><strong>Rate these services</strong></div>
				<!-- service列表 -->
				<div class="inner_div">
					<s:iterator value="serviceBeans" id="service" status="st">
					  	<div class="lines">
					  		<table width="100%" border="0"><tr>
						  		<td align="left" width="500">
							  		<div>Service Name: <a href="service/ServiceOverView.action?serviceId=<s:property value="id" />&searchText=<s:property value="searchText" />&searchType=<s:property value="searchType" />"><s:property value="name"/></a></div>
									<div>By <a href="<s:property value="url"/>"><s:property value="url"/></a></div>
									<div>Description: <s:property value="description"/></div>
								</td>
								<td align="right">
									<div class="starWrapper" onmouseover="rate(this,event)"> 
        								<img src="credit/images/star1.gif" title="很烂" /><img src="credit/images/star1.gif" title="一般" /><img src="credit/images/star1.gif" title="还好" /><img src="credit/images/star1.gif" title="较好" /><img src="credit/images/star1.gif" title="很好" />
        							</div>
								</td>
							</tr></table>
					  	</div>
					  </s:iterator>
				</div>
				 <div>
				 	<table width="100%"><tr><td align="right">
				 		<form action="comments/Recommendation.action" method="post">
				 			<select name="preference">
				 				<option value="correctness">correctness</option>
				 				<option value="executeTime">execute time</option>
				 				<option value="respondVelocity">respond velocity</option>
				 				<option value="price">price</option>
				 				<option value="reputation">reputation</option>
				 				<option value="reliability">reliability</option>
				 				<option value="stability">stability</option>
				 				<option value="compatibility">compatibility</option>
				 			</select>
				  			<input type="submit" value="I'm done" />
				  			Go to recommendations &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  		</form>
				  	</td></tr></table>
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
