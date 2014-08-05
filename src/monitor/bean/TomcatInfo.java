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
 * Data structure for tomcat information
 * 
 * @author Zhao Tao
 *
 */
public class TomcatInfo {
	//tomcat running port
	private int port;
	//statistic information for tomcat memory
	private StatisticInfo<Long> memory;
	//statistic information for tomcat cpu usage
	private StatisticInfo<Double> cpu;
	//tomcat running time(s)
	private String runningTime;
	//all webapps' information
	private List<AppInfo> apps;
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
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
	
	public String getRunningTime() {
		return runningTime;
	}
	
	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}
	
	public List<AppInfo> getApps() {
		return apps;
	}
	
	public void setApps(List<AppInfo> apps) {
		this.apps = apps;
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public AppInfo getAppInfoById(String name){
		for(int i=0;i<apps.size();i++){
			AppInfo aInfo=apps.get(i);
			if(aInfo.getName().equals(name))
				return aInfo;
		}
		return null;
	}
}	
