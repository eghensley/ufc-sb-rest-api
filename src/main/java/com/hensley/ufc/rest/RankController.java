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

import com.hensley.ufc.enums.WeightClassEnum;
import com.hensley.ufc.pojo.dto.rank.FighterRankElementDto;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.service.FighterRankService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin()
@RestController
@RequestMapping("ranks")
@Api(value = "Rank System")
public class RankController {

	@Autowired
	FighterRankService rankService;

	@Value("${credentials.admin.password}")
	private String loginKey;
	private static String loginFailed = "Admin login failed";

	@ApiOperation(value = "Update fighter ranking")
	@PostMapping("update")
	public ResponseEntity<ParseResponse> addRoundScores(
			@RequestHeader(value = "password", required = true) String attemptedPassword,
			@RequestBody FighterRankElementDto reqPayload) {
		if (loginKey.equals(attemptedPassword)) {
			ParseResponse response = rankService.updateFighterRanking(reqPayload);
			return new ResponseEntity<>(response, response.getStatus());
		} else {
			String errorMsg = loginFailed;
			ParseResponse response = new ParseResponse(null, 1, 0, HttpStatus.FORBIDDEN, errorMsg);
			return new ResponseEntity<>(response, response.getStatus());
		}
	}

	@ApiOperation(value = "Get rankings by weight class")
	@GetMapping("weightClass/{weightClassVal}")
	public ResponseEntity<GetResponse> getWeightClassRankings(
			@PathVariable("weightClassVal") WeightClassEnum weightClassVal) {
		GetResponse response = rankService.getRanksForWeightClass(weightClassVal);
		return new ResponseEntity<>(response, response.getStatus());
	}

}