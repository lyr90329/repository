package cn.org.act.sdp.bpmnRepository.portal.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Return {
	static public void error(HttpServletRequest request,
			HttpServletResponse response, String msg) throws ServletException,
			IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("error.jsp").forward(request, response);
	}

	static public void login(HttpServletRequest request,
			HttpServletResponse response, String msg) throws ServletException,
			IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("login_register.jsp").forward(request,
				response);
	}

	static public void print(HttpServletResponse response, String msg)
			throws IOException {
		PrintWriter pw = response.getWriter();
		pw.write(msg);

		pw.flush();
		pw.close();
	}

	static public void print(HttpServletResponse response, byte[] b)
			throws IOException {
		ServletOutputStream os = response.getOutputStream();
		if (b != null) {
			os.write(b);
		}
		os.flush();
		os.close();
	}
}
