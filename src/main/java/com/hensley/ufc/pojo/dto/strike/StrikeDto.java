package com.hensley.ufc.pojo.dto.strike;

public class StrikeDto {

	private String oid;
	private Integer round;
	private Integer knockdowns;
	private Integer totStrikeAttempted;
	private Integer totStrikeSuccessful;
	private Integer takedownAttempted;
	private Integer takedownSuccessful;
	private Double takedownAccuracy;
	private Integer submissionAttempted;
	private Integer passSuccessful;
	private Integer reversalSuccessful;
	private Integer tkoKo;
	private Integer submissionSuccessful;
	private Double submissionAccuracy;
	private SigStrikePositionDto sigStrikePosition;
	private SigStrikeTargetDto sigStrikeTarget;
	private Double score;
	private Double submissionScore;
	private Double koScore;
	
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

	public Double getTotStrikeAccuracy() {
		if (this.totStrikeAttempted == 0) {
			return 0.0;
		} else {
			return Double.valueOf(Double.valueOf(this.totStrikeSuccessful) / Double.valueOf(this.totStrikeAttempted));
		}
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
	 * @return the takedownAccuracy
	 */
	public Double getSubmissionAccuracy() {
		return submissionAccuracy;
	}
	
	/**
	 * @return the takedownAccuracy
	 */
	public void setSubmissionAccuracy(Double submissionAccuracy) {
		this.submissionAccuracy = submissionAccuracy;
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
		if (this.sigStrikePosition == null) {
			return null;
		}
		return this.sigStrikePosition.getDistanceStrikeAttemped();
	}

	/**
	 * @return the distanceStrikeSuccessful
	 */
	public Double getDistanceSigStrikeSuccessful() {
		if (this.sigStrikePosition == null) {
			return null;
		}
		return this.sigStrikePosition.getDistanceStrikeSuccessful();
	}

	/**
	 * @return the distanceStrikeAccuracy
	 */
	public Double getDistanceSigStrikeAccuracy() {
		if (this.sigStrikePosition == null) {
			return null;
		}
		return this.sigStrikePosition.getDistanceStrikeAccuracy();
	}

	/**
	 * @return the clinchStrikeAttemped
	 */
	public Double getClinchSigStrikeAttemped() {
		if (this.sigStrikePosition == null) {
			return null;
		}
		return this.sigStrikePosition.getClinchStrikeAttemped();
	}

	/**
	 * @return the clinchStrikeSuccessful
	 */
	public Double getClinchSigStrikeSuccessful() {
		if (this.sigStrikePosition == null) {
			return null;
		}
		return this.sigStrikePosition.getClinchStrikeSuccessful();
	}

	/**
	 * @return the clinchStrikeAccuracy
	 */
	public Double getClinchSigStrikeAccuracy() {
		if (this.sigStrikePosition == null) {
			return null;
		}
		return this.sigStrikePosition.getClinchStrikeAccuracy();
	}

	/**
	 * @return the groundStrikeAttemped
	 */
	public Double getGroundSigStrikeAttemped() {
		if (this.sigStrikePosition == null) {
			return null;
		}
		return this.sigStrikePosition.getGroundStrikeAttemped();
	}

	/**
	 * @return the groundStrikeSuccessful
	 */
	public Double getGroundSigStrikeSuccessful() {
		if (this.sigStrikePosition == null) {
			return null;
		}
		return this.sigStrikePosition.getGroundStrikeSuccessful();
	}

	/**
	 * @return the groundStrikeAccuracy
	 */
	public Double getGroundSigStrikeAccuracy() {
		if (this.sigStrikePosition == null) {
			return null;
		}
		return this.sigStrikePosition.getGroundStrikeAccuracy();
	}

	/**
	 * @return the headStrikeAttemped
	 */
	public Double getHeadSigStrikeAttemped() {
		if (this.sigStrikeTarget == null) {
			return null;
		}
		return this.sigStrikeTarget.getHeadStrikeAttemped();
	}

	/**
	 * @return the headStrikeSuccessful
	 */
	public Double getHeadSigStrikeSuccessful() {
		if (this.sigStrikeTarget == null) {
			return null;
		}
		return this.sigStrikeTarget.getHeadStrikeSuccessful();
	}

	/**
	 * @return the headStrikeAccuracy
	 */
	public Double getHeadSigStrikeAccuracy() {
		if (this.sigStrikeTarget == null) {
			return null;
		}
		return this.sigStrikeTarget.getHeadStrikeAccuracy();
	}

	/**
	 * @return the bodyStrikeAttemped
	 */
	public Double getBodySigStrikeAttemped() {
		if (this.sigStrikeTarget == null) {
			return null;
		}
		return this.sigStrikeTarget.getBodyStrikeAttemped();
	}

	/**
	 * @return the bodyStrikeSuccessful
	 */
	public Double getBodySigStrikeSuccessful() {
		if (this.sigStrikeTarget == null) {
			return null;
		}
		return this.sigStrikeTarget.getBodyStrikeSuccessful();
	}

	/**
	 * @return the bodyStrikeAccuracy
	 */
	public Double getBodySigStrikeAccuracy() {
		if (this.sigStrikeTarget == null) {
			return null;
		}
		return this.sigStrikeTarget.getBodyStrikeAccuracy();
	}

	/**
	 * @return the legStrikeAttemped
	 */
	public Double getLegSigStrikeAttemped() {
		if (this.sigStrikeTarget == null) {
			return null;
		}
		return this.sigStrikeTarget.getLegStrikeAttemped();
	}

	/**
	 * @return the legStrikeSuccessful
	 */
	public Double getLegSigStrikeSuccessful() {
		if (this.sigStrikeTarget == null) {
			return null;
		}
		return this.sigStrikeTarget.getLegStrikeSuccessful();
	}

	/**
	 * @return the legStrikeAccuracy
	 */
	public Double getLegSigStrikeAccuracy() {
		if (this.sigStrikeTarget == null) {
			return null;
		}
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
	 * @return the takedownAccuracy
	 */
	public Double getTakedownAccuracy() {
		return takedownAccuracy;
	}

	/**
	 * @param takedownAccuracy the takedownAccuracy to set
	 */
	public void setTakedownAccuracy(Double takedownAccuracy) {
		this.takedownAccuracy = takedownAccuracy;
	}

	/**
	 * @return the submissionScore
	 */
	public Double getSubmissionScore() {
		return submissionScore;
	}

	/**
	 * @param submissionScore the submissionScore to set
	 */
	public void setSubmissionScore(Double submissionScore) {
		this.submissionScore = submissionScore;
	}

	/**
	 * @return the koScore
	 */
	public Double getKoScore() {
		return koScore;
	}

	/**
	 * @param koScore the koScore to set
	 */
	public void setKoScore(Double koScore) {
		this.koScore = koScore;
	}

}
