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
				var res = xmlhttp.responseText;
				AddResponse(res);
			}
			else
			{
				alert("error");
			}
		}
	};

	xmlhttp.open("post",url,true);

	xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');

	xmlhttp.send(para);
}

function AddResponse(msg)
{
	alert(msg);
	window.navigate("ServiceCloud/J2WS.jsp"); 
} 

function deployJ2WS(userName, fileName)
{
	//AjaxSubmit("userName="+userName+"&fileName="+fileName,"service/deployJ2WS.jsp");
	//window.navigate("ServiceCloud/MyServiceContainer.jsp");
	window.location.href="./service/deployJ2WS.jsp?"+"userName="+userName+"&fileName="+fileName;
}

/*function downloadJ2WS(userName, fileName)
{
	AjaxSubmit("userName="+userName+"&fileName="+fileName,"service/downloadJ2WS.jsp");
}*/

function removeJ2WS(userName, fileName)
{
	//AjaxSubmit("userName="+userName+"&fileName="+fileName,"service/removeJ2WS.jsp");
	window.location.href="./service/removeJ2WS.jsp?"+"userName="+userName+"&fileName="+fileName;
}

