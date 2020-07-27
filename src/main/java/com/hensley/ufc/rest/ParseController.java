package com.hensley.ufc.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.service.BoutDetailService;
import com.hensley.ufc.service.BoutService;
import com.hensley.ufc.service.FightService;
import com.hensley.ufc.service.OddsService;
import com.hensley.ufc.service.RoundScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("parse")
@Api(value = "Parsing System")
public class ParseController {

	@Autowired
	FightService fightService;

	@Autowired
	BoutService boutService;

	@Autowired
	BoutDetailService boutDetailService;

	@Autowired
	RoundScoreService roundScoreService;

	@Autowired
	OddsService oddsService;

	@Value("${credentials.admin.password}")
	private String loginKey;

	private static String loginFailed = "Admin login failed";

	@ApiOperation(value = "Parse fight events")
	@GetMapping("fights")
	public ResponseEntity<ParseResponse> getFights(
			@RequestHeader(value = "password", required = true) String attemptedPassword) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = fightService.fightScraper();
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Parse future fight events")
	@GetMapping("fights/next")
	public ResponseEntity<ParseResponse> getFutureFight(
			@RequestHeader(value = "password", required = true) String attemptedPassword) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = fightService.futFightScraper();
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Parse bouts in a fight")
	@GetMapping("fights/{fightId}/bouts")
	public ResponseEntity<ParseResponse> getBouts(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("fightId") String fightId) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = boutService.scrapeBoutsFromFightId(fightId);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Parse bouts in a future fight")
	@GetMapping("fights/{fightId}/bouts/future")
	public ResponseEntity<ParseResponse> getFutureBouts(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("fightId") String fightId) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = boutService.scrapeBoutsFromFutureFightId(fightId);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Parse details in a bout")
	@GetMapping("fights/{fightId}/bouts/{boutId}")
	public ResponseEntity<ParseResponse> getBoutDetails(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("boutId") String boutId, @PathVariable("fightId") String fightId) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = boutDetailService.scrapeBoutDetailsFromBoutId(fightId, boutId);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Parse details in all bouts for a fight")
	@GetMapping("fights/{fightId}/bouts/all")
	public ResponseEntity<ParseResponse> getFightBoutDetails(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("fightId") String fightId) {
		if (loginKey.equals(attemptedPassword)) {
			@SuppressWarnings("unused")
			ParseResponse initResponse = boutService.scrapeBoutsFromFightId(fightId);

			List<String> boutList = boutService.getBoutsFromFight(fightId);
			for (String boutId : boutList) {
				@SuppressWarnings("unused")
				ParseResponse response = boutDetailService.scrapeBoutDetailsFromBoutId(fightId, boutId);
			}
			return new ResponseEntity<>(new ParseResponse(), HttpStatus.OK);
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Get judge scores for year")
	@GetMapping("fights/judgeScores/{year}")
	public ResponseEntity<ParseResponse> getRoundJudgeScores(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("year") String year) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = roundScoreService.addFightScoreUrl(year);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Add url to fight")
	@GetMapping("fights/judgeScores/fight/{fightOid}")
	public ResponseEntity<ParseResponse> getRoundFightScores(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("fightOid") String fightOid) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = roundScoreService.addBoutScoreUrl(fightOid);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Add odds url to fight")
	@GetMapping("odds/fight/{fightOid}/fightOdds/{fightURl}")
	public ResponseEntity<ParseResponse> addFightOddsUrl(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("fightOid") String fightOid, @PathVariable("fightURl") String fightUrl) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = fightService.addFightOddsUrl(fightOid,
					String.format("https://www.bestfightodds.com/events/%s", fightUrl));
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Parse round scores")
	@GetMapping("fights/judgeScores/bout/{boutOid}")
	public ResponseEntity<ParseResponse> parseRoundBoutScores(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("boutOid") String boutOid) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = roundScoreService.addBoutScores(boutOid);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Parse fight odds")
	@GetMapping("odds/fight/{fightId}/expOutcomes")
	public ResponseEntity<ParseResponse> parseFightExpOutcomes(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("fightId") String fightId) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = oddsService.scrapeExpOutcomesFromFightOid(fightId);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Parse fight odds")
	@GetMapping("odds/fight/{fightId}/odds")
	public ResponseEntity<ParseResponse> parseFightOdds(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@PathVariable("fightId") String fightId) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = oddsService.scrapeOddsFromFightOid(fightId);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

}
