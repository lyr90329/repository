package cn.org.act.sdp.bpmnRepository.portal.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.entity.UserBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;

public class LoginCheck {
	static public boolean isLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", Message.MSG_NO_LOGIN);
			request.getRequestDispatcher("login_register.jsp").forward(request,
					response);
			return false;
		} else {
			return true;
		}
	}
}
