package com.mycompany.vo;

import java.io.Serializable;
import java.util.Vector;

public class ResponseVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private byte[] packet;
	private int totalPackets;
	private int index;
	private Vector<byte[]> totalContent;
	private long milliSecs;
	private String sourceName;
	
	
	
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public long getMilliSecs() {
		return milliSecs;
	}

	public void setMilliSecs(long milliSecs) {
		this.milliSecs = milliSecs;
	}

	public Vector<byte[]> getTotalContent() {
		return totalContent;
	}

	public void setTotalContent(Vector<byte[]> totalContent) {
		this.totalContent = totalContent;
	}

	public int getTotalPackets() {
		return totalPackets;
	}

	public void setTotalPackets(int totalPackets) {
		this.totalPackets = totalPackets;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public byte[] getPacket() {
		return packet;
	}

	public void setPacket(byte[] packet) {
		this.packet = packet;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
