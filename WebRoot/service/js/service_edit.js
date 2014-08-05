$(document).ready(function(){
	//hide the editing area
	$(".additionalInfoEdit").hide();
	$(".user_tags_add").hide();
	//*************tags*********************************************
	//when click the edit buttom
   $("input@[name=edit_button]").click(function(){
	   $(this).hide();
	   $(".additionalInfo").hide();
	   $(".additionalInfoEdit").show();
   });
   //when click the save buttom
   $("input@[name=save_edit]").click(function(){
	   var url = 'service/EditInformation.action';
	   var params = {
		   serviceId:$("input@[name=serviceId]").attr("value"),
		   additionalInfo:$("textarea@[name=additionalInfo]").val()
	   };
	   jQuery.post(url, params, function(data){
		   $(".additionalInfo").text(data.additionalInfo);
		   $("input@[name=edit_button]").show();
		   $(".additionalInfo").show();
		   $(".additionalInfoEdit").hide();
	   }, 'json');
   });
   //when click the reset buttom
   $("input@[name=reset_edit]").click(function(){
	   $("form")[1].reset();
   });
   //when click the cancel buttom
   $("input@[name=cancel_edit]").click(function(){
	   $("input@[name=edit_button]").show();
	   $(".additionalInfo").show();
	   $("form")[1].reset();
	   $(".additionalInfoEdit").hide();
   });
   
   //*************tags*********************************************
   //when click the add buttom
   $("input@[name=add_button]").click(function(){
	   $(this).hide();
	   $(".user_tags_add").show();
   });
   //when click the save buttom
   $("input@[name=save_add]").click(function(){
	   var url = 'tags/AddTag.action';
	   var params = {
		   serviceId:$("input@[name=serviceId]").attr("value"),
		   tagName:$("input@[name=tagName]").attr("value")
	   };
	   jQuery.post(url, params, function(data){
		   var div = $(".user_tags");
		   var tags = data.tags;
		   div.empty();
		   $.each(tags,function(i, n){
				div.append("<a href=\"service/ServicesByTag.action?tagName="+n.name+"\" >"+n.name+"</a> ");
			}); 
		   $("input@[name=add_button]").show();
		   $(".user_tags_add").hide();
	   }, 'json');
   });
   //when click the cancel buttom
   $("input@[name=cancel_add]").click(function(){
	   $("input@[name=add_button]").show();
	   $(".user_tags_add").hide();
   });
	   
 });