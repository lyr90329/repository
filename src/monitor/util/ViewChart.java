/**
 * Service4All: A Service-oriented Cloud Platform for All about Software
 * Development Copyright (C) Institute of Advanced Computing Technology, Beihang
 * University Contact: service4all@act.buaa.edu.cn
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3.0 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package monitor.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

/**
 * Make bar,pie,line chart
 * 
 * @author Zhao Tao
 * 
 */
public class ViewChart {

	public byte[] makeBarChart(double[][] data, String[] rowKeys,
			String[] columnKeys,String chartTitle,String valueAxisLabel) throws IOException {
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);
		JFreeChart chart = ChartFactory.createBarChart(chartTitle, "",
				valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, false,
				false);
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.white);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.gray);
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		DecimalFormat df = new DecimalFormat("0.0");
		vn.setNumberFormatOverride(df);
		// x-axis
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);
		domainAxis.setTickLabelFont(labelFont);
		domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);
		domainAxis.setLowerMargin(0.1);
		domainAxis.setUpperMargin(0.1);
		plot.setDomainAxis(domainAxis);
		plot.setBackgroundPaint(Color.lightGray);
		plot.setRangeGridlinePaint(new Color(255, 255, 255));
		// y-axis
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(labelFont);
		rangeAxis.setTickLabelFont(labelFont);
		rangeAxis.setUpperMargin(0.15);
		rangeAxis.setLowerMargin(0.15);
		plot.setRangeAxis(rangeAxis);
		BarRenderer renderer = new BarRenderer();
		renderer.setMaximumBarWidth(0.1);
		renderer.setMinimumBarLength(0.2);
		renderer.setBaseOutlinePaint(Color.BLACK);
		renderer.setDrawBarOutline(true);
		renderer.setSeriesPaint(0, new Color(0, 0, 205));
		renderer.setSeriesPaint(1, new Color(153, 204, 255));
		renderer.setSeriesPaint(2, new Color(51, 204, 204));
		renderer.setItemMargin(0.0);
		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		plot.setRenderer(renderer);
		plot.setForegroundAlpha(1.0f);
		BufferedImage bi = chart.createBufferedImage(600, 300);
		return ChartUtilities.encodeAsPNG(bi);
	}
	
	@SuppressWarnings("deprecation")
	public static JFreeChart makeLineChart(TimeSeries[] timeSeries,String chartTitle,String xlabel,String ylabel,double maxRange) throws IOException {
		TimeSeriesCollection timeseriescollection=new TimeSeriesCollection();
		for(int i=0;i<timeSeries.length;i++)
			timeseriescollection.addSeries(timeSeries[i]);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(chartTitle, xlabel,
				ylabel, timeseriescollection, true, false, false);
		chart.setBackgroundPaint(Color.white);
		XYPlot xyplot = (XYPlot) chart.getPlot(); 
		xyplot.setRangeGridlinesVisible(true); 
		xyplot.setRangeGridlinePaint(Color.white);
		xyplot.setDomainGridlinesVisible(true);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 0D)); 
		xyplot.setBackgroundPaint(Color.lightGray); 
		DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
		dateaxis.setAxisLineVisible(true);
		dateaxis.setTickMarksVisible(true);
		dateaxis.setAutoTickUnitSelection(false);
		dateaxis.setFixedAutoRange(60000L);
		DateFormat dateFormat = new SimpleDateFormat("");
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.SECOND, 2,
				dateFormat));
		dateaxis.setDateFormatOverride(dateFormat);
		NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
		numberaxis.setAxisLineVisible(true);
		numberaxis.setTickMarksVisible(true);
		if(maxRange>0){
			numberaxis.setRange(0D, maxRange);
			numberaxis.setAutoTickUnitSelection(false);
			numberaxis.setTickUnit(new NumberTickUnit(10D));
			numberaxis.setAutoRangeMinimumSize(10D);
		}
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot
				.getRenderer();
		xylineandshaperenderer.setSeriesFillPaint(0, new Color(255, 69, 0));  
		xylineandshaperenderer.setSeriesPaint(0, new Color(255, 69, 0));  
		xylineandshaperenderer.setSeriesFillPaint(1, new Color(50, 205, 50));  
		xylineandshaperenderer.setSeriesPaint(1, new Color(50, 205, 50));
		xylineandshaperenderer.setSeriesFillPaint(2, new Color(0,102,255));
		xylineandshaperenderer.setSeriesPaint(2, new Color(0,102,255));
		xylineandshaperenderer.setSeriesFillPaint(3, new Color(255,255,51));
		xylineandshaperenderer.setSeriesPaint(3, new Color(255,255,51));
		XYItemRenderer xyitem = xyplot.getRenderer();
		xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		return chart;
	}

	public byte[] makePieChart(double[] data,String chartTitle,String[] keys) throws IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < data.length; i++) {
			dataset.setValue(keys[i], data[i]);
		}
		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, dataset,// data
				true,// include legend
				true, false);
		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.white);
		Font font = new Font("宋体", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}={1}GB({2})", new DecimalFormat("0.0"),
				new DecimalFormat("0.0%")));
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}:{1}GB({2})", new DecimalFormat("0.0"),
				new DecimalFormat("0.0%")));
		plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));
		plot.setForegroundAlpha(0.3f);
		plot.setCircular(false, true);
		plot.setStartAngle(90);
		plot.setSectionPaint(keys[0], new Color(50, 205, 50));
		plot.setSectionPaint(keys[1], new Color(255, 69, 0));
		BufferedImage bi=chart.createBufferedImage(600, 300);
		return ChartUtilities.encodeAsPNG(bi);
	}
}
