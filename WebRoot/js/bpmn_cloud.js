var s_data = {
	action: "",
	bpmnId: "",
	userName: "",
	jobId: ""
};

var newWin;

function refresh(data,textStatus){
	
	
	if(data.ret == false){
		alert(data.msg);
		
		return;
	}else {
		if(data.action=="deployid")
		{
		alert("BPMN " + data.bpmnName + " deploy successfully!");
		}
		else if(data.action=="undeployid")
		{
		alert("BPMN " + data.bpmnName + " undeploy successfully!");
		}
	}
	// if true
	var id = data.bpmnId;
	var $input = $("input[name='bpmnId'][value='"+data.bpmnId+"']");
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
	s_data.action = $(e).attr("id");
	
	var $form = $(e).parent();
	s_data.bpmnId = $form.find("input[name='bpmnId']").attr("value");
}

function showDialog(e){
	s_data.action = $(e).attr("id");
	var $form = $(e).parent();
	s_data.bpmnId = $form.find("input[name='bpmnId']").attr("value");
	s_data.userName = $("#data").attr("userName");
	var myBpmnId=$form.find("input[name='MyBpmnId']").attr("value");
	
	var ac="";
	if(s_data.action=="implementid")
	{
	  ac="implement";
	}
	else
	{
	  ac=s_data.action;
	}
	window.open("test.jsp?action="+s_data.action+"&bpmnId="+s_data.bpmnId+"&userName="+s_data.userName+"&MyBpmnId="+myBpmnId);
}


function showDialog2(e){
	s_data.action = $(e).attr("id");
	var $form = $(e).parent();
	s_data.bpmnId = $form.find("input[name='bpmnId']").attr("value");
	s_data.userName = $("#data").attr("userName");
	var ac="";
	if(s_data.action=="implementid")
	{
	  ac="implement";
	}
	else
	{
	  ac=s_data.action;
	}
	
	window.open("test2.jsp?action="+s_data.action+"&bpmnId="+s_data.bpmnId+"&userName="+s_data.userName);
	
	
	
}




function bpmnOperateImplement(e,bpmnId,argno,name,type){
	s_data.action = $(e).attr("value");
	s_data.userName = $("#data").attr("userName");
	var $dialog = $("#implementDialog");
	
	var argvalue="";
	for(i=0;i<argno;i++){
		argvalue=argvalue+document.getElementById("arg"+i).value+",";
	}
	
	$.ajax({
		url:"BpmnOperate",
		data: {"action":s_data.action,"bpmnId":bpmnId,"userName":s_data.userName,"argno":argno,"argvalue":argvalue,"name":name,"type":type},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
		success: function(data, textStatus){
					s_data.jobId = data.jobId;
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


function bpmnOperate(){
	hideDialog();
	s_data.userName = $("#data").attr("userName");
	$.ajax({
		url:"BpmnOperate",
		data: {"action":s_data.action,"bpmnId":s_data.bpmnId,"userName":s_data.userName},
		dataType: "json",
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
	bpmnOperate();	
}

function undeploy(e){
	getData(e);
	bpmnOperate();
}

function monitoring(e){
	getData(e);
	hideDialog();
	s_data.userName = $("#data").attr("userName");
	$.ajax({
		url:"BpmnOperate",
		data: {"action":s_data.action,"bpmnId":s_data.bpmnId,"userName":s_data.userName},
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