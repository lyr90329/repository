var n_needed = "The name is needed.";
var u_needed = "The Url is needed.";
var w_needed = "The Wsdl Url is needed.";

$(document).ready(function(){
	$("input@[name=name]").blur(function(){
		checkName($(this));
	});
	$("input@[name=url]").blur(function(){
		checkUrl($(this));
	});
	$("input@[name=wsdlUrl]").blur(function(){
		checkWsdlUrl($(this));
	});
});

function validateRegistry(form){
	var flag = true;
	flag = checkName($("input@[name=name]"));
	flag = checkUrl($("input@[name=url]")) && flag;
	flag = checkWsdlUrl($("input@[name=wsdlUrl]")) && flag;
	return flag;
}

function checkName(input){
	var name = input.attr("value").trim();
	if(name.length==0){
		input.parents("div").next(".warning").text(n_needed);
		return false;
	}
	else{
		input.parents("div").next(".warning").text("");
		return true;
	}
}

function checkUrl(input){
	var url = input.attr("value").trim();
	if(url.length==0){
		input.parents("div").next(".warning").text(u_needed);
		return false;
	}
	else{
		input.parents("div").next(".warning").text("");
		return true;
	}
}

function checkWsdlUrl(input){
	var wsdlUrl = input.attr("value").trim();
	if(wsdlUrl.length==0){
		input.parents("div").next(".warning").text(w_needed);
		return false;
	}
	else{
		input.parents("div").next(".warning").text("");
		return true;
	}
}