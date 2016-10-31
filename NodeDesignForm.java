package com.mycompany.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.management.openmbean.OpenDataException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jfree.chart.ChartPanel;
import org.jfree.ui.RefineryUtilities;
import org.jvnet.substance.SubstanceLookAndFeel;

import com.mycompany.logic.Open;
import com.mycompany.logic.ServerLogic;
import com.mycompany.utils.Constants;
import com.mycompany.utils.RandomDetails;
import com.mycompany.utils.SocketConnection;
import com.mycompany.vo.RequestVo;
import com.mycompany.workout.PackageBasedChart;
import com.mycompany.workout.TimeBasedChart;

public class NodeDesignForm extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RandomDetails randomDetails = new RandomDetails();
	private String nodeName;
	private String port;
        String mode = "";
	private Font font = new Font("algerian", Font.BOLD, 16);
	public JTextField jTextField;
	private JButton submitButton, openButton;
	private ServerLogic serverLogic;
	private SocketConnection socketConnection;
	private String sysIp;
	private JRadioButton jRadioButtonNormal, jRadioButtonAttack;
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

	public void design() throws UnknownHostException {
		sysIp = InetAddress.getLocalHost().getHostName();
		socketConnection = new SocketConnection();
		nodeName = randomDetails.getName();
		port = randomDetails.getPort();
		setTitle(nodeName);
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

		JLabel jLabelReq = new JLabel("File Request");
		jLabelReq.setBounds(10, 10, 120, 30);
		jLabelReq.setForeground(new Color(247, 13, 26));
		jLabelReq.setFont(font);
		jPanel.add(jLabelReq);

		jTextField = new JTextField();
		jTextField.setBounds(10, 60, 200, 30);
		jLabelReq.setBackground(new Color(250, 235, 215));
		jPanel.add(jTextField);

		submitButton = new JButton("Submit");
		submitButton.setBounds(10, 120, 100, 30);
		submitButton.setBackground(new Color(255, 222, 173));
		submitButton.addActionListener(this);
		jPanel.add(submitButton);

		openButton = new JButton("Play");
		openButton.setBounds(150, 120, 100, 30);
		openButton.setBackground(new Color(200, 129, 65));
		openButton.addActionListener(this);
		jPanel.add(openButton);

		jRadioButtonNormal = new JRadioButton("Distortion-Resistant", true);
		jRadioButtonNormal.setForeground(new Color(255, 140, 47 ));
		jRadioButtonAttack = new JRadioButton("Distortion");
		jRadioButtonAttack.setForeground(new Color(255, 140, 47 ));
		jRadioButtonNormal.setBounds(250, 60, 500, 30);
		jRadioButtonAttack.setBounds(450, 60, 100, 30);
		jPanel.add(jRadioButtonAttack);
		jPanel.add(jRadioButtonNormal);
		jRadioButtonAttack.addActionListener(this);
		jRadioButtonNormal.addActionListener(this);

		ChartPanel timeChartPanel = new ChartPanel(timeBasedChart.timeChart());
		timeChartPanel.setBounds(10, 300, 400, 300);
		jPanel.add(timeChartPanel);

		ChartPanel packageChartPanel = new ChartPanel(
				packageBasedChart.packageChart());
		packageChartPanel.setBounds(450, 300, 400, 300);
		jPanel.add(packageChartPanel);

		return jPanel;
	}

	public static void main(String[] args) throws UnknownHostException {
		NodeDesignForm nodeDesignForm = new NodeDesignForm();
		nodeDesignForm.design();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == submitButton) {
			String req = jTextField.getText();
			if (req != null && !req.equals("")) {
				serverLogic.timeMap=new TreeMap<String, Long>();
				serverLogic.slotMap=new TreeMap<String, Long>();
				

				if (jRadioButtonAttack.isSelected()) {
					mode = Constants.ATTACK;
				} else {
					mode = Constants.NORMAL;
				}
				String serverPort = randomDetails.getServerPort();
				String sysIp = randomDetails.getSysIp();
				ObjectOutputStream objectOutputStream = socketConnection
						.SocketSend(serverPort, sysIp);
				RequestVo requestVo = new RequestVo();
				requestVo.setNodeName(nodeName);
				requestVo.setFileName(req);
				requestVo.setPort(port);
				requestVo.setSysIp(sysIp);
				requestVo.setMode(mode);
				try {
					objectOutputStream.writeObject(requestVo);
					objectOutputStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				UIManager UI=new UIManager();
				 UI.put("OptionPane.background", new Color(238, 154, 7));
				 UI.put("Panel.background", new Color(238, 154, 77));
				JOptionPane.showMessageDialog(null, "Enter the file Name");
			}
			UIManager UI=new UIManager();
			 UI.put("OptionPane.background", new Color(238, 154, 77));
			 UI.put("Panel.background", new Color(238, 154, 77));
			JOptionPane.showMessageDialog(null, "Request sent to server ");
		} else if (e.getSource() == openButton) {

			// timeBasedChart.dataset.addValue(1.0, "11", "11");
                    if(mode.equalsIgnoreCase(Constants.ATTACK))
                    {
                        JOptionPane.showMessageDialog(this, "Video Distraction occured");
                    }
                    else
                    {
			String req = jTextField.getText();
			Open open = new Open("output/" + req);
                    }

		} else if (e.getSource() == jRadioButtonAttack) {
			jRadioButtonNormal.setSelected(false);
			jRadioButtonAttack.setSelected(true);
		} else if (e.getSource() == jRadioButtonNormal) {
			jRadioButtonNormal.setSelected(true);
			jRadioButtonAttack.setSelected(false);
		}
	}

}
