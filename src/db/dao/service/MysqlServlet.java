package db.dao.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MysqlServlet extends HttpServlet{
//	public void service(HttpServletRequest req,HttpServletResponse res)
//			throws ServletException,IOException{
//		doGet(req, res);
//	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException{
		
         PrintWriter out = res.getWriter();
         String username = req.getParameter("mysqlUser");
         String password = req.getParameter("mysqlPassword");

         String url=new GetClientMySQL(username,password).getMysqlUrl();
         req.setAttribute("url",url);      
         RequestDispatcher requestDispatcher=req.getRequestDispatcher("/ServiceCloud/mysqlUrl.jsp");   
         requestDispatcher.forward(req,res);
	}
}
