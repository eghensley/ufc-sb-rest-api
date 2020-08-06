package com.hensley.ufc.pojo.dto.bout;

import java.util.Date;
import java.util.List;

import com.hensley.ufc.enums.GenderEnum;
import com.hensley.ufc.enums.WeightClassEnum;
import com.hensley.ufc.pojo.dto.SuperAuditDto;
import com.hensley.ufc.pojo.dto.fighter.BasicFighterBoutXrefDto;

public class BasicBoutInfoDto extends SuperAuditDto {

	private String boutId;
	private WeightClassEnum weightClass;
	private Boolean champBout;
	private String fightId;
	private Date fightDate;
	private String fightOid;
	private Boolean completed;
	private Integer schedRounds;
	private List<BasicFighterBoutXrefDto> fighterBoutXRefs;
	private GenderEnum gender;
	
	public BasicBoutInfoDto() {
		
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
		this.gender = weightClass.gender;
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
	 * @return the fighterBoutXRefs
	 */
	public List<BasicFighterBoutXrefDto> getFighterBoutXRefs() {
		return fighterBoutXRefs;
	}

	/**
	 * @param fighterBoutXRefs the fighterBoutXRefs to set
	 */
	public void setFighterBoutXRefs(List<BasicFighterBoutXrefDto> fighterBoutXRefs) {
		this.fighterBoutXRefs = fighterBoutXRefs;
	}

	/**
	 * @return the gender
	 */
	public GenderEnum getGender() {
		return gender;
	}

	/**
	 * @return the fightDate
	 */
	public Date getFightDate() {
		return fightDate;
	}

	/**
	 * @param fightDate the fightDate to set
	 */
	public void setFightDate(Date fightDate) {
		this.fightDate = fightDate;
	}
	
	
}