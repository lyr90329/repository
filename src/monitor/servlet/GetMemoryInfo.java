/**
 * Service4All: A Service-oriented Cloud Platform for All about Software
 * Development Copyright (C) Institute of Advanced Computing Technology, Beihang
 * University Contact: service4all@act.buaa.edu.cn
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3.0 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package monitor.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import monitor.bean.HostInfo;
import monitor.bean.MonitorInfo;
import monitor.bean.StatisticInfo;
import monitor.bean.TomcatInfo;
import monitor.util.DocParser;
import monitor.util.ViewChart;


/**
 * Servlet for memory chart request,
 * use host.id,tomcat.port as parameter,
 * returns corresponding memory chart image
 * 
 * @author Zhao Tao
 * 
 */
public class GetMemoryInfo extends HttpServlet {

	private static final long serialVersionUID = 7068357346807745120L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("image/png");
		response.setDateHeader("Expires", 0);
		OutputStream out = response.getOutputStream();
		ViewChart vc = new ViewChart();
		String hostId = request.getParameter("hostId");
		String tomcatId = request.getParameter("tomcatId");
		DocParser parser=new DocParser();
		MonitorInfo host=parser.parse();
		double[][] data;
		String[] rowKeys = { "memory" };
		String[] columnKeys;
		String chartTitle = "Memory Info";
		String valueAxisLabel = "memory(MB)";
		byte[] pic = null;
		if (tomcatId != null) {
			TomcatInfo tInfo=host.getHostInfoById(Integer.parseInt(hostId)).getTomcatInfoById(Integer.parseInt(tomcatId));
			if(tInfo!=null){
				StatisticInfo<Long> memory=tInfo.getMemory();
				data = new double[][] { {
					memory.getAverage()*1.0 / (1024*1024),
					memory.getMinimum()*1.0 / (1024*1024),
					memory.getMaximum()*1.0 / (1024*1024),
					memory.getUsed()*1.0 / (1024*1024),
					memory.getFree()*1.0 / (1024*1024) } };
				columnKeys = new String[] { "avg", "min", "max", "used", "free" };
				pic = vc.makeBarChart(data, rowKeys, columnKeys, chartTitle,
						valueAxisLabel);
			}
		} else {
			HostInfo hInfo=host.getHostInfoById(Integer.parseInt(hostId));
			if(hInfo!=null){
				StatisticInfo<Long> memory=hInfo.getMemory();
				data = new double[][] { {
					memory.getFree()*1.0 / (1024),
					memory.getUsed()*1.0 / (1024) } };
				columnKeys = new String[] { "free", "used" };
				pic = vc.makeBarChart(data, rowKeys, columnKeys, chartTitle,
						valueAxisLabel);
			}
		}
		out.write(pic);
		out.flush();
		out.close();
	}
}
