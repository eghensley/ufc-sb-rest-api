package com.hensley.ufc.pojo.dto.fight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.hensley.ufc.pojo.dto.SuperAuditDto;
import com.hensley.ufc.pojo.dto.bout.BoutBetHistGetDto;
import com.hensley.ufc.pojo.dto.location.LocationDto;

public class FightBetHistoryDto extends SuperAuditDto {
	private String fightId;
	private LocationDto location;
	private String fightName;
	private Date fightDate;
	private Boolean completed;
	private List<BoutBetHistGetDto> bouts;
	private String mmaDecFightUrl;
	private String bestFightOddsUrl;

	public FightBetHistoryDto() {
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
	 * @return the bouts
	 */
	public Collection<BoutBetHistGetDto> getBouts() {
		return bouts;
	}

	public void addBout(BoutBetHistGetDto bout) {
		if (!CollectionUtils.isEmpty(bout.getFighterBoutXRefs())) {
			this.bouts.add(bout);
		}
	}
	/**
	 * @param bouts the bouts to set
	 */
	public void setBouts(Collection<BoutBetHistGetDto> bouts) {
		if (!CollectionUtils.isEmpty(bouts)) {
			for (BoutBetHistGetDto bout: bouts) {
				addBout(bout);
			}
		}
	}

	/**
	 * @return the mmaDecFightUrl
	 */
	public String getMmaDecFightUrl() {
		return mmaDecFightUrl;
	}

	/**
	 * @param mmaDecFightUrl the mmaDecFightUrl to set
	 */
	public void setMmaDecFightUrl(String mmaDecFightUrl) {
		this.mmaDecFightUrl = mmaDecFightUrl;
	}

	/**
	 * @return the bestFightOddsUrl
	 */
	public String getBestFightOddsUrl() {
		return bestFightOddsUrl;
	}

	/**
	 * @param bestFightOddsUrl the bestFightOddsUrl to set
	 */
	public void setBestFightOddsUrl(String bestFightOddsUrl) {
		this.bestFightOddsUrl = bestFightOddsUrl;
	}
	
}