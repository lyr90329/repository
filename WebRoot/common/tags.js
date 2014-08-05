$(document).ready(function(){
    var url = 'tags/HotTags.action';
    var params = {
        num:20
    };
    jQuery.post(url, params, callbackTags, 'json');
});



function callbackTags(data)
{
	var div = $(".right_two").find(".side_content").find(".tag_cloud");
	var tags = data.tags;
	$.each(tags,function(i, n){
		div.append("<strong><a class=\"globaltagweight"+n.globalTagWeight+"\" "+"style=\"text-decoration:none;\" "+"title=\""+n.title+"\" "+"href=\"service/ServicesByTag.action?tagName="+n.tagName+"\" >"+n.tagName+"</a></strong> ");
	}) 
}