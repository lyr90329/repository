<%@ page language="java" import="db.dao.*,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String parentid=(String)request.getParameter("parentid");      
       String userName=(String)session.getAttribute("userName");
//       String userName="user1";	   
	   List list=Data.getInstance().getPath(Long.parseLong(parentid),userName);
	   
	   if(list!=null)
		{
		    rp="<return status=\"true\">";
			for(int i=0;i<list.size();i++)
			{
				Path path=(Path)list.get(i);
				String type="";
				if(path.getType()==0)
				{
				   type="folder";
				}
				else
				{
				   type="file";
				}
				String tmp="<item name=\""+path.getPathname()+"\" type=\""+type+"\" id=\""+path.getId()+"\"/>";
				rp=rp+tmp;
				
			}
			rp=rp+"</return>";
		}	   
	   else
	   {
	     rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing database</errinfo></return>";
	   }
	   
	out.print(rp);

 %>
