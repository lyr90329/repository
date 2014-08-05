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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import monitor.bean.AppInfo;
import monitor.bean.MultiLineChart;
import monitor.bean.StatisticInfo;
import monitor.bean.UserInfo;
import monitor.util.DocParser;

import org.jfree.chart.ChartUtilities;
import org.jfree.data.time.Second;

/**
 * Servlet for user-oriented CPU chart request,
 * use app.name as parameter,
 * returns corresponding CPU chart image
 * @author Zhao Tao
 *
 */
public class GetUserCpuInfo extends HttpServlet {

	private static final long serialVersionUID = -8812453350429258650L;
	
	private Map<String,MultiLineChart> chartMap;
	@Override
	public void init() throws ServletException {
		chartMap=new HashMap<String,MultiLineChart>();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/png");
		response.setDateHeader("Expires", 0);
		OutputStream out=response.getOutputStream();
		DocParser parser=new DocParser();
		UserInfo userInfo=parser.parseUser((String)request.getSession().getAttribute("userName"));
		String appId=request.getParameter("appId");
		if(appId!=null){
			AppInfo aInfo=userInfo.getAppInfoById(appId);
			if(aInfo!=null){
				MultiLineChart chart=null;
				if(chartMap.containsKey(appId))
					chart=chartMap.get(appId);
				else {
					chart=new MultiLineChart(new String[]{"current","avg","max","min"},"CPU Info","cpu percentage",100D);
					chartMap.put(appId, chart);
				}
				StatisticInfo<Double> cpuInfo=aInfo.getCpu();
				Second now=new Second();
				chart.getDatas()[0].addOrUpdate(now, cpuInfo.getUsed());
				chart.getDatas()[1].addOrUpdate(now, cpuInfo.getAverage());
				chart.getDatas()[2].addOrUpdate(now, cpuInfo.getMaximum());
				chart.getDatas()[3].addOrUpdate(now, cpuInfo.getMinimum());
				BufferedImage bi=chart.getChart().createBufferedImage(600, 300);
				out.write(ChartUtilities.encodeAsPNG(bi));
				out.flush();
				out.close();
				return;
			}
		}
		else{
			MultiLineChart chart=null;
			if(chartMap.containsKey("_TOTAL"))
				chart=chartMap.get("_TOTAL");
			else {
				chart=new MultiLineChart(new String[]{"current","avg","max","min"},"CPU Info","cpu percentage",100D);
				chartMap.put("_TOTAL", chart);
			}
			StatisticInfo<Double> cpuInfo=userInfo.getCpu();
			Second now=new Second();
			chart.getDatas()[0].addOrUpdate(now, cpuInfo.getUsed());
			chart.getDatas()[1].addOrUpdate(now, cpuInfo.getAverage());
			chart.getDatas()[2].addOrUpdate(now, cpuInfo.getMaximum());
			chart.getDatas()[3].addOrUpdate(now,cpuInfo.getMinimum());
			BufferedImage bi=chart.getChart().createBufferedImage(600, 300);
			out.write(ChartUtilities.encodeAsPNG(bi));
			out.flush();
			out.close();
			return;
		}
	}

}
