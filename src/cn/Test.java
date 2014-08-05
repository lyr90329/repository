package cn;

import java.util.ArrayList;


import repository.atomicServices.ManageServiceClient;
import repository.atomicServices.ServiceInfo;

public class Test {
	public static void main(String[] args) throws Exception {
//		String remoteUri="http://192.168.3.206:8192/WSNameQuerybyID/";
//		String userName="gdl";
//		ManageServiceClient msc = new ManageServiceClient(remoteUri);
//		ArrayList<ServiceInfo> list = msc.queryServiceIDByUserName(userName);
//		for(ServiceInfo info:list){
//			System.out.println("serviceid:"+info.getId()+" servicename:"+info.getName());
//		}
		String a = "\"abc\"";
		System.out.println(a);
		System.out.println(a.replace("\"","\\\""));
		
	}
}
