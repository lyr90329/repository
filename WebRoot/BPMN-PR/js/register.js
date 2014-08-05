function register(form){
	if(!onSubmit(form,'name') || !onSubmit(form,'password')){
		return;
	}
	var name = form.name.value;
	var pwd = form.password.value;
	window.location = "Register?name="+name+"&password="+pwd;
}