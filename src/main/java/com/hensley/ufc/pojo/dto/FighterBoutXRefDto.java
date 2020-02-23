package com.hensley.ufc.pojo.dto;

import java.util.ArrayList;
import java.util.List;

import com.hensley.ufc.enums.BoutOutcomeEnum;

public class FighterBoutXRefDto {
	private FighterDto fighter;
	private BoutOutcomeEnum outcome;
	private List<StrikeDto> boutDetails;

	
	public FighterBoutXRefDto() {
		this.boutDetails = new ArrayList<>();
	}

	/**
	 * @return the fighter
	 */
	public FighterDto getFighter() {
		return fighter;
	}

	/**
	 * @param fighter the fighter to set
	 */
	public void setFighter(FighterDto fighter) {
		this.fighter = fighter;
	}

	/**
	 * @return the outcome
	 */
	public BoutOutcomeEnum getOutcome() {
		return outcome;
	}

	/**
	 * @param outcome the outcome to set
	 */
	public void setOutcome(BoutOutcomeEnum outcome) {
		this.outcome = outcome;
	}

	/**
	 * @return the boutDetails
	 */
	public List<StrikeDto> getBoutDetails() {
		return boutDetails;
	}

	/**
	 * @param boutDetails the boutDetails to set
	 */
	public void setBoutDetails(List<StrikeDto> boutDetails) {
		this.boutDetails = boutDetails;
	}
	
	
}
