//package com.hensley.ufc.rest;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hensley.ufc.pojo.parse.ScorePartialParse;
//import com.hensley.ufc.service.RoundScoreService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("rest/scores")
//@Api(value="Score System")
//public class ScoreController {
//
//	@Autowired
//	RoundScoreService scoreService;
//	
//	@ApiOperation(value = "Fetch all fight IDs")
//	@GetMapping("{year}")
//	public ResponseEntity<List<ScorePartialParse>> get(@PathVariable("year") String year) {
//		List<ScorePartialParse> response = scoreService.fetchJudgeScoreYear(year); 
//		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
//	}
//}
