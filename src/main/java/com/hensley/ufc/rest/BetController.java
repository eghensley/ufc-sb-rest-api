package com.hensley.ufc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.enums.common.ErrorEnum;
import com.hensley.ufc.enums.common.FunctionEnum;
import com.hensley.ufc.pojo.common.ApiRequestTracker;
import com.hensley.ufc.pojo.dto.fighter.FighterXrefBetHistDto;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.service.BetService;
import com.hensley.ufc.service.CommonService;
import com.hensley.ufc.service.ErrorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin()
@RestController
@RequestMapping("bet")
@Api(value = "Bet System")
public class BetController {

	@Value("${credentials.admin.password}")
	private String loginKey;

	@Autowired
	BetService betService;

	@Autowired
	CommonService commonService;

	@Autowired
	ErrorService errorService;
	
	private static String loginFailed = "Admin login failed";
	private static String CONTROLLER_ERROR = "%s [%s] Request Failed with %s";

	@ApiOperation(value = "Update fighter bet")
	@PostMapping("update")
	public ResponseEntity<ParseResponse> addBet(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@RequestBody FighterXrefBetHistDto reqPayload) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = betService.updateFighterBet(reqPayload);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Get bet for oid")
	@GetMapping("oid/{xRefOid}")
	public ResponseEntity<GetResponse> getBoutBet(@PathVariable("xRefOid") String xRefOid) {
		GetResponse response = betService.getBetForBout(xRefOid);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Get bet history")
	@GetMapping("history")
	public ResponseEntity<GetResponse> getFightBetsById() {
		GetResponse response = betService.getFightBetDto();
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Get direct bet history")
	@GetMapping("history/v2")
	public ResponseEntity<GetResponse> getBetHistory() {
		ApiRequestTracker req = new ApiRequestTracker();

		try {
			req.setPath("bet/history/v2");
			req.setFunction(FunctionEnum.GET_BET_HIST);
			commonService.preHookProcessing(req);
			GetResponse response = betService.getBetHistory(req);
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, req.getFunction().getName(), req.getFunction().getApiMethod(), e.getLocalizedMessage());
			String errorName = e.getClass().getName();
			req.setErrorStr(errorName, errorMsg, ErrorEnum.UNCAUGHT_CONTROLLER_ERROR);
			errorService.log(req);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Get direct bet table")
	@GetMapping("table/fight/{fightId}")
	public ResponseEntity<GetResponse> getBetTable(@PathVariable("fightId") String fightId) {
		ApiRequestTracker req = new ApiRequestTracker();

		try {
			req.setPath("table/fight/{fightId}");
			req.setFunction(FunctionEnum.GET_BET_TBL);
			req.setFightId(fightId);
			commonService.preHookProcessing(req);
			GetResponse response = betService.getBetPreds(req);
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, req.getFunction().getName(), req.getFunction().getApiMethod(), e.getLocalizedMessage());
			String errorName = e.getClass().getName();
			req.setErrorStr(errorName, errorMsg, ErrorEnum.UNCAUGHT_CONTROLLER_ERROR);
			errorService.log(req);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Get past direct bet table")
	@GetMapping("table/fight/{fightId}/past")
	public ResponseEntity<GetResponse> getPastBetTable(@PathVariable("fightId") String fightId) {
		ApiRequestTracker req = new ApiRequestTracker();

		try {
			req.setPath("table/fight/{fightId}/past");
			req.setFunction(FunctionEnum.GET_PAST_BET_TBL);
			req.setFightId(fightId);
			commonService.preHookProcessing(req);
			GetResponse response = betService.getFightBetResult(req);
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, req.getFunction().getName(), req.getFunction().getApiMethod(), e.getLocalizedMessage());
			String errorName = e.getClass().getName();
			req.setErrorStr(errorName, errorMsg, ErrorEnum.UNCAUGHT_CONTROLLER_ERROR);
			errorService.log(req);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
