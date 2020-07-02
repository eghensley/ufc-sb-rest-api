package com.hensley.ufc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.request.AddFutureBoutSummary;
import com.hensley.ufc.pojo.request.AddRoundMlScore;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.service.BoutService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("rest/bout")
@Api(value="Fight System")
public class BoutController {


	@Autowired
	BoutService boutService;
	
	@ApiOperation(value = "Fetch bout by Id")
	@GetMapping("all")
	public ResponseEntity<GetResponse> getBoutsDateAsc() {
		GetResponse response = boutService.getBoutsDateAsc(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch bout by Id")
	@GetMapping("year/2019")
	public ResponseEntity<GetResponse> get2019Bouts() {
		GetResponse response = boutService.get2019BoutsDateAsc(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch bout by Id")
	@GetMapping("new")
	public ResponseEntity<GetResponse> getNewBoutsDateAsc() {
		GetResponse response = boutService.getNewBoutsDateAsc(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch bout by Id")
	@GetMapping("{boutId}/info")
	public ResponseEntity<GetResponse> getBoutById(@PathVariable("boutId") String boutId) {
		GetResponse response = boutService.getBoutDto(boutId); 
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Fetch bout by Id")
	@GetMapping("{boutId}/data")
	public ResponseEntity<GetResponse> getBoutDataById(@PathVariable("boutId") String boutId) {
		GetResponse response = boutService.getBoutDataDto(boutId); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch bout by Id")
	@GetMapping("missing/scores")
	public ResponseEntity<GetResponse> getBoutsMissingScores() {
		GetResponse response = boutService.getBoutsMissingScores(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch bout by Id")
	@GetMapping("{boutOid}/summary")
	public ResponseEntity<GetResponse> getBoutSummary(@PathVariable("boutOid") String boutOid) {
		GetResponse response = boutService.getBoutSummary(boutOid); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Add future bout info")
	@PostMapping("future/summary/add")
	public ResponseEntity<ParseResponse> addFutureBoutSummaryInfo(@RequestBody AddFutureBoutSummary request) {
		ParseResponse response = boutService.addFutBoutSum(request);
		return new ResponseEntity<>(response, response.getStatus());
	}
}
