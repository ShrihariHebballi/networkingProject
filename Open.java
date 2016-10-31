package com.mycompany.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Open {
	public Open(String fileName) {
		if (fileName.contains(".jpg")) {
			JPanel panel=new JPanel();
		    JFrame frame = new JFrame("Image File");
		    //frame.setUndecorated(true);
		    panel.setLayout(null);
		    Icon icon = new ImageIcon(fileName);	    
		    JLabel label3 = new JLabel(icon, JLabel.CENTER);
		    label3.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		    panel.add(label3);
		    panel.setBackground(Color.white);
		    frame.add(panel);
		    Dimension di = frame.getToolkit().getScreenSize();
			int width = di.width;
			int height = di.height;
			int wi = (width - 800) / 2;
			int he = (height - 600) / 2;
			frame.setLocation(150, 20);
		    frame.setSize(icon.getIconWidth(), icon.getIconHeight());
		    frame.setVisible(true);
		} else if (fileName.contains(".txt") ) {
			try {
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						new FileInputStream(new File(fileName)));
				// System.out.println(bufferedInputStream.available());
				byte[] byt = new byte[bufferedInputStream.available()];
				bufferedInputStream.read(byt, 0, bufferedInputStream
						.available());
				String text = new String(byt);
				 JFrame frame = new JFrame();
				 frame.setLayout(null);
				JTextArea jTextArea = new JTextArea();
				jTextArea.setText(text);
				JScrollPane jScrollPane = new JScrollPane(jTextArea);
				jScrollPane.setBounds(30, 30, 500, 500);
				frame.add(jScrollPane);
				Dimension di = frame.getToolkit().getScreenSize();
				int width = di.width;
				int height = di.height;
				int wi = (width - 800) / 2;
				int he = (height - 600) / 2;
				frame.setLocation(wi, he);
				frame.setSize(600, 600);
				frame.setVisible(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (fileName.contains(".avi")) {
			try {
				
				File file=new File(fileName);
		        URL mediaURL = file.toURL();
				JFrame mediaTest = new JFrame( "Video File" );
	            mediaTest.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	            
	            MediaPanel mediaPanel = new MediaPanel( mediaURL );
	            mediaTest.add( mediaPanel );
	            Dimension di = mediaTest.getToolkit().getScreenSize();
	            int width = di.width;
				int height = di.height;
				int wi = (width - 800) / 2;
				int he = (height - 600) / 2;
				mediaTest.setLocation(wi+250, he+150);
	            mediaTest.setSize(300, 300 );
	            mediaTest.setVisible( true );
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		Open open=new Open("video/1.avi");
	}
}