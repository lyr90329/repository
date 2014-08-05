package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;

import constant.MenuItems;
import db.dao.UserDao;
import db.entity.User;

public class MenuConf extends HttpServlet {
	public MenuConf() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String menuConf = request.getParameter("menu_conf");
		int conf = Integer.valueOf(menuConf);
		System.out.println(conf+"------11111111111111111");
		UserDao dao = new UserDao();
		User user = new User();

		String userName = null;
		HttpSession session = request.getSession();
		userName = (String) session.getAttribute("userName");
		user.setUserName(userName);
		conf |= MenuItems.confInt; 	//防止不可去掉的菜单项被恶意修改网页而去掉
		user.setConf(conf);
		System.out.println(MenuItems.confInt+"------------------");
		System.out.println(conf+"-----222222222222222");
		dao.update(user);
		request.getRequestDispatcher("/ServiceCloud/MyServiceContainer.jsp").forward(request, response);
	}
}
