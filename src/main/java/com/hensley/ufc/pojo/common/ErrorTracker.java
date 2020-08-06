package com.hensley.ufc.pojo.common;

import com.hensley.ufc.enums.SeverityEnum;
import com.hensley.ufc.enums.common.ErrorEnum;

public class ErrorTracker {
	private String fightId;
	private String fightOid;
	private String boutId;
	private String boutOid;
	private ErrorEnum errorType;
	private String errorStr;
	private String errorName;
	private SeverityEnum severity;
	
	public ErrorTracker() {
		this.fightId = "";
		this.fightOid = "";
		this.boutId = "";
		this.boutOid = "";
		this.errorType = ErrorEnum.NONE;
		this.errorName = "";
		this.severity = SeverityEnum.INFO;
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
	 * @return the fightOid
	 */
	public String getFightOid() {
		return fightOid;
	}

	/**
	 * @param fightOid the fightOid to set
	 */
	public void setFightOid(String fightOid) {
		this.fightOid = fightOid;
	}

	/**
	 * @return the boutId
	 */
	public String getBoutId() {
		return boutId;
	}

	/**
	 * @param boutId the boutId to set
	 */
	public void setBoutId(String boutId) {
		this.boutId = boutId;
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
	 * @return the errorType
	 */
	public ErrorEnum getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(ErrorEnum errorType) {
		this.errorType = errorType;
	}

	/**
	 * @return the errorStr
	 */
	public String getErrorStr() {
		return errorStr;
	}

	/**
	 * @param errorStr the errorStr to set
	 */
	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}

	/**
	 * @return the errorName
	 */
	public String getErrorName() {
		return errorName;
	}

	/**
	 * @param errorName the errorName to set
	 */
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	/**
	 * @return the severity
	 */
	public SeverityEnum getSeverity() {
		return severity;
	}

	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(SeverityEnum severity) {
		this.severity = severity;
	}
	
	
}
