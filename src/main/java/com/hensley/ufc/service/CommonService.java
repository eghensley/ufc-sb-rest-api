package com.hensley.ufc.service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.GsonBuilder;
import com.hensley.ufc.enums.common.ErrorEnum;
import com.hensley.ufc.pojo.common.ApiRequestTracker;

@Service
public class CommonService {
	private static final Logger LOG = Logger.getLogger(CommonService.class.toString());

	@Value("${credentials.admin.password}")
	private String loginKey;

	private static String loginFailed = "Admin login failed";

	private final ErrorService errorService;
	
	@Autowired
	public CommonService(ErrorService errorService) {
		this.errorService = errorService;
	}

	@Transactional
	public void preHookProcessing(ApiRequestTracker req) {
		String jsonReq;

		try {
			clearThreadContextForLogging();
			setThreadContextForLogging(req);
			jsonReq = getJson(req);
			if (req.getFunction().isAdmin()) {
				validatePw(req);
			}
			LOG.info(String.format("Request recieved: %s", jsonReq));
		} catch (Exception e) {
			String errorMsg = String.format("Request initialization failed. Error: %s", e.getLocalizedMessage());
			String errorName = e.getClass().getName();
			req.setErrorStr(errorName, errorMsg, ErrorEnum.INITIALIZATION_FAILURE);
			errorService.log(req);	
		}
	}

	public void validatePw(ApiRequestTracker req) throws Exception {
		if (req.getPw() != null) {
			if (!loginKey.equals(req.getPw())) {
				throw new Exception(loginFailed);
			}
		} else {
			throw new Exception("Admin password missing");
		}
	}

	private String getJson(ApiRequestTracker req) {
		String jsonRequest;

		try {
			if (req.getBody() != null) {
				jsonRequest = new GsonBuilder().serializeSpecialFloatingPointValues().create().toJson(req.getBody());
			} else {
				jsonRequest = null;
			}
			return jsonRequest;
		} catch (Exception e) {
			String warnStr = String.format("Unable to convert request to JSON.. with error %s",
					e.getLocalizedMessage());
			LOG.log(Level.WARNING, warnStr, e);
			return null;
		}
	}

	private void setThreadContextForLogging(ApiRequestTracker req) {
		String transId = UUID.randomUUID().toString();
		Thread.currentThread().setName(transId);
		ThreadContext.put("TRANSID", transId);
		ThreadContext.put("SEVERITY", req.getError().getSeverity().toString());
		ThreadContext.put("TYPE", req.getError().getErrorType().toString());
		ThreadContext.put("ERROR_NAME", req.getError().getErrorName().toString());
		ThreadContext.put("FIGHT_OID", req.getFightOid());
		ThreadContext.put("FIGHT_ID", req.getFightId());
		ThreadContext.put("BOUT_OID", req.getBoutOid());
		ThreadContext.put("BOUT_ID", req.getBoutId());
		ThreadContext.put("PATH", req.getPath());
		ThreadContext.put("CONTROLLER", req.getFunction().getController().toString());
		ThreadContext.put("METHOD", req.getFunction().getApiMethod().toString());
		ThreadContext.put("FUNCTION", req.getFunction().getName());
		ThreadContext.put("ADMIN", req.getFunction().isAdmin().toString());
	}

	private void clearThreadContextForLogging() {
		ThreadContext.remove("TRANSID");
		ThreadContext.remove("SEVERITY");
		ThreadContext.remove("TYPE");
		ThreadContext.remove("ERROR_NAME");
		ThreadContext.remove("FIGHT_OID");
		ThreadContext.remove("FIGHT_ID");
		ThreadContext.remove("BOUT_OID");
		ThreadContext.remove("BOUT_ID");
		ThreadContext.remove("PATH");
		ThreadContext.remove("CONTROLLER");
		ThreadContext.remove("METHOD");
		ThreadContext.remove("FUNCTION");
		ThreadContext.remove("ADMIN");
	}
}
