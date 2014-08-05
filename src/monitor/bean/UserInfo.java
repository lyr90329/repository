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
 * Data structure for User-oriented information
 * 
 * @author Zhao Tao
 *
 */
public class UserInfo {
	//user name
	private String name;
	//statistic information for all webapps' cpu usage
	private StatisticInfo<Double> cpu;
	//all webapps' information
	private List<AppInfo> apps;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public StatisticInfo<Double> getCpu() {
		return cpu;
	}
	
	public void setCpu(StatisticInfo<Double> cpu) {
		this.cpu = cpu;
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
