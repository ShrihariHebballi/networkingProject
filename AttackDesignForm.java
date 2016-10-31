package com.mycompany.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jvnet.substance.SubstanceLookAndFeel;

import com.mycompany.logic.ServerLogic;
import com.mycompany.utils.RandomDetails;
import com.mycompany.workout.PackageBasedChart;
import com.mycompany.workout.TimeBasedChart;

public class AttackDesignForm extends JFrame {
	private RandomDetails randomDetails;
	private String sysIp;
	private String port;
	private ServerLogic serverLogic;
	public DefaultTableModel defaultTableModel;
	private JTable jTable;
	private Font font = new Font("algeria", Font.BOLD, 16);
	public TimeBasedChart timeBasedChart = new TimeBasedChart();
	public PackageBasedChart packageBasedChart = new PackageBasedChart();
//	static {
//		try {
//			SubstanceLookAndFeel
//					.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBinaryWatermark");
//			SubstanceLookAndFeel
//					.setCurrentTheme("org.jvnet.substance.theme.SubstanceInvertedTheme");
//			SubstanceLookAndFeel
//					.setCurrentGradientPainter("org.jvnet.substance.painter.SpecularGradientPainter");
//			SubstanceLookAndFeel
//					.setCurrentButtonShaper("org.jvnet.substance.button.ClassicButtonShaper");
//			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	public void design() {
		randomDetails = new RandomDetails();
		sysIp = randomDetails.getAttackIp();
		port = randomDetails.getAttackPort();
		setTitle("Attacker");
		add(mainPanel());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();
		screenSize.height = screenSize.height / 2;
		screenSize.width = screenSize.width / 2;
		size.height = size.height / 2;
		size.width = size.width / 2;
		int y = screenSize.height - size.height;
		int x = screenSize.width - size.width;
		setLocation(300, y);
		setSize(900, 700);
		setVisible(true);
		serverLogic = new ServerLogic(Integer.parseInt(port), this);

	}

	public JPanel mainPanel() {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		jPanel.setBackground(new Color(0, 0, 0));

		JLabel jLabel=new JLabel("File Details");
		jLabel.setFont(font);
		jLabel.setBounds(50, 20, 100, 30);
		jLabel.setForeground (new Color(247, 13, 26));
		jPanel.add(jLabel);
		
		defaultTableModel = new DefaultTableModel();
		defaultTableModel.addColumn("Node Name");
		defaultTableModel.addColumn("File Name");
		defaultTableModel.addColumn("Status");
		jTable = new JTable(defaultTableModel);
		JScrollPane jScrollPane = new JScrollPane(jTable);
		jScrollPane.setBounds(50, 80, 300, 200);
		jScrollPane.setBackground(new Color(238, 154, 77));
		jPanel.add(jScrollPane);

		ChartPanel timeChartPanel = new ChartPanel(timeBasedChart.timeChart());
		timeChartPanel.setBounds(10, 300, 400, 300);
		jScrollPane.setBackground(new Color(238, 154, 77));
		jPanel.add(timeChartPanel);

		ChartPanel packageChartPanel = new ChartPanel(
				packageBasedChart.packageChart());
		packageChartPanel.setBounds(450, 300, 400, 300);
		jPanel.add(packageChartPanel);
		
		return jPanel;
	}

	public static void main(String[] args) {
		AttackDesignForm attackDesignForm = new AttackDesignForm();
		attackDesignForm.design();
	}
}
