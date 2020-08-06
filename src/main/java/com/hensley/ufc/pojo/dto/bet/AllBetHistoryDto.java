package com.hensley.ufc.pojo.dto.bet;

import java.util.Date;

public class AllBetHistoryDto {
	private String fightName;
	private Date fightDate;
	private Double result;
	
	public AllBetHistoryDto() {
		
	}

	/**
	 * @return the fightName
	 */
	public String getFightName() {
		return fightName;
	}

	/**
	 * @param fightName the fightName to set
	 */
	public void setFightName(String fightName) {
		this.fightName = fightName;
	}

	/**
	 * @return the fightDate
	 */
	public Date getFightDate() {
		return fightDate;
	}

	/**
	 * @param fightDate the fightDate to set
	 */
	public void setFightDate(Date fightDate) {
		this.fightDate = fightDate;
	}

	/**
	 * @return the result
	 */
	public Double getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Double result) {
		this.result = result;
	}
	
	
}
