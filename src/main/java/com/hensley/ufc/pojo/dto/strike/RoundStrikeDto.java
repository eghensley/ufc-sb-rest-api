package com.hensley.ufc.pojo.dto.strike;

import com.hensley.ufc.enums.WeightClassEnum;

public class RoundStrikeDto {
	private WeightClassEnum weightClass;

	private String oid;

	private Integer seconds;
	
	private Double offScore;
	private Double defScore;

	private Integer round;
	private Integer offKnockdowns;
	private Integer defKnockdowns;

	private Integer offTotStrikeAttempted;
	private Integer defTotStrikeAttempted;

	private Integer offTotStrikeSuccessful;
	private Integer defTotStrikeSuccessful;

	private Double offTotStrikeAccuracy;
	private Double defTotStrikeAccuracy;

	private Integer offTakedownAttempted;
	private Integer defTakedownAttempted;

	private Integer offTakedownSuccessful;
	private Integer defTakedownSuccessful;

	private Double offTakedownAccuracy;
	private Double defTakedownAccuracy;
	
	private Integer offSubmissionAttempted;
	private Integer defSubmissionAttempted;
	
	private Double offSubmissionAccuracy;
	private Double defSubmissionAccuracy;

	private Integer offControlTime;
	private Integer defControlTime;

	private Integer offReversalSuccessful;
	private Integer defReversalSuccessful;

	private Integer offTkoKo;
	private Integer defTkoKo;

	private Integer offSubmissionSuccessful;
	private Integer defSubmissionSuccessful;

	private Double offDistanceSigStrikeAttempted;
	private Double defDistanceSigStrikeAttempted;

	private Double offDistanceSigStrikeSuccessful;
	private Double defDistanceSigStrikeSuccessful;

	private Double offDistanceSigStrikeAccuracy;
	private Double defDistanceSigStrikeAccuracy;

	private Double offClinchSigStrikeAttempted;
	private Double defClinchSigStrikeAttempted;

	private Double offClinchSigStrikeSuccessful;
	private Double defClinchSigStrikeSuccessful;

	private Double offClinchSigStrikeAccuracy;
	private Double defClinchSigStrikeAccuracy;

	private Double offGroundSigStrikeAttempted;
	private Double defGroundSigStrikeAttempted;

	private Double offGroundSigStrikeSuccessful;
	private Double defGroundSigStrikeSuccessful;

	private Double offGroundSigStrikeAccuracy;
	private Double defGroundSigStrikeAccuracy;

	private Double offHeadSigStrikeAttempted;
	private Double defHeadSigStrikeAttempted;

	private Double offHeadSigStrikeSuccessful;
	private Double defHeadSigStrikeSuccessful;

	private Double offHeadSigStrikeAccuracy;
	private Double defHeadSigStrikeAccuracy;

	private Double offLegSigStrikeAttempted;
	private Double defLegSigStrikeAttempted;

	private Double offLegSigStrikeSuccessful;
	private Double defLegSigStrikeSuccessful;

	private Double offLegSigStrikeAccuracy;
	private Double defLegSigStrikeAccuracy;

	private Double offBodySigStrikeAttempted;
	private Double defBodySigStrikeAttempted;

	private Double offBodySigStrikeSuccessful;
	private Double defBodySigStrikeSuccessful;

	private Double offBodySigStrikeAccuracy;
	private Double defBodySigStrikeAccuracy;

	private String boutOid;
	private String fighterOid;

	private Double offSubmissionScore;
	private Double offKoScore;
	private Double defSubmissionScore;
	private Double defKoScore;
	public RoundStrikeDto() {

	}

	/**
	 * @return the round
	 */
	public Integer getRound() {
		return this.round;
	}

