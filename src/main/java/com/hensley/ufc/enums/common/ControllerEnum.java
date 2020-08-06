package com.hensley.ufc.enums.common;

public enum ControllerEnum {
	BT("BET"), FT("FIGHT");
	
	private String controllerName;
	
	private ControllerEnum(String controllerName) {
		this.controllerName = controllerName;
	}
	
	public String getControllerName() {
		return controllerName;
	}
	
}
