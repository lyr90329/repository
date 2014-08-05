// global variable
var versions = new Object;
var modelDatas = new Object;
var $modelData = {};
var modelData = {};

// functions
function refresh(){
	$modelData.attr("bpmnId",modelData.bpmnId);
	$modelData.attr("titleId",modelData.titleId);
	$modelData.attr("version",modelData.version);
	$modelData.attr("location",modelData.location);
	$modelData.attr("latest",modelData.latest);
	$("#modelModifyTime").html(modelData.modifyTime);
	$("#revision").html(modelData.version);
	if(modelData.version < modelData.latest){
		$("#next_revision").css("visibility","visible");
	}else {
		$("#next_revision").css("visibility","hidden");
	}
	if(modelData.version > 1){
		$("#pre_revision").css("visibility","visible");
	} else {
		$("#pre_revision").css("visibility","hidden");
	}
	
	//change edit link
	var edit_link = $("#edit_link").attr("href");
	var reg = /(.*bpmnId=)\d*/;
	edit_link = edit_link.replace(reg, "$1"+modelData.bpmnId);
	$("#edit_link").attr("href",edit_link);
	
	// change download address
	$("#modelDownload").attr("href","GetModel?type=bpmn&bpmnId="+modelData.bpmnId);
	// change image
	loadImage("GetModel?type=flexImg&bpmnId="+modelData.bpmnId, function(img){
															$content = $("#modelContent");
															$content.empty();
															$content.append(img);
															loadFlexFile(modelData.bpmnId);
														});
//	loadFlexImgFile("GetModel?type=flexImg&bpmnId="+modelData.bpmnId, $("#modelContent"));
}

function noModel(){
	$("#next_revision").css("visibility","hidden");
	$("#pre_revision").css("visibility","hidden");
	$("#revision").html("");
}

function preRevision() {
	var nowRevisionInt = modelData.version;
	if(nowRevisionInt <= 1){
		return false;
	}
	var preRevisionInt = nowRevisionInt -1;
	var preRevisionStr = preRevisionInt + ""; // convert number to string
	if(versions[preRevisionStr] === undefined || modelDatas[preRevisionStr] ===undefined){
		loadModelInfo(modelData.titleId,preRevisionInt);
	}else{
		modelData = modelDatas[preRevisionStr];
		refresh();
		data2html(versions[preRevisionStr],null);
	}
	return false;
}

function nextRevision() {
	var nowRevisionInt = modelData.version;
	if(nowRevisionInt >= modelData.latest){
		return false;
	}
	var nextRevisionInt = nowRevisionInt + 1;
	var nextRevisionStr = nextRevisionInt + "";
	if(versions[nextRevisionStr] === undefined || modelDatas[nextRevisionStr] === undefined){
		loadModelInfo(modelData.titleId,nextRevisionInt);
	}else{
		modelData = modelDatas[nextRevisionStr];
		refresh();
		data2html(versions[nextRevisionStr],null);
	}
	return false; 
}

function data2html(data,textStatus){
	// save this version model
	if (data !== null) {
		var version = $modelData.attr("version");
		versions[version] = data;
	}
	var content = $("#modelContent");
	content.empty();
	content.html("<xmp>" + data + "</xmp>");	
}
function beforeSend(){
	$("#modelContent").html("Loading...");
}

function error(XMLHttpRequest, textStatus, errorThrown){
	$("#modelContent").html("Model load failed");
}

function loadBPMNFile(bpmnId) {
	$.ajax({
		beforeSend:function () {beforeSend();},
		type: "POST",
		data: {"bpmnId":bpmnId},
	 	url: "GetModel",
	 	error: function (XMLHttpRequest, textStatus, errorThrown) {error(XMLHttpRequest, textStatus, errorThrown);},
		success: function(data,textStatus){data2html(data,textStatus);}
		});
}

function loadModelInfo( id,v ){
	$.ajax({
		beforeSend: function(){beforeSend();},
		url: "GetModelInfo",
		data: {"titleId":id, "version":v},
		dataType: "json",
		error: function (XMLHttpRequest, textStatus, errorThrown) {error(XMLHttpRequest, textStatus, errorThrown);},
		success: function(data, textStatus) {
						modelData = data.modelData;
						modelDatas[modelData.version+""] = modelData;
						refresh();
//						loadBPMNFile(modelData.bpmnId);
//						loadFlexImgFile(modelData.location,$("#modelContent"));
					}
	});
}

function subscribe(){
	$.ajax({
		url:"BPMNSubscribe",
		data: {"bpmnId":modelData.bpmnId},
		dataType: "json",
		type: "POST",
		error: function (XMLHttpRequest, textStatus, errorThrown) {alert("error");},
		success: function(data,textStatus) {
					alert("subscribe bpmn successfully!");
				}
	});
}

function addTag(titleId,form){
	var tags = document.getElementById("tag_text").value;
	$.ajax({
		url:"AddTag",
		data: {"titleId":titleId,"tags":tags},
		dataType: "json",
		type: "POST",
		error: function (XMLHttpRequest, textStatus, errorThrown) {alert(textStatus);},
		success: function(data,textStatus) {
					if(!data.ret){
						alert(data.errMsg);
					}else {
						var tags = data.tags;
						var html = "";
						for(var i=0;i<tags.length;i++){
							html +='<a href="GetModelByTag?tagId='+tags[i].id+'">'+tags[i].tag+'</a>&nbsp;&nbsp;';
						}
						document.getElementById("tag_list").innerHTML = html;
					}
				}
	});
}

function loadFlexFile(bpmnId){
	$.ajax({
		url:"GetModel",
		data: {type:"flex", bpmnId: bpmnId},
		dataType: "xml",
		type: "POST",
		error: function (XMLHttpRequest, textStatus, errorThrown) {alert(textStatus);},
		success: function(data,textStatus) {
					$(data).find("Figure").each(function(){
						var figure = $(this);
						var x = parseInt(figure.attr("x"));
						var y = parseInt(figure.attr("y"));
						var width = parseInt(figure.attr("width"));
						var height = parseInt(figure.attr("height"));
						var figureId = parseInt(figure.attr("figureid"));
	//					alert("x:"+x+" y:"+y+" widht:"+width+" height:"+height);
						
						var div = createDIV(x,y,width,height,figureId);
						
						$("#modelContent").append(div);
					});
				}
	});
}

window.onload = function(){
	$modelData = $("#modelData");
	if($modelData.length != 0){
		modelData = {
			titleId: parseInt($modelData.attr("titleId")),
			bpmnId: parseInt($modelData.attr("bpmnId")),
			version: parseInt($modelData.attr("version")),
			location: $modelData.attr("location"),
			latest: $modelData.attr("latest"),
			modifyTime: ""
		};
		
		// start run
	//	if (modelData !== null && modelData.location !== undefined) {
		//	loadBPMNFile($modelData.attr("bpmnId"));
	//		loadFlexImgFile(modelData.location,$("#modelContent"));
	//	}
		// 
		refresh();
	}else{
		noModel();
	}
}
