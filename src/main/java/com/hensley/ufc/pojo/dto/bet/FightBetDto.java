package com.hensley.ufc.pojo.dto.bet;

import java.math.BigInteger;

import com.hensley.ufc.enums.WeightClassEnum;

public class FightBetDto {
	private String boutOid;
	private Double mlOdds;
	private Double expOdds;
	private String fighterName;
	private WeightClassEnum weightClass;
	private BigInteger count;
	
	
	public FightBetDto() {
		
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
	 * @return the mlOdds
	 */
	public Double getMlOdds() {
		return mlOdds;
	}


	/**
	 * @param mlOdds the mlOdds to set
	 */
	public void setMlOdds(Double mlOdds) {
		this.mlOdds = mlOdds;
	}


	/**
	 * @return the expOdds
	 */
	public Double getExpOdds() {
		return expOdds;
	}


	/**
	 * @param expOdds the expOdds to set
	 */
	public void setExpOdds(Double expOdds) {
		this.expOdds = expOdds;
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
	 * @return the weightClass
	 */
	public WeightClassEnum getWeightClass() {
		return weightClass;
	}


	/**
	 * @param weightClass the weightClass to set
	 */
	public void setWeightClass(Integer dbKey) {
		this.weightClass = WeightClassEnum.valueOfDbKey(dbKey);
	}


	/**
	 * @return the count
	 */
	public BigInteger getCount() {
		return count;
	}


	/**
	 * @param count the count to set
	 */
	public void setCount(BigInteger count) {
		this.count = count;
	}
	
	
}
