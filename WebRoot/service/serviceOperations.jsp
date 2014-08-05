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
    
    <title>Service Detials</title>
    <s:head theme="ajax"/>
	
	<%@ include file="../common/imports.html" %>
	
	<link rel="stylesheet" type="text/css" href="service/css/service_detials.css" media="screen" />
	
	<!-- User Feedback -->
	<script type="text/javascript" src="common/swfobject.js"></script>
	<script type="text/javascript">
		swfobject.embedSWF("service/open-flash-chart.swf", "feedback_chart", "500", "250",
			"9.0.0", "expressInstall.swf", {
				"data-file" : "FeedbackRaderJSONServelet?serviceId=<s:property value="serviceId" />"
			});
	</script>
	
  </head>
 
<script type="text/javascript">
function submitOperation(){
	var form = document.getElementById('operationSelect');
	form.submit();
}
</script>

  <!--Script and CSS includes for YUI dependencies on this page-->
<link rel="stylesheet" type="text/css" href="service/yui/build/slider/assets/skins/sam/slider.css" />
<link rel="stylesheet" type="text/css" href="service/yui/build/button/assets/skins/sam/button.css" />
<script type="text/javascript" src="service/yui/build/yuiloader/yuiloader-min.js"></script>
<script type="text/javascript" src="service/yui/build/dom/dom-min.js"></script>
<script type="text/javascript" src="service/yui/build/event/event-min.js"></script>
<script type="text/javascript" src="service/yui/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="service/yui/build/slider/slider-min.js"></script>
<script type="text/javascript" src="service/yui/build/element/element-min.js"></script>
<script type="text/javascript" src="service/yui/build/button/button-min.js"></script>

