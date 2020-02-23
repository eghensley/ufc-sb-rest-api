package com.hensley.ufc.pojo.dto;

import java.util.Date;

import com.hensley.ufc.enums.FighterStanceEnum;

public class FighterDto {
	private String fighterId;
	private String fighterName;	
	private Integer reach;		
	private Integer height;	
	private Date dob;	
	private FighterStanceEnum stance;
	
	public FighterDto() {
		
	}

	/**
	 * @return the fighterId
	 */
	public String getFighterId() {
		return fighterId;
	}

	/**
	 * @param fighterId the fighterId to set
	 */
	public void setFighterId(String fighterId) {
		this.fighterId = fighterId;
	}

	/**
	 * @return the fighterName
	 */
	public String getFighterName() {
		return fighterName;
	}

	/**
	 * @param fighterName the fighterName to set
	 */
	public void setFighterName(String fighterName) {
		this.fighterName = fighterName;
	}

	/**
	 * @return the reach
	 */
	public Integer getReach() {
		return reach;
	}

	/**
	 * @param reach the reach to set
	 */
	public void setReach(Integer reach) {
		this.reach = reach;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the stance
	 */
	public FighterStanceEnum getStance() {
		return stance;
	}

	/**
	 * @param stance the stance to set
	 */
	public void setStance(FighterStanceEnum stance) {
		this.stance = stance;
	}
	
	
}
