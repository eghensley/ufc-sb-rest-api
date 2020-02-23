package com.hensley.ufc.pojo.dto;

public class StrikeDto {

	private Integer round;
	private Integer knockdowns;
	private Integer totStrikeAttempted;
	private Integer totStrikeSuccessful;
	private Integer takedownAttempted;
	private Integer takedownSuccessful;
	private Integer submissionAttempted;	
	private Integer passSuccessful;
	private Integer reversalSuccessful;	
	private Integer tkoKo;	
	private Integer submissionSuccessful;
	private SigStrikePositionDto sigStrikePosition;
	private SigStrikeTargetDto sigStrikeTarget;
	private Double score;

	public StrikeDto() {
		
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

	public Integer getTotStrikeAccuracy() {
		return this.totStrikeSuccessful/this.totStrikeAttempted;
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
	 * @param sigStrikePosition the sigStrikePosition to set
	 */
	public void setSigStrikePosition(SigStrikePositionDto sigStrikePosition) {
		this.sigStrikePosition = sigStrikePosition;
	}

	/**
	 * @param sigStrikeTarget the sigStrikeTarget to set
	 */
	public void setSigStrikeTarget(SigStrikeTargetDto sigStrikeTarget) {
		this.sigStrikeTarget = sigStrikeTarget;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDistanceSigStrikeAttemped() {
		return this.sigStrikePosition.getDistanceStrikeAttemped();
	}
	
	/**
	 * @return the distanceStrikeSuccessful
	 */
	public Double getDistanceSigStrikeSuccessful() {
		return this.sigStrikePosition.getDistanceStrikeSuccessful();
	}
	
	/**
	 * @return the distanceStrikeAccuracy
	 */
	public Double getDistanceSigStrikeAccuracy() {
		return this.sigStrikePosition.getDistanceStrikeAccuracy();
	}
	
	/**
	 * @return the clinchStrikeAttemped
	 */
	public Double getClinchSigStrikeAttemped() {
		return this.sigStrikePosition.getClinchStrikeAttemped();
	}
	
	/**
	 * @return the clinchStrikeSuccessful
	 */
	public Double getClinchSigStrikeSuccessful() {
		return this.sigStrikePosition.getClinchStrikeSuccessful();
	}
	
	/**
	 * @return the clinchStrikeAccuracy
	 */
	public Double getClinchSigStrikeAccuracy() {
		return this.sigStrikePosition.getClinchStrikeAccuracy();
	}
	
	/**
	 * @return the groundStrikeAttemped
	 */
	public Double getGroundSigStrikeAttemped() {
		return this.sigStrikePosition.getGroundStrikeAttemped();
	}
	
	/**
	 * @return the groundStrikeSuccessful
	 */
	public Double getGroundSigStrikeSuccessful() {
		return this.sigStrikePosition.getGroundStrikeSuccessful();
	}
	
	/**
	 * @return the groundStrikeAccuracy
	 */
	public Double getGroundSigStrikeAccuracy() {
		return this.sigStrikePosition.getGroundStrikeAccuracy();
	}
	
	/**
	 * @return the headStrikeAttemped
	 */
	public Double getHeadSigStrikeAttemped() {
		return this.sigStrikeTarget.getHeadStrikeAttemped();
	}

	/**
	 * @return the headStrikeSuccessful
	 */
	public Double getHeadSigStrikeSuccessful() {
		return this.sigStrikeTarget.getHeadStrikeSuccessful();
	}

	/**
	 * @return the headStrikeAccuracy
	 */
	public Double getHeadSigStrikeAccuracy() {
		return this.sigStrikeTarget.getHeadStrikeAccuracy();
	}
	
	/**
	 * @return the bodyStrikeAttemped
	 */
	public Double getBodySigStrikeAttemped() {
		return this.sigStrikeTarget.getBodyStrikeAttemped();
	}

	/**
	 * @return the bodyStrikeSuccessful
	 */
	public Double getBodySigStrikeSuccessful() {
		return this.sigStrikeTarget.getBodyStrikeSuccessful();
	}

	/**
	 * @return the bodyStrikeAccuracy
	 */
	public Double getBodySigStrikeAccuracy() {
		return this.sigStrikeTarget.getBodyStrikeAccuracy();
	}
	
	/**
	 * @return the legStrikeAttemped
	 */
	public Double getLegSigStrikeAttemped() {
		return this.sigStrikeTarget.getLegStrikeAttemped();
	}

	/**
	 * @return the legStrikeSuccessful
	 */
	public Double getLegSigStrikeSuccessful() {
		return this.sigStrikeTarget.getLegStrikeSuccessful();
	}
	
	/**
	 * @return the legStrikeAccuracy
	 */
	public Double getLegSigStrikeAccuracy() {
		return this.sigStrikeTarget.getLegStrikeAccuracy();
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
	
	
}
