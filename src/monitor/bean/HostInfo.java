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
package monitor.bean;

import java.util.List;

/**	
 * Data structure for host information
 * 
 * @author Zhao Tao
 */
public class HostInfo {
	//transform from ip for convenience
	private int id;
	//host ip address
	private String ip;
	//statistic information for host memory
	private StatisticInfo<Long> memory;
	//statistic information for host cpu usage
	private StatisticInfo<Double> cpu;
	//network send rate(KB/s)
	private double sendRate;
	//network receive rate(KB/s)
	private double receiveRate;
	//host start time
	private String startTime;
	//all disks' information
	private List<DiskInfo> disk;
	//all tomcats' information
	private List<TomcatInfo> tomcat;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public StatisticInfo<Long> getMemory() {
		return memory;
	}
	
	public void setMemory(StatisticInfo<Long> memory) {
		this.memory = memory;
	}
	
	public StatisticInfo<Double> getCpu() {
		return cpu;
	}
	public void setCpu(StatisticInfo<Double> cpu) {
		this.cpu = cpu;
	}

	public double getSendRate() {
		return sendRate;
	}
	
	public void setSendRate(double sendRate) {
		this.sendRate = sendRate;
	}
	
	public double getReceiveRate() {
		return receiveRate;
	}
	
	public void setReceiveRate(double receiveRate) {
		this.receiveRate = receiveRate;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public List<DiskInfo> getDisk() {
		return disk;
	}
	
	public void setDisk(List<DiskInfo> disk) {
		this.disk = disk;
	}
	
	public List<TomcatInfo> getTomcat() {
		return tomcat;
	}
	
	public void setTomcat(List<TomcatInfo> tomcat) {
		this.tomcat = tomcat;
	}
	/**
	 * 
	 * @param port 
	 * @return
	 */
	public TomcatInfo getTomcatInfoById(int port){
		for(int i=0;i<tomcat.size();i++){
			TomcatInfo tInfo=tomcat.get(i);
			if(tInfo.getPort()==port)
				return tInfo;
		}
		return null;
	}
}
