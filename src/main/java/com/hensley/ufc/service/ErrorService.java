package com.hensley.ufc.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hensley.ufc.pojo.response.ParseResponse;

@Service
public class ErrorService {
	private static final Logger LOG = Logger.getLogger(ErrorService.class.toString());

	public ParseResponse handleParseError(String errorStr, Exception e, ParseResponse response) {
		LOG.log(Level.SEVERE, errorStr);
		LOG.log(Level.WARNING, e.getMessage(), e);
		response.addResponseMsg(HttpStatus.INTERNAL_SERVER_ERROR, errorStr);
		return response;
	}
	
	public ParseResponse handleParseError(String errorStr, ParseResponse response) {
		LOG.log(Level.SEVERE, errorStr);
		response.addResponseMsg(HttpStatus.INTERNAL_SERVER_ERROR, errorStr);
		return response;
	}
}
