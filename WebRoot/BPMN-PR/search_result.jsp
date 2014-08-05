<%@ page language="java" pageEncoding="ISO-8859-1"
	import="java.util.*"%>
<%
	String path = request.getContextPath() + "/BPMN-PR";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/lib/tld/c.tld"%>
<!-- List titleList  -->
<!-- String flexImgFolerPath -->
<!-- String keywords -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<base href="<%=basePath%>" />
		<title>BPMN Repository</title>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="css/style.css"
			media="screen" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/repository.js"></script>
		<script type="text/javascript" src="js/highlight.js"></script>
		<script type="text/javascript" src="js/search_result.js"></script>
	</head>
	<body>
		<div id="keys" value="${ keywords }" ></div>
		<jsp:include page="search.html"></jsp:include>
		<jsp:include page="header.jsp"></jsp:include>
		<div class="content">
			<div class="path">
				<ul>
					<li>
						<a>Home</a>
					</li>
				</ul>
			</div>
			<!-- ================================ right =============================== -->
			<div class="right">
				<a id="right_panel_drawerhandle"
					class="drawerhandle drawerhandle_right" title="Collapse panel"></a>
				<div id="right_panel_collapse">
					<div class="right_panel_content" id="comments">
						<div class="subsection_title">
							<h3>
								Annotations
							</h3>
						</div>
						<div id="writenew_annotation">
							<div class="reload_comment_form_show">
								<form method="post" action="" class="commentform">
									<div>
										<span class="smaller"><label for="id_comment">
												Your Annotation:
											</label> </span>
										<div class="textarea_wrapper">
											<textarea id="id_comment" rows="5" cols="25" name="comment">
										</textarea>
										</div>
										<input name="belongs_to" value="" type="hidden" />
										<input value="Post Annotation" type="submit" />
									</div>
								</form>
							</div>
						</div>

						<div id="comment_list">
							<hr />
						</div>

					</div>

					<div class="right_panel_content">
						<div id="tags">
							<h3>
								Tags
							</h3>
						</div>
						<div id="tag_list">
						</div>
						<div id="add_tags">
							<form method="post" action="">
								<p>
									<input class="ac_input" autocomplete="off" name="tags"
										id="id_tags" type="text" />
									<input value="Add tags" type="submit" />
								</p>
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- ============================== right end ================================= -->
			<!-- ============================== left ================================= -->
			<div class="left">
				<!-- title -->
				<div class="title">
					<h2>
						Search Result
					</h2>
				</div>
				<br></br>
				<br></br>
				<!-- processes list -->
				<div class="process_list" id="process_list">
					<ul>
						<c:if test="${ titleList != null }">
							<c:forEach var="title" items="${ titleList }">
								<c:set var="model" value="${ title.metas.v0 }"></c:set>
								<li>
									<div class="data" bpmnLocation="${ model==null?"":model.bpmn.bpmnLocation }"
										bpmnId="${ model.id }" 
										flexLocation="${model== null?"":flexImgFolderPath }${ model == null?"":model.bpmn.flexLocation }">
									</div>
									<div class="process_title">
										<a href="AProcess?id=${ title.id }">${ title.title }</a>
										<div class="metamenu">
											<button class="trash_icon" title="Move to trash" onclick="removeTitle(${ title.id })"></button>
										</div>
									</div>
									<br></br>
									<div class="modelContentSnap">
										<c:if test="${ model == null || model.bpmn == null }">
											<a href="AProcess?id=${ title.id }">Click here to
												start modeling</a>
										</c:if>
									</div>
									<div class="process_description">
										<h3>Description</h3>
										<p>${ title.description == null || title.description == ""? "no description ..." : title.description }</p>
										<a onclick="editDescription(${ title.id },this)">Edit</a>
									</div>
								</li>
							</c:forEach>
						</c:if>
						<c:if test="${ titleList == null }">
							no process's title and description matches your search keywords.
						</c:if>
					</ul>
				</div>
			</div>
			<!-- ================================== left end =========================== -->
		</div>
		
		<div class="footer">
			<p>
				&copy; 2007 Internet Hosting, Design: Luka Cvrk -
				<a href="#"
					title="Information Architecture and Web Design">Solucija</a>
			</p>
			<p>
				<a href="#">RSS Feed</a> &middot;
				<a href="#">Contact</a> &middot;
				<a href="#">Accessibility</a> &middot;
				<a href="#">Products</a> &middot;
				<a href="#">Disclaimer</a> &middot;
				<a href="#">CSS</a>
				and
				<a href="#">XHTML</a>
				<br />
			</p>
		</div>
	</body>
</html>
