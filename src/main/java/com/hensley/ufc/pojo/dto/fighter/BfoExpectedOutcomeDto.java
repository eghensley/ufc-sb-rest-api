package com.hensley.ufc.pojo.dto.fighter;

public class BfoExpectedOutcomeDto {
	private Double finishWin;
	private Double decisionWin;
	private Double draw;
	private Double decisionLoss;
	private Double finishLoss;
	
	public BfoExpectedOutcomeDto() {
		
	}

	/**
	 * @return the finishWin
	 */
	public Double getFinishWin() {
		return finishWin;
	}

	/**
	 * @param finishWin the finishWin to set
	 */
	public void setFinishWin(Double finishWin) {
		this.finishWin = finishWin;
	}

	/**
	 * @return the decisionWin
	 */
	public Double getDecisionWin() {
		return decisionWin;
	}

	/**
	 * @param decisionWin the decisionWin to set
	 */
	public void setDecisionWin(Double decisionWin) {
		this.decisionWin = decisionWin;
	}

	/**
	 * @return the draw
	 */
	public Double getDraw() {
		return draw;
	}

	/**
	 * @param draw the draw to set
	 */
	public void setDraw(Double draw) {
		this.draw = draw;
	}

	/**
	 * @return the decisionLoss
	 */
	public Double getDecisionLoss() {
		return decisionLoss;
	}

	/**
	 * @param decisionLoss the decisionLoss to set
	 */
	public void setDecisionLoss(Double decisionLoss) {
		this.decisionLoss = decisionLoss;
	}

	/**
	 * @return the finishLoss
	 */
	public Double getFinishLoss() {
		return finishLoss;
	}

	/**
	 * @param finishLoss the finishLoss to set
	 */
	public void setFinishLoss(Double finishLoss) {
		this.finishLoss = finishLoss;
	}
	
}
