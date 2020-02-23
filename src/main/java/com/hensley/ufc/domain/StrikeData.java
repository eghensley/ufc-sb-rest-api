package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STRIKE_DATA")
public class StrikeData  extends BaseAuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2296744090729623624L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIGHTER_BOUT_OID", referencedColumnName = "OID", nullable = false)
	private FighterBoutXRefData fighterBout;
	
	@Column(name = "ROUND", nullable = false)
	private Integer round;
	@Column(name = "KNOCKDOWN", nullable = false)
	private Integer knockdowns;
	@Column(name = "TOT_STRIKE_ATTEMPTED", nullable = false)
	private Integer totStrikeAttempted;
	@Column(name = "TOT_STRIKE_SUCCESSFUL", nullable = false)
	private Integer totStrikeSuccessful;
	@Column(name = "TAKEDOWN_ATTEMPTED", nullable = false)
	private Integer takedownAttempted;
	@Column(name = "TAKEDOWN_SUCCESSFUL", nullable = false)
	private Integer takedownSuccessful;
	@Column(name = "SUBMISSION_ATTEMPTED", nullable = false)
	private Integer submissionAttempted;	
	@Column(name = "PASS_SUCCESSFUL", nullable = false)
	private Integer passSuccessful;
	@Column(name = "REVERSAL_SUCCESSFUL", nullable = false)
	private Integer reversalSuccessful;	
	@Column(name = "TKO_KO", nullable = false)
	private Integer tkoKo;	
	@Column(name = "SUBMISSION_SUCCESSFUL", nullable = false)
	private Integer submissionSuccessful;	
	
	@Column(name = "SCORE", nullable = true)
	private Double score;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private SigStrikePositionData sigStrikePosition;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private SigStrikeTargetData sigStrikeTarget;
	
	public StrikeData() {
		
	}

	/**
	 * @return the knockdowns
	 */
	public Integer getKnockdowns() {
		return knockdowns;
	}

	/**
	 * @param knockdowns the knockdowns to set
	 */
	public void setKnockdowns(Integer knockdowns) {
		this.knockdowns = knockdowns;
	}

	/**
	 * @return the totStrikeAttempted
	 */
	public Integer getTotStrikeAttempted() {
		return totStrikeAttempted;
	}

	/**
	 * @param totStrikeAttempted the totStrikeAttempted to set
	 */
	public void setTotStrikeAttempted(Integer totStrikeAttempted) {
		this.totStrikeAttempted = totStrikeAttempted;
	}

	/**
	 * @return the totStrikeSuccessful
	 */
	public Integer getTotStrikeSuccessful() {
		return totStrikeSuccessful;
	}

	/**
	 * @param totStrikeSuccessful the totStrikeSuccessful to set
	 */
	public void setTotStrikeSuccessful(Integer totStrikeSuccessful) {
		this.totStrikeSuccessful = totStrikeSuccessful;
	}

	/**
	 * @return the takedownAttempted
	 */
	public Integer getTakedownAttempted() {
		return takedownAttempted;
	}

	/**
	 * @param takedownAttempted the takedownAttempted to set
	 */
	public void setTakedownAttempted(Integer takedownAttempted) {
		this.takedownAttempted = takedownAttempted;
	}

	/**
	 * @return the takedownSuccessful
	 */
	public Integer getTakedownSuccessful() {
		return takedownSuccessful;
	}

	/**
	 * @param takedownSuccessful the takedownSuccessful to set
	 */
	public void setTakedownSuccessful(Integer takedownSuccessful) {
		this.takedownSuccessful = takedownSuccessful;
	}

	/**
	 * @return the submissionAttempted
	 */
	public Integer getSubmissionAttempted() {
		return submissionAttempted;
	}

	/**
	 * @param submissionAttempted the submissionAttempted to set
	 */
	public void setSubmissionAttempted(Integer submissionAttempted) {
		this.submissionAttempted = submissionAttempted;
	}

	/**
	 * @return the passSuccessful
	 */
	public Integer getPassSuccessful() {
		return passSuccessful;
	}

	/**
	 * @param passSuccessful the passSuccessful to set
	 */
	public void setPassSuccessful(Integer passSuccessful) {
		this.passSuccessful = passSuccessful;
	}

	/**
	 * @return the reversalSuccessful
	 */
	public Integer getReversalSuccessful() {
		return reversalSuccessful;
	}

	/**
	 * @param reversalSuccessful the reversalSuccessful to set
	 */
	public void setReversalSuccessful(Integer reversalSuccessful) {
		this.reversalSuccessful = reversalSuccessful;
	}

	/**
	 * @return the sigStrikePosition
	 */
	public SigStrikePositionData getSigStrikePosition() {
		return sigStrikePosition;
	}

	/**
	 * @param sigStrikePosition the sigStrikePosition to set
	 */
	public void setSigStrikePosition(SigStrikePositionData sigStrikePosition) {
		this.sigStrikePosition = sigStrikePosition;
		sigStrikePosition.setStrikeData(this);
	}

	/**
	 * @return the sigStrikeTarget
	 */
	public SigStrikeTargetData getSigStrikeTarget() {
		return sigStrikeTarget;
	}

	/**
	 * @param sigStrikeTarget the sigStrikeTarget to set
	 */
	public void setSigStrikeTarget(SigStrikeTargetData sigStrikeTarget) {
		this.sigStrikeTarget = sigStrikeTarget;
		sigStrikeTarget.setStrikeData(this);
	}

	/**
	 * @return the tkoKo
	 */
	public Integer getTkoKo() {
		return tkoKo;
	}

	/**
	 * @param tkoKo the tkoKo to set
	 */
	public void setTkoKo(Integer tkoKo) {
		this.tkoKo = tkoKo;
	}

	/**
	 * @return the submissionSuccessful
	 */
	public Integer getSubmissionSuccessful() {
		return submissionSuccessful;
	}

	/**
	 * @param submissionSuccessful the submissionSuccessful to set
	 */
	public void setSubmissionSuccessful(Integer submissionSuccessful) {
		this.submissionSuccessful = submissionSuccessful;
	}

	/**
	 * @return the round
	 */
	public Integer getRound() {
		return round;
	}

	/**
	 * @param round the round to set
	 */
	public void setRound(Integer round) {
		this.round = round;
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
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Double score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(fighterBout, knockdowns, passSuccessful, reversalSuccessful, round,
				score, sigStrikePosition, sigStrikeTarget, submissionAttempted, submissionSuccessful, takedownAttempted,
				takedownSuccessful, tkoKo, totStrikeAttempted, totStrikeSuccessful);
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
		if (!(obj instanceof StrikeData)) {
			return false;
		}
		StrikeData other = (StrikeData) obj;
		return Objects.equals(fighterBout, other.fighterBout) && Objects.equals(knockdowns, other.knockdowns)
				&& Objects.equals(passSuccessful, other.passSuccessful)
				&& Objects.equals(reversalSuccessful, other.reversalSuccessful) && Objects.equals(round, other.round)
				&& Objects.equals(score, other.score) && Objects.equals(sigStrikePosition, other.sigStrikePosition)
				&& Objects.equals(sigStrikeTarget, other.sigStrikeTarget)
				&& Objects.equals(submissionAttempted, other.submissionAttempted)
				&& Objects.equals(submissionSuccessful, other.submissionSuccessful)
				&& Objects.equals(takedownAttempted, other.takedownAttempted)
				&& Objects.equals(takedownSuccessful, other.takedownSuccessful) && Objects.equals(tkoKo, other.tkoKo)
				&& Objects.equals(totStrikeAttempted, other.totStrikeAttempted)
				&& Objects.equals(totStrikeSuccessful, other.totStrikeSuccessful);
	}
	
}
