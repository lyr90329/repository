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
 * Data structure for Administrator-oriented information
 * 
 * @author Zhao Tao
 */
public class MonitorInfo {
	//all hosts' informaton
	private List<HostInfo> host;

	public List<HostInfo> getHost() {
		return host;
	}

	public void setHost(List<HostInfo> host) {
		this.host = host;
	}
	
	public HostInfo getHostInfoById(int id){
		for(int i=0;i<host.size();i++){
			HostInfo hInfo=host.get(i);
			if(hInfo.getId()==id)
				return hInfo;
		}
		return null;
	}
	
	public HostInfo getFirstHostInfo(){
		try {
			return host.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
