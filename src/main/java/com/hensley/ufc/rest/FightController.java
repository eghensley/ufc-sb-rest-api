package com.hensley.ufc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.service.FightService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("rest/fight")
@Api(value="Fight System")
public class FightController {

	@Autowired
	FightService fightService;
	
	@ApiOperation(value = "Fetch fight details by Id")
	@GetMapping("{fightId}/details")
	public ResponseEntity<GetResponse> getFightById(@PathVariable("fightId") String fightId) {
		GetResponse response = fightService.getFightDto(fightId); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	
	@ApiOperation(value = "Fetch all fight IDs")
	@GetMapping("all")
	public ResponseEntity<GetResponse> getFightIds() {
		GetResponse response = fightService.getFights(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch all fight IDs")
	@GetMapping("all/scoreUrl")
	public ResponseEntity<GetResponse> getFightIdsWithScoreUrl() {
		GetResponse response = fightService.getFightsWithScore(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
}
