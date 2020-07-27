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

import com.hensley.ufc.enums.WeightClassEnum;
import com.hensley.ufc.pojo.dto.rank.FighterRankElementDto;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.service.FighterRankService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("ranks")
@Api(value = "Rank System")
public class RankController {

	@Autowired
	FighterRankService rankService;

	@ApiOperation(value = "Fetch all fight IDs")
	@PostMapping("update")
	public ResponseEntity<ParseResponse> addRoundScores(@RequestBody FighterRankElementDto reqPayload) {
		ParseResponse response = rankService.updateFighterRanking(reqPayload);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ApiOperation(value = "Get rankings by weight class")
	@GetMapping("weightClass/{weightClassVal}")
	public ResponseEntity<GetResponse> getWeightClassRankings(
			@PathVariable("weightClassVal") WeightClassEnum weightClassVal) {
		GetResponse response = rankService.getRanksForWeightClass(weightClassVal);
		return new ResponseEntity<>(response, response.getStatus());
	}

}