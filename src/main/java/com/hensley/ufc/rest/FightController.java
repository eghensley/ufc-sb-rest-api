package com.hensley.ufc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.enums.common.ErrorEnum;
import com.hensley.ufc.enums.common.FunctionEnum;
import com.hensley.ufc.pojo.common.ApiRequestTracker;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.service.CommonService;
import com.hensley.ufc.service.ErrorService;
import com.hensley.ufc.service.FightService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("rest/fight")
@Api(value = "Fight System")
@CrossOrigin()
public class FightController {

	@Autowired
	FightService fightService;

	@Autowired
	CommonService commonService;

	@Autowired
	ErrorService errorService;

	private static String CONTROLLER_ERROR = "%s [%s] Request Failed with %s";

	@ApiOperation(value = "Fetch fight details by Id")
	@GetMapping("year/{year}")
	public ResponseEntity<GetResponse> getFightsByYear(@PathVariable("year") Integer year) {
		GetResponse response = fightService.getFightsIdsByYear(year);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Fetch fight details by Id")
	@GetMapping("{fightId}/details")
	public ResponseEntity<GetResponse> getFightById(@PathVariable("fightId") String fightId) {
		GetResponse response = fightService.getFightDto(fightId);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Fetch fight bet details by Id")
	@GetMapping("{fightId}/details/bet")
	public ResponseEntity<GetResponse> getFightBetsById(@PathVariable("fightId") String fightId) {
		GetResponse response = fightService.getFightBetDto(fightId);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Fetch fight bet details by Id")
	@GetMapping("{fightId}/details/basic")
	public ResponseEntity<GetResponse> getFightBasicsById(@PathVariable("fightId") String fightId) {
		ApiRequestTracker req = new ApiRequestTracker();
		try {
			req.setPath("rest/fight/{fightId}/details/basic");
			req.setFunction(FunctionEnum.GET_BASIC_FIGHT);
			req.setFightId(fightId);
			commonService.preHookProcessing(req);
			GetResponse response = fightService.getFightBasicDto(req);
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, req.getFunction().getName(),
					req.getFunction().getApiMethod(), e.getLocalizedMessage());
			String errorName = e.getClass().getName();
			req.setErrorStr(errorName, errorMsg, ErrorEnum.UNCAUGHT_CONTROLLER_ERROR);
			errorService.log(req);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Fetch all fight IDs")
	@GetMapping("all")
	public ResponseEntity<GetResponse> getFightIds() {
		GetResponse response = fightService.getFights();
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Fetch most recent 10 fights")
	@GetMapping("recent")
	public ResponseEntity<GetResponse> getMostRecentTenFights() {
		GetResponse response = fightService.getRecentFights();
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Fetch fights with score url")
	@GetMapping("all/scoreUrl")
	public ResponseEntity<GetResponse> getFightIdsWithScoreUrl() {
		GetResponse response = fightService.getFightsWithScore();
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Fetch fights without score url")
	@GetMapping("missing/scoreUrl")
	public ResponseEntity<GetResponse> getFightIdsWithoutScoreUrl() {
		GetResponse response = fightService.getFightsWithoutScore();
		return new ResponseEntity<>(response, response.getStatus());
	}

}
