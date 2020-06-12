package com.hensley.ufc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.dto.fighter.FighterBoutEloScoresDto;
import com.hensley.ufc.pojo.request.AddBoutRoundScoreRequest;
import com.hensley.ufc.pojo.request.AddBoutWinProb;
import com.hensley.ufc.pojo.request.AddMyBookieOddsRequest;
import com.hensley.ufc.pojo.request.AddRoundMlScore;
import com.hensley.ufc.pojo.response.GetResponse;
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
	
	@ApiOperation(value = "Fetch all fight IDs")
	@PostMapping("bout/round/ml/add")
	public ResponseEntity<ParseResponse> addRoundMlScores(@RequestBody AddRoundMlScore request) {
		ParseResponse response = scoreService.addMlRoundScore(request);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch all fight IDs")
	@PostMapping("bout/ml/add")
	public ResponseEntity<ParseResponse> addBoutMlScores(@RequestBody AddBoutWinProb request) {
		ParseResponse response = scoreService.addMlBoutScore(request);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch Last Elo Rankings")
	@GetMapping("elo/last/fighter/{fighterOid}/fight/{fightOid}")
	public ResponseEntity<GetResponse> getLastEloData(@PathVariable("fighterOid") String fighterIdx, @PathVariable("fightOid") String fightIdx) {
		GetResponse response = scoreService.getLastFBX(fighterIdx, fightIdx); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch Count of Last Elo Rankings")
	@GetMapping("elo/last/fighter/{fighterOid}/fight/{fightOid}/count")
	public ResponseEntity<GetResponse> getCountPrevFights(@PathVariable("fighterOid") String fighterIdx, @PathVariable("fightOid") String fightIdx) {
		GetResponse response = scoreService.getCountLastFights(fighterIdx, fightIdx); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Update Elo Rankings")
	@PostMapping("elo/update")
	public ResponseEntity<ParseResponse> updateElo(@RequestBody FighterBoutEloScoresDto request) {
		ParseResponse response = scoreService.addEloScore(request);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Clear Elo Rankings")
	@GetMapping("elo/clear")
	public ResponseEntity<ParseResponse> clearElo() {
		ParseResponse response = scoreService.clearEloScores();
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Update Elo Rankings")
	@PostMapping("odds/myBookie/add")
	public ResponseEntity<ParseResponse> updateMyBookieOdds(@RequestBody AddMyBookieOddsRequest request) {
		ParseResponse response = scoreService.addManualMlOdds(request);
		return new ResponseEntity<>(response, response.getStatus());
	}
}
