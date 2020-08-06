package com.hensley.ufc.pojo.dto.fight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hensley.ufc.pojo.dto.SuperAuditDto;
import com.hensley.ufc.pojo.dto.bout.BasicBoutInfoDto;
import com.hensley.ufc.pojo.dto.location.LocationDto;

public class BasicFightInfoDto extends SuperAuditDto {
	private String fightId;
	private LocationDto location;
	private String fightName;
	private Date fightDate;
	private List<BasicBoutInfoDto> bouts;


	public BasicFightInfoDto() {
		this.bouts = new ArrayList<>();
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
	 * @return the location
	 */
	public LocationDto getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(LocationDto location) {
		this.location = location;
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
	 * @return the bouts
	 */
	public List<BasicBoutInfoDto> getBouts() {
		return bouts;
	}

	/**
	 * @param bouts the bouts to set
	 */
	public void setBouts(List<BasicBoutInfoDto> bouts) {
		this.bouts = bouts;
	}

	
}