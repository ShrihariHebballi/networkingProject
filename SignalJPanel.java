package com.mycompany.logic;

import java.util.Random;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.mycompany.design.ServerDesignForm;

public class SignalJPanel extends JPanel implements Runnable {
	private TimeSeries series;
	private double lastValue = 0.0;
	public static double Packet = 0.0;
	public ValueAxis axis;
	Random random = new Random();
	ServerDesignForm serverDesignForm;
	public int value=0;
	public SignalJPanel(ServerDesignForm serverDesignForm) {
		this.serverDesignForm=serverDesignForm;
		this.series = new TimeSeries("Signal", Millisecond.class);
		final TimeSeriesCollection dataset = new TimeSeriesCollection(
				this.series);
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
		chartPanel.setPreferredSize(new java.awt.Dimension(700, 700));
		Thread t = new Thread(this);
		t.start();
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
				"Traffic Signal", "Time in Seconds", "S(n) Value", dataset,
				true, true, false);
		final XYPlot plot = result.getXYPlot();
		axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(900000.0); // 60 seconds
		axis = plot.getRangeAxis();
		axis.setRange(-10.0, 100);
		return result;
	}

	public void run() {
		synchronized (this) {
			for (;;) {
				try {
					/*if (intermediateServer.mode.equalsIgnoreCase("unblock")) {
						value = serverLogic.packetSize;
					}*/
					final double factor = 0.90 + (-0.2) * Math.random();
					this.lastValue = (factor * this.Packet);
					final Millisecond now = new Millisecond();
					final Second now1 = new Second();
					this.series.addOrUpdate(new Millisecond(), value);
					this.wait(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