	public void setDefStats(StrikeDto defStats) {
		this.defKnockdowns = defStats.getKnockdowns();
		this.defTotStrikeAttempted = defStats.getTotStrikeAttempted();
		this.defTotStrikeSuccessful = defStats.getTotStrikeSuccessful();
		this.defTotStrikeAccuracy = defStats.getTotStrikeAccuracy();
		this.defTakedownAttempted = defStats.getTakedownAttempted();
		this.defTakedownSuccessful = defStats.getTakedownSuccessful();
		this.defSubmissionAttempted = defStats.getSubmissionAttempted();
		this.defControlTime = defStats.getControlTime();
		this.defReversalSuccessful = defStats.getReversalSuccessful();
		this.defTkoKo = defStats.getTkoKo();
		this.defSubmissionSuccessful = defStats.getSubmissionSuccessful();
		this.defDistanceSigStrikeAttempted = defStats.getDistanceSigStrikeAttemped();
		this.defDistanceSigStrikeSuccessful = defStats.getDistanceSigStrikeSuccessful();
		this.defDistanceSigStrikeAccuracy = defStats.getDistanceSigStrikeAccuracy();
		this.defClinchSigStrikeAttempted = defStats.getClinchSigStrikeAttemped();
		this.defClinchSigStrikeSuccessful = defStats.getClinchSigStrikeSuccessful();
		this.defClinchSigStrikeAccuracy = defStats.getClinchSigStrikeAccuracy();
		this.defGroundSigStrikeAttempted = defStats.getGroundSigStrikeAttemped();
		this.defGroundSigStrikeSuccessful = defStats.getGroundSigStrikeSuccessful();
		this.defGroundSigStrikeAccuracy = defStats.getGroundSigStrikeAccuracy();
		this.defHeadSigStrikeAttempted = defStats.getHeadSigStrikeAttemped();
		this.defHeadSigStrikeSuccessful = defStats.getHeadSigStrikeSuccessful();
		this.defHeadSigStrikeAccuracy = defStats.getHeadSigStrikeAccuracy();
		this.defLegSigStrikeAttempted = defStats.getLegSigStrikeAttemped();
		this.defLegSigStrikeSuccessful = defStats.getLegSigStrikeSuccessful();
		this.defLegSigStrikeAccuracy = defStats.getLegSigStrikeAccuracy();
		this.defBodySigStrikeAttempted = defStats.getBodySigStrikeAttemped();
		this.defBodySigStrikeSuccessful = defStats.getBodySigStrikeSuccessful();
		this.defBodySigStrikeAccuracy = defStats.getBodySigStrikeAccuracy();
		this.defTakedownAccuracy = defStats.getTakedownAccuracy();
		this.defSubmissionAccuracy = defStats.getSubmissionAccuracy();
		this.defScore = defStats.getScore();
		this.defKoScore = defStats.getKoScore();
		this.defSubmissionScore = defStats.getSubmissionScore();
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
	public Integer getOffKnockdowns() {
		return this.offKnockdowns;
	}

	/**
	 * @param knockdowns the knockdowns to set
	 */
	public void setKnockdowns(Integer knockdowns) {
		this.offKnockdowns = knockdowns;
	}

	/**
	 * @return the defKnockdowns
	 */
	public Integer getDefKnockdowns() {
		return this.defKnockdowns;
	}

	/**
	 * @return the totStrikeAttempted
	 */
	public Integer getOffTotStrikeAttempted() {
		return this.offTotStrikeAttempted;
	}

	/**
	 * @param totStrikeAttempted the totStrikeAttempted to set
	 */
	public void setTotStrikeAttempted(Integer totStrikeAttempted) {
		this.offTotStrikeAttempted = totStrikeAttempted;
	}

	/**
	 * @return the defTotStrikeAttempted
	 */
	public Integer getDefTotStrikeAttempted() {
		return this.defTotStrikeAttempted;
	}

	/**
	 * @return the totStrikeSuccessful
	 */
	public Integer getOffTotStrikeSuccessful() {
		return this.offTotStrikeSuccessful;
	}

	/**
	 * @param totStrikeSuccessful the totStrikeSuccessful to set
	 */
	public void setTotStrikeSuccessful(Integer totStrikeSuccessful) {
		this.offTotStrikeSuccessful = totStrikeSuccessful;
	}

	/**
	 * @return the defTotStrikeSuccessful
	 */
	public Integer getDefTotStrikeSuccessful() {
		return defTotStrikeSuccessful;
	}

	public Double getOffTotStrikeAccuracy() {
		return this.offTotStrikeAccuracy;
	}

	public void setTotStrikeAccuracy(Double totStrikeAccuracy) {
		this.offTotStrikeAccuracy = totStrikeAccuracy;
	}

	public Double getDefTotStrikeAccuracy() {
		return this.defTotStrikeAccuracy;
	}

	/**
	 * @return the defTakedownAttempted
	 */
	public Integer getDefTakedownAttempted() {
		return this.defTakedownAttempted;
	}

	/**
	 * @return the takedownAttempted
	 */
	public Integer getOffTakedownAttempted() {
		return this.offTakedownAttempted;
	}

	/**
	 * @param takedownAttempted the takedownAttempted to set
	 */
	public void setTakedownAttempted(Integer takedownAttempted) {
		this.offTakedownAttempted = takedownAttempted;
	}

	/**
	 * @return the defTakedownSuccessful
	 */
	public Integer getDefTakedownSuccessful() {
		return defTakedownSuccessful;
	}

	/**
	 * @return the takedownSuccessful
	 */
	public Integer getOffTakedownSuccessful() {
		return this.offTakedownSuccessful;
	}

	/**
	 * @param takedownSuccessful the takedownSuccessful to set
	 */
	public void setTakedownSuccessful(Integer takedownSuccessful) {
		this.offTakedownSuccessful = takedownSuccessful;
	}

	/**
	 * @return the offTakedownAccuracy
	 */
	public Double getOffTakedownAccuracy() {
		return offTakedownAccuracy;
	}

	/**
	 * @param offTakedownAccuracy the offTakedownAccuracy to set
	 */
	public void setTakedownAccuracy(Double offTakedownAccuracy) {
		this.offTakedownAccuracy = offTakedownAccuracy;
	}

	/**
	 * @return the defTakedownAccuracy
	 */
	public Double getDefTakedownAccuracy() {
		return defTakedownAccuracy;
	}

	/**
	 * @return the submissionAttempted
	 */
	public Integer getOffSubmissionAttempted() {
		return this.offSubmissionAttempted;
	}

	/**
	 * @return the defSubmissionAttempted
	 */
	public Integer getDefSubmissionAttempted() {
		return defSubmissionAttempted;
	}

	/**
	 * @param submissionAttempted the submissionAttempted to set
	 */
	public void setSubmissionAttempted(Integer submissionAttempted) {
		this.offSubmissionAttempted = submissionAttempted;
	}

	/**
	 * @return the offSubmissionAccuracy
	 */
	public Double getOffSubmissionAccuracy() {
		return offSubmissionAccuracy;
	}

	/**
	 * @param offSubmissionAccuracy the offSubmissionAccuracy to set
	 */
	public void setSubmissionAccuracy(Double offSubmissionAccuracy) {
		this.offSubmissionAccuracy = offSubmissionAccuracy;
	}

	/**
	 * @return the defSubmissionAccuracy
	 */
	public Double getDefSubmissionAccuracy() {
		return defSubmissionAccuracy;
	}

	/**
	 * @return the passSuccessful
	 */
	public Integer getOffControlTime() {
		return this.offControlTime;
	}

	/**
	 * @return the defPassSuccessful
	 */
	public Integer getDefControlTime() {
		return defControlTime;
	}

	/**
	 * @param passSuccessful the passSuccessful to set
	 */
	public void setControlTime(Integer controlTime) {
		this.offControlTime = controlTime;
	}

	/**
	 * @return the reversalSuccessful
	 */
	public Integer getOffReversalSuccessful() {
		return this.offReversalSuccessful;
	}

	/**
	 * @return the defReversalSuccessful
	 */
	public Integer getDefReversalSuccessful() {
		return defReversalSuccessful;
	}

	/**
	 * @param reversalSuccessful the reversalSuccessful to set
	 */
	public void setReversalSuccessful(Integer reversalSuccessful) {
		this.offReversalSuccessful = reversalSuccessful;
	}

	/**
	 * @return the tkoKo
	 */
	public Integer getOffTkoKo() {
		return this.offTkoKo;
	}

	/**
	 * @return the defTkoKo
	 */
	public Integer getDefTkoKo() {
		return defTkoKo;
	}

	/**
	 * @param tkoKo the tkoKo to set
	 */
	public void setTkoKo(Integer tkoKo) {
		this.offTkoKo = tkoKo;
	}

	/**
	 * @return the submissionSuccessful
	 */
	public Integer getOffSubmissionSuccessful() {
		return this.offSubmissionSuccessful;
	}

	/**
	 * @return the defSubmissionSuccessful
	 */
	public Integer getDefSubmissionSuccessful() {
		return defSubmissionSuccessful;
	}

	/**
	 * @param submissionSuccessful the submissionSuccessful to set
	 */
	public void setSubmissionSuccessful(Integer submissionSuccessful) {
		this.offSubmissionSuccessful = submissionSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffDistanceSigStrikeAttempted() {
		return this.offDistanceSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefDistanceSigStrikeAttempted() {
		return this.defDistanceSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setDistanceSigStrikeAttemped(Double distanceSigStrikeAttempted) {
		this.offDistanceSigStrikeAttempted = distanceSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffDistanceSigStrikeAccuracy() {
		return this.offDistanceSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefDistanceSigStrikeAccuracy() {
		return this.defDistanceSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setDistanceSigStrikeAccuracy(Double distanceSigStrikeAccuracy) {
		this.offDistanceSigStrikeAccuracy = distanceSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffDistanceSigStrikeSuccessful() {
		return this.offDistanceSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefDistanceSigStrikeSuccessful() {
		return this.defDistanceSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setDistanceSigStrikeSuccessful(Double distanceSigStrikeSuccessful) {
		this.offDistanceSigStrikeSuccessful = distanceSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffClinchSigStrikeAttempted() {
		return this.offClinchSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefClinchSigStrikeAttempted() {
		return this.defClinchSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setClinchSigStrikeAttemped(Double clinchSigStrikeAttempted) {
		this.offClinchSigStrikeAttempted = clinchSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffClinchSigStrikeAccuracy() {
		return this.offClinchSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefClinchSigStrikeAccuracy() {
		return this.defClinchSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setClinchSigStrikeAccuracy(Double clinchSigStrikeAccuracy) {
		this.offClinchSigStrikeAccuracy = clinchSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffClinchSigStrikeSuccessful() {
		return this.offClinchSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefClinchSigStrikeSuccessful() {
		return this.defClinchSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setClinchSigStrikeSuccessful(Double clinchSigStrikeSuccessful) {
		this.offClinchSigStrikeSuccessful = clinchSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffGroundSigStrikeAttempted() {
		return this.offGroundSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefGroundSigStrikeAttempted() {
		return this.defGroundSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setGroundSigStrikeAttemped(Double groundSigStrikeAttempted) {
		this.offGroundSigStrikeAttempted = groundSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffGroundSigStrikeAccuracy() {
		return this.offGroundSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefGroundSigStrikeAccuracy() {
		return this.defGroundSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setGroundSigStrikeAccuracy(Double groundSigStrikeAccuracy) {
		this.offGroundSigStrikeAccuracy = groundSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffGroundSigStrikeSuccessful() {
		return this.offGroundSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefGroundSigStrikeSuccessful() {
		return this.defGroundSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setGroundSigStrikeSuccessful(Double groundSigStrikeSuccessful) {
		this.offGroundSigStrikeSuccessful = groundSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffHeadSigStrikeAttempted() {
		return this.offHeadSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefHeadSigStrikeAttempted() {
		return this.defHeadSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setHeadSigStrikeAttemped(Double headSigStrikeAttempted) {
		this.offHeadSigStrikeAttempted = headSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffHeadSigStrikeAccuracy() {
		return this.offHeadSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefHeadSigStrikeAccuracy() {
		return this.defHeadSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setHeadSigStrikeAccuracy(Double headSigStrikeAccuracy) {
		this.offHeadSigStrikeAccuracy = headSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffHeadSigStrikeSuccessful() {
		return this.offHeadSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefHeadSigStrikeSuccessful() {
		return this.defHeadSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setHeadSigStrikeSuccessful(Double headSigStrikeSuccessful) {
		this.offHeadSigStrikeSuccessful = headSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffBodySigStrikeAttempted() {
		return this.offBodySigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefBodySigStrikeAttempted() {
		return this.defBodySigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setBodySigStrikeAttemped(Double bodySigStrikeAttempted) {
		this.offBodySigStrikeAttempted = bodySigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffBodySigStrikeAccuracy() {
		return this.offBodySigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefBodySigStrikeAccuracy() {
		return this.defBodySigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setBodySigStrikeAccuracy(Double bodySigStrikeAccuracy) {
		this.offBodySigStrikeAccuracy = bodySigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffBodySigStrikeSuccessful() {
		return this.offBodySigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefBodySigStrikeSuccessful() {
		return this.defBodySigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setBodySigStrikeSuccessful(Double bodySigStrikeSuccessful) {
		this.offBodySigStrikeSuccessful = bodySigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffLegSigStrikeAttempted() {
		return this.offLegSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefLegSigStrikeAttempted() {
		return this.defLegSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setLegSigStrikeAttemped(Double legSigStrikeAttempted) {
		this.offLegSigStrikeAttempted = legSigStrikeAttempted;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffLegSigStrikeAccuracy() {
		return this.offLegSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefLegSigStrikeAccuracy() {
		return this.defLegSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setLegSigStrikeAccuracy(Double legSigStrikeAccuracy) {
		this.offLegSigStrikeAccuracy = legSigStrikeAccuracy;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getOffLegSigStrikeSuccessful() {
		return this.offLegSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDefLegSigStrikeSuccessful() {
		return this.defLegSigStrikeSuccessful;
	}

	/**
	 * @return the distanceStrikeAttemped
	 */
	public void setLegSigStrikeSuccessful(Double legSigStrikeSuccessful) {
		this.offLegSigStrikeSuccessful = legSigStrikeSuccessful;
	}

	/**
	 * @return the boutOid
	 */
	public String getBoutOid() {
		return boutOid;
	}

	/**
	 * @param boutOid the boutOid to set
	 */
	public void setBoutOid(String boutOid) {
		this.boutOid = boutOid;
	}

	/**
	 * @return the fighterOid
	 */
	public String getFighterOid() {
		return fighterOid;
	}

	/**
	 * @param fighterOid the fighterOid to set
	 */
	public void setFighterOid(String fighterOid) {
		this.fighterOid = fighterOid;
	}

	/**
	 * @return the score
	 */
	public Double getScore() {
		if (this.offScore == null || this.defScore == null) {
			return null;
		} else {
			return this.offScore - this.defScore;
		}
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Double score) {
		this.offScore = score;
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
	 * @return the weightClass
	 */
	public WeightClassEnum getWeightClass() {
		return weightClass;
	}
	
	/**
	 * @return the weightClass
	 */
	public Integer getWeight() {
		return weightClass.getWeight();
	}

	/**
	 * @param weightClass the weightClass to set
	 */
	public void setWeightClass(WeightClassEnum weightClass) {
		this.weightClass = weightClass;
	}

	/**
	 * @return the seconds
	 */
	public Integer getSeconds() {
		return seconds;
	}

	/**
	 * @param seconds the seconds to set
	 */
	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	
	public Integer getOffFinish() {
		if (this.offTkoKo > 0 || this.offSubmissionSuccessful > 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public Integer getDefFinish() {
		if (this.defTkoKo > 0 || this.defSubmissionSuccessful > 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public Integer getFinish() {
		if (this.offTkoKo > 0 || this.offSubmissionSuccessful > 0 || this.defTkoKo > 0 || this.defSubmissionSuccessful > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * @return the offSubmissionScore
	 */
	public Double getOffSubmissionScore() {
		return offSubmissionScore;
	}

	/**
	 * @return the offSubmissionScore
	 */
	public Double getDefSubmissionScore() {
		return this.defSubmissionScore;
	}
	
	/**
	 * @param offSubmissionScore the offSubmissionScore to set
	 */
	public void setSubmissionScore(Double offSubmissionScore) {
		this.offSubmissionScore = offSubmissionScore;
	}

	/**
	 * @return the offKoScore
	 */
	public Double getOffKoScore() {
		return offKoScore;
	}
	
	/**
	 * @return the offKoScore
	 */
	public Double getDefKoScore() {
		return this.defKoScore;
	}

	/**
	 * @param offKoScore the offKoScore to set
	 */
	public void setKoScore(Double offKoScore) {
		this.offKoScore = offKoScore;
	}
	
	
}
