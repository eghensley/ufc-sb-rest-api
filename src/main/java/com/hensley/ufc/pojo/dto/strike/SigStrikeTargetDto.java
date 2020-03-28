package com.hensley.ufc.pojo.dto.strike;

public class SigStrikeTargetDto {

	private Double headStrikeAttemped;
	private Double headStrikeSuccessful;

	private Double bodyStrikeAttemped;
	private Double bodyStrikeSuccessful;

	private Double legStrikeAttemped;
	private Double legStrikeSuccessful;

	public SigStrikeTargetDto() {

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
		if (this.headStrikeSuccessful == null || this.headStrikeAttemped == null) {
			return null;
		} else if (this.headStrikeAttemped == 0) {
			return 0.0;
		} else {
			return this.headStrikeSuccessful / this.headStrikeAttemped;
		}
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
		if (this.bodyStrikeSuccessful == null || this.bodyStrikeAttemped == null) {
			return null;
		} else if (this.bodyStrikeAttemped == 0) {
			return 0.0;
		} else {
			return this.bodyStrikeSuccessful / this.bodyStrikeAttemped;
		}
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
		if (this.legStrikeAttemped == null || this.legStrikeSuccessful == null) {
			return null;
		} else if (this.legStrikeAttemped == 0) {
			return 0.0;
		} else {
			return this.legStrikeSuccessful / this.legStrikeAttemped;
		}
	}

}
