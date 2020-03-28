package com.hensley.ufc.pojo.dto.bout;

import com.hensley.ufc.enums.WeightClassEnum;

public class BoutSummaryDto {
	private String fightOid;
	private WeightClassEnum weightClass;
	private String fighterOid;
	private Integer reach;		
	private Integer height;		
	
	public BoutSummaryDto() {
		
	}

	/**
	 * @return the fightOid
	 */
	public String getFightOid() {
		return fightOid;
	}

	/**
	 * @return the weightClass
	 */
	public WeightClassEnum getWeightClass() {
		return weightClass;
	}

	/**
	 * @return the fighterOid
	 */
	public String getFighterOid() {
		return fighterOid;
	}

	/**
	 * @return the reach
	 */
	public Integer getReach() {
		return reach;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}
	
	
}
