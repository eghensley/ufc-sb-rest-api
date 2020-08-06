package com.hensley.ufc.enums.common;

public enum ControllerEnum {
	BT("BET"), FT("FIGHT"), RK("RANKS");
	
	private String controllerName;
	
	private ControllerEnum(String controllerName) {
		this.controllerName = controllerName;
	}
	
	public String getControllerName() {
		return controllerName;
	}
	
}
