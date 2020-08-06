package com.hensley.ufc.enums.common;

public enum FunctionEnum {
	GET_BET_HIST(RestEnum.GET, ControllerEnum.BT, "Get Bet History", false),
	GET_BET_TBL(RestEnum.GET, ControllerEnum.BT, "Get Bet Table", false),
	GET_BASIC_FIGHT(RestEnum.GET, ControllerEnum.FT, "Get Basic Fight Details", false),

	;
	
	private RestEnum apiMethod;
	private ControllerEnum controller;
	private String name;
	private boolean admin;
	
	private FunctionEnum(RestEnum apiMethod, ControllerEnum controller, String name, boolean admin) {
		this.apiMethod = apiMethod;
		this.controller = controller;
		this.name = name;
		this.admin = admin;
	}
	
	public RestEnum getApiMethod() {
		return apiMethod;
	}
	
	public ControllerEnum getController() {
		return controller;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean isAdmin() {
		return admin;
	}
	
}
