/**
 * Service4All: A Service-oriented Cloud Platform for All about Software Development
 * Copyright (C) Institute of Advanced Computing Technology, Beihang University
 * Contact: service4all@act.buaa.edu.cn
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or any later version. 
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details. 
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA

 */
package monitor.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import monitor.bean.MultiLineChart;

import org.jfree.chart.ChartUtilities;

/**
 * Servlet for CPU chart request,
 * use host.id,tomcat.port,app.name as parameter,
 * returns corresponding CPU chart image
 * 
 * @author Zhao Tao
 * 
 */
public class GetCpuInfo extends HttpServlet {

	private static final long serialVersionUID = -3844101849249219518L;
	//host cpu chart map,use host.id as key
	private Map<String, MultiLineChart> hostChart;
	//tomcat cpu chart map,use host.id+tomcat.port as key
	private Map<String, MultiLineChart> tomcatChart;
	//webapp cpu chart map,use host.id+tomcat.port+app.name as key
	private Map<String, MultiLineChart> appChart;

	@Override
	public void init() throws ServletException {
		hostChart = Worker.hostChart;
		tomcatChart = Worker.tomcatChart;
		appChart = Worker.appChart;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/png");
		response.setDateHeader("Expires", 0);
		OutputStream out = response.getOutputStream();
		String hostId = request.getParameter("hostId");
		String tomcatId = request.getParameter("tomcatId");
		String appId = request.getParameter("appId");
		if (appId != null) {
			String key = hostId + tomcatId + appId;
			MultiLineChart chart = appChart.get(key);
			if (chart != null) {
				BufferedImage bi = chart.getChart().createBufferedImage(600,
						300);
				out.write(ChartUtilities.encodeAsPNG(bi));
				out.flush();
				out.close();
			}
			return;
		} else if (tomcatId != null) {
			String key = hostId + tomcatId;
			MultiLineChart chart = tomcatChart.get(key);
			if (chart != null) {
				BufferedImage bi = chart.getChart().createBufferedImage(600,
						300);
				out.write(ChartUtilities.encodeAsPNG(bi));
				out.flush();
				out.close();
			}
			return;
		} else {
			MultiLineChart chart = hostChart.get(hostId);
			if (chart != null) {
				BufferedImage bi = chart.getChart().createBufferedImage(600,
						300);
				out.write(ChartUtilities.encodeAsPNG(bi));
				out.flush();
				out.close();
			}
			return;
		}
	}
}
