<%@ page language="java" pageEncoding="GBK" import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String name = (String)session.getAttribute("userName");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Service Cloud Platform</title>
<link href="css/firstpage_style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div id="wrapper">
	<div id="header">
		<!--<div id="logo">
			<h1><a name="pagetop">Service Cloud Platform</a></h1>
			<p> designed by  BUAA-ACT</p>
		</div>
	--></div>
	<!-- end #header -->
	<div id="menu">
		<ul>
			<li><a href="/repository">Home</a></li>
			<li><a href="/servicexchange">ServiceXchange</a></li>
			<li><a href="/repository/ServiceCloud/MyServiceContainer.jsp">ServiceFoundry</a></li>
			<li><a href="/repository/ServiceCloud/resources.jsp">Service-oriented App Engine</a></li>
			<li><a href="/repository/ServiceCloud/Toolkits.jsp">Toolkits</a></li>
			<li><a href="/repository/ServiceCloud/documentation.jsp">Documentation</a></li>
                        <li class="current_page_item"><a href="/repository/ServiceCloud/publications.jsp">Publications</a></li>
			<li><%if(name!=null) {%><a href="/repository/user/LogoutUser.action">Logout</a><%} else {%><a href="/repository/testuser/login.jsp">Sign In</a><%} %></li>
		</ul>
	</div>
	<!-- end #menu -->
	<div id="quickstart">
	<div id="quickstart-bgtop">
	<div id="quickstart-bgbtm">
		<div id="quickstart-content">
			<div class="post">
				<h3 class="title"><a href="#005">Publications</a></h3>
			</div>
			<div class="post">
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul class="pub">
                                                <h4>Journal papers</h4>                                              
                                                <h5>2014<h5>
                                                <li>Ting Deng and Wenfei Fan. <a href="download/download_doc60.jsp">On the Complexity of Query Result Diversification.</a> ACM Transactions on Database Systems (TODS), 39(2), 2014.</li>
                                                <li>Minzhi Yan, Hailong Sun, Xudong Liu, Xu Wang, Ting Deng. <a href="download/download_doc61.jsp">Delivering Web Service Load Testing as a Service with Cloud Computing Platform.</a> Concurrency and Computation: Practice and Experience, 14 MAR 2014.</li>
 
                                                <h5>2013<h5>
                                                <li>Ting Deng, Wenfei Fan, Leonid Libkin, Yinghui Wu, <a href="download/download_doc43.jsp">On the aggregation problem for synthesized Web services. </a>Journal of Computer and System Sciences Volume 79, Issue 6, September 2013, Pages 873–891</li>
                                                <li>Xiaohui Guo, Richong Zhang, Jinpeng Huai, Hailong Sun, and Xudong Liu. <a href="download/download_doc59.jsp">Discovering User Preference from Folksonomy.</a> 2013 IEEE International Conference on Systems, Man, and Cybernetics 13-16 October 2013  Manchester, United Kingdom, Pages 2114-2119.</li>                                         
                                                <h5>2012<h5>
                                                <li>Ting Deng, Jinpeng Huai, Tianyu Wo. <a href="download/download_doc44.jsp">Complexity of synthesis of composite service with correctness guarantee. </a>SCIENCE CHINA Information Sciences. March 2012 Vol. 55 No. 3: 638–649</li>
                                                <li>Hailong Sun, Jin Zeng, Huipeng Guo, Chunming Hu, Jinpeng Huai. <a href="download/download_doc45.jsp">Achieving Dependable Composite Services Through Two-level Redundancy.</a> Book chapter: Performance and Dependability in Service Computing: Concepts, Techniques and Research Directions. IGI Global. （accepted）2012. (pages 295-316)</li>
                                                <h5>2011<h5>
                                                <li>李翔, 怀进鹏, 刘旭东, 孙海龙, 曲先洋, <a href="download/download_doc46.jsp">基于消息日志的Web 服务接口业务协议挖掘.</a> 软件学报. 2011, 22(7)</li>
                                                <h5>2010<h5>
                                                <li>曾晋, 孙海龙, 刘旭东, 邓婷, 怀进鹏, <a href="download/download_doc47.jsp">基于服务组合的可信软件动态演化机制.</a> 软件学报, Vol.21, No.2, February 2010, pp.261-276</li>
                                                <li>金若凡, 孙海龙, 刘旭东, 李翔, <a href="download/download_doc48.jsp">SOArTester:一个基于精简用例的组合服务自动化测试系统.</a> 电子学报, 2010.2A:65-70</li>
                                                <h5>2009<h5>
                                                <li>Jinpeng Huai, Ting Deng, Xianxian Li, Zongxia Du, Huipeng Guo, <a href="download/download_doc49.jsp">AutoSyn: A new approach to automated synthesis of composite web services with correctness guarantee.</a> Science in China Series F: Information Sciences 52(9): 1534-1549 (2009)</li>
                                                <li>李翔, 怀进鹏, 曾晋, 高鹏,<a href="download/download_doc50.jsp">一种Java 遗留系统服务化切分和封装方法.</a> 计算机学报, Vol.9, September 2009, pp.1804-1815</li>
                                                <h5>2007<h5>
                                                <li>李扬, 怀进鹏, 郭慧鹏, 杜宗霞, <a href="download/download_doc51.jsp">一个基于服务层叠网的分层服务组合框架.</a> 软件学报, 2007(12):2967-2979</li>
                                                <div style="clear: both;">&nbsp;</div>
		                                <div style="clear: both;">&nbsp;</div>
		                                <div style="clear: both;">&nbsp;</div>
		                                <div style="clear: both;">&nbsp;</div>
		                                <div style="clear: both;">&nbsp;</div>
                                                <h4>Conference papers</h4>
                                                <h5>2014<h5>
                                                <li>Yili Fang, Hailong Sun, Richong Zhang, Jinpeng Huai and Yongyi Mao. <a href="download/download_doc62.jsp">A Model for Aggregating Contributions of Synergistic Crowdsourcing Workflows.</a> The 28th AAAI Conference on Artificial Intelligence (AAAI-14) (poster paper).</li>
                                                <li>Huan Huang, Hailong Sun, Guoqing Ma, Xu wang and Xudong Liu. <a href="download/download_doc63.jsp">A Framework for Instant Mobile Web Browsing with Smart Prefetching and Caching.</a> Proceedings of the 20th annual international conference on Mobile computing & networking (MobiCom 2014) (poster paper).</li>
                                                <li>Xu Wang, Hailong Sun, Ting Deng, Jinpeng Huai. <a href="download/download_doc64.jsp">A Quantitative Analysis of Quorum System Availability in Data Centers.</a> IWQoS 2014.</li>
                                                <li>Wancai Zhang, Hailong Sun, Xudong Liu, Xiaohui Guo. <a href="download/download_doc65.jsp">Temporal QoS-Aware Web Service Recommendation via Non-negative Tensor Factorization.</a> 23rd International World Wide Web Conference (WWW2014).</li>
                                                <li>Wancai Zhang, Hailong Sun, Xudong Liu, Xiaohui Guo. <a href="download/download_doc66.jsp">Incorporating Invocation Time in Predicting Web Service QoS via Triadic Factorization.</a> 21th IEEE International Conference on Web Services (ICWS2014).</li>
                                                <li>Chune Li, Richong Zhang, Jinpeng Huai and Hailong Sun.  <a href="download/download_doc67.jsp">A Novel Approach for API Recommendation in Mashup Development.</a> 21th IEEE International Conference on Web Services (ICWS2014).</li>
                                                <li>Hailong Sun, Tao Zhao, Yu Tang and Xudong Liu. <a href="download/download_doc68.jsp">A QoS-aware Load Balancing Policy in Multi-tenancy Environment.</a> The 8th international Symposium on service-Oriented System Engineering (SOSE 2014).</li>
                                                <li>Xinyi Liu, Hailong Sun, Hanxiong Wu, Richong Zhang and Xudong Liu. <a href="download/download_doc69.jsp">Using Sequential Pattern Mining and Interactive Recommendation to Assist Pipe-like Mashup Development.</a> The 8th international Symposium on service-Oriented System Engineering (SOSE 2014).</li>
                                                <li>Yanghao Wang, Hailong Sun, Richong Zhang. <a href="download/download_doc70.jsp">AdaMF: Adaptive Boosting Matrix Factorization for Recommender System.</a> WAIM 2014.</li>
                                                <li>Qi Wang, Hailong Sun, Yu Tang and Xudong Liu. <a href="download/download_doc71.jsp">SPKV: A Multi-dimensional Index System for Large Scale Key-Value Stores.</a> The 16th International Asia-Pacific Web Conference (APWeb 2014).</li>
                                                <li>Ji Yuan, Xudong Liu, Richong Zhang, Hailong Sun, Xiaohui Guo, Yanghao Wang.  <a href="download/download_doc72.jsp">Discovering Semantic Mobility Pattern from Check-in Data.</a> Web Information Systems Engineering (WISE 2014).</li>
                                                <h5>2013<h5>
						<li>Chune Li, Richong Zhang, Jinpeng Huai, Xiaohui Guo, Hailong Sun, <a href="download/download_doc3.jsp">A Probabilistic Approach for Web Service Discovery.</a> SCC '13 Proceedings of the 2013 IEEE International Conference on Services Computing, Washington, DC, USA. 2013</li>
						<li>Kai Wang, Richong Zhang, Xudong Liu, Xiaohui Guo, Hailong Sun, and Jinpeng Huai, <a href="download/download_doc4.jsp">Time-aware Travel Attraction Recommendation.</a> The 14th International Conference on Web Information System Engineering (WISE) , Nanjing, China. October 13-15, 2013</li>
						<li>Ting Deng, Wenfei Fan, <a href="download/download_doc5.jsp">On the Complexity of Query Result Diversification.</a> Proceedings of the VLDB Endowment, august 26 th - 30 th 2013, riva del garda , trento , Italy.2013</li>
						<li>Xu Wang, Hailong Sun, Ting Deng, Jinpeng Huai. <a href="download/download_doc6.jsp">Consistency or Latency? A Quantitative Analysis of Replication Systems Based on Replicated State Machines.</a> The 43rd Annual IEEE/IFIP International Conference on Dependable Systems and Networks (DSN), Budapest, Hungary. 24-27th June 2013</li>
                                                <li>Tao Zhao, Hailong Sun, Yu Tang, Xudong Liu. <a href="download/download_doc52.jsp">A Load Balancing Algorithm in Multi-tenancy Environment.</a> Middleware 2013 Posters and Demos Track December 9-13, 2013, Beijing,China.</li>
                                                <li>Wei Yuan, Hailong Sun, Xu Wang, Xudong Liu. <a href="download/download_doc53.jsp">Towards Efficient Deployment of Cloud Applications Through Dynamic Reverse Proxy Optimization.</a> The 15th IEEE International Conference on High Performance Computing and Communications (HPCC 2013), Zhangjiajie, China, November 13-15, 2013.</li>
                                                <li>Muhammad Ali, Hailong Sun, Wei Yuan. <a href="download/download_doc54.jsp">An Efficient Routing Scheme for Overlay Network of SOAP Proxies in Constrained Networks.</a> The 15th IEEE International Conference on High Performance Computing and Communications (HPCC 2013), Zhangjiajie, China, November 13-15, 2013.</li>
                                                <li>Zhe Zhang, Hailong Sun,Richong Zhang, Xudong Liu, Xu Peilong. <a href="download/download_doc55.jsp">A Cooperation Model Towards the Internet of Applications.</a> Internetware 2013</li>
                                                <li>Zhang Fenglin and Wang Xu. <a href="download/download_doc56.jsp">Optimizing Paxos with Request Exchangeability for Highly Available Web Services.</a> Internetware 2013 poster.</li>
                                                <li>Hailong Sun, Xu Wang, Minzhi Yan, Yu Tang, Xudong Liu. <a href="download/download_doc57.jsp">Towards a Scalable PaaS for Service Oriented Middleware.</a> Crowd and Cloud Computing Workshop In Conjunction with ICPADS 2013.</li>
                                                <li>Fei Wang, Zhe Zhang, Hailong Sun, Richong Zhang, and Xudong Liu. <a href="download/download_doc58.jsp">A Cooperation Based Metric for Mobile Applications Recommendation.</a>  WI-IAT Workshop 2013.</li>
                                                <h5>2012<h5>
						<li>Shilong Zhang, Richong Zhang, Xudong Liu, Hailong Sun, <a href="download/download_doc7.jsp">A Personalized Trust-Aware Model for Travelogue Discovering.</a> IEEE/WIC/ACM International Conferences on Web Intelligence and Intelligent Agent Technology. Macau, China, 2012</li>
						<li>Meng Zhang, Xudong Liu, Richong Zhang, Hailong Sun, <a href="download/download_doc8.jsp">A Web Service Recommendation Approach Based on QoS Prediction Using Fuzzy Clustering.</a> IEEE SCC 2012 9th International Conference on Service Computing, Hyatt Regency Waikiki Resort and Spa, Honolulu, Hawaii, USA. June 24-29 2012</li>
						<li>Jianyu Yang, Jun Han, Xu Wang, and Hailong Sun, <a href="download/download_doc9.jsp">MashStudio: An On-the-fly Environment for Rapid Mashup Development.</a> 5th International Conference, IDCS 2012, Wuyishan, Fujian, China, November 21-23, 2012</li>
						<li>Jingbo Xu, Hailong Sun, Xu Wang, Xudong Liu, Richong Zhang, <a href="download/download_doc10.jsp">Optimizing Pipe-like Mashup Execution for Improving Resource Utilization.</a> The 5th IEEE International Conference on Service-Oriented Computing and Applications (SOCA), 2012</li>
						<li>Ting Deng, Wenfei Fan, Floris Geerts, <a href="download/download_doc11.jsp">On the complexity of package recommendation problems.</a> PODS 2012: 261-272</li>
                                                <li>Xu Wang, Hailong Sun, Ting Deng, Jinpeng Huai, <a href="download/download_doc12.jsp">Rep4WS: A Paxos Based Replication Framework for Building Consistent and Reliable Web Services.</a> ICWS 2012: 295-302</li>
                                                <li>Minzhi Yan, Hailong Sun, Xu Wang, Xudong Liu, <a href="download/download_doc13.jsp">Building a TaaS Platform for Web Service Load Testing.</a> IEEE International Conference on Cluster Computing (CLUSTER), 2012</li>
                                                <li>Minzhi Yan, Hailong Sun, Xu Wang, Xudong Liu, <a href="download/download_doc14.jsp">WS-TaaS: A Testing as a Service Platform for Web Service Load Testing.</a> IEEE 18th International Conference on Parallel and Distributed Systems (ICPADS), 2012</li>
                                                <li>Zhixing Zeng, Richong Zhang, Xudong Liu, Xiaohui Guo, and Hailong Sun, <a href="download/download_doc15.jsp">Generating Tourism Path from Trajectories and Geo-Photos.</a> Web Information Systems Engineering (WISE), 2012</li>
                                                <h5>2011<h5>
                                                <li>He Huang, Xudong Liu, Hailong Sun, Dalu Guo, Xu Wang, <a href="download/download_doc16.jsp">SAPF: An On-demand and Trustworthy Middleware Provisioning Framework for A Service Cloud Platform.</a> In Proc. of the 2nd International Workshop on Software Trustworthiness(SoTrust2011) in conjunction with the 12th International Conference on Software Reuse(ICSR). 2011,pages 51-58</li>
                                                <li>Huang Liu, Xudong Liu,Jianxin Li, Yongwang Zhao, Zhuqing Li.<a href="download/download_doc17.jsp">Building High-speed Roads: Improving Performance of SOAP Processing For Cloud Services.</a> The 6th IEEE International Symposium on Service-Oriented System Engineering (SOSE), 2011</li>
                                                <li>Linlin Meng, Jianxin Li, Hailong Sun, <a href="download/download_doc18.jsp">WSRank: A Collaborative Ranking Approach for Web Service Selection.</a> CIT-DSOC 2011</li>
                                                <li>Duo Qu, Xudong Liu, Hailong Sun, Zicheng Huang. <a href="download/download_doc19.jsp">A Ranking Method for Social-Annotation-Based Service Discovery.</a> The 6th IEEE International Symposium on Service-Oriented System Engineering (SOSE), 2011</li>
                                                <h5>2010<h5>
                                                <li>Hailong Sun, Xu Wang, Chao Zhou, Zicheng Huang, Xudong Liu, <a href="download/download_doc20.jsp">Early Experience of Building a Cloud Platform for Service Oriented Software Development.</a> IEEE International Conference on Cluster Computing (Cluster 2010)</li>
                                                <li>Hailong Sun,Xudong Liu, Xiang Li, Jin Zeng, Zicheng Huang, <a href="download/download_doc21.jsp">SOARWare:A Service Oriented Software Production and Running Environment.</a> Proceedings of the 12th International Asia-Pacific Web Conference (APWeb 2010)</li>
                                                <li>Huipeng Guo，Xudong Liu, Zongxia Du, Jianxin Li, Mu Li，<a href="download/download_doc22.jsp">PASCAL: A Protocol-based Approach for Service Composition and Dependable Optimization.</a> 5th International Symposium on Service Oriented System Engineering(SOSE 2010)</li>
                                                <li>Jiangjun Zhu, Hailong Sun, Zicheng Huang, XuDong Liu, <a href="download/download_doc23.jsp">MBPR：A Business Process Repository Supporting Multi-Granularity Process Model Retrieval.</a> The Second International Conferences on Advanced Service Computing (SERVICE COMPUTATION 2010)</li>
                                                <li>Jianing Zou, Hailong Sun, Xudong Liu, Kun Fang, Jingjing Lin, <a href="download/download_doc24.jsp">A Hybrid Instance Migration Approach for Composite Service Evolution.</a> The Second International Conferences on Advanced Service Computing (SERVICE COMPUTATION 2010) </li>
                                                <li>Jianing Zou, Xudong Liu, Hailong Sun, Jin Zeng, <a href="download/download_doc25.jsp">Live Instance Migration with Data Consistency in Composite Service Evolution.</a> services, pp.653-656, 2010 6th World Congress on Services(SERVICES 2010)</li>
                                                <li>Jin Zeng, Hailong Sun, Xudong Liu, Ting Deng and Jianing Zou, <a href="download/download_doc26.jsp">PRV: An Approach to Process Model Refactoring in Evolving Process-Aware Information Systems.</a> in Proceedings of IEEE International Conference on Services Computing (SCC), pp. 441-448, 2010</li>
                                                <li>Jing Li,Yongwang Zhao,Jiawen Ren, Dianfu Ma, <a href="download/download_doc27.jsp">An Adaptive Heuristic Approach for Distributed QoS-based Service Composition.</a> 2010 IEEE International Conference on Service-Oriented Computing and Applications (SOCA2010)</li>
                                                <li>Mu Li, Ting Deng, Hailong Sun, Huipeng Guo, Xudong Liu, <a href="download/download_doc28.jsp">GOS: A Global Optimal Selection Approach for QoS-Aware Web Services Composition.</a> pp. 7-14, 2010 Fifth IEEE International Symposium on Service Oriented System Engineering(SOSE 2010)</li>
                                                <li>Weinan Zhao, Hailong Sun, Zicheng Huang, Xudong Liu, Xitong Kang, <a href="download/download_doc29.jsp">A User-oriented Approach to Assessing Web Service Trustworthiness.</a> International Conference on Autonomic and Trusted Computing (ATC 2010)</li>
                                                <li>Wenjia Huai, Xudong Liu, Hailong Sun, <a href="download/download_doc30.jsp">Towards Trustworthy Composite Service Through Business Process Model Verification.</a> 1st International Workshop on Software Trustworthiness (SoTrust 2010)</li>
                                                <li>Xi chen，Xudong Liu, Zicheng Huang, Hailong Sun, <a href="download/download_doc31.jsp">RegionKNN: A Scalable Hybrid Collaborative Filtering Algorithm for Personalized Web Service Recommendation.</a> 2010 8th IEEE International Conference on Web Services(ICWS2010)</li>
                                                <li>Xitong Kang, Xudong Liu, Hailong Sun, Yanjiu Huang and Chao zhou, <a href="download/download_doc32.jsp">Improving Performance for Decentralized Execution of Composite Web Services.</a> In 6th IEEE World Congress on Services(SERVICES 2010)</li>
                                                <li>Zicheng Huang, Jinpeng Huai, Xudong Liu, Jiangjun Zhu, <a href="download/download_doc33.jsp">Business Process Decomposition based on Service Relevance Mining.</a> WI-IAT, vol. 1, pp.573-580, 2010 IEEE/WIC/ACM International Joint Conference on Web Intelligence and Intelligent Agent Technology, 2010</li>
                                                <li>Ting Deng, Wenfei Fan, Leonid Libkin, Yinghui Wu, <a href="download/download_doc34.jsp">On the Aggregation Problem for Synthesized Web Services.</a> 13th International Conference on Database Theory (ICDT 2010): 242-251</li>
                                                <h5>2009<h5>
                                                <li>Fanxin Deng, Hailong Sun, Xiang Li, <a href="download/download_doc35.jsp">BPIDE: A Business Process IDE Supporting Multi-role Collaboration.</a> Computation World: Future Computing, Service Computation, Cognitive, Adaptive, content, Patterns, 2009</li>
                                                <li>Huipeng Guo, Jinpeng Huai, Yang Li, Ting Deng. <a href="download/download_doc36.jsp">KAF: Kalman Filter Based Adaptive Maintenance for Dependability of Composite Services.</a> CAiSE, 2008, 328-342</li>
                                                <li>Jin Zeng, Jinpeng Huai, Hailong Sun, Ting Deng and Xiang Li, <a href="download/download_doc37.jsp">LiveMig: An Approach to Live Instance Migration in Composite Service Evolution.</a> in Proceedings of IEEE International Conference on Web Services (ICWS), pp. 679-686, 2009</li>
                                                <li>Mu Li, Jinpeng Huai, <a href="download/download_doc38.jsp">An adaptive Web Services selection method based on the QoS prediction mechanism.</a> International Conference on Web Intelligence (WI 2009)</li>
                                                <li>Ting Deng, Jinpeng Huai, Xianxian Li, Zongxia Du, Huipeng Guo, <a href="download/download_doc39.jsp">Automated synthesis of composite services with correctness guarantee.</a> WWW 2009: 1127-1128</li>
                                                <li>Xianyang Qu, Hailong Sun, Xiang Li, Xudong Liu and Wei Lin, <a href="download/download_doc40.jsp">WSSM: A WordNet-Based Web Service Similarity Mining Mechanism.</a> Proceedings of IARIA International Conference on Service Computation, 2009</li>
                                                <li>Yang Li, Jinpeng Huai, Hailong Sun, Ting Deng, Huipeng Guo, <a href="download/download_doc41.jsp">PASS: An Approach to Personalized Automated Service Composition.</a> IEEE SCC, 2008(1), 283-290</li>
                                                <li>Yipeng Ji, Hailong Sun, Xudong Liu, Jin Zeng, Shangda Bai, <a href="download/download_doc42.jsp">A Decentralized Framework for Executing Composite Services Based on BPMN.</a> Computation World, Athens, Greece, 2009: 332-338</li>
					</ul>
				</div>
			</div>	
		</div>
		<!-- end #content -->
		<div style="clear: both;">&nbsp;</div>
	</div>
	</div>
	</div>
	<!-- end #page -->
</div>
	<div id=toTop><a href="#">Top<img src="images/arrow_up.png"/><br/></a></div>
	<div id="footer">
		<p>Copyright (c) Software Design and Productivity Group
		The Institute of Advanced Computing Technology, Beijing, China.</p>
	</div>
	<!-- end #footer -->
</body>
</html>
