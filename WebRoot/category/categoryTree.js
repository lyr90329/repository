$(document).ready(function(){
	var url = 'cluster/CategoryTree.action';
    var params = {
        clusterNum:10
    };
    jQuery.post(url, params, callbackCategory, 'json');
});
function callbackCategory(data){
	var categories = data.categories;
	var counter = 0;
	for(i in categories){
		counter++;
		var category_name = "category "+counter+" ("+categories[i].size+")";
//		var category_id = "category"+counter;
		$("#category").append("<li><a href=\"cluster/ServicesByCluster.action?cId="+categories[i].clusterId+"\">"+category_name+"</a></li>");
//		$("#category").append("<li class=\"closed\"><span class=\"folder\">"+category_name+"</span><ul id=\""+category_id+"\"></ul></li>");
//		for(j in services){
//			var service_name = services[j].name;
//			var service_url = "service/ServiceOverView.action?serviceId="+services[j].id;
//			$("#category").find("#category"+counter).append("<li><span class=\"file\"><a href=\""+service_url+"\">"+service_name+"</a></span></li>");
//		}
	}
	$("#category").treeview();
}