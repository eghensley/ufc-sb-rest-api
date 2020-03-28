package com.hensley.ufc.pojo.dto.strike;

public class SigStrikePositionDto {

	private Double distanceStrikeAttemped;
	private Double distanceStrikeSuccessful;

	private Double clinchStrikeAttemped;
	private Double clinchStrikeSuccessful;

	private Double groundStrikeAttemped;
	private Double groundStrikeSuccessful;

	public SigStrikePositionDto() {

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
		if (this.distanceStrikeAttemped == null || this.distanceStrikeSuccessful == null) {
			return null;
		} else if (this.distanceStrikeAttemped == 0) {
			return 0.0;
		} else {
			return this.distanceStrikeSuccessful / this.distanceStrikeAttemped;
		}
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
		if (this.clinchStrikeSuccessful == null || this.clinchStrikeAttemped == null) {
			return null;
		} else if (this.clinchStrikeAttemped == 0) {
			return 0.0;
		} else {
			return this.clinchStrikeSuccessful / this.clinchStrikeAttemped;
		}
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
		if (this.groundStrikeAttemped == null || this.groundStrikeSuccessful == null) {
			return null;
		} else if (this.groundStrikeAttemped == 0) {
			return 0.0;
		} else {
			return this.groundStrikeSuccessful / this.groundStrikeAttemped;
		}
	}
}
