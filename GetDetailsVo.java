package com.mycompany.vo;

import java.io.Serializable;
import java.util.TreeMap;

public class GetDetailsVo implements Serializable {
	private TreeMap<String, Long> timeMap;
	private TreeMap<String, Long> slotMap;
	private String mode;
	
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public TreeMap<String, Long> getTimeMap() {
		return timeMap;
	}

	public void setTimeMap(TreeMap<String, Long> timeMap) {
		this.timeMap = timeMap;
	}

	public TreeMap<String, Long> getSlotMap() {
		return slotMap;
	}

	public void setSlotMap(TreeMap<String, Long> slotMap) {
		this.slotMap = slotMap;
	}

}
