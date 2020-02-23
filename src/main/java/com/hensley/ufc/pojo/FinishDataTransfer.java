package com.hensley.ufc.pojo;

import com.hensley.ufc.enums.FightMethodEnum;

public class FinishDataTransfer {

	private boolean needApply;
	private FightMethodEnum method;
	private Integer round;
	
	public FinishDataTransfer() {
		this.needApply = false;
	}

	/**
	 * @return the needApply
	 */
	public boolean getNeedApply() {
		return needApply;
	}

	/**
	 * @param needApply the needApply to set
	 */
	public void setNeedApply(boolean needApply) {
		this.needApply = needApply;
	}

	/**
	 * @return the method
	 */
	public FightMethodEnum getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(FightMethodEnum method) {
		this.method = method;
	}

	/**
	 * @return the round
	 */
	public Integer getRound() {
		return round;
	}

	/**
	 * @param round the round to set
	 */
	public void setRound(Integer round) {
		this.round = round;
	}
	
	
}
