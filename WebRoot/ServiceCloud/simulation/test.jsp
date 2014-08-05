<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
   
    <title>My JSP 'login.jsp' starting page</title>
   
   <meta http-equiv="pragma" content="no-cache">
   <meta http-equiv="cache-control" content="no-cache">
   <meta http-equiv="expires" content="0">   
   <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
   <meta http-equiv="description" content="This is my page">
   <!--
   <link rel="stylesheet" type="text/css" href="styles.css">
   -->
 
  </head>
 
  <body>
   <form action="http://localhost:8080/SimServer/simexe" method="post">
      process:&nbsp;<input name="process" value="<bpmn:BusinessProcessDiagram xmi:version='2.0' xmlns:xmi='http://www.omg.org/XMI' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:bpmn='http://sdp.act.org.cn/bpmn'>
  <bpmn:graphicalElements id='1' xsi:type='bpmn:StartEvent' triggers=''/>
  <bpmn:graphicalElements id='4'/>
  <bpmn:graphicalElements id='2' xsi:type='bpmn:EndEvent'/>
  <bpmn:graphicalElements id='5'/>
  <bpmn:graphicalElements id='10' xsi:type='bpmn:InclusiveGateway'/>
  <bpmn:sequenceFlows id='13' sourceRef='10' targetRef='2' name='' conditionExpression=''/>
  <bpmn:sequenceFlows id='11' sourceRef='5' targetRef='10' name='' conditionExpression=''/>
  <bpmn:sequenceFlows id='12' sourceRef='4' targetRef='10' name='' conditionExpression=''/>
  <bpmn:graphicalElements id='3' xsi:type='bpmn:InclusiveGateway'/>
  <bpmn:sequenceFlows id='6' sourceRef='1' targetRef='3' name='' conditionExpression=''/>
  <bpmn:sequenceFlows id='7' sourceRef='3' targetRef='4' name='' conditionExpression='16'/>
  <bpmn:sequenceFlows id='8' sourceRef='3' targetRef='5' name='' conditionExpression='17'/>
  <invisibleElement>
    <bpmn:supportingElements xsi:type='bpmn:Expression' id='16' expressionBody='11,100' name='con'/>
    <bpmn:supportingElements xsi:type='bpmn:Expression' id='17' expressionBody='50,100' name='con'/>
  </invisibleElement>
</bpmn:BusinessProcessDiagram>"/><br/>
      exeNum: &nbsp;<input name="exeNum" value="3"/><br/>
     algorithm:<input name="algorithm" value="DefaultAlgorithm"/><br/>
	 timeWeight:<input name="timeWeight" value="3"/><br/>
	 costWeight:<input name="costWeight" value="3"/><br/>
	 relWeight:<input name="relWeight" value="3"/><br/>
      <input type="submit" value="submit"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="reset"/>
   </form>
  </body>
</html>