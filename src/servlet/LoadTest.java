package servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.axiom.om.OMElement;

import config.Config;
import repository.loadTester.LoadTesterClient;

public class LoadTest extends HttpServlet {

	public void init() throws ServletException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out
				.println("enter Servlet LoadTest!!!----------------------------------------------------------------");
		String soap = (String) request.getParameter("soap");
		System.out.println(soap);
		String operation = (String) request.getParameter("operation");
		System.out.println(operation);
		String obj_url = (String) request.getParameter("obj_url");
		System.out.println(obj_url);
		String test_num = (String) request.getParameter("test_num");
		System.out.println(test_num);
		String strategy = (String) request.getParameter("strategy");
		String accuracy = (String) request.getParameter("accuracy");
		String timeout = (String) request.getParameter("timeout");
		String value = (String) request.getParameter("value");
		String num = (String) request.getParameter("num");
		String beginnum = (String) request.getParameter("beginnum");
		String endnum = (String) request.getParameter("endnum");
		String stepnum = (String) request.getParameter("stepnum");
		String para = (String) request.getParameter("para");
		System.out.println(para);

		para = para.substring(0, para.length() - 1);
		String[] paralist = para.split(";");

		LoadTesterClient client = new LoadTesterClient(strategy, test_num);

		client.setAccuracy(accuracy);
		client.setExcept_body(value);
		client.setTimeout(timeout);
		client.setNum(Integer.valueOf(num));
		client.setStartNum(Integer.valueOf(beginnum));
		client.setEndNum(Integer.valueOf(endnum));
		client.setStep(Integer.valueOf(stepnum));

		obj_url = obj_url.substring(0, obj_url.length() - 5);

		ArrayList<String> iplist = new ArrayList<String>();
		for (String ip : paralist) {
			System.out.println("测试结果IP：" + ip);
			iplist.add(ip);
		}

		try {
			OMElement insoap = client.String2OMElement(soap, "utf-8");
			OMElement rs = client.invoke(iplist, "external", obj_url,
					operation, insoap,
					Config.getCloudTestUrl()+"TestJobDeployment/");
			System.out.println("测试结果：" + rs);

		} catch (Exception e) {
			System.out.println("there are something wrong!");
			e.printStackTrace();
		}
	}
}