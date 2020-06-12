package com.hensley.ufc.pojo.request;

import com.hensley.ufc.pojo.dto.SuperAuditDto;

public class AddFutureBoutSummary extends SuperAuditDto {
	private Integer schedRounds;
	private Boolean champBout;

	public AddFutureBoutSummary() {
		
	}

	/**
	 * @return the schedRounds
	 */
	public Integer getSchedRounds() {
		return schedRounds;
	}

	/**
	 * @param schedRounds the schedRounds to set
	 */
	public void setSchedRounds(Integer schedRounds) {
		this.schedRounds = schedRounds;
	}

	/**
	 * @return the champBout
	 */
	public Boolean getChampBout() {
		return champBout;
	}

	/**
	 * @param champBout the champBout to set
	 */
	public void setChampBout(Boolean champBout) {
		this.champBout = champBout;
	}
	
	
}
