package com.hensley.ufc.pojo.dto.fighter;

import java.util.ArrayList;
import java.util.List;

import com.hensley.ufc.enums.BoutOutcomeEnum;
import com.hensley.ufc.pojo.dto.strike.StrikeDto;

public class FighterBoutXrefDataDto {
	private String oid;
	private FighterDto fighter;
	private BoutOutcomeEnum outcome;
	private List<StrikeDto> boutDetails;
	private Integer mlOdds;
	private BfoExpectedOutcomeDto bfoExpectedOutcomes;

	public FighterBoutXrefDataDto() {
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

	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * @return the mlOdds
	 */
	public Integer getMlOdds() {
		return mlOdds;
	}

	/**
	 * @param mlOdds the mlOdds to set
	 */
	public void setMlOdds(Integer mlOdds) {
		this.mlOdds = mlOdds;
	}

	/**
	 * @return the bfoExpectedOutcomes
	 */
	public BfoExpectedOutcomeDto getBfoExpectedOutcomes() {
		return bfoExpectedOutcomes;
	}

	/**
	 * @param bfoExpectedOutcomes the bfoExpectedOutcomes to set
	 */
	public void setBfoExpectedOutcomes(BfoExpectedOutcomeDto bfoExpectedOutcomes) {
		this.bfoExpectedOutcomes = bfoExpectedOutcomes;
	}
}