<!--begin custom header content for this example-->
<style type="text/css">
#slider-bg {
    background:url(service/yui/examples/slider/assets/bg-h.gif) 5px 0 no-repeat;
}
</style>
<!--end custom header content for this example-->
<script type="text/javascript">
//enable passthrough of errors from YUI Event:
if ((typeof YAHOO !== "undefined") && (YAHOO.util) && (YAHOO.util.Event)) {
	YAHOO.util.Event.throwErrors = true;
}
</script>
<script type="text/javascript">
(function() {
    var Event = YAHOO.util.Event,
        Dom   = YAHOO.util.Dom,
        lang  = YAHOO.lang,
        slider, 
        bg="slider-bg", thumb="slider-thumb", 
        leftvalue="start-value",rightvalue="end-value"
        
        
	//获取服务器端数据	
	var regDate = '${regDate}';
	var nowDate = '${nowDate}';
	var width = ${width};
	
	//解析传输过来的字符串日期
	regDateSplit = regDate.split('-');
	nowDateSplit = nowDate.split('-');
	
	regDateJS = new Date();
	nowDateJS = new Date();
	
	//根据传输过来的日期创建JS Date类型
	regDateJS.setYear(regDateSplit[0]);
	regDateJS.setMonth(regDateSplit[1]-1);
	regDateJS.setDate(regDateSplit[2]);
	
	nowDateJS.setYear(nowDateSplit[0]);
	nowDateJS.setMonth(nowDateSplit[1]-1);
	nowDateJS.setDate(nowDateSplit[2]);
	
	//当前时间和注册时间的间隔的天数
	var spaceDate = parseInt((nowDateJS.getTime()-regDateJS.getTime())/1000/60/60/24);
    
    // The slider can move 0 pixels up
    var topConstraint = 0;

    // The slider can move 200 pixels down
    var bottomConstraint = 200
    
    // Custom scale factor for converting the pixel offset into a real value
    var scaleFactor = 2;

    // The amount the slider moves when the value is changed with the arrow keys
    var keyIncrement = 20;
    
    var operationData;
   
    Event.onDOMReady(function() {

        slider = YAHOO.widget.Slider.getHorizSlider(bg, 
                         thumb, topConstraint, bottomConstraint);

        // Sliders with ticks can be animated without YAHOO.util.Anim
        slider.animate = true;

        slider.getRealValue = function() {
            return Math.round(this.getValue() * length / 200);
        }

        slider.subscribe("change", function(offsetFromStart) {     
        	//获取页面节点
			var leftNode = Dom.get(leftvalue);
    		var rightNode = Dom.get(rightvalue);

        	//计算滚动条左右的时间
            leftDate = new Date(regDateJS-0 + (offsetFromStart*(200-width)/200)/200*spaceDate*86400000);
            rightDate = new Date(regDateJS-0 + ((offsetFromStart*(200-width)/200)+width)/200*spaceDate*86400000);       
            
            // Display the pixel value of the control
            leftNode.innerHTML = leftDate.getDate()+"."+(leftDate.getMonth()+1)+"."+leftDate.getFullYear();
            rightNode.innerHTML = rightDate.getDate()+"."+(rightDate.getMonth()+1)+"."+rightDate.getFullYear();
           
           //得出服务器请求参数
			var leftDateTrance = "_"+leftDate.getFullYear()+"-"+(leftDate.getMonth()+1)+"-"+leftDate.getDate();
			var rightDateTrance = "_"+rightDate.getFullYear()+"-"+(rightDate.getMonth()+1)+"-"+rightDate.getDate();
			operationData = "ResponseTimeJSONServelet?operationData=<s:property value="operationId" />" + leftDateTrance + rightDateTrance;
        });

        slider.subscribe("slideStart", function() {
                //YAHOO.log("slideStart fired", "warn");
            });

        slider.subscribe("slideEnd", function() {
				swfobject.embedSWF("service/open-flash-chart.swf", "availability_chart", "500", "300",
				"9.0.0", "expressInstall.swf", {
					"data-file" : operationData
				});
            });
    });
})();
</script>

  <body id="yahoo-com" class=" yui-skin-sam">

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
				<div class="outer_title"><strong>Web Service Details: <s:property value="serviceBean.name"/></strong></div>
				<!-- 服务菜单 -->
				<div class="inner_menu">
					<span class="menu_botton"><a href="service/ServiceOverView.action?serviceId=<s:property value="serviceId"/>">Overview</a></span>
					<span id="current" class="menu_botton"><a href="service/ServiceOperations.action?serviceId=<s:property value="serviceId" />">Details</a></span>
					<span class="menu_botton"><a href="service/ServiceGraph.action?serviceId=<s:property value="serviceId" />">Connectability</a></span>
					<span class="menu_botton"><a href="service/GetServiceInput.action?serviceId=<s:property value="serviceId" />">Try it</a></span>
					<span class="menu_botton"><a href="service/GetQos.action?serviceId=<s:property value="serviceId" />">Preference QoS</a></span>
					<span class="menu_botton_last"><a href="comments/GetComments.action?serviceId=<s:property value="serviceId" />">Comments</a></span>
				</div>
				<div class="inner_div">
					<div class="inner_title"><strong>User Feedback</strong></div>
					<div id="feedback_chart"></div>
					<div class="inner_title">
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td><strong>Service Availability</strong></td>
								<td align="right">
									<form action="service/ServiceOperations.action" id="operationSelect">
							 			<input type="hidden" name="serviceId" value="<s:property value="serviceId" />" />
								 		<select name="operationId" onchange="submitOperation();">
								 			<s:iterator value="operations" status="st">
								 				<option value="<s:property value="id" />" <s:if test="operationId == id"> selected</s:if> ><s:property value="name" /></option>
								 			</s:iterator>
							 			</select>
							 		</form>
							 	</td>
						 	</tr>
					 	</table>
			 		</div>
					<div id="availability_chart"></div>
					<div id="slider-bg" class="yui-h-slider" tabindex="-1" title="Slider" style="margin:0 auto;">
			    		<div align="center" id="slider-thumb" class="yui-slider-thumb"><img src="service/yui/examples/slider/assets/thumb-n.gif"></div>
					</div>
					<div align="center">time period selected: <span id="start-value"></span>-<span id="end-value"></span></div>
					
					<script type="text/javascript">
								    
						//获取服务器端数据	
						var regDate = '${regDate}';
						var nowDate = '${nowDate}';
						var width = ${width};
						
						//解析传输过来的字符串日期
						regDateSplit = regDate.split('-');
						nowDateSplit = nowDate.split('-');
						
						regDateJS = new Date();
						nowDateJS = new Date();
						
						//根据传输过来的日期创建JS Date类型
						regDateJS.setYear(regDateSplit[0]);
						regDateJS.setMonth(regDateSplit[1]-1);
						regDateJS.setDate(regDateSplit[2]);
						
						nowDateJS.setYear(nowDateSplit[0]);
						nowDateJS.setMonth(nowDateSplit[1]-1);
						nowDateJS.setDate(nowDateSplit[2]);
						
						//当前时间和注册时间的间隔的天数
						var spaceDate = parseInt((nowDateJS.getTime()-regDateJS.getTime())/1000/60/60/24);
					    
					    //计算滚动条的左右时间（初始的）
					    var leftDate = regDateJS;
					    var rightDate = new Date(regDateJS-0 + width/200*spaceDate*86400000);
			    
			    		//设置页面显示内容
					    document.getElementById("start-value").innerHTML = leftDate.getDate()+"."+(leftDate.getMonth()+1)+"."+leftDate.getFullYear();
					    document.getElementById("end-value").innerHTML = rightDate.getDate()+"."+(rightDate.getMonth()+1)+"."+rightDate.getFullYear();
					    
					    //得出服务器请求参数
					    var leftDateTrance = "_"+leftDate.getFullYear()+"-"+(leftDate.getMonth()+1)+"-"+leftDate.getDate();
						var rightDateTrance = "_"+rightDate.getFullYear()+"-"+(rightDate.getMonth()+1)+"-"+rightDate.getDate();
						operationData = "ResponseTimeJSONServelet?operationData=<s:property value="operationId" />" + leftDateTrance + rightDateTrance;
					    
					    //服务器请求
					    swfobject.embedSWF("service/open-flash-chart.swf", "availability_chart", "500", "300",
							"9.0.0", "expressInstall.swf", {
								"data-file" : operationData
							});
					</script>
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
