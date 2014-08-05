function subscribe_application(input){
	var id = input.getAttribute("appId");
	$.post("SubscribeApplication",{id:id},function(data){
											alert(data);
										},"text");
}