package com.hensley.ufc.pojo.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class GeneralApiResponse {

	protected HttpStatus status;
	protected Date timestamp;
	protected String errorMsg;
	
	public GeneralApiResponse() {
		
	}
	
	public GeneralApiResponse(HttpStatus status, String errorMsg) {
		this.status = status;
		this.timestamp = new Date();
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
