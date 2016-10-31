package com.mycompany.workout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class PackageBasedChart extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	public JFreeChart packageChart() {
		final CategoryDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);

		// chartPanel.setPreferredSize(new Dimension(500, 270));
		return chart;
	}

	private CategoryDataset createDataset() {
		final String series1 = "First";
		final String series2 = "Second";
		final String series3 = "Third";
		final String category1 = "Category 1";
		final String category2 = "Category 2";
		final String category3 = "Category 3";
		final String category4 = "Category 4";
		final String category5 = "Category 5";

		/*
		 * dataset.addValue(1.0, series1, category1); dataset.addValue(4.0,
		 * series1, category2); dataset.addValue(3.0, series1, category3);
		 * dataset.addValue(5.0, series1, category4); dataset.addValue(5.0,
		 * series1, category5);
		 * 
		 * dataset.addValue(5.0, series2, category1); dataset.addValue(7.0,
		 * series2, category2); dataset.addValue(6.0, series2, category3);
		 * dataset.addValue(8.0, series2, category4); dataset.addValue(4.0,
		 * series2, category5);
		 * 
		 * dataset.addValue(4.0, series3, category1); dataset.addValue(3.0,
		 * series3, category2); dataset.addValue(2.0, series3, category3);
		 * dataset.addValue(3.0, series3, category4); dataset.addValue(6.0,
		 * series3, category5);
		 */

		return dataset;

	}

	private JFreeChart createChart(final CategoryDataset dataset) {
		final JFreeChart chart = ChartFactory.createBarChart("Package Series", // chart
																				// title
				"Slots", // domain axis label
				"Traffic", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);
		chart.setBackgroundPaint(Color.white);
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		final GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
				0.0f, 0.0f, Color.lightGray);
		final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green,
				0.0f, 0.0f, Color.lightGray);
		final GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,
				0.0f, 0.0f, Color.lightGray);
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 6.0));
		return chart;
	}
}
