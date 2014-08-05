var s_data = {
	action: "",
	appId: "",
	userName: ""
};

function refresh(data,textStatus){
	
	
	if(data.ret == false){
		alert(data.msg);
		return;
	}else {
		
		alert("APP " + data.appName + " " +data.action +" successfully!");
	}
	// if true
	var id = data.appId;
	var $input = $("input[name='appId'][value='"+data.appId+"']");
	var $li = $input.parent();
	var tagName = $li.attr("tagName").toLowerCase();
	while(tagName != "li" && tagName != "body"){
		$li = $li.parent();
		tagName = $li.attr("tagName").toLowerCase();
	}
	if($li.attr("tagName") == "body") return;
	// find the <li>
	$li.find("a[name='"+ data.variable +"']").html(data.value);
}

function getData(e){
	s_data.action = $(e).attr("value");
	var $form = $(e).parent();
	s_data.appId = $form.find("input[name='appId']").attr("value");
}

function showDialog(e){
	s_data.action = $(e).attr("value");
	var $form = $(e).parent();
	s_data.appId = $form.find("input[name='appId']").attr("value");
	s_data.userName = $("#data").attr("userName");
	var $dialog = $("#"+s_data.action+"Dialog");
	
	$.ajax({
		url:"AppOperate",
		data: {"action":s_data.action,"appId":s_data.appId,"userName":s_data.userName},
		dataType: "json",
		type: "POST",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
		success: function(data, textStatus){
					var $content = $(".content form",$dialog);
					
					
					
					var html="";
					html += "<form action='AppOperate' method='post'>";	
					html +="<input type='hidden' value='"+data.argNumber+"' id='argno' name='argno'></input>";
					var type="";
					var name="";
					for(var i = 0;i < data.argNumber ; i++){
						html=html+"<label>"+eval("data.argName"+i)+":"+eval("data.argType"+i)+":"+"</label>"+"<input type='text' value='' id='arg"+i+"' name='arg"+i+"'></input><br></br>";
						type=type+eval("data.argType"+i)+",";
						name=name+eval("data.argName"+i)+",";
					}
					html=html+"<input type=\"button\" value=\"submit\" onclick=\"appOperateImplement(this,"+s_data.appId+","+data.argNumber+",'"+name+"','"+type+"')\"></input>&nbsp;&nbsp;&nbsp;&nbsp";
					html=html+"<input type='button' value='cancle' onclick='hideDialog()'></input>";
					html += "</form>";	
					$content.html(html);
					$("#veil").find("form").innerHTML="$content.html";
				}
	
	});
	
	
	
	
	if($dialog.length > 0 ){
		$("#veil").show();
		$dialog.show();
	}
}

function appOperateImplement(e,appId,argno,name,type){
	s_data.action = $(e).attr("value");
	s_data.userName = $("#data").attr("userName");
	var $dialog = $("#implementDialog");
	
	var argvalue="";
	for(i=0;i<argno;i++){
		argvalue=argvalue+document.getElementById("arg"+i).value+",";
	}
	
	$.ajax({
		url:"AppOperate",
		data: {"action":s_data.action,"appId":appId,"userName":s_data.userName,"argno":argno,"argvalue":argvalue,"name":name,"type":type},
		dataType: "json",
		type: "POST",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
		success: function(data, textStatus){
					var $content = $(".content form",$dialog);
					
					
					var html="<br>"+data.value;
					$content.html($content.html()+html);
					$("#veil").find("form").innerHTML="$content.html";
					$("#veil").show();
					$dialog.show();
				}
	
	});
}


function hideDialog(){
	$("#veil").hide();
    $("#implementDialog").hide();
	//$("#"+s_data.action+"Dialog").hide();

}


function appOperate(){
	hideDialog();
	s_data.userName = $("#data").attr("userName");
	$.ajax({
		url:"AppOperate",
		data: {"action":s_data.action,"appId":s_data.appId,"userName":s_data.userName},
		dataType: "json",
		type: "POST",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
		success: function(data, textStatus){
					refresh(data,textStatus);					
				}
	
	});
	
	
}

function deploy(e){
	getData(e);
	appOperate();	
}

function undeploy(e){
	getData(e);
	appOperate();
}

function monitoring(e){
	getData(e);
	hideDialog();
	s_data.userName = $("#data").attr("userName");
	$.ajax({
		url:"AppOperate",
		data: {"action":s_data.action,"appId":s_data.appId,"userName":s_data.userName},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
		success: function(data, textStatus){
					alert(data.monitorMessage);					
				}
	
	});
}

function paraGenerate(o){
	var $form = $(o).parent();
	var argsStr = $("input[name='args']",$form).attr("value");
	var n=0;
	if(argsStr !== "" && argsStr !== null && argsStr!= undefined){
		n = parseInt(argsStr);
	}
	// ...
	var $dialog = $("#"+s_data.action+"Dialog");
	var $content = $(".content form",$dialog);
	var input = "<input type='text' value='' name=''></input><br></br>";
	var html="";
	for(var i = 0;i < n ; i++){
		html = input + html;
	}
	$content.html(html+$content.html());
}

$(document).ready(function(){
	var title = $("head title").html();
	$("#menu a:contains('"+ title +"')").addClass("current");
})