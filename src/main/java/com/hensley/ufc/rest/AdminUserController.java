package com.hensley.ufc.rest;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin()
@RestController
@RequestMapping("rest/admin")
@Api(value = "Admin System")
public class AdminUserController {

	@Autowired
	AdminService adminService;

	@Value("${credentials.admin.password}")
	private String loginKey;

	private static String loginFailed = "Admin login failed";

	@ApiOperation(value = "Fetch count of fighters missing age")
	@GetMapping("missing/fighter/age")
	public ResponseEntity<GetResponse> getMissingAgeCount(
			@RequestHeader(value = "password", required = true) String attemptedPassword) {
		if (loginKey.equals(attemptedPassword)) {
			GetResponse response = adminService.getMissingDob();
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			GetResponse response = new GetResponse(HttpStatus.FORBIDDEN, errorMsg, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Fetch count of fighters missing reach")
	@GetMapping("missing/fighter/reach")
	public ResponseEntity<GetResponse> getMissingReachCount(
			@RequestHeader(value = "password", required = true) String attemptedPassword) {
		if (loginKey.equals(attemptedPassword)) {
			GetResponse response = adminService.getMissingReach();
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			GetResponse response = new GetResponse(HttpStatus.FORBIDDEN, errorMsg, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Fetch count of fighters missing height")
	@GetMapping("missing/fighter/height")
	public ResponseEntity<GetResponse> getMissingHeightCount(
			@RequestHeader(value = "password", required = true) String attemptedPassword) {
		if (loginKey.equals(attemptedPassword)) {
			GetResponse response = adminService.getMissingHeight();
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			GetResponse response = new GetResponse(HttpStatus.FORBIDDEN, errorMsg, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Fetch count of bouts missing data")
	@GetMapping("missing/bouts")
	public ResponseEntity<GetResponse> getIncompleteBoutCount(
			@RequestHeader(value = "password", required = true) String attemptedPassword) {
		if (loginKey.equals(attemptedPassword)) {
			GetResponse response = adminService.getMissingBoutCount();
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			GetResponse response = new GetResponse(HttpStatus.FORBIDDEN, errorMsg, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Reset bouts for fight")
	@GetMapping("reset/fight/{fightOid}")
	public ResponseEntity<GetResponse> resetBouts(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("fightOid") String fightOid) {
		if (loginKey.equals(attemptedPassword)) {
			GetResponse response = adminService.clearFightBouts(fightOid);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			GetResponse response = new GetResponse(HttpStatus.FORBIDDEN, errorMsg, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
}
