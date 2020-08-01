package com.hensley.ufc.pojo.dto.fighter;

public class FighterXrefBetHistGetDto {
	
	private String oid;
	private Boolean betMade;
	private Boolean betPredicted;
	private Double betAmount;
	private Double betResult;
	private FighterDto fighter;

	public FighterXrefBetHistGetDto() {
		
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
	 * @return the betMade
	 */
	public Boolean getBetMade() {
		return betMade;
	}

	/**
	 * @param betMade the betMade to set
	 */
	public void setBetMade(Boolean betMade) {
		this.betMade = betMade;
	}

	/**
	 * @return the betPredicted
	 */
	public Boolean getBetPredicted() {
		return betPredicted;
	}

	/**
	 * @param betPredicted the betPredicted to set
	 */
	public void setBetPredicted(Boolean betPredicted) {
		this.betPredicted = betPredicted;
	}

	/**
	 * @return the betAmount
	 */
	public Double getBetAmount() {
		return betAmount;
	}

	/**
	 * @param betAmount the betAmount to set
	 */
	public void setBetAmount(Double betAmount) {
		this.betAmount = betAmount;
	}

	/**
	 * @return the betResult
	 */
	public Double getBetResult() {
		return betResult;
	}

	/**
	 * @param betResult the betResult to set
	 */
	public void setBetResult(Double betResult) {
		this.betResult = betResult;
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
	
}