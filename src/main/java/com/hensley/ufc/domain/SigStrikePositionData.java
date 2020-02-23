package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SIG_STRIKE_POSITION_DATA")
public class SigStrikePositionData extends BaseAuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7082201610925793684L;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "STRIKE_DATA_OID", referencedColumnName = "OID", nullable = false)
	private StrikeData strikeData;
	
	@Column(name = "DISTANCE_STRIKE_ATTEMPTED", nullable = false)
	private Double distanceStrikeAttemped;
	@Column(name = "DISTANCE_STRIKE_SUCCESSFUL", nullable = false)
	private Double distanceStrikeSuccessful;
	
	@Column(name = "CLINCH_STRIKE_ATTEMPTED", nullable = false)
	private Double clinchStrikeAttemped;
	@Column(name = "CLINCH_STRIKE_SUCCESSFUL", nullable = false)
	private Double clinchStrikeSuccessful;	
	
	@Column(name = "GROUND_STRIKE_ATTEMPTED", nullable = false)
	private Double groundStrikeAttemped;
	@Column(name = "GROUND_STRIKE_SUCCESSFUL", nullable = false)
	private Double groundStrikeSuccessful;	
	
	public SigStrikePositionData() {
		
	}
	
	
	/**
	 * @param strikeData the strikeData to set
	 */
	public void setStrikeData(StrikeData strikeData) {
		this.strikeData = strikeData;
	}


	/**
	 * @return the distanceStrikeAttemped
	 */
	public Double getDistanceStrikeAttemped() {
		return distanceStrikeAttemped;
	}
	
	/**
	 * @param distanceStrikeAttemped the distanceStrikeAttemped to set
	 */
	public void setDistanceStrikeAttemped(Integer distanceStrikeAttemped) {
		this.distanceStrikeAttemped = Double.valueOf(distanceStrikeAttemped);
	}
	
	/**
	 * @return the distanceStrikeSuccessful
	 */
	public Double getDistanceStrikeSuccessful() {
		return distanceStrikeSuccessful;
	}
	
	/**
	 * @param distanceStrikeSuccessful the distanceStrikeSuccessful to set
	 */
	public void setDistanceStrikeSuccessful(Integer distanceStrikeSuccessful) {
		this.distanceStrikeSuccessful = Double.valueOf(distanceStrikeSuccessful);
	}
	
	/**
	 * @return the distanceStrikeAccuracy
	 */
	public Double getDistanceStrikeAccuracy() {
		return this.distanceStrikeSuccessful/this.distanceStrikeAttemped;
	}
	
	/**
	 * @return the clinchStrikeAttemped
	 */
	public Double getClinchStrikeAttemped() {
		return clinchStrikeAttemped;
	}
	
	/**
	 * @param clinchStrikeAttemped the clinchStrikeAttemped to set
	 */
	public void setClinchStrikeAttemped(Integer clinchStrikeAttemped) {
		this.clinchStrikeAttemped = Double.valueOf(clinchStrikeAttemped);
	}
	
	/**
	 * @return the clinchStrikeSuccessful
	 */
	public Double getClinchStrikeSuccessful() {
		return clinchStrikeSuccessful;
	}
	
	/**
	 * @param clinchStrikeSuccessful the clinchStrikeSuccessful to set
	 */
	public void setClinchStrikeSuccessful(Integer clinchStrikeSuccessful) {
		this.clinchStrikeSuccessful = Double.valueOf(clinchStrikeSuccessful);
	}
	
	/**
	 * @return the clinchStrikeAccuracy
	 */
	public Double getClinchStrikeAccuracy() {
		return this.clinchStrikeSuccessful/this.clinchStrikeAttemped;
	}
	
	/**
	 * @return the groundStrikeAttemped
	 */
	public Double getGroundStrikeAttemped() {
		return groundStrikeAttemped;
	}
	
	/**
	 * @param groundStrikeAttemped the groundStrikeAttemped to set
	 */
	public void setGroundStrikeAttemped(Integer groundStrikeAttemped) {
		this.groundStrikeAttemped = Double.valueOf(groundStrikeAttemped);
	}
	
	/**
	 * @return the groundStrikeSuccessful
	 */
	public Double getGroundStrikeSuccessful() {
		return groundStrikeSuccessful;
	}
	
	/**
	 * @param groundStrikeSuccessful the groundStrikeSuccessful to set
	 */
	public void setGroundStrikeSuccessful(Integer groundStrikeSuccessful) {
		this.groundStrikeSuccessful = Double.valueOf(groundStrikeSuccessful);
	}	
	
	/**
	 * @return the groundStrikeAccuracy
	 */
	public Double getGroundStrikeAccuracy() {
		return this.groundStrikeSuccessful/this.groundStrikeAttemped;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(clinchStrikeAttemped, clinchStrikeSuccessful, distanceStrikeAttemped,
				distanceStrikeSuccessful, groundStrikeAttemped, groundStrikeSuccessful, strikeData);
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
		if (!(obj instanceof SigStrikePositionData)) {
			return false;
		}
		SigStrikePositionData other = (SigStrikePositionData) obj;
		return Objects.equals(clinchStrikeAttemped, other.clinchStrikeAttemped)
				&& Objects.equals(clinchStrikeSuccessful, other.clinchStrikeSuccessful)
				&& Objects.equals(distanceStrikeAttemped, other.distanceStrikeAttemped)
				&& Objects.equals(distanceStrikeSuccessful, other.distanceStrikeSuccessful)
				&& Objects.equals(groundStrikeAttemped, other.groundStrikeAttemped)
				&& Objects.equals(groundStrikeSuccessful, other.groundStrikeSuccessful)
				&& Objects.equals(strikeData, other.strikeData);
	}


	
}
