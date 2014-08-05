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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import monitor.bean.DiskInfo;
import monitor.bean.HostInfo;
import monitor.bean.MonitorInfo;
import monitor.util.DocParser;
import monitor.util.ViewChart;


/**
 * Servlet for disk chart request,
 * use host.id as parameter,
 * returns corresponding disk chart image
 * 
 * @author Zhao Tao
 *
 */
public class GetDiskInfo extends HttpServlet {

	private static final long serialVersionUID = -7309031821396843118L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("image/png");
		response.setDateHeader("Expires", 0);
		OutputStream out=response.getOutputStream();
		ViewChart vc = new ViewChart();
		DocParser parser=new DocParser();
		MonitorInfo host=parser.parse();
		HostInfo hInfo=host.getHostInfoById(Integer.parseInt(request.getParameter("hostId")));
		for(Iterator<DiskInfo> it=hInfo.getDisk().iterator();it.hasNext();){
			DiskInfo disk=it.next();
			if(disk.getName().equals(request.getParameter("name"))){
				String chartTitle="Disk "+disk.getName();
				double[] data={disk.getFree()*1.0/(1024*1024),disk.getUsed()*1.0/(1024*1024)};
				String[] keys = { "free", "used" };
				out.write(vc.makePieChart(data, chartTitle,keys));
				out.flush();
				out.close();
				return;
			}
		}
	}

}
