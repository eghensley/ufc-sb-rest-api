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

@CrossOrigin()
@RestController
@RequestMapping("scores")
@Api(value = "Score System")
public class ScoreController {

	@Autowired
	RoundScoreService scoreService;

	@Value("${credentials.admin.password}")
	private String loginKey;

	private static String loginFailed = "Admin login failed";

	@ApiOperation(value = "Add Round scores")
	@PostMapping("bout/{boutOid}/round/add")
	public ResponseEntity<ParseResponse> addRoundScores(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("boutOid") String boutOid, @RequestBody AddBoutRoundScoreRequest request) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = scoreService.addManualBoutScore(request);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Add round ML scores")
	@PostMapping("bout/round/ml/add")
	public ResponseEntity<ParseResponse> addRoundMlScores(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@RequestBody AddRoundMlScore request) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = scoreService.addMlRoundScore(request);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Add bout scores")
	@PostMapping("bout/ml/add")
	public ResponseEntity<ParseResponse> addBoutMlScores(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@RequestBody AddBoutWinProb request) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = scoreService.addMlBoutScore(request);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Fetch Last Elo Rankings")
	@GetMapping("elo/last/fighter/{fighterOid}/fight/{fightOid}")
	public ResponseEntity<GetResponse> getLastEloData(@PathVariable("fighterOid") String fighterIdx,
			@PathVariable("fightOid") String fightIdx) {
		GetResponse response = scoreService.getLastFBX(fighterIdx, fightIdx);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Fetch Fighter and Fight Elo Rankings")
	@GetMapping("elo/post/fighter/{fighterOid}/fight/{fightOid}")
	public ResponseEntity<GetResponse> getFightEloData(@PathVariable("fighterOid") String fighterIdx,
			@PathVariable("fightOid") String fightIdx) {
		GetResponse response = scoreService.getFightFBX(fighterIdx, fightIdx);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch Count of Last Elo Rankings")
	@GetMapping("elo/last/fighter/{fighterOid}/fight/{fightOid}/count")
	public ResponseEntity<GetResponse> getCountPrevFights(@PathVariable("fighterOid") String fighterIdx,
			@PathVariable("fightOid") String fightIdx) {
		GetResponse response = scoreService.getCountLastFights(fighterIdx, fightIdx);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Fetch Count of Elo Rankings")
	@GetMapping("elo/fighter/{fighterOid}/count")
	public ResponseEntity<GetResponse> getCountFights(@PathVariable("fighterOid") String fighterIdx) {
		GetResponse response = scoreService.getCountFights(fighterIdx);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Update Elo Rankings")
	@PostMapping("elo/update")
	public ResponseEntity<ParseResponse> updateElo(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@RequestBody FighterBoutEloScoresDto request) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = scoreService.addEloScore(request);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Clear Elo Rankings")
	@GetMapping("elo/clear")
	public ResponseEntity<ParseResponse> clearElo(
			@RequestHeader(value = "password", required = true) String attemptedPassword) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = scoreService.clearEloScores();
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Update Elo Rankings")
	@PostMapping("odds/myBookie/add")
	public ResponseEntity<ParseResponse> updateMyBookieOdds(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@RequestBody AddMyBookieOddsRequest request) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = scoreService.addManualMlOdds(request);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}
}
