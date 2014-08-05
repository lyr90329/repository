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

/**
 * Data structure for webapp information
 * 
 * @author Zhao Tao
 */
public class AppInfo {
	//webapp name used as id
	private String name;
	//webapp physical deployed location
	private String location;
	//webapp deployer
	private String user;
	//statistic information for throughput
	private StatisticInfo<Long> throughput;
	//statistic information for cpu usage
	private StatisticInfo<Double> cpu;
	//statistic information for response time
	private StatisticInfo<Long> responseTime;
	//successfully invoke times
	private long successfull;
	//failure invoke times
	private long failed;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getSuccessfull() {
		return successfull;
	}
	
	public void setSuccessfull(long successfull) {
		this.successfull = successfull;
	}
	
	public long getFailed() {
		return failed;
	}
	
	public void setFailed(long failed) {
		this.failed = failed;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public StatisticInfo<Long> getThroughput() {
		return throughput;
	}
	
	public void setThroughput(StatisticInfo<Long> throughput) {
		this.throughput = throughput;
	}
	
	public StatisticInfo<Double> getCpu() {
		return cpu;
	}
	
	public void setCpu(StatisticInfo<Double> cpu) {
		this.cpu = cpu;
	}
	
	public StatisticInfo<Long> getResponseTime() {
		return responseTime;
	}
	
	public void setResponseTime(StatisticInfo<Long> responseTime) {
		this.responseTime = responseTime;
	}
}
