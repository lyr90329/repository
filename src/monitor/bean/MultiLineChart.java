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

import java.io.IOException;

import monitor.util.ViewChart;

import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;

/**
 * Create and modify a line chart including multiple broken lines.
 * Mainly be used to create cpu usage line chart,including current,average,maximum,minimum information.
 * 
 * @author Zhao Tao
 */
public class MultiLineChart {
	//maximum point number to show
	private int count=31;
	//the main chart
	private JFreeChart chart;
	//collections of data
	private TimeSeries[] datas;
	/**
	 * 
	 * @param dataNames data collections' names
	 * @param chartTitle multi-line chart title
	 * @param yLabel y-axis title
	 * @param maxRange y-axis max range
	 */
	public MultiLineChart(String[] dataNames,String chartTitle,String yLabel,double maxRange){
		datas=new TimeSeries[dataNames.length];
		for(int i=0;i<dataNames.length;i++){
			datas[i]=new TimeSeries(dataNames[i]);
			datas[i].setMaximumItemCount(count);
		}
		try {
			chart=ViewChart.makeLineChart(datas,chartTitle,"Time Interval: 2s",yLabel,maxRange);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JFreeChart getChart() {
		return chart;
	}
	
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	
	public TimeSeries[] getDatas() {
		return datas;
	}
	/**
	 * JFreeChart will automatically save previous data,
	 * just put the newly data into corresponding TimeSeries
	 * 
	 * @param datas
	 */
	public void setDatas(TimeSeries[] datas) {
		this.datas = datas;
	}
	
}
