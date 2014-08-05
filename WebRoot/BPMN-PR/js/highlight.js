function highlight(str,color){
	var bookmark;
	if(document.createRange){
		var range = document.createRange();
	}else{
		var range = document.body.createTextRange();
		bookmark = range.getBookmark();
	}

	if(range.findText){
		range.collapse(true);
		range.moveToBookmark(bookmark);
		while(range.findText(str)){
			range.pasteHTML(range.text.fontcolor(color));
		}
	}else{
		var s,n;
		s = window.getSelection();
		s.collapse(document.body,0);
		while(window.find(str)){
			var n = document.createElement("SPAN");
			n.style.background=color;
			s.getRangeAt(0).surroundContents(n);
		}
	}
}