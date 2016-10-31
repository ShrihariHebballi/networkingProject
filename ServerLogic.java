package com.mycompany.logic;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.mycompany.design.AttackDesignForm;
import com.mycompany.design.NodeDesignForm;
import com.mycompany.design.ServerDesignForm;
import com.mycompany.utils.Constants;
import com.mycompany.utils.RandomDetails;
import com.mycompany.utils.SocketConnection;
import com.mycompany.vo.GetDetailsVo;
import com.mycompany.vo.RequestVo;
import com.mycompany.vo.ResponseVo;

public class ServerLogic extends Thread {
	private ServerSocket serverSocket;
	private Socket socket;
	private ObjectInputStream objectInputStream;
	Object object;
	public TreeMap<String, String> nodeDetails = new TreeMap<String, String>();
	FileOutputStream fileOutputStream;
	public String mode = "";
	public TreeMap<String, Long> timeMap = new TreeMap<String, Long>();
	public TreeMap<String, Long> slotMap = new TreeMap<String, Long>();
	Long millisecs = 0l;
	private SocketConnection socketConnection;
	public TreeMap<String, Long> timeNodeMap = new TreeMap<String, Long>();
	public TreeMap<String, Long> slotNodeMap = new TreeMap<String, Long>();

	public TreeMap<String, Long> timeAttackMap = new TreeMap<String, Long>();
	public TreeMap<String, Long> slotAttackMap = new TreeMap<String, Long>();

