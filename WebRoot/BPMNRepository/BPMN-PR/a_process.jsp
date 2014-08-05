<%@ page language="java" pageEncoding="ISO-8859-1"
	import="java.util.*"%>
<%
	String path = request.getContextPath() + "/BPMNRepository/BPMN-PR";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- cn.org.act.sdp.bpmnRepository.entity.MetaBean meta -->
<!-- cn.org.act.sdp.bpmnRepository.entity.DomainBean domain -->
<!-- cn.org.act.sdp.bpmnRepository.entity.TitleBean title -->
<!-- String modelPath -->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<base href="<%=basePath%>" />
		<title>BPMN Repository</title>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="css/style.css"
			media="screen" />
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/repository.js"></script>
		<script type="text/javascript" src="js/a_process.js"></script>
	</head>
	<body>
		<jsp:include page="search.html"></jsp:include>
		<!-- =============================== header ================================ -->
		<div class="header">
			<h1>
				Business Process Repository (BPR)
			</h1>
			<p>
				... ...
			</p>
			<ul class="tablist">
				<li>
					<a>Tutorials</a>
				</li>
				<li>
					<a class="current">ReferenceProcesses</a>
				</li>
			</ul>
		</div>
		<!-- ============================= header end ================================== -->
		<!-- ============================== content ================================= -->
		<c:if test="${ meta!=null }">
			<div class="data" id="modelData" version="${ meta.version }"
				location="${ modelPath }" titleId="${ title.id }"
				latest="${ title.latest }" bpmnId="${ meta.id }"></div>
		</c:if>
		<div class="content">
			<div class="path">
				<ul>
					<li>
						<a>Home</a>
					</li>
					<li>
						&nbsp;>>&nbsp;
						<a href="ReferenceProcesses">Reference Processes</a>
					</li>
					<li>
						&nbsp;>>&nbsp;
						<a href="AReferenceProcesses?id=${domain.id}">${ domain.name}
						</a>
					</li>
					<li>
						&nbsp;>>&nbsp;
						<a href="AProcess?id=${ title.id }">${ title.title }</a>
					</li>
				</ul>
			</div>
			<div class="right">
				<!-- ========================= right ====================================== -->
				<a id="right_panel_drawerhandle"
					class="drawerhandle drawerhandle_right" title="Collapse panel"></a>
				<div id="right_panel_collapse">
					<div>
						<div class="title">
							<h3>
								Annotations
							</h3>
						</div>
						<div>
							<form method="post" action="" class="commentform">
								<div>
									<span class="smaller"> <label for="id_comment">
											Your Annotation:
										</label> </span>
									<div class="textarea_wrapper">
										<textarea id="id_comment" rows="5" cols="25" name="comment"> </textarea>
									</div>
									<input name="belongs_to" value="" type="hidden" />
									<input value="Post
Annotation" type="submit" />
								</div>
							</form>
						</div>
					</div>
					<div class="blank_divider"></div>
					<div>
						<div class="title">
							<h3>
								Revision
								<span id="revision"> ${ meta == null ? 0 :
									meta.version}</span>
							</h3>
						</div>
						<p id="modelModifyTime">
							${ meta==null?"":meta.modDate }
						</p>
						<p>
							<c:set var="preRevision" value="hidden" />
							<c:set var="nextRevision" value="hidden" />
							<c:if test="${ meta != null}">
								<c:if test="${ meta.version < title.latest }">
									<c:set var="nextRevision" value="visible" />
								</c:if>
								<c:if test="${ meta.version > 1 }">
									<c:set var="preRevision" value="visible" />
								</c:if>
							</c:if>
							<a id="pre_revision" onclick="return preRevision()">Previous
								Revision</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a id="next_revision" onclick="return nextRevision()">Next
								Revision</a>
						</p>
					</div>
					<div class="blank_divider"></div>
					<div>
						<div class="title">
							<h3>
								Tags
							</h3>
						</div>
						<div id="tag_list">
						</div>
						<div>
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
				<div class="title relative">
					<h2>${ title.title }</h2>
					<div class="operateMenu">
						<a target="_blank" href="ToModelEditor?action=modify&bpmnId=${ meta.id }">Edit</a>
						<a target="_blank" href="ToModelEditor?action=new&titleId=${ title.id }">New Version</a>
					</div>
				</div>
				<div id="modelContent" class="modelContent">
					<c:if test="${ meta ==null }">
						<a onclick="return showForm()">Click here to start modeling</a>
					</c:if>
				</div>
				<br></br>
				<a onclick="return showForm()">Click here to create new Model.</a>
				<form action="NewModel" method="post" enctype="multipart/form-data"
					id="form" style="display: none" onsubmit="return onSubmit(this,'file')">
					<p>
						Upload your new version file.
					</p>
					<input type="hidden" name="url" id="form" value="AProcess?id=${title.id }" ></input>
					<input type="hidden" name="bpmnId" value="${title.id }"></input>
					<input type="hidden" name="action" value="newVersion"></input>
					<input type="file" name="file"></input>
					<br></br>
					<input type="submit" value="Submit"></input>
				</form>
				<div>
					<div class="title">
						<h2>Description</h2>
					</div>
					<p>
					${ title.description == null || title.description == ""? "no description ..." : title.description }</p>
					<a onclick="editDescription(${ title.id },this)">Edit</a>
				</div>
				<c:if test="${ meta != null }">
					<div class="title">
							<h2>
								Subscribe
							</h2>
					</div>
					<input type="button" value="Subscribe" onclick="subscribe()"/>
					
					<div class="title">
						<h2>
							Download
						</h2>
					</div> right click <a id="modelDownload"
						href="GetModel?bpmnId=${ meta.id }">here</a> and choose save as ... 
				</c:if>
			</div>
			<!-- ======================= left end ======================================== -->
		</div>
		<!-- =========================== content end ==================================== -->
		<div class="footer">
			<p>
				&copy; 2007 Internet Hosting, Design: Luka Cvrk -
				<a href="http://www.solucija.com/"
					title="Information Architecture
						and Web Design">Solucija</a>
			</p>
			<p>
				<a>RSS Feed</a> &middot;
				<a>Contact</a> &middot;
				<a>Accessibility</a> &middot;
				<a>Products</a> &middot;
				<a>Disclaimer</a> &middot;
				<a>CSS</a>
				and
				<a>XHTML</a>
				<br />
			</p>
		</div>
		<!-- ================================= script =================================== -->
		
	</body>
</html>