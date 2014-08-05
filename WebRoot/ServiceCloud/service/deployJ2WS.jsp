<%@ page language="java"
	import="db.entity.UploadService,db.dao.ServiceDao,java.io.File,db.dao.Data,java.util.*,repository.atomicServices.ManageServiceClient,config.Config"
	pageEncoding="gb2312"%>
<%
	String rp = "";
	String fileName = (String) request.getParameter("fileName");
	String userName = (String) request.getParameter("userName");

	String absolutePath = Config.rootPath + File.separator + "j2ws"
			+ File.separator + fileName;

	if (!absolutePath.endsWith(".aar")) {
		absolutePath += ".aar";
	}

	ManageServiceClient msc1 = new ManageServiceClient(
			Config.getWebServiceDeployUrl());//部署反部署组件所在的位置。220是服务器
	ArrayList<String> address = new ArrayList<String>();
	String serviceLevel = "0.2";
	File toBeDeployed = new File(absolutePath);
	if (!toBeDeployed.exists()) {
		System.out.println("The file to be deployed is not found : "
				+ toBeDeployed.getAbsolutePath());
		rp = "Deploy failed: The service " + fileName
				+ " to be deployed is not found!";
		out.print(rp);
		return;
	}
	address = msc1.deployRemoteService(serviceLevel, toBeDeployed,
			userName);
	String falseInfo = "";
	for (int i = 0; i < address.size(); i++) {
		System.out.println("打印：" + address.get(i));
		if (address.get(0).equals("false")) {
			falseInfo = address.get(1);
			break;
		}
	}
	String deployId = address.get(0);
	String isSuccessful = address.get(1);
	String sandboxInfo = "";
	System.out.println("结果：" + isSuccessful);
	ServiceDao dao = new ServiceDao();
	if ("true".equals(isSuccessful)) {
		String basePath = "/home/sdp/uploadServices";
		dao.saveUploadService(new UploadService(userName, fileName,
				basePath + File.separator + userName + "&" + fileName
						+ ".aar", "", "", deployId));
		//request.getRequestDispatcher("../MyServiceContainer.jsp").forward(/request, response);
	}

	else if ("false".equals(deployId)) {
		sandboxInfo = address.get(1);
		System.out.println("sandbox信息：" + sandboxInfo);
		if (!"".equals(sandboxInfo))
			dao.updateSandBoxInfo(sandboxInfo);
		request.getRequestDispatcher("ServiceDeployException.jsp")
				.forward(request, response);
	}

	//File deleteFile = new File(absolutePath);//删除上传的文件
	//if (deleteFile.exists())
	//	deleteFile.delete();
	//System.out.println("上传文件已删除！");

	rp = "Deploy Service " + fileName + " Successfully!";

	out.print(rp);
	response.setHeader("Refresh", "1;URL=../MyServiceContainer.jsp");
%>
