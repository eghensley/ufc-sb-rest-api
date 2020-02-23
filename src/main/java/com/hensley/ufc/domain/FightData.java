package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FIGHT")
public class FightData extends BaseAuditEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2506269570657528171L;
	
	@Column(name = "FIGHT_ID", nullable = false)
	private String fightId;
	@ManyToOne
	@JoinColumn(name = "LOCATION_OID", referencedColumnName = "OID", nullable = false)
	private LocationData location;
	@Column(name = "FIGHT_NAME", nullable = false)
	private String fightName;
	@Column(name = "FIGHT_DATE", nullable = false)
	private Date fightDate;
	@Column(name = "F_COMPLETED", nullable = false)
	private Boolean completed;
	@OneToMany(mappedBy = "fight", cascade = CascadeType.PERSIST)
	private List<BoutData> bouts;
	@Column(name = "MMA_DEC_FIGHT_URL", nullable = true)
	private String mmaDecFightUrl;
	
	public FightData() {
		this.bouts = new ArrayList<>();
		this.completed = false;
	}

	public FightData(String fightId, String fightName, Date fightDate, LocationData location) {
		this.fightDate = fightDate;
		this.fightName = fightName;
		this.location = location;
		this.fightId = fightId;
		this.bouts = new ArrayList<>();
		this.completed = false;
	}

	public List<String> retrieveBoutList(){
		List<String> boutList = new ArrayList<>();
		for (BoutData bout: this.bouts) {
			boutList.add(bout.getBoutId());
		}
		return boutList;
	}
	
	public boolean ifBoutIdInc(String boutId) {
		List<String> boutList = new ArrayList<>();
		for (BoutData bout: this.bouts) {
			boutList.add(bout.getBoutId());
		}
		if (boutList.contains(boutId)) {
			return true;
		} else {
			return false;
		}
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
	public LocationData getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(LocationData location) {
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
	public List<BoutData> getBouts() {
		return bouts;
	}

	/**
	 * @param bouts the bouts to set
	 */
	public void setBouts(List<BoutData> bouts) {
		if (bouts != null) {
			for (BoutData bout : bouts) {
				addBout(bout);
			}
		}
	}

	public void addBout(BoutData bout) {
		this.bouts.add(bout);
		bout.setFight(this);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ Objects.hash(bouts, completed, fightDate, fightId, fightName, location, mmaDecFightUrl);
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
		if (!(obj instanceof FightData)) {
			return false;
		}
		FightData other = (FightData) obj;
		return Objects.equals(bouts, other.bouts) && Objects.equals(completed, other.completed)
				&& Objects.equals(fightDate, other.fightDate) && Objects.equals(fightId, other.fightId)
				&& Objects.equals(fightName, other.fightName) && Objects.equals(location, other.location)
				&& Objects.equals(mmaDecFightUrl, other.mmaDecFightUrl);
	}

	@Override
	public String toString() {
		return "FightData [fightId=" + fightId + ", location=" + location + ", fightName=" + fightName + ", fightDate="
				+ fightDate + ", completed=" + completed + ", bouts=" + bouts + "]";
	}

}
