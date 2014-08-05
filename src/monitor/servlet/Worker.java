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


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import monitor.bean.MultiLineChart;
import monitor.util.ReceiverThread;

/**
 * Servlet for timer task of administrator-oriented message receive and storage,
 * creates and maintains CPU and network chart map,
 * load on startup
 * @author Zhao Tao
 *
 */
public class Worker extends HttpServlet {
	
	private static final long serialVersionUID = -9200377209325298374L;
	public static Map<String,MultiLineChart> hostChart;
	public static Map<String,MultiLineChart> tomcatChart;
	public static Map<String,MultiLineChart> appChart;
	public static Map<String,MultiLineChart> netChart;
	
	public void init() throws ServletException {
		hostChart=new HashMap<String,MultiLineChart>();
		tomcatChart=new HashMap<String, MultiLineChart>();
		appChart=new HashMap<String, MultiLineChart>();
		netChart=new HashMap<String, MultiLineChart>();
		Timer timer=new Timer();
		timer.schedule(new ReceiverThread(timer),0L,2000L);
		System.out.println("worker started!");
	}

}
