package com.hensley.ufc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.service.FighterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin()
@RestController
@RequestMapping("rest/fighter")
@Api(value = "Fight System")
public class FighterController {

	private final FighterService fighterService;

	@Autowired
	public FighterController(FighterService fighterService) {
		this.fighterService = fighterService;
	}

	@ApiOperation(value = "Fetch fight details by Id")
	@GetMapping("all/basic")
	public ResponseEntity<GetResponse> getFightsByYear() {
		GetResponse response = fighterService.getFighters();
		return new ResponseEntity<>(response, response.getStatus());
	}

}
