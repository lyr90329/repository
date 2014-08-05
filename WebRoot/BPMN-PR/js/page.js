//ul <ul>, n means the number of items per page
var n_page = {
	container:{},
	childTagName: "",
	per:0,	// number of items per page
	pages:0,	// number of pages
	list:{},	//the list of <li>
	// container,like <ul>
	// tagName , like <li>
	//per, the number of items per page.
	init: function(container,tagName,per){
		n_page.container = container;
		n_page.childTagName = tagName;
		n_page.list = ul.getElementsByTagName(n_page.childTagName);
		n_page.per = per;
		pages = Math.ceil(n_page.list.length / per);
		for(var i = 0; i< length; i++){	// save the original display style
			if(!list[i].style.original_display) {
				list[i].style.original_display = list[i].style.display;
			}
		}
	},
	
	turnPage : function(n){
		var from = (n-1)*n_page.per;
		var to = n*n_page.per-1;
		var length = n_page.list.length;
		if(from >= length) from = length-1;
		if(to >= length) to = length -1;
		var i;
		for(i = 0; i < length; i++){
			n_page.list[i].style.display = "none";
		}
		for(i = from ;i <= to ; i++){
			n_page.list[i].style.display = "";
		}	
	}
};