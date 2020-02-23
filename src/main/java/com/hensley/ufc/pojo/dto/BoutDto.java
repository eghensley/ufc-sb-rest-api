package com.hensley.ufc.pojo.dto;

import java.util.List;

import com.hensley.ufc.enums.FightMethodEnum;
import com.hensley.ufc.enums.WeightClassEnum;

public class BoutDto extends SuperAuditDto {

	private String boutId;
	private WeightClassEnum weightClass;
	private Boolean champBout;
	private String fightId;
	private String fightOid;
	private Boolean completed;
	private Integer schedRounds;
	private String referee;
	private Integer finishRounds;
	private Integer finishTime;
	private FightMethodEnum finishMethod;
	private String finishDetails;
	private List<FighterBoutXRefDto> fighterBoutXRefs;
	
	public BoutDto() {
		
	}

	/**
	 * @return the boutId
	 */
	public String getBoutId() {
		return boutId;
	}

	/**
	 * @param boutId the boutId to set
	 */
	public void setBoutId(String boutId) {
		this.boutId = boutId;
	}

	/**
	 * @return the weightClass
	 */
	public WeightClassEnum getWeightClass() {
		return weightClass;
	}

	/**
	 * @param weightClass the weightClass to set
	 */
	public void setWeightClass(WeightClassEnum weightClass) {
		this.weightClass = weightClass;
	}

	/**
	 * @return the champBout
	 */
	public Boolean getChampBout() {
		return champBout;
	}

	/**
	 * @param champBout the champBout to set
	 */
	public void setChampBout(Boolean champBout) {
		this.champBout = champBout;
	}

	/**
	 * @return the fightId
	 */
	public String getFightId() {
		return fightId;
	}

	/**
	 * @param fightId the fightId to set
	 */
	public void setFightId(String fightId) {
		this.fightId = fightId;
	}

	/**
	 * @return the fightOid
	 */
	public String getFightOid() {
		return fightOid;
	}

	/**
	 * @param fightOid the fightOid to set
	 */
	public void setFightOid(String fightOid) {
		this.fightOid = fightOid;
	}

	/**
	 * @return the completed
	 */
	public Boolean getCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return the schedRounds
	 */
	public Integer getSchedRounds() {
		return schedRounds;
	}

	/**
	 * @param schedRounds the schedRounds to set
	 */
	public void setSchedRounds(Integer schedRounds) {
		this.schedRounds = schedRounds;
	}

	/**
	 * @return the referee
	 */
	public String getReferee() {
		return referee;
	}

	/**
	 * @param referee the referee to set
	 */
	public void setReferee(String referee) {
		this.referee = referee;
	}

	/**
	 * @return the finishRounds
	 */
	public Integer getFinishRounds() {
		return finishRounds;
	}

	/**
	 * @param finishRounds the finishRounds to set
	 */
	public void setFinishRounds(Integer finishRounds) {
		this.finishRounds = finishRounds;
	}

	/**
	 * @return the finishTime
	 */
	public Integer getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(Integer finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @return the finishMethod
	 */
	public FightMethodEnum getFinishMethod() {
		return finishMethod;
	}

	/**
	 * @param finishMethod the finishMethod to set
	 */
	public void setFinishMethod(FightMethodEnum finishMethod) {
		this.finishMethod = finishMethod;
	}

	/**
	 * @return the finishDetails
	 */
	public String getFinishDetails() {
		return finishDetails;
	}

	/**
	 * @param finishDetails the finishDetails to set
	 */
	public void setFinishDetails(String finishDetails) {
		this.finishDetails = finishDetails;
	}

	/**
	 * @return the fighterBoutXRefs
	 */
	public List<FighterBoutXRefDto> getFighterBoutXRefs() {
		return fighterBoutXRefs;
	}

	/**
	 * @param fighterBoutXRefs the fighterBoutXRefs to set
	 */
	public void setFighterBoutXRefs(List<FighterBoutXRefDto> fighterBoutXRefs) {
		this.fighterBoutXRefs = fighterBoutXRefs;
	}
	
}
