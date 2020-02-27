package com.hensley.ufc.pojo.dto.fight;

import java.util.Date;

public class BasicFightDto {

	private String fightId;
	private String fightName;
	private Date fightDate;
	private String oid;

	public BasicFightDto() {
		
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
	 * @return the fightName
	 */
	public String getFightName() {
		return fightName;
	}

	/**
	 * @param fightName the fightName to set
	 */
	public void setFightName(String fightName) {
		this.fightName = fightName;
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
	
	
}
