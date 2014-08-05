package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import cn.org.act.sdp.bpmnRepository.portal.config.Config;

public class UploadImage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploadImage() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// returnImage(request,response);
		saveImage2(request, response);
	}

	public void saveImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Upload image");
		String bpmnIdStr = request.getParameter("bpmnId");
		if (bpmnIdStr == null) {
			System.out.println("no bpmn id");
		} else
			System.out.println(bpmnIdStr);
		InputStream ris = request.getInputStream();
		byte[] b = new byte[1024];
		int rl;
		File file = new File("d:/a.jpg");
		FileOutputStream fos = new FileOutputStream(file);
		while ((rl = ris.read(b)) != -1) {
			fos.write(b, 0, rl);
			System.out.println(rl);
		}
		ris.close();
		fos.close();
	}

	public void returnImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Content-Length:", String.valueOf(request
				.getInputStream().available()));

		ServletOutputStream sos = response.getOutputStream();
		IOUtils.copy(request.getInputStream(), sos);
		sos.flush();
		sos.close();
	}

	public void saveImage2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// String fileName = "D://" + System.currentTimeMillis() + ".jpg";
		String fileName = System.currentTimeMillis() + ".jpg";
		BufferedImage bufferedImage = ImageIO.read(request.getInputStream());
		if (bufferedImage != null) {
			File file = new File(Config
					.getPath(Config.imgFolderPath + fileName));
			file.mkdirs();
			ImageIO.write(bufferedImage, "jpeg", file);
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
