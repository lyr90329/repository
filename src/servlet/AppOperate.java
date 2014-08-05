package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import config.Config;

import db.dao.AppDao;
import db.entity.App;
import folderOP.FolderCopy;
import folderOP.FolderDeleter;

public class AppOperate extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AppOperate() {
		super();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.err.println("===========a==============");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		String appIdStr = request.getParameter("appId");
		int appId = Integer.valueOf(appIdStr);
		String userName = request.getParameter("userName"); // get entity from
		// database
		AppDao dao = new AppDao();
		App trial;
		trial = dao.findByAppIdAndUserName(appId, userName);
		JSONObject data = new JSONObject();
		System.out.println("1");
		try {

			data.put("action", action);
			data.put("userName", userName);
			data.put("appId", appId);
			data.put("appName", trial.getAppName());
			data.put("ret", false);
			if (action.equals("deploy")) {
				if (trial.getDeployStatus().equals("true")) {
					data.put("ret", false);
					data.put("msg", "Alert! App " + trial.getAppName()
							+ " has been already deployed .");
				} else {
					if (deploy(trial)) {
						data.put("variable", "deployStatus");
						data.put("ret", true);
						data.put("value", "true");
					}
				}
			} else if (action.equals("undeploy")) {
				System.out.println(trial.getDeployStatus());
				if (trial.getDeployStatus().equals("false")) {
					System.out.println("2");
					data.put("ret", false);
					data.put("msg", "Alert! App " + trial.getAppName()
							+ " has been already undeployed .");
				} else {
					if (undeploy(trial)) {
						data.put("variable", "deployStatus");
						data.put("ret", true);
						data.put("value", "false");
					}
				}
			}// else if ...
			
			
			else if(action.equals("implement")){
				data.put("argNumber", getArgNumber(trial));
				for(int i=0;i<getArgNumber(trial);i++){
					
					data.put("argName"+i, getArgName(trial).get(i));
					data.put("argType"+i, getArgType(trial).get(i));
				}
				data.put("ret", true);
				System.out.println("3");
				
			}
			
			else if(action.equals("submit")){
				String argnostr=request.getParameter("argno");
				int argno=Integer.parseInt(argnostr);
				String argvalue=request.getParameter("argvalue");
				String name=request.getParameter("name");
				String type=request.getParameter("type");
				String value=implement(trial,argno,name,type,argvalue);
				//System.out.println(name+";;;"+type);

				//接口
				data.put("value", value);
				//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			}
			else if(action.equals("monitoring")){
				data.put("monitorMessage", monitoring(trial));
				System.out.println(monitoring(trial));
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			out.print(data.toString());
			out.flush();
			out.close();
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	doPost(request, response);
	}


	private boolean deploy(App trial) {
		// TODO deploy operation
		
		File sourFile = null, desFile = null;
		String sourFolder="C:/WebApps/"+trial.getAppName();
		String desFolder ="D:/Project/Tomcat/webapps/"+trial.getAppName(); 		
		sourFile = new File(sourFolder);
		if (!sourFile.isDirectory() || !sourFile.exists()) {
			System.out.println("源文件夹不存在");
		}
		desFile = new File(desFolder);
		desFile.mkdir();
		
		FolderCopy.copy(sourFile.listFiles(), desFile);                    
		
		
		// update database
		AppDao dao = new AppDao();
		trial.setDeployStatus("true");
		if (dao.update(trial) < 0)
			return false;
		return true;
	}

	private boolean undeploy(App trial) {
		// TODO undeploy operation
		FolderDeleter a= new FolderDeleter();
    	try {
			a.deleteDirs("D:/Project/Tomcat/webapps/"+trial.getAppName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	System.out.println("OK");
		
		// update database
		AppDao dao = new AppDao();
		trial.setDeployStatus("false");
		if (dao.update(trial) < 0)
			return false;
		return true;
	}
	private int getArgNumber(App trial){
		// TODO getArgNumber operation
		return 3; 
		
	}
	
	private List<String> getArgName(App trial){
		// TODO getArgName operation
		List<String> argName=new ArrayList<String>( );
		argName.add(0, "arg0");
		argName.add(1, "arg1");
		argName.add(2, "arg2");
		return argName;
	}
	
	private List<String> getArgType(App trial){
		// TODO getArgName operation
		List<String> argType=new ArrayList<String>( );
		argType.add(0, "String");
		argType.add(1, "String");
		argType.add(2, "String");
		return argType;
	}
	private String implement(App trial,int argno,String name,String type,String value) {
		String[] namearr=name.split(",");
		for (int i = 0; i < namearr.length; i++) {
			System.out.println(namearr[i]);
		}
		String[] valueList=value.split(",");
		// TODO implement operation
		String resultvalue="resultValue";
		
		return resultvalue;
	}
	private String monitoring(App trial){
		// TODO implement operation
		String monitorMesg="Monitoring...";
		return monitorMesg;
	}
}