	public ServerLogic(int portNo, Object object) {
		try {
			socketConnection = new SocketConnection();
			fileOutputStream = new FileOutputStream(new File("output/1.avi"),
					true);
			this.object = object;
			serverSocket = new ServerSocket(portNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		start();
	}

	public void run() {
		try {
			while (true) {
				socket = serverSocket.accept();
				objectInputStream = new ObjectInputStream(
						socket.getInputStream());
				Object obj = objectInputStream.readObject();
				if (obj.toString().equals("GET")) {
					RandomDetails randomDetails = new RandomDetails();
					String serverPort = randomDetails.getServerPort();
					String serverIp = randomDetails.getSysIp();
					GetDetailsVo getDetailsVo = new GetDetailsVo();
					getDetailsVo.setSlotMap(slotMap);
					getDetailsVo.setTimeMap(timeMap);
					if (object instanceof AttackDesignForm) {
						getDetailsVo.setMode(Constants.ATTACK);
						mode=Constants.ATTACK;
					} else {
						getDetailsVo.setMode(Constants.NORMAL);
						mode=Constants.NORMAL;
					}
					ObjectOutputStream oo = socketConnection.SocketSend(
							serverPort, serverIp);
					oo.writeObject(getDetailsVo);
					oo.close();
					// socketConnection=
				} else if (obj instanceof RequestVo) {

					if (object instanceof ServerDesignForm) {
						ServerDesignForm serverDesignForm = (ServerDesignForm) object;
						RequestVo req = (RequestVo) obj;
						mode = req.getMode();
						String node = req.getNodeName();
						if (!serverDesignForm.maliciousList.contains(node)) {
							String fileName = req.getFileName();
							Vector<String> addRow = new Vector<String>();
							addRow.add(node);
							addRow.add(fileName);
							addRow.add(Constants.REQ);
							if (!nodeDetails.containsKey(node)) {
								nodeDetails.put(node,
										req.getPort() + ";" + req.getSysIp());
							} else {
								nodeDetails.remove(node);
								nodeDetails.put(node,
										req.getPort() + ";" + req.getSysIp());
							}

							serverDesignForm.defaultTableModel.addRow(addRow);
						} else {
							UIManager UI=new UIManager();
							 UI.put("OptionPane.background", new Color(247, 13, 26));
							 UI.put("Panel.background", new Color(238, 154, 77));
							JOptionPane.showMessageDialog(null, node
									+ " already added in malicious list");
						}

					}
				} else if (obj instanceof ResponseVo) {
					if (object instanceof NodeDesignForm) {
						NodeDesignForm nodeDesignForm = (NodeDesignForm) object;
						ResponseVo responseVo = (ResponseVo) obj;
						millisecs += responseVo.getMilliSecs();
						int index = responseVo.getIndex();
						int totalPackets = responseVo.getTotalPackets();
						System.out.println("Index ::" + index);
						System.out.println("totalPackets ::" + totalPackets);
						if (index == totalPackets) {

							FileOutputStream fileOutputStream = new FileOutputStream(
									new File(
											"output/"
													+ nodeDesignForm.jTextField
															.getText()), true);
							for (byte[] by : responseVo.getTotalContent()) {
								fileOutputStream.write(by);

							}
							fileOutputStream.close();

							JOptionPane.showMessageDialog(null,
									"Received all the packets");

						}
						timeMap.put(String.valueOf(millisecs), new Long(
								responseVo.getPacket().length));
						slotMap.put(Constants.SLOT + index,
								new Long(responseVo.getPacket().length));
						System.out.println(timeMap);

						nodeDesignForm.timeBasedChart.dataset.addValue(
								new Double(responseVo.getPacket().length),
								new Double(responseVo.getPacket().length),
								millisecs);

						nodeDesignForm.packageBasedChart.dataset.addValue(
								new Double(responseVo.getPacket().length),
								new Double(responseVo.getPacket().length),
								Constants.SLOT + index);

						if (index == totalPackets) {
							millisecs = 0l;
						}
					} else if (object instanceof AttackDesignForm) {
						AttackDesignForm attackDesignForm = (AttackDesignForm) object;
						ResponseVo responseVo = (ResponseVo) obj;
						millisecs += responseVo.getMilliSecs();
						int index = responseVo.getIndex();
						int totalPackets = responseVo.getTotalPackets();
						if (index == 0) {
							timeMap = new TreeMap<String, Long>();
							slotMap = new TreeMap<String, Long>();
						}
						if (index == totalPackets) {
							Vector<String> addRow = new Vector<String>();
							addRow.add(responseVo.getSourceName());
							addRow.add(responseVo.getFileName());
							addRow.add("YES");
							attackDesignForm.defaultTableModel.addRow(addRow);
							FileOutputStream fileOutputStream = new FileOutputStream(
									new File("attack/"
											+ responseVo.getFileName()), true);
							for (byte[] by : responseVo.getTotalContent()) {
								fileOutputStream.write(by);

							}
							fileOutputStream.close();

							JOptionPane.showMessageDialog(null,
									"Received all the packets");

						}
						timeMap.put(String.valueOf(millisecs), new Long(
								responseVo.getPacket().length));
						slotMap.put(Constants.SLOT + index,
								new Long(responseVo.getPacket().length));
						System.out.println(timeMap);

						attackDesignForm.timeBasedChart.dataset.addValue(
								new Double(responseVo.getPacket().length),
								new Double(responseVo.getPacket().length),
								millisecs);

						attackDesignForm.packageBasedChart.dataset.addValue(
								new Double(responseVo.getPacket().length),
								new Double(responseVo.getPacket().length),
								Constants.SLOT + index);

						if (index == totalPackets) {
							millisecs = 0l;
						}
					}

				} else if (obj instanceof GetDetailsVo) {
					GetDetailsVo getDetailsVo = (GetDetailsVo) obj;
					String mode = getDetailsVo.getMode();
					if (mode.equalsIgnoreCase(Constants.ATTACK)) {
						timeAttackMap = getDetailsVo.getTimeMap();
						slotAttackMap = getDetailsVo.getSlotMap();
					} else {
						timeNodeMap = getDetailsVo.getTimeMap();
						slotNodeMap = getDetailsVo.getSlotMap();

					}

				}
			}
		} catch (Exception e) {
			System.exit(0);
			e.printStackTrace();
		}
	}
}
