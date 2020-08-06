package com.hensley.ufc.pojo.dto.bout;

public class BoutBetDto {
	private String oid;
	private String predWinner;
	private boolean bet;
	private Double predProb;
	private Double vegasOdds;
	private Double oddsDiff;
	private Double wagerWeight;
	private String notes;
	private String boutName;
	
	public BoutBetDto() {
		
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
	 * @return the bet
	 */
	public boolean isBet() {
		return bet;
	}

	/**
	 * @param bet the bet to set
	 */
	public void setBet(boolean bet) {
		this.bet = bet;
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
		this.predProb = predProb;
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
	 * @return the oddsDiff
	 */
	public Double getOddsDiff() {
		return oddsDiff;
	}

	/**
	 * @param oddsDiff the oddsDiff to set
	 */
	public void setOddsDiff(Double oddsDiff) {
		this.oddsDiff = oddsDiff;
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
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
	 * @return the boutName
	 */
	public String getBoutName() {
		return boutName;
	}

	/**
	 * @param boutName the boutName to set
	 */
	public void setBoutName(String boutName) {
		this.boutName = boutName;
	}
	
}
