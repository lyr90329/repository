var s_data = {
	action: "",
	serviceId: "",
	userName: ""
};

function refresh(data,textStatus){
	
	
	if(data.ret == false){
		alert(data.msg);
		return;
	}else {
		
		alert("Service " + data.serviceName + " " +data.action +" successfully!");
	}
	// if true
	var id = data.serviceId;
	var $input = $("input[name='serviceId'][value='"+data.serviceId+"']");
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
	s_data.serviceId = $form.find("input[name='serviceId']").attr("value");
}

function showDialog(e){
	s_data.action = $(e).attr("id");
	var $form = $(e).parent();
	s_data.serviceId = $form.find("input[name='serviceId']").attr("value");
	s_data.userName = $("#data").attr("userName");
	var $dialog = $("#"+s_data.action+"Dialog");
	
	$.ajax({
		url:"ServiceOperate",
		data: {"action":s_data.action,"serviceId":s_data.serviceId,"userName":s_data.userName},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
		success: function(data, textStatus){
					var $content = $(".content form",$dialog);
					
					
					
					var html="";
					html += "<form action='ServiceOperate' method='post'>";	
					html +="<input type='hidden' value='"+data.argNumber+"' id='argno' name='argno'></input>";
					var type="";
					var name="";
					for(var i = 0;i < data.argNumber ; i++){
						html=html+"<label>"+eval("data.argName"+i)+":"+eval("data.argType"+i)+":"+"</label>"+"<input type='text' value='' id='arg"+i+"' name='arg"+i+"'></input><br></br>";
						type=type+eval("data.argType"+i)+",";
						name=name+eval("data.argName"+i)+",";
					}
					html=html+"<input type=\"button\" value=\"submit\" onclick=\"serviceOperateImplement(this,"+s_data.serviceId+","+data.argNumber+",'"+name+"','"+type+"')\"></input>&nbsp;&nbsp;&nbsp;&nbsp";
					html=html+"<input type='button' value='cancel' onclick='hideDialog()'></input>";
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

function serviceOperateImplement(e,serviceId,argno,name,type){
	s_data.action = $(e).attr("value");
	s_data.userName = $("#data").attr("userName");
	var $dialog = $("#implementDialog");
	
	var argvalue="";
	for(i=0;i<argno;i++){
		argvalue=argvalue+document.getElementById("arg"+i).value+",";
	}
	
	$.ajax({
		url:"ServiceOperate",
		data: {"action":s_data.action,"serviceId":serviceId,"userName":s_data.userName,"argno":argno,"argvalue":argvalue,"name":name,"type":type},
		dataType: "json",
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


function serviceOperate(){
	hideDialog();
	s_data.userName = $("#data").attr("userName");
	$.ajax({
		url:"ServiceOperate",
		data: {"action":s_data.action,"serviceId":s_data.serviceId,"userName":s_data.userName},
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
	serviceOperate();	
}

function undeploy(e){
	getData(e);
	serviceOperate();
}

function useService(serviceid,wsdlurl,servicename)
{

   window.open("serviceform.jsp?serviceid="+serviceid+"&wsdlurl="+wsdlurl+"&servicename="+servicename);

}

function commentService(serviceid){
	window.open("/servicexchange/service/ServiceOverView.action?serviceId="+serviceid+"&comment=true")
}

function testOperation(serviceid)
{
	alert("OK!!!");
}

function loadTest(serviceName, deployid, userName)
{
	window.open("getInnerWsdl.jsp?serviceName="+serviceName+"&deployid="+deployid+"&userName="+userName);
}

function useUpService(serviceName, deployid, userName)
{
   window.open("uploadserviceform.jsp?serviceName="+serviceName+"&deployid="+deployid+"&userName="+userName);
}

function useUploadService(serviceid)
{
   window.open("uploadserviceform.jsp?serviceid="+serviceid);
}


function AjaxSubmit(para,url)
    {
       
       
       var xmlhttp;
       try{
             xmlhttp = new XMLHttpRequest();
          }
          catch(e)
          {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
          }
          
       xmlhttp.onreadystatechange=function(){
           if(4==xmlhttp.readyState){
                if(200==xmlhttp.status){
                    var res =xmlhttp.responseText;
                    AddResponse(res);
                    }
                    else
                    {
                    alert("error");
                    }
            }
         }
         
         xmlhttp.open("post",url,true);
         
         xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
         
         xmlhttp.send(para);               
    }

function removeService(serviceid,servicename)
{
    
   AjaxSubmit("serviceid="+serviceid+"&serviceName="+servicename,"service/remove.jsp");
   
}

function removeUpService(deployid,servicename,userName)
{
   window.location.href="./service/removeUpService.jsp?"+"deployid="+deployid+"&serviceName="+servicename+"&userName="+userName;
}

function AddResponse(msg)
    {
       alert(msg);
	    window.location.reload();    
	} 

function monitoring(e){
	getData(e);
	hideDialog();
	s_data.userName = $("#data").attr("userName");
	$.ajax({
		url:"ServiceOperate",
		data: {"action":s_data.action,"serviceId":s_data.serviceId,"userName":s_data.userName},
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

