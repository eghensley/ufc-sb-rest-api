package com.hensley.ufc.pojo.dto.rank;

import java.util.Date;

import com.hensley.ufc.enums.WeightClassEnum;
import com.hensley.ufc.pojo.dto.SuperAuditDto;
import com.hensley.ufc.pojo.dto.fighter.FighterBoutXRefEloDto;
import com.hensley.ufc.pojo.dto.fighter.FighterDto;

public class FighterRankElementDto extends SuperAuditDto {

	private FighterDto fighter;	
	private Date fightDate;
	private WeightClassEnum weightClass;	
	private FighterBoutXRefEloDto fighterBoutXRef;
	
	public FighterRankElementDto() {
		
	}

	/**
	 * @return the fighter
	 */
	public FighterDto getFighter() {
		return fighter;
	}

	/**
	 * @param fighter the fighter to set
	 */
	public void setFighter(FighterDto fighter) {
		this.fighter = fighter;
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
	 * @return the fighterBoutXRef
	 */
	public FighterBoutXRefEloDto getFighterBoutXRef() {
		return fighterBoutXRef;
	}

	/**
	 * @param fighterBoutXRef the fighterBoutXRef to set
	 */
	public void setFighterBoutXRef(FighterBoutXRefEloDto fighterBoutXRef) {
		this.fighterBoutXRef = fighterBoutXRef;
	}
	
}
