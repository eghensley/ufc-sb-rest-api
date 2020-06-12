package com.hensley.ufc.pojo.request;

import com.hensley.ufc.pojo.dto.SuperAuditDto;

public class AddBoutWinProb extends SuperAuditDto {
	private Double expOdds;

	public AddBoutWinProb() {
		
	}

	/**
	 * @return the expOdds
	 */
	public Double getExpOdds() {
		return expOdds;
	}

	/**
	 * @param expOdds the expOdds to set
	 */
	public void setExpOdds(Double expOdds) {
		this.expOdds = expOdds;
	}
	
	
}
