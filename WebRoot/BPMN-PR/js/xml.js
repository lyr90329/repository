// 加载xml文档
function showDashedFrame(o){
//	o.setAttribute("class","dashed_frame");
	o.className = "dashed_frame";
}

function noFrame(o){
//	o.setAttribute("class","no_frame");
	o.className = "no_frame";
}

function annotateElement(div){
	var annotation = prompt("Annotation");
}

function createDIV(x,y,width,height,figureId){
	if(width <= 0 || height <=0){
//		throw new Error("width or height can't be less than 0");
		return null;
	}
	var div;
	div = document.createElement("div");
	div.style.position = "absolute";
	y -= 4; 
	x -= 4;
	width +=8;
	height +=8;
	div.style.top = y+"px";
	div.style.left = x+"px";
	div.style.width = width+"px";
	div.style.height = height+"px";
	div.style.zIndex = "4";
//	div.setAttribute("class","no_frame");
	div.className = "no_frame";
	div.setAttribute("figureId",figureId);
	div.onmouseover = function(e){
						showDashedFrame(div);
					};
	div.onmouseout = function(e){
						noFrame(div);
					};
	div.onclick = function(e){
					annotateElement(div);
				};
	return div;
}

