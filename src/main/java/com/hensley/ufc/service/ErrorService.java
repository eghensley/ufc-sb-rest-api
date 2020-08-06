package com.hensley.ufc.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hensley.ufc.pojo.common.ApiRequestTracker;
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
	
	public void log(ApiRequestTracker req) {
		ThreadContext.put("SEVERITY", req.getError().getSeverity().toString());
		ThreadContext.put("TYPE", req.getError().getErrorType().toString());
		ThreadContext.put("ERROR_NAME", req.getError().getErrorName().toString());
		ThreadContext.put("FIGHT_OID", req.getError().getFightOid());
		ThreadContext.put("FIGHT_ID", req.getError().getFightId());
		ThreadContext.put("BOUT_OID", req.getError().getBoutOid());
		ThreadContext.put("BOUT_ID", req.getError().getBoutId());
		
		LOG.log(Level.parse(req.getError().getSeverity().toString()), req.getError().getErrorStr());
		req.resetError();
		
		ThreadContext.put("SEVERITY", null);
		ThreadContext.put("TYPE", null);
		ThreadContext.put("ERROR_NAME", null);
		ThreadContext.put("FIGHT_OID", req.getFightOid());
		ThreadContext.put("FIGHT_ID", req.getFightId());
		ThreadContext.put("BOUT_OID", req.getBoutOid());
		ThreadContext.put("BOUT_ID", req.getBoutId());
	}
	
	
}
