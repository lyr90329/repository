
window.onload = function () {
	var list = $("#process_list >ul >li");
	list.each(function (i) {
		var data = $(".data", this);
		if (data == null) {
			return;
		}
		var bpmnId = data.attr("bpmnId");
		if (bpmnId == null || bpmnId == "") {
			return;
		}
		var $content = $(".modelContentSnap", this);
		loadFlexImgFile("GetModel?type=flexImg&bpmnId="+bpmnId, $content);
	});
};

function loadBPMNFile(bpmnId, content) {
	$.get("GetModel?bpmnId=" + bpmnId, function (data) {
		content.empty();
		content.html("<xmp>" + data + "</xmp>");
	});
}