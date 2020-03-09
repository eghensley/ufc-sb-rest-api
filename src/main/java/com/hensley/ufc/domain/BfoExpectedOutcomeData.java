package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BFO_EXPECTED_OUTCOME")
public class BfoExpectedOutcomeData extends BaseAuditEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2553950023318210194L;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FIGHTER_BOUT_OID", referencedColumnName = "OID", nullable = false)
	private FighterBoutXRefData fighterBout;
	
	@Column(name = "FINISH_WIN", nullable = false)
	private Double finishWin;
	
	@Column(name = "DESCISION_WIN", nullable = false)
	private Double decisionWin;

	@Column(name = "DRAW", nullable = false)
	private Double draw;
	
	@Column(name = "DESCISION_LOSS", nullable = false)
	private Double decisionLoss;
	
	@Column(name = "FINISH_LOSS", nullable = false)
	private Double finishLoss;
	
	public BfoExpectedOutcomeData() {
		
	}

	/**
	 * @return the fighterBout
	 */
	public FighterBoutXRefData getFighterBout() {
		return fighterBout;
	}

	/**
	 * @param fighterBout the fighterBout to set
	 */
	public void setFighterBout(FighterBoutXRefData fighterBout) {
		this.fighterBout = fighterBout;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(decisionLoss, decisionWin, draw, fighterBout, finishLoss, finishWin);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof BfoExpectedOutcomeData)) {
			return false;
		}
		BfoExpectedOutcomeData other = (BfoExpectedOutcomeData) obj;
		return Objects.equals(decisionLoss, other.decisionLoss) && Objects.equals(decisionWin, other.decisionWin)
				&& Objects.equals(draw, other.draw) && Objects.equals(fighterBout, other.fighterBout)
				&& Objects.equals(finishLoss, other.finishLoss) && Objects.equals(finishWin, other.finishWin);
	}

}
