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
@Table(name = "SIG_STRIKE_TARGET_DATA")
public class SigStrikeTargetData extends BaseAuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4999248725873916864L;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "STRIKE_DATA_OID", referencedColumnName = "OID", nullable = false)
	private StrikeData strikeData;
	
	@Column(name = "HEAD_STRIKE_ATTEMPTED", nullable = false)
	private Double headStrikeAttemped;
	@Column(name = "HEAD_STRIKE_SUCCESSFUL", nullable = false)
	private Double headStrikeSuccessful;
	
	@Column(name = "BODY_STRIKE_ATTEMPTED", nullable = false)
	private Double bodyStrikeAttemped;
	@Column(name = "BODY_STRIKE_SUCCESSFUL", nullable = false)
	private Double bodyStrikeSuccessful;	
	
	@Column(name = "LEG_STRIKE_ATTEMPTED", nullable = false)
	private Double legStrikeAttemped;
	@Column(name = "LEG_STRIKE_SUCCESSFUL", nullable = false)
	private Double legStrikeSuccessful;	
	
	public SigStrikeTargetData() {
		
	}

	/**
	 * @return the headStrikeAttemped
	 */
	public Double getHeadStrikeAttemped() {
		return headStrikeAttemped;
	}

	/**
	 * @param headStrikeAttemped the headStrikeAttemped to set
	 */
	public void setHeadStrikeAttemped(Integer headStrikeAttemped) {
		this.headStrikeAttemped = Double.valueOf(headStrikeAttemped);
	}

	/**
	 * @return the headStrikeSuccessful
	 */
	public Double getHeadStrikeSuccessful() {
		return headStrikeSuccessful;
	}

	/**
	 * @param headStrikeSuccessful the headStrikeSuccessful to set
	 */
	public void setHeadStrikeSuccessful(Integer headStrikeSuccessful) {
		this.headStrikeSuccessful = Double.valueOf(headStrikeSuccessful);
	}

	/**
	 * @return the headStrikeAccuracy
	 */
	public Double getHeadStrikeAccuracy() {
		return this.headStrikeSuccessful/this.headStrikeAttemped;
	}
	
	/**
	 * @return the bodyStrikeAttemped
	 */
	public Double getBodyStrikeAttemped() {
		return bodyStrikeAttemped;
	}

	/**
	 * @param bodyStrikeAttemped the bodyStrikeAttemped to set
	 */
	public void setBodyStrikeAttemped(Integer bodyStrikeAttemped) {
		this.bodyStrikeAttemped = Double.valueOf(bodyStrikeAttemped);
	}

	/**
	 * @return the bodyStrikeSuccessful
	 */
	public Double getBodyStrikeSuccessful() {
		return bodyStrikeSuccessful;
	}

	/**
	 * @param bodyStrikeSuccessful the bodyStrikeSuccessful to set
	 */
	public void setBodyStrikeSuccessful(Integer bodyStrikeSuccessful) {
		this.bodyStrikeSuccessful = Double.valueOf(bodyStrikeSuccessful);
	}

	/**
	 * @return the bodyStrikeAccuracy
	 */
	public Double getBodyStrikeAccuracy() {
		return this.bodyStrikeSuccessful/this.bodyStrikeAttemped;
	}
	
	/**
	 * @return the legStrikeAttemped
	 */
	public Double getLegStrikeAttemped() {
		return legStrikeAttemped;
	}

	/**
	 * @param legStrikeAttemped the legStrikeAttemped to set
	 */
	public void setLegStrikeAttemped(Integer legStrikeAttemped) {
		this.legStrikeAttemped = Double.valueOf(legStrikeAttemped);
	}

	/**
	 * @return the legStrikeSuccessful
	 */
	public Double getLegStrikeSuccessful() {
		return legStrikeSuccessful;
	}

	/**
	 * @param legStrikeSuccessful the legStrikeSuccessful to set
	 */
	public void setLegStrikeSuccessful(Integer legStrikeSuccessful) {
		this.legStrikeSuccessful = Double.valueOf(legStrikeSuccessful);
	}
	
	/**
	 * @return the legStrikeAccuracy
	 */
	public Double getLegStrikeAccuracy() {
		return this.legStrikeSuccessful/this.legStrikeAttemped;
	}

	/**
	 * @param strikeData the strikeData to set
	 */
	public void setStrikeData(StrikeData strikeData) {
		this.strikeData = strikeData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(bodyStrikeAttemped, bodyStrikeSuccessful, headStrikeAttemped,
				headStrikeSuccessful, legStrikeAttemped, legStrikeSuccessful, strikeData);
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
		if (!(obj instanceof SigStrikeTargetData)) {
			return false;
		}
		SigStrikeTargetData other = (SigStrikeTargetData) obj;
		return Objects.equals(bodyStrikeAttemped, other.bodyStrikeAttemped)
				&& Objects.equals(bodyStrikeSuccessful, other.bodyStrikeSuccessful)
				&& Objects.equals(headStrikeAttemped, other.headStrikeAttemped)
				&& Objects.equals(headStrikeSuccessful, other.headStrikeSuccessful)
				&& Objects.equals(legStrikeAttemped, other.legStrikeAttemped)
				&& Objects.equals(legStrikeSuccessful, other.legStrikeSuccessful)
				&& Objects.equals(strikeData, other.strikeData);
	}
	
}
