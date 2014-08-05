function monitor(){
	$.ajax({
		url:"GetJobId",
		data: {"action":"getJobId","bpmnId":s_data.bpmnId,"userName":s_data.userName},
		dataType: "json",
		type: "POST",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
		success: function(data, textStatus){
					if(data.jobId == null){
						alert("no job id,wait a miniute");
					}else{
						s_data.jobId = data.jobId;
						window.open('monitor.jsp?jobID='+s_data.jobId+'&bpmnID='+s_data.bpmnId);
					}
				}
	
	});
}

function AjaxSubmit(para,url)
    {
       
       
       var xmlhttp;
       try{
             xmlhttp = new XMLHttpRequest();
          }
          catch(e)
          {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
          }
          
       xmlhttp.onreadystatechange=function(){
           if(4==xmlhttp.readyState){
                if(200==xmlhttp.status){
                    var res =xmlhttp.responseText;
                    AddResponse(res);
                    }
                    else
                    {
                    alert("error");
                    }
            }
         }
         
         xmlhttp.open("post",url,true);
         
         xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
         
         xmlhttp.send(para); 
    }

function publish(bpmnid)
{
    
    AjaxSubmit("bpmnid="+bpmnid,"bpmn/publish.jsp");


}

function edit(bpmnid)
{
    
    window.open("http://192.168.104.114:8080/SaaS/process/BpmnEditor.html");


}

function removeMyBPMN(bpmnid,EngineBpmnId)
{
    AjaxSubmit("bpmnid="+bpmnid+"&EngineBpmnId="+EngineBpmnId,"bpmn/remove.jsp");
}


function removeUpBPMN(bpmnid)
{
   window.location.href="removeUpBpmn.jsp?"+"bpmnid="+bpmnid;
}


function useBPMN(bpmnid)
{
    
    window.open("invokebpmn.jsp?bpmnid="+bpmnid);


}

function AddResponse(msg)
    {
       alert(msg);
	       
	   var flag=msg.substring(2,8);
	  
	   
	   if(flag=="Remove")
      {
            window.location.reload(); 

      } 
	}  