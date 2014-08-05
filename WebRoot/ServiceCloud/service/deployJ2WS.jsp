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
			Config.getWebServiceDeployUrl());//���𷴲���������ڵ�λ�á�220�Ƿ�����
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
		System.out.println("��ӡ��" + address.get(i));
		if (address.get(0).equals("false")) {
			falseInfo = address.get(1);
			break;
		}
	}
	String deployId = address.get(0);
	String isSuccessful = address.get(1);
	String sandboxInfo = "";
	System.out.println("�����" + isSuccessful);
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
		System.out.println("sandbox��Ϣ��" + sandboxInfo);
		if (!"".equals(sandboxInfo))
			dao.updateSandBoxInfo(sandboxInfo);
		request.getRequestDispatcher("ServiceDeployException.jsp")
				.forward(request, response);
	}

	//File deleteFile = new File(absolutePath);//ɾ���ϴ����ļ�
	//if (deleteFile.exists())
	//	deleteFile.delete();
	//System.out.println("�ϴ��ļ���ɾ����");

	rp = "Deploy Service " + fileName + " Successfully!";

	out.print(rp);
	response.setHeader("Refresh", "1;URL=../MyServiceContainer.jsp");
%>
