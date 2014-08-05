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
package monitor.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import monitor.bean.AppInfo;
import monitor.bean.HostInfo;
import monitor.bean.MonitorInfo;
import monitor.bean.MultiLineChart;
import monitor.bean.StatisticInfo;
import monitor.bean.TomcatInfo;
import monitor.servlet.Worker;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.jfree.data.time.Second;

/**
 * Thread for getting and saving administrator-oriented message,
 * adding datas to corresponding multi-line chart
 * 
 * @author Zhao Tao
 *
 */
public class ReceiverThread extends TimerTask {
	
	public static String tmpFile;
	private DocParser parser;
	private Timer timer;
	static{
		tmpFile = System.getProperty("user.dir");
		if (tmpFile.endsWith("bin"))
		{
			tmpFile = tmpFile.substring(0, tmpFile.lastIndexOf('/'));
		}
		tmpFile += "/webapps/repository/mctmp";
		File dir=new File(tmpFile);
		if(!dir.exists())
			dir.mkdir();
		tmpFile+="/temp.xml";
	}
	public ReceiverThread(Timer timer){
		parser=new DocParser();
		this.timer=timer;
	}
	@Override
	public void run() {
		try {
			OMElement doc=parser.getDoc(Constant.RESOURCE_METHOD,null);
			MonitorInfo host=parser.parse(doc);
			if(host!=null&&host.getHost().size()!=0){
				Map<String,MultiLineChart> hostChart=Worker.hostChart;
				Map<String,MultiLineChart> tomcatChart=Worker.tomcatChart;
				Map<String,MultiLineChart> appChart=Worker.appChart;
				Map<String,MultiLineChart> netChart=Worker.netChart;
				for(Iterator<HostInfo> hostIt=host.getHost().iterator();hostIt.hasNext();){
					HostInfo hInfo=hostIt.next();
					String hostId=String.valueOf(hInfo.getId());
					Second now=new Second();
					MultiLineChart nchart=null;
					if(netChart.containsKey(hostId))
						nchart=netChart.get(hostId);
					else{
						nchart=new MultiLineChart(new String[]{"sendrate","receiverate"},"Network Info","rate:KB/s",0);
						netChart.put(hostId, nchart);
					}
					nchart.getDatas()[0].addOrUpdate(now,hInfo.getSendRate());
					nchart.getDatas()[1].addOrUpdate(now,hInfo.getReceiveRate());
					MultiLineChart hchart=null;
					if(hostChart.containsKey(hostId))
						hchart=hostChart.get(hostId);
					else {
						hchart=new MultiLineChart(new String[]{"current","avg","max","min"},"CPU Info","cpu percentage",100D);
						hostChart.put(hostId, hchart);
					}
					StatisticInfo<Double> hcpuInfo=hInfo.getCpu();
					hchart.getDatas()[0].addOrUpdate(now,hcpuInfo.getUsed());
					hchart.getDatas()[1].addOrUpdate(now, hcpuInfo.getAverage());
					hchart.getDatas()[2].addOrUpdate(now,hcpuInfo.getMaximum());
					hchart.getDatas()[3].addOrUpdate(now,hcpuInfo.getMinimum());
					if(hInfo.getTomcat().size()!=0){
						for(Iterator<TomcatInfo> tomcatIt=hInfo.getTomcat().iterator();tomcatIt.hasNext();){
							TomcatInfo tInfo=tomcatIt.next();
							String tomcatId=hostId+tInfo.getPort();
							MultiLineChart tchart=null;
							if(tomcatChart.containsKey(tomcatId))
								tchart=tomcatChart.get(tomcatId);
							else {
								tchart=new MultiLineChart(new String[]{"current","avg","max","min"},"CPU Info","cpu percentage",100D);
								tomcatChart.put(tomcatId, tchart);
							}
							StatisticInfo<Double> tcpuInfo=tInfo.getCpu();
							tchart.getDatas()[0].addOrUpdate(now,tcpuInfo.getUsed());
							tchart.getDatas()[1].addOrUpdate(now, tcpuInfo.getAverage());
							tchart.getDatas()[2].addOrUpdate(now,tcpuInfo.getMaximum());
							tchart.getDatas()[3].addOrUpdate(now,tcpuInfo.getMinimum());
							if(tInfo.getApps().size()!=0){
								for(Iterator<AppInfo> appIt=tInfo.getApps().iterator();appIt.hasNext();){
									AppInfo aInfo=appIt.next();
									String appId=tomcatId+aInfo.getName();
									MultiLineChart achart=null;
									if(appChart.containsKey(appId))
										achart=appChart.get(appId);
									else {
										achart=new MultiLineChart(new String[]{"current","avg","max","min"},"CPU Info","cpu percentage",100D);
										appChart.put(appId, achart);
									}
									StatisticInfo<Double> acpuInfo=aInfo.getCpu();
									achart.getDatas()[0].addOrUpdate(now,acpuInfo.getUsed());
									achart.getDatas()[1].addOrUpdate(now, acpuInfo.getAverage());
									achart.getDatas()[2].addOrUpdate(now,acpuInfo.getMaximum());
									achart.getDatas()[3].addOrUpdate(now,acpuInfo.getMinimum());
								}
							}
						}
					}
				}
				OMFactory factory = OMAbstractFactory.getOMFactory();
				OMDocument document = factory.createOMDocument();
				document.addChild(doc);
				document.serializeAndConsume(new FileOutputStream(tmpFile));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(parser.getFailueTime()>9){
				timer.cancel();
				System.out.println("Monitor thread has been cancelled!");
			}
		}
	}
	
}
