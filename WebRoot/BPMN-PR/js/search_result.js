window.onload = function(){
	var $keys = $("#keys");
	var keys = $keys.attr("value");
	var keyArray = keys.split(" ");
	for(var i =0; i< keyArray.length; i++){
		highlight(keyArray[i],"yellow");
	}
	
	var list = $("#process_list >ul >li");
	list.each(function (i) {
		var data = $(".data", this);
		if (data === null) {
			return;
		}
		var bpmnId = data.attr("bpmnId");
		if (bpmnId === null || bpmnId === "") {
			return;
		}
		var $content = $(".modelContentSnap", this);
		loadImage("GetModel?type=flexImg&bpmnId="+bpmnId, function(img){
															loadFlex2Html($content,img);
														});
//		loadFlexImgFile("GetModel?type=flexImg&bpmnId="+bpmnId, $content);
	});
}