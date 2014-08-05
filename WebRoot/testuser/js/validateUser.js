var right = "right";

//user name
var u_enter= "Please Enter your active Email Address";
var u_needed = "Email Address is needed";
var u_invalid = "Your Email Address is invalid";
var u_exist = "The Email Address have been used";

//password
var p_enter = "Enter your password";
var p_needed = "The password is needed";
var p_length = "The length must be between 6 and 16";


//password_confirm
var pc_enter = "Please confirm your password";
var pc_needed = "Please confirm your password";
var pc_length = "The length must be between 6 and 16";
var pc_same = "Must be same as Password";

$(document).ready(function(){
	
	//On mouse focus validate user name
	$("input@[name=userName]").focus(function(){
		$(this).parents("td").find(".account_info").css("color","#aaa");
		$(this).parents("td").find(".account_info").text(u_enter);
	});
	$("input@[name=userName]").blur(function(){
		checkUserName($(this));
	});
	
	//validate password
	$("input@[name=password]").focus(function(){
		$(this).parents("td").find(".account_info").css("color","#aaa");
		$(this).parents("td").find(".account_info").text(p_enter);
	});
	$("input@[name=password]").blur(function(){
		checkPassword($(this));
	});
	
	//validate password confirm
	$("input@[name=password_confirm]").focus(function(){
		$(this).parents("td").find(".account_info").css("color","#aaa");
		$(this).parents("td").find(".account_info").text(pc_enter);
	});
	$("input@[name=password_confirm]").blur(function(){
		checkPasswordConfirm($("input@[name=password]"),$(this));
	});
		
});

function signUp(form){
	var flag = true;
	flag = checkUserName($("input@[name=userName]"));
	flag = checkPassword($("input@[name=password]")) && flag;
	flag = checkPasswordConfirm($("input@[name=password]"),$("input@[name=password_confirm]")) && flag;
	return flag;
}

//check user name
function checkUserName(input){
	var userName = input.attr("value").trim();
	var flag = true;
	if(userName.length==0){
		input.parents("td").find(".account_info").css("color","red");
		input.parents("td").find(".account_info").text(u_needed);
		flag = false;
	}
	else if(!checkEmail(userName)){
		input.parents("td").find(".account_info").css("color","red");
		input.parents("td").find(".account_info").text(u_invalid);
		flag = false;
	}
	else{
		var url = 'user/IsUserNameExist.action';
		var params = {
				userName:userName
		};
		jQuery.post(url, params, flag = callbackUserName, 'json');
	}
	return flag;
}

//check password
function checkPassword(input){
	var password = input.attr("value");
	if(password.length==0){
		input.parents("td").find(".account_info").css("color","red");
		input.parents("td").find(".account_info").text(p_needed);
		return false;
	}
	else if(password.length<6 || password.length>16){
		input.parents("td").find(".account_info").css("color","red");
		input.parents("td").find(".account_info").text(p_length);
		return false;
	}
	else{
		input.parents("td").find(".account_info").css("color","green");
		input.parents("td").find(".account_info").text(right);
		return true;
	}
}

//check password confirm
function checkPasswordConfirm(Ipassword,IpasswordConfirm){
	var password = Ipassword.attr("value");
	var password_confirm = IpasswordConfirm.attr("value");
	if(password_confirm.length==0){
		IpasswordConfirm.parents("td").find(".account_info").css("color","red");
		IpasswordConfirm.parents("td").find(".account_info").text(pc_needed);
		return false;
	}
	else if(password_confirm.length<6 || password_confirm.length>16){
		IpasswordConfirm.parents("td").find(".account_info").css("color","red");
		IpasswordConfirm.parents("td").find(".account_info").text(pc_length);
		return false;
	}
	else if(password != password_confirm){
		IpasswordConfirm.parents("td").find(".account_info").css("color","red");
		IpasswordConfirm.parents("td").find(".account_info").text(pc_same);
		return false;
	}
	else{
		IpasswordConfirm.parents("td").find(".account_info").css("color","green");
		IpasswordConfirm.parents("td").find(".account_info").text(right);
		return true;
	}
}

function callbackUserName(data)
{
	if(data.flag==true){
		$("input@[name=userName]").parents("td").find(".account_info").css("color","red");
		$("input@[name=userName]").parents("td").find(".account_info").text(u_exist);
		return false;
	}
	else{
		$("input@[name=userName]").parents("td").find(".account_info").css("color","green");
		$("input@[name=userName]").parents("td").find(".account_info").text(right);
		return true;
	}
}

//validate email address
function checkEmail(email)
{
    var pattern=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    if (pattern.test(email))
    	return true;
    else
    	return false;
}
