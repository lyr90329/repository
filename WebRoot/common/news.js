$(document).ready(function(){
    var url = 'news/GetNews.action';
    var params = {
        num:10
    };
    jQuery.post(url, params, callbackNews, 'json');
});

function callbackNews(data)
{
	var news = data.news;
	for(i in news){
		$(".left_two").find(".side_content").find("marquee").append("<div><a href=\"news/NewsDetails.action?id="+news[i].id+"\">"+news[i].title+"</a></div>");
	}
}