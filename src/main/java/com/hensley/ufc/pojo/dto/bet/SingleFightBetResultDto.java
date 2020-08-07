package com.hensley.ufc.pojo.dto.bet;

public class SingleFightBetResultDto {
	private Boolean bet;
	private String oid;
	private String predWinner;
	private Double vegasOdds;
	private Double predProb;
	private Double wagerWeight;
	private Double betResult;
	
	public SingleFightBetResultDto() {
		
	}

	/**
	 * @return the predWinner
	 */
	public String getPredWinner() {
		return predWinner;
	}

	/**
	 * @param predWinner the predWinner to set
	 */
	public void setPredWinner(String predWinner) {
		this.predWinner = predWinner;
	}

	/**
	 * @return the vegasOdds
	 */
	public Double getVegasOdds() {
		return vegasOdds;
	}

	/**
	 * @param vegasOdds the vegasOdds to set
	 */
	public void setVegasOdds(Double vegasOdds) {
		this.vegasOdds = vegasOdds;
	}



	/**
	 * @return the predProb
	 */
	public Double getPredProb() {
		return predProb;
	}

	/**
	 * @param predProb the predProb to set
	 */
	public void setPredProb(Double predProb) {
		this.predProb = predProb * 100;
	}

	/**
	 * @return the wagerWeight
	 */
	public Double getWagerWeight() {
		return wagerWeight;
	}

	/**
	 * @param wagerWeight the wagerWeight to set
	 */
	public void setWagerWeight(Double wagerWeight) {
		this.wagerWeight = wagerWeight;
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
	 * @return the bet
	 */
	public Boolean getBet() {
		return bet;
	}

	/**
	 * @param bet the bet to set
	 */
	public void setBet(Boolean bet) {
		this.bet = bet;
	}
	
	public Double getOddsDiff() {
		return this.predProb - this.vegasOdds;
	}
}
