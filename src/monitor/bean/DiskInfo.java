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
 * @author Zhao Tao
 *	Data structure for disk information
 */
public class DiskInfo {
	//disk name,eg. C:\
	private String name;
	//used disk capacity(KB)
	private long used;
	//free disk capacity(KB)
	private long free;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getUsed() {
		return used;
	}
	
	public void setUsed(long used) {
		this.used = used;
	}
	
	public long getFree() {
		return free;
	}
	
	public void setFree(long free) {
		this.free = free;
	}
	
}
