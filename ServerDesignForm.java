package com.mycompany.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import com.mycompany.logic.ServerLogic;
import com.mycompany.logic.SignalJPanel;
import com.mycompany.utils.Constants;
import com.mycompany.utils.RandomDetails;
import com.mycompany.utils.SocketConnection;
import com.mycompany.vo.ResponseVo;

public class ServerDesignForm extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane jTabbedPane;
	private JList<String> movieList;
	private DefaultListModel<String> defaultListModel;
	private Font font = new Font("algerian", Font.BOLD, 16);
	private JTable jTableReq;
	public DefaultTableModel defaultTableModel;
	private JScrollPane jScrollPane;
	private SignalJPanel signalJPanel;
	private RandomDetails randomDetails;
	private String serverPort;
	private ServerLogic serverLogic;
	private JButton chunkButton, sendButton, detectButton, checkButton;
	private String chunk;
	String clientNode;
	private SocketConnection socketConnection;
	private Vector<byte[]> content;
	private String fileName;
	int bandwidth = 0;
	private Random rand = new Random();
	private JRadioButton timeBasedRadio, packetbasedRadio;
	public DefaultTableModel defaultTableTime, defaultTableSlot;
	public JTable jTableTime, jTableSlot;
	public ArrayList<String> maliciousList = new ArrayList<String>();
	public JList<String> jListMalicious;
	public DefaultListModel defListModel;
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
		try {
			socketConnection = new SocketConnection();
			signalJPanel = new SignalJPanel(this);
			Thread t = new Thread(signalJPanel);
			t.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		randomDetails = new RandomDetails();
		serverPort = randomDetails.getServerPort();
		serverLogic = new ServerLogic(Integer.parseInt(serverPort), this);
		setTitle("Server");
		setLayout(null);
		jTabbedPane = new JTabbedPane();
		jTabbedPane.addTab("Main", mainPanel());
		jTabbedPane.addTab("Traffic", signalJPanel);
		jTabbedPane.setBackground(new Color(252, 223, 255));
		jTabbedPane.setBounds(0, 0, 900, 700);
		add(jTabbedPane);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();
		screenSize.height = screenSize.height / 2;
		screenSize.width = screenSize.width / 2;
		size.height = size.height / 2;
		size.width = size.width / 2;
		int y = screenSize.height - size.height;
		setLocation(300, y);
		setSize(900, 700);
		setVisible(true);
	}

	public JPanel mainPanel() {
		JPanel jpanel = new JPanel();
		jpanel.setLayout(null);
		jpanel.setBackground(new Color(20, 15, 6));

		JLabel contentLabel = new JLabel("Content ::");
		contentLabel.setBounds(600, 10, 100, 30);
		contentLabel.setForeground(new Color(247, 13, 26));
		contentLabel.setFont(font);
		jpanel.add(contentLabel);

		defaultListModel = new DefaultListModel<String>();
		movieList = new JList<String>(defaultListModel);
		JScrollPane jScrollPane = new JScrollPane(movieList);
		jScrollPane.setBounds(600, 60, 200, 100);
		jpanel.add(jScrollPane);

		JLabel jLabelReq = new JLabel("Request Details");
		jLabelReq.setFont(font);
		jLabelReq.setForeground(new Color(247, 13, 26));
		jLabelReq.setBounds(10, 10, 200, 30);
		jpanel.add(jLabelReq);

		packetbasedRadio = new JRadioButton("Packet Based", true);
		packetbasedRadio.setForeground(new Color(255, 166, 47 ));
		packetbasedRadio.setBounds(210, 10, 200, 30);
		jpanel.add(packetbasedRadio);
		packetbasedRadio.addActionListener(this);

		timeBasedRadio = new JRadioButton("Time Based");
		timeBasedRadio.setForeground(new Color(255, 166, 47 ));
		timeBasedRadio.setBounds(410, 10, 200, 30);
		jpanel.add(timeBasedRadio);
		timeBasedRadio.addActionListener(this);

		defaultTableModel = new DefaultTableModel();
		defaultTableModel.addColumn("Name");
		defaultTableModel.addColumn("File Name");
		defaultTableModel.addColumn("Status");

		jTableReq = new JTable(defaultTableModel);
		jScrollPane = new JScrollPane(jTableReq);
		jScrollPane.setBounds(10, 60, 400, 100);
		jpanel.add(jScrollPane);

		chunkButton = new JButton("Chunk");
		chunkButton.setBounds(10, 200, 100, 30);
		chunkButton.setBackground(new Color(200, 129, 65));
		chunkButton.addActionListener(this);
		jpanel.add(chunkButton);

		sendButton = new JButton("Send");
		sendButton.setBounds(120, 200, 100, 30);
		sendButton.setBackground(new Color(200, 129, 65));
		sendButton.addActionListener(this);
		jpanel.add(sendButton);

		detectButton = new JButton("Get Values");
		detectButton.setBounds(230, 200, 100, 30);
		detectButton.setBackground(new Color(200, 129, 65));
		detectButton.addActionListener(this);
		detectButton.setEnabled(false);

		checkButton = new JButton("Check Distortion");
		checkButton.setBackground(new Color(200, 129, 65));
		checkButton.setBounds(350, 200, 500, 30);
		checkButton.addActionListener(this);
		checkButton.setEnabled(false);
		jpanel.add(checkButton);
		jpanel.add(detectButton);

		defaultTableTime = new DefaultTableModel();
		defaultTableTime.addColumn("Node Name");
		defaultTableTime.addColumn("Distortion");
		defaultTableTime.addColumn("Value");
		defaultTableTime.addColumn("Status");

		JLabel jLabelTime = new JLabel("Values");
		jLabelTime.setBounds(10, 270, 100, 30);
		jLabelTime.setForeground(new Color(247, 13, 26 ));
		jLabelTime.setFont(font);
		jpanel.add(jLabelTime);

		jTableTime = new JTable(defaultTableTime);
		JScrollPane jScrollPaneTime = new JScrollPane(jTableTime);
		jScrollPaneTime.setBounds(10, 320, 300, 200);
		jpanel.add(jScrollPaneTime);

		JLabel jLabelMalicious=new JLabel("Distortion Node");
		jLabelMalicious.setFont(font);
		jLabelMalicious.setForeground(new Color(247, 13, 26 ));
		jLabelMalicious.setBounds(600, 270, 150, 30);
		jpanel.add(jLabelMalicious);
		
		defListModel = new DefaultListModel();
		jListMalicious = new JList<String>(defListModel);
		JScrollPane jScrollPaneMalicious = new JScrollPane(jListMalicious);
		jScrollPaneMalicious.setBounds(600, 320, 200, 200);
		jpanel.add(jScrollPaneMalicious);
		
		readFiles();
		return jpanel;
	}

	public JPanel trafficPanel() {
		JPanel jpanel = new JPanel();
		jpanel.setLayout(null);
		jpanel.setBackground(new Color(0, 0, 0));
		JButton jButton = new JButton("traffic");
		jButton.setBounds(10, 10, 100, 30);
		jpanel.add(jButton);
		return jpanel;
	}

	void readFiles() {
		try {
			File contentDir = new File("video");
			for (String content : contentDir.list()) {
				File file = new File(content);
				defaultListModel.addElement(file.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Vector<byte[]> splitChunks(String chunks, String inputFile) {
		Vector<byte[]> content = new Vector<byte[]>();
		try {
			int packetSize = Integer.parseInt(chunks);
			FileInputStream fileInputStream = new FileInputStream(new File(
					inputFile));
			int len = fileInputStream.available();
			int readSize = len / packetSize;
			int nop = packetSize;
			int rem = len % packetSize;
			if (rem != 0) {
				nop = nop + 1;
			}
			for (int i = 0; i < nop; i++) {
				if (i == nop - 1 && rem != 0) {
					byte[] b = new byte[rem];
					try {
						fileInputStream.read(b);
						content.add(b);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				} else {
					byte[] b = new byte[readSize];
					try {
						fileInputStream.read(b);
						content.add(b);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
			fileInputStream.close();
			/*
			 * FileOutputStream fileOutputStream = new FileOutputStream(new
			 * File( "video/2.avi"), true); for (byte[] by : content) {
			 * fileOutputStream.write(by);
			 * 
			 * } fileInputStream.close(); fileOutputStream.close();
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public static void main(String[] args) {
		ServerDesignForm serverDesignForm = new ServerDesignForm();
		serverDesignForm.design();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == chunkButton) {
			int rowCount = defaultTableModel.getRowCount();
			chunk = randomDetails.getChunks();
			for (int i = 0; i < rowCount; i++) {
				clientNode = defaultTableModel.getValueAt(i, 0).toString();
				fileName = defaultTableModel.getValueAt(i, 1).toString();
				String mode = defaultTableModel.getValueAt(i, 2).toString();
				if (mode.equals(Constants.REQ)) {
					content = splitChunks(chunk, "video/" + fileName);
					JOptionPane.showMessageDialog(null, fileName
							+ " is chunked sucessfully !!");

					break;
				}

			}
		} else if (e.getSource() == sendButton) {
			if (clientNode != null && !clientNode.equals("")) {
				String sysDetails = serverLogic.nodeDetails.get(clientNode);
				String clientPort = sysDetails.split(";")[0];
				String sysIp = randomDetails.getSysIp1();
				FileInputStream fileInputStream;
				try {
					fileInputStream = new FileInputStream(new File("video/"
							+ fileName));
					signalJPanel.axis.setRange(-500,
							fileInputStream.available());
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				int opt_value = rand.nextInt(2);
				// int opt_value = 0;
				sendReq(clientPort, sysIp, opt_value,serverLogic.mode);
				String mode = serverLogic.mode;
                                System.out.println("Eswar--Mode=="+mode);
				if (mode.equalsIgnoreCase(Constants.ATTACK)) {
					JOptionPane.showMessageDialog(null,
							"Malicious Node is detected");
					detectButton.setEnabled(true);
				}
			}

		} else if (e.getSource() == detectButton) {
			String sysDetails = serverLogic.nodeDetails.get(clientNode);
			String clientPort = sysDetails.split(";")[0];
			String sysIp = randomDetails.getSysIp1();

			ObjectOutputStream oo = socketConnection.SocketSend(clientPort,
					sysIp);
			try {
				oo.writeObject("GET");
				Thread.sleep(1000);
				String attackIp = randomDetails.getAttackIp();
				String attackPort = randomDetails.getAttackPort();
				oo = socketConnection.SocketSend(attackPort, attackIp);
				oo.writeObject("GET");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			checkButton.setEnabled(true);
		} else if (e.getSource() == packetbasedRadio) {
			packetbasedRadio.setSelected(true);
			timeBasedRadio.setSelected(false);
		} else if (e.getSource() == timeBasedRadio) {
			packetbasedRadio.setSelected(false);
			timeBasedRadio.setSelected(true);
		} else if (e.getSource() == checkButton) {
			defaultTableTime.setRowCount(0);
			if (timeBasedRadio.isSelected()) {
				TreeMap<String, Long> timeAttackMap = serverLogic.timeAttackMap;
				TreeMap<String, Long> timeNodeMap = serverLogic.timeNodeMap;
				monitorValue(timeNodeMap, timeAttackMap);
			} else {
				TreeMap<String, Long> slotAttackMap = serverLogic.slotAttackMap;
				TreeMap<String, Long> slotNodeMap = serverLogic.slotNodeMap;
				monitorValue(slotNodeMap, slotAttackMap);
			}
			for (int i = 0; i < defaultTableTime.getRowCount(); i++) {
				String status = defaultTableTime.getValueAt(i, 3).toString();
				if (status.equals("YES")) {
					JOptionPane.showMessageDialog(null, clientNode
							+ " is Distortion Node");
					maliciousList.add(clientNode);
					defListModel.addElement(clientNode);
					break;
				}
			}
			serverLogic.timeAttackMap = new TreeMap<String, Long>();
			serverLogic.timeNodeMap = new TreeMap<String, Long>();
			serverLogic.slotAttackMap = new TreeMap<String, Long>();
			serverLogic.slotNodeMap = new TreeMap<String, Long>();
		}
	}

	public void monitorValue(TreeMap<String, Long> timeNode,
			TreeMap<String, Long> timeAttack) {

		for (Entry<String, Long> entry : timeNode.entrySet()) {
			String key = entry.getKey();
			Long value = entry.getValue();
			Vector<String> addRow = new Vector<String>();
			addRow.add(key);
			addRow.add("");
			addRow.add("");
			addRow.add("");
			defaultTableTime.addRow(addRow);
			System.out.println(key + " => " + value);
		}
		int index = 0;
		for (Entry<String, Long> entry : timeAttack.entrySet()) {
			String key = entry.getKey();
                        System.out.println("Eswar==key--->"+key);
			Long value = entry.getValue();
			String nodeContent = defaultTableTime.getValueAt(index, 0)
					.toString();
                         System.out.println("Eswar==nodecontent--->"+nodeContent);
			if (nodeContent.equalsIgnoreCase(key)) {
				defaultTableTime.setValueAt(key, index, 1);
				defaultTableTime.setValueAt("YES", index, 3);
			} else {
				defaultTableTime.setValueAt(key, index, 1);
				defaultTableTime.setValueAt("NO", index, 3);
			}
			defaultTableTime.setValueAt(String.valueOf(value), index, 2);
			System.out.println(key + " => " + value);
			++index;
		}
	}

	public void sendReq(String clientPort, String sysIp, int opt_value,String modees) {
		try {
			ArrayList<Long> milliSecList = new ArrayList<Long>();
			String mode = serverLogic.mode;
                        System.out.println("Eswar222=mode"+mode);
			int incre = 1;
			if (mode.equals(Constants.ATTACK)) {
				incre += 1;
			}
                        else
                        {
                            incre=1;
                        }
			for (int index = 0; index < incre; index++) {
				int i = 0;

				for (byte[] byt : content) {
					long sleep = 0;
					try {
						sleep = rand.nextInt(4000) + 3000;
						if (index == 0) {
							milliSecList.add(sleep);
						}
						if (opt_value == 1) {
							if (index == 1) {
								sleep = rand.nextInt(6000) + 5000;
							}
						} else {
							if (index == 1) {
								sleep = milliSecList.get(i);
							}
						}

						Thread.sleep(sleep);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					int len = byt.length;
					signalJPanel.value = len;
					System.out.println("Length-->"+len);
					i++;
					ResponseVo responseVo = new ResponseVo();
					responseVo.setPacket(byt);
					responseVo.setFileName(fileName);
					responseVo.setIndex(i);
					responseVo.setMilliSecs(sleep);
					responseVo.setTotalContent(content);
					responseVo.setTotalPackets(content.size());
					responseVo.setSourceName(clientNode);
					try {
						if (index == 1) {
							String attackIp = randomDetails.getAttackIp();
							String attackPort = randomDetails.getAttackPort();
							ObjectOutputStream oo = socketConnection
									.SocketSend(attackPort, attackIp);
							oo.writeObject(responseVo);
							oo.close();
						} else {
							ObjectOutputStream oo = socketConnection
									.SocketSend(clientPort, sysIp);
							oo.writeObject(responseVo);
							// oo.close();
						}

					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
