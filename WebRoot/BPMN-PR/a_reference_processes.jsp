<%@ page language="java" pageEncoding="ISO-8859-1"
	import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/lib/tld/c.tld"%>
<%
	String path = request.getContextPath() + "/BPMN-PR";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- cn.org.act.sdp.bpmnRepository.entity.DomainBean domain  -->
<!-- List titleList  -->
<!-- String flexImgPath -->
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
		<script type="text/javascript" src="js/a_reference_processes.js"></script>
	</head>
	<body>
		<jsp:include page="search.html"></jsp:include>
		<jsp:include page="header.jsp"></jsp:include>
		<div class="content">
			<div class="path">
				<ul>
					<li>
						<a>Home</a>
					</li>
					<li>
						&nbsp;&gt;&gt;&nbsp;
						<a href="ReferenceProcesses">Reference Processes</a>
					</li>
					<li>
						&nbsp;&gt;&gt;&nbsp;
						<a href="AReferenceProcesses?id=${ domain.id }">${ domain.name
							}</a>
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
						${ domain.name }
					</h2>
				</div>
				<!-- in place edit block -->
				<div class="inplaceedit_block">
					<p>
						Here we collect processes concerning state-of-the-art E-Commerce.
					</p>
				</div>
				<!-- inline menu -->
				<span class="inline_menu"> <a id="">Edit Description</a> </span>
				<!-- wiki meta -->
				<div class="wiki_meta">
					<p>
						Last edited by
						<a href="/user/Markus_Guentert/" id="wiki_84-edited_by_link"
							class="wiki_84"> <span id="wiki_84-edited_by" class="wiki_84">Markus</span>
						</a> on
						<span id="wiki_84-edited_on" class="wiki_84">${
							domain.modifiedTime }</span>
					</p>
				</div>
				<p>
					&nbsp;&nbsp;&nbsp;
					<a onclick="return showForm()">Add a new process model</a>
				</p>
				<form method="post" action="NewTitle" id="form" style="display: none"
					onSubmit="return onSubmit(this,'bpmnName')">
						<label>
							Title:(less than 45)
						</label>
						<input type="hidden" name="domainId" value="${ domain.id }"></input>
						<input type="text" maxlength="45" name="title" />
						<input type="submit" value="create" class="submit" />
				</form>
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
										flexLocation="${model== null?"":flexImgPath }${ model == null?"":model.bpmn.flexLocation }">
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
		<!-- ================================= script =================================== -->
		<script type="text/javascript">
	
				
		</script>
	</body>
</html>
