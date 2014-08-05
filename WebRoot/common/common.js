function openWindow(url, w, h, s){
	var left = (screen.availWidth-w)/2;
	var top = (screen.availHeight-h)/2;
	var win = window.showModalDialog(url,"_blank","menubar=no,toolbar=no,location=no,status=no,scrollbars="+(s?"no":"yes")+",resizable=yes");
	win.resizeTo(w, h);
	win.moveTo(left, top);
	window.opener = null;	
	return win;
}