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
 * 
 * @author Zhao Tao
 *
 * @param <E>
 */
public class StatisticInfo<E> {
	private E average;
	private E minimum;
	private E maximum;
	private E free;
	private E used;
	public E getAverage() {
		return average;
	}
	public void setAverage(E average) {
		this.average = average;
	}
	public E getMinimum() {
		return minimum;
	}
	public void setMinimum(E minimum) {
		this.minimum = minimum;
	}
	public E getMaximum() {
		return maximum;
	}
	public void setMaximum(E maximum) {
		this.maximum = maximum;
	}
	public E getFree() {
		return free;
	}
	public void setFree(E free) {
		this.free = free;
	}
	public E getUsed() {
		return used;
	}
	public void setUsed(E used) {
		this.used = used;
	}
}
