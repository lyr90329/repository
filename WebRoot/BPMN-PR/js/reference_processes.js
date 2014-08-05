function removeDomain(id){
    if(!confirm("Are you sure to remove this domain including its sub-process !")){
        return;
    }
    var ret = $.ajax({
        type: "POST",
        url: "DeleteDomain",
        data: {"domainId":id},
        async: false
    }).responseText;
    
    alert(ret);
    window.location.reload();
}