$(document).ready(function(){
   $(".lines").hover(function(){
     $(this).addClass("hover_lines");
   },function(){
     $(this).removeClass("hover_lines");
   });
 });

$(document).ready(function(){
   $(".lines:last").addClass("last_lines");
 });

//服务订阅结果
$(document).ready(function(){
	
});

//comments up and down
$(document).ready(function(){
	$("input@[name=up]").hover(function(){
		$(this).parents("span").addClass("appraise_hover");
	},function(){
		$(this).parents("span").removeClass("appraise_hover");
	});
	$("input@[name=down]").hover(function(){
		$(this).parents("span").addClass("appraise_hover");
	},function(){
		$(this).parents("span").removeClass("appraise_hover");
	});
	$("input@[name=up]").click(function(){
		var parent = $(this).parent("span");
		var url = 'comments/AppraiseCommentsUp.action';
	    var params = {
		   id:$(this).parents("td").find("input@[name=id]").attr("value")
	    };
	    jQuery.post(url, params, function(data){
	    	 var up_value = parseInt(parent.find("span").text())+1;
	    	 parent.find("span").text(up_value);
	    }, 'json');
	});
	$("input@[name=down]").click(function(){
		var parent = $(this).parent("span");
		var url = 'comments/AppraiseCommentsDown.action';
	    var params = {
		   id:$(this).parents("td").find("input@[name=id]").attr("value")
	    };
	    jQuery.post(url, params, function(data){
	    	var up_value = parseInt(parent.find("span").text())+1;
	    	parent.find("span").text(up_value);
	    }, 'json');
	});
});