package com.mycompany.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

public class RandomDetails {
	private Properties properties;
	private Random random = new Random();

	public String getName() {
		return "NOD " + random.nextInt(10) + random.nextInt(10)
				+ random.nextInt(10);
	}

	public String getPort() {
		return "" + random.nextInt(10) + random.nextInt(10)
				+ random.nextInt(10) + random.nextInt(10);
	}

	public String getServerPort() {
		String serverPort = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					"config.properties"));
			properties = new Properties();
			properties.load(fileInputStream);
			serverPort = properties.getProperty(Constants.PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverPort;
	}

	public String getSysIp() {
		String sysIp = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					"config.properties"));
			properties = new Properties();
			properties.load(fileInputStream);
			sysIp = properties.getProperty(Constants.SYS_IP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysIp;
	}
        public String getSysIp1() {
		String sysIp = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					"config.properties"));
			properties = new Properties();
			properties.load(fileInputStream);
			sysIp = properties.getProperty(Constants.SYS_IP1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysIp;
	}

	public String getChunks() {
		String chunk = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					"config.properties"));
			properties = new Properties();
			properties.load(fileInputStream);
			chunk = properties.getProperty(Constants.CHUNK);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return chunk;
	}

	public String getAttackPort() {
		String attackPort = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					"config.properties"));
			properties = new Properties();
			properties.load(fileInputStream);
			attackPort = properties.getProperty(Constants.ATTACK_PORT);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return attackPort;
	}

	public String getAttackIp() {
		String attackIp = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					"config.properties"));
			properties = new Properties();
			properties.load(fileInputStream);
			attackIp = properties.getProperty(Constants.ATTACK_IP);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return attackIp;
	}
}
