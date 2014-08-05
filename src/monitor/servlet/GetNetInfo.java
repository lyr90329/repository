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
 * Servlet for network chart request,
 * use host.id as parameter,
 * returns network chart image
 * 
 * @author Zhao Tao
 *
 */
public class GetNetInfo extends HttpServlet {
	
	
	private static final long serialVersionUID = 4933247274596234985L;
	//network chart map,use host.id as key
	private Map<String,MultiLineChart> chart;
	
	@Override
	public void init() throws ServletException {
		chart=Worker.netChart;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/png");
		response.setDateHeader("Expires", 0);
		OutputStream out=response.getOutputStream();
		String hostId=request.getParameter("hostId");
		MultiLineChart netChart=chart.get(hostId);
		if(netChart!=null){
			BufferedImage bi=netChart.getChart().createBufferedImage(600, 300);
			out.write(ChartUtilities.encodeAsPNG(bi));
			out.flush();
			out.close();
		}
	}

}
