package com.hensley.ufc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("rest/admin")
@Api(value="Admin System")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@ApiOperation(value = "Fetch count of fighters missing age")
	@GetMapping("missing/fighter/age")
	public ResponseEntity<GetResponse> getMissingAgeCount() {
		GetResponse response = adminService.getMissingDob(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch count of fighters missing reach")
	@GetMapping("missing/fighter/reach")
	public ResponseEntity<GetResponse> getMissingReachCount() {
		GetResponse response = adminService.getMissingReach(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ApiOperation(value = "Fetch count of fighters missing height")
	@GetMapping("missing/fighter/height")
	public ResponseEntity<GetResponse> getMissingheightCount() {
		GetResponse response = adminService.getMissingHeight(); 
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	
}
