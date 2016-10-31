package com.mycompany.vo;

import java.io.Serializable;

public class RequestVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nodeName;
	private String fileName;
	private String port;
	private String sysIp;
	private String mode;
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSysIp() {
		return sysIp;
	}

	public void setSysIp(String sysIp) {
		this.sysIp = sysIp;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
