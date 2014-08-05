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
    
    <title>About Us</title>

	<!-- css样式 -->
	<link rel="stylesheet" type="text/css" href="css/logo.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/menu.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="user/css/user.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="user/css/login.css" media="screen" />
	
	
	<!-- 菜单js函数 -->
	<script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="js/menu.js" type="text/javascript"></script>
	
	<meta http-equiv="keywords" content="service,introduction">
	<meta http-equiv="description" content="introduction of serviceXchange">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
		
  </head>
  
  <body>
  
<!-- Logo图片 -->
<div class="logo"></div>

<!-- 顶部菜单 -->
<div id="menu">
	<ul class="nav current-user">
		<%@ include file="../common/menu.html" %>
	</ul>
</div>

<div class="outer_about">
			<h3>
				About ServiceXchange
			</h3>
			<p>
				ServiceXchange is a free web service discovery platform designed by
				the
				<!-- <a href="#aboutus"> -->

				Institute of Advanced Computing Technology
				<!-- </a>--> at Beihang University, Beijing, China. To make the search more
				effective and efficient, ServiceXchange employs the following
				technologies:
			</p>
			<ul>
				<li>
					Vertical search engine for web service discovery
				</li>
				<li>
					Web service QoS monitoring
				</li>

				<li>
					Web service clustering
				</li>
				<li>
					Web service tagging
				</li>
				<li>
					Web service online trial
				</li>

			</ul>


		</div>

		<div class="outer_about">
			<h3 name="aboutus">
				About ServiceXchange Team
			</h3>
			<p>
				ServiceXchange is maintained by members of the Institute of Advanced
				Computing Technology at Beihang University, Beijing, China. We study
				a number of research areas:
			</p>

			<ul>
				<li>
					Service selection and recommendation
				</li>
				<li>
					Service trustworthiness assessment
				</li>
				<li>
					Business similarity measuring
				</li>

				<li>
					Service mining
				</li>
			</ul>
		</div>
		<div class="outer_about">
			<h3>
				Publications
			</h3>
			<ul>

				<li>
					--2009--
					<div>
						<ul>

							<li style="margin-top:5px">
								<a href="publications/BestRec.pdf">Zicheng Huang, Jinpeng Huai, Hailong Sun, Xudong Liu, Xiang Li
								"BestRec: A Behavior Similarity based Approach to Services
								Recommendation" IEEE 2009 Congress on Services</a>
							</li>
							
							<li style="margin-top:5px">
								<a href="publications/WSSM.pdf">Xianyang Qu, Hailong Sun，Xiang Li，Xudong Liu and Wei Lin "WSSM：A
								WordNet-Based Web Service Similarity Mining Mechanism" IARIA
								International Conference on Service Computation 2009</a>
							</li>

						</ul>
					</div>
				</li>
			</ul>
		</div>
		<div class="outer_about">
			<h3>
				Acknowledgments
			</h3>

			<p>
				This work is funded by the National High Technology Research and
				Development Program of China (863 program) under grant 2007AA010301,
				2006AA01A106 and 2009AA01Z419.

			</p>
		</div>


<div class="bottom">The Institute of Advanced Computing Technology, Beijing, China. Email: <a href="mailto:servicexchange@act.buaa.edu.cn">servicexchange@act.buaa.edu.cn</a></div>

  </body>
</html>
