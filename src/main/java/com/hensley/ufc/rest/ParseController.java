package com.hensley.ufc.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.service.BoutDetailService;
import com.hensley.ufc.service.BoutService;
import com.hensley.ufc.service.FightService;
import com.hensley.ufc.service.RoundScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("parse")
@Api(value="Parsing System")
public class ParseController {

	@Autowired
	FightService fightService;
	
	@Autowired
	BoutService boutService;
	
	@Autowired
	BoutDetailService boutDetailService;
	
	@Autowired
	RoundScoreService roundScoreService;
	
	@ApiOperation(value = "Parse fight events")
	@GetMapping("fights")
	public ResponseEntity<ParseResponse> getFights() throws MalformedURLException, IOException{
		ParseResponse response = fightService.fightScraper(); 
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Parse bouts in a fight")
	@GetMapping("fights/{fightId}/bouts")
	public ResponseEntity<ParseResponse> getBouts(@PathVariable("fightId") String fightId) throws MalformedURLException, IOException{
//		String baseUrl = "http://www.ufcstats.com/event-details/8d04923f2db59b7f" ;
		ParseResponse response = boutService.scrapeBoutsFromFightId(fightId); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Parse details in a bout")
	@GetMapping("fights/{fightId}/bouts/{boutId}")
	public ResponseEntity<ParseResponse> getBoutDetails(@PathVariable("boutId") String boutId, @PathVariable("fightId") String fightId) throws MalformedURLException, IOException{
//		String baseUrl = "http://www.ufcstats.com/event-details/8d04923f2db59b7f" ;
		ParseResponse response = boutDetailService.scrapeBoutDetailsFromBoutId(fightId, boutId); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Parse details in all bouts for a fight")
	@GetMapping("fights/{fightId}/bouts/all")
	public ResponseEntity<ParseResponse> getFightBoutDetails(@PathVariable("fightId") String fightId) throws MalformedURLException, IOException{
//		String baseUrl = "http://www.ufcstats.com/event-details/8d04923f2db59b7f" ;
		ParseResponse initResponse = boutService.scrapeBoutsFromFightId(fightId); 

		List<String> boutList = boutService.getBoutsFromFight(fightId);
		for (String boutId: boutList) {
			ParseResponse response = boutDetailService.scrapeBoutDetailsFromBoutId(fightId, boutId); 
		}
		return new ResponseEntity<>(new ParseResponse(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Parse round scores")
	@GetMapping("fights/judgeScores/{year}")
	public ResponseEntity<ParseResponse> getRoundJudgeScores(@PathVariable("year") String year) throws MalformedURLException, IOException{
//		String baseUrl = "http://www.ufcstats.com/event-details/8d04923f2db59b7f" ;
//		ParseResponse response = roundScoreService.scrapeScoresFromBout(); 
		ParseResponse response = roundScoreService.addFightScoreUrl(year);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Parse round scores")
	@GetMapping("fights/judgeScores/fight/{fightOid}")
	public ResponseEntity<ParseResponse> getRoundFightScores(@PathVariable("fightOid") String fightOid) throws MalformedURLException, IOException{
//		String baseUrl = "http://www.ufcstats.com/event-details/8d04923f2db59b7f" ;
//		ParseResponse response = roundScoreService.scrapeScoresFromBout(); 
		ParseResponse response = roundScoreService.addBoutScoreUrl(fightOid);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	
	@ApiOperation(value = "Parse round scores")
	@GetMapping("fights/judgeScores/bout/{boutOid}")
	public ResponseEntity<ParseResponse> parseRoundBoutScores(@PathVariable("boutOid") String boutOid) throws MalformedURLException, IOException{
//		String baseUrl = "http://www.ufcstats.com/event-details/8d04923f2db59b7f" ;
//		ParseResponse response = roundScoreService.scrapeScoresFromBout(); 
		ParseResponse response = roundScoreService.addBoutScores(boutOid);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	
	
}
