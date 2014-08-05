// global constants
var r_const = {
	bpmnFolderPath: "bpmn/"
};

// global function
function showForm() {
	var target = document.getElementById("form");
	if (target.style.display == "block") {
		target.style.display = "none";
	} else {
		target.style.display = "block";
	}
	return false;
}
//check the form's input named 'name' empty or not 
function onSubmit(form,name){
	reg = /^\s*$/;
	if(reg.test(form[name].value)){	// blank
		alert(name + " can't be blank!");
		return false;
	}
	var reg=/^[\w\s]*$/;
	if(!reg.test(form[name].value)){	// consisted of 0-9a-zA-Z_
		alert(name + " can be consisted of 0-9 a-z A-Z _");
		return false;
	}
	
	return true;
}

function loadFlexImgFile(url,$container){
	var img = document.createElement("img");
	img.src = url;
	var width = $container.width();
	var height = $container.height();
	if(img.width/width > img.height/height){
		img.width = width;
	}else {
		img.height = height;
	}
//	var $img = $('<img '+
//		'src="'+url+'" '+
//		'width="'+$container.css("width")+'"'+
//		'height="'+$container.css("height")+'" />');
	$container.empty();
	$container.append(img);
}

// titleId: as name indicate
// anchor: the <a> dom object
function editDescription(titleId , anchor){
	var $div = $(anchor).parent();
	var $a = $("a",$div);	// the "Edit" anchor <a>
	var $p = $("p",$div);	// the description <p>
	var preDes = $p.text();
	var $text = $("<textarea cols='40' rows='6' style='display:block'></textarea>");
	var $saveBtn = $("<input type='button' onclick='updateDescription("+titleId+",this)' value='Save' />");
	var $cancleBtn = $("<input type='button' onclick='cancleEdit(this)' value='Cancle'/>");
//	var $saveBtn.css({margin-right:"10px"});
//	var $cancleBtn.css({margin-left:"10px"});
	$text.text(preDes);
	$p.hide();
	$a.hide();
	$div.append($text);
	$div.append($saveBtn);
	$div.append($cancleBtn);
}

function updateDescription(titleId,saveBtn){
	var $div = $(saveBtn).parent();
	var $text = $("textarea",$div);
	var text = $text.val();
	$.ajax({
		url: "UpdateTitleDescription",
		data: {titleId:titleId,description:text},
		type: "POST",
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(textStatus);},
		success: function(data, textStatus){
					if(data.ret){
						$("p",$div).text(data.description);
						$("input",$div).remove();
						$("textarea",$div).remove();
						$("*",$div).show();
					}else{
						alert(data.msg);
					}
				}
	});
}

function cancleEdit(input){
	var $div = $(input).parent();
	$("input",$div).remove();
	$("textarea",$div).remove();
	$("*",$div).show();
}

function removeTitle(id){
	if(!confirm("Are you sure to remove this process including all versions !")){
        return;
    }
    var ret = $.ajax({
        type: "POST",
        url: "DeleteTitle",
        data: {"titleId":id},
        async: false
    }).responseText;

    alert(ret);
    window.location.reload();
}

function loadImage(url, callback) {
    var img = new Image(); //创建一个Image对象，实现图片的预下载
    img.src = url;
   
    if (img.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数
        callback(img);
        return; // 直接返回，不用再处理onload事件
    }

    img.onload = function () { //图片下载完毕时异步调用callback函数。
        callback(img);//将回调函数的this替换为Image对象
    };
}

function loadFlex2Html($container,img){
	var width = $container.width();
	var height = $container.height();
	var rateW = img.width/width; //宽比
	var rateH = img.height/height; //高比
	if(rateW > rateH){
		img.width = width;
		img.height /= rateW;
	}else {
		img.height = height;
		img.width /= rateH;
	}
//	var $img = $('<img '+
//		'src="'+url+'" '+
//		'width="'+$container.css("width")+'"'+
//		'height="'+$container.css("height")+'" />');
	$container.empty();
	$container.append(img);
}
