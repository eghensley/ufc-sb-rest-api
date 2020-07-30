package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.hensley.ufc.enums.WeightClassEnum;

@Entity
@Table(name = "FIGHTER_RANK")
public class FighterRankData extends BaseAuditEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1454028610253737421L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FIGHTER_OID", referencedColumnName = "OID", nullable = false)
	private FighterData fighter;
	
	@Column(name = "FIGHT_DATE", nullable = false)
	private Date fightDate;
	
	@Column(name = "WEIGHT_CLASS", nullable = false)
	private WeightClassEnum weightClass;
	
	@OneToOne(fetch = FetchType.EAGER)
	private FighterBoutXRefData fighterBoutXRef;
	
	public FighterRankData() {
		
	}

	/**
	 * @return the fighter
	 */
	public FighterData getFighter() {
		return fighter;
	}

	/**
	 * @param fighter the fighter to set
	 */
	public void setFighter(FighterData fighter) {
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
	public FighterBoutXRefData getFighterBoutXRef() {
		return fighterBoutXRef;
	}

	/**
	 * @param fighterBoutXRef the fighterBoutXRef to set
	 */
	public void setFighterBoutXRef(FighterBoutXRefData fighterBoutXRef) {
		this.fighterBoutXRef = fighterBoutXRef;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(fightDate, fighter, fighterBoutXRef, weightClass);
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
		if (!(obj instanceof FighterRankData)) {
			return false;
		}
		FighterRankData other = (FighterRankData) obj;
		return Objects.equals(fightDate, other.fightDate) && Objects.equals(fighter, other.fighter)
				&& Objects.equals(fighterBoutXRef, other.fighterBoutXRef) && weightClass == other.weightClass;
	}


}
