package com.hensley.ufc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.service.BoutService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("rest/bout")
@Api(value="Fight System")
public class BoutController {


	@Autowired
	BoutService boutService;
	
	@ApiOperation(value = "Fetch bout by Id")
	@GetMapping("{boutId}/info")
	public ResponseEntity<GetResponse> getBoutById(@PathVariable("boutId") String boutId) {
		GetResponse response = boutService.getBoutDto(boutId); 
		return new ResponseEntity<>(response, response.getStatus());
	}
}
