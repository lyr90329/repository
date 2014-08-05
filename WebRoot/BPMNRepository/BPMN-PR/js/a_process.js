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
	// change download address
	$("#modelDownload").attr("href","GetModel?type=bpmn&bpmnId="+modelData.bpmnId);
	// change image
	loadFlexImgFile("GetModel?type=flexImg&bpmnId="+modelData.bpmnId, $("#modelContent"));
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
		type: "POST",
		error: function (XMLHttpRequest, textStatus, errorThrown) {alert("error");},
		success: function(data,textStatus) {
					alert(data);
				}
	});
}
window.onload = function(){
	$modelData = $("#modelData");
	modelData = {
		titleId: parseInt($modelData.attr("titleId")),
		bpmnId: parseInt($modelData.attr("bpmnId")),
		version: parseInt($modelData.attr("version")),
		location: $modelData.attr("location"),
		latest: $modelData.attr("latest"),
		modifyTime: ""
	};
	
	// start run
	if (modelData !== null && modelData.location !== undefined) {
	//	loadBPMNFile($modelData.attr("bpmnId"));
		loadFlexImgFile(modelData.location,$("#modelContent"));
	}
	// 
	refresh();
}
