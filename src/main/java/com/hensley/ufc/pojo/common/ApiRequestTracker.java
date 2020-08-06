package com.hensley.ufc.pojo.common;

import com.hensley.ufc.enums.common.ErrorEnum;
import com.hensley.ufc.enums.common.FunctionEnum;

public class ApiRequestTracker {
	private String fightId;
	private String fightOid;
	private String boutId;
	private String boutOid;
	private String path;
	private Long startTime;
	private Long endTime;
	private ErrorTracker error;
	private FunctionEnum function;
	private Object body;
	private String pw;
	
	public ApiRequestTracker() {
		this.startTime = System.nanoTime();
		this.error = new ErrorTracker();
		this.fightId = "";
		this.fightOid = "";
		this.boutId = "";
		this.boutOid = "";
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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the startTime
	 */
	public Long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the error
	 */
	public ErrorTracker getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(ErrorTracker error) {
		this.error = error;
	}

	/**
	 * @return the function
	 */
	public FunctionEnum getFunction() {
		return function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(FunctionEnum function) {
		this.function = function;
	}

	/**
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(Object body) {
		this.body = body;
	}

	/**
	 * @return the pw
	 */
	public String getPw() {
		return pw;
	}

	/**
	 * @param pw the pw to set
	 */
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public void resetError() {
		this.error = new ErrorTracker();
	}
	
	public void setErrorStr(String errorName, String errorStr, ErrorEnum errorType) {
		this.error.setErrorName(errorName);
		this.error.setErrorStr(errorStr);
		this.error.setErrorType(errorType);
	}
}
