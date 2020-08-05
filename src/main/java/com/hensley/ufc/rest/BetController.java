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

import com.hensley.ufc.pojo.dto.fighter.FighterXrefBetHistDto;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.service.BetService;

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
	
	private static String loginFailed = "Admin login failed";
	
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
	public ResponseEntity<GetResponse> getBoutBet(
			@PathVariable("xRefOid") String xRefOid) {
		GetResponse response = betService.getBetForBout(xRefOid);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Get bet history")
	@GetMapping("history")
	public ResponseEntity<GetResponse> getFightBetsById() {
		GetResponse response = betService.getFightBetDto(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
}
