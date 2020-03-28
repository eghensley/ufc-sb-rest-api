package com.hensley.ufc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.request.AddBoutRoundScoreRequest;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.service.RoundScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("scores")
@Api(value = "Score System")
public class ScoreController {

	@Autowired
	RoundScoreService scoreService;

	@ApiOperation(value = "Fetch all fight IDs")
	@PostMapping("bout/{boutOid}/round/add")
	public ResponseEntity<ParseResponse> addRoundScores(@PathVariable("boutOid") String boutOid,
			@RequestBody AddBoutRoundScoreRequest request) {
		ParseResponse response = scoreService.addManualBoutScore(request);
		return new ResponseEntity<>(response, response.getStatus());
	}
}
