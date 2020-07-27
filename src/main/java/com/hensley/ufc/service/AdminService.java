package com.hensley.ufc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.repository.AdminRepository;

@Service
public class AdminService {

	private final AdminRepository adminRepo;
	
	public AdminService(AdminRepository adminRepo) {
		this.adminRepo = adminRepo;
	}
	
	@Transactional
	public GetResponse getMissingHeight() {
		String errorString = null;
		Integer count = adminRepo.findCountFightersWithNullHeight();
		return new GetResponse(HttpStatus.ACCEPTED, errorString, count);
	}
	
	@Transactional
	public GetResponse getMissingReach() {
		String errorString = null;
		Integer count = adminRepo.findCountFightersWithNullReach();
		return new GetResponse(HttpStatus.ACCEPTED, errorString, count);
	}
	
	@Transactional
	public GetResponse getMissingDob() {
		String errorString = null;
		Integer count = adminRepo.findCountFightersWithNullDob();
		return new GetResponse(HttpStatus.ACCEPTED, errorString, count);
	}
	
	@Transactional
	public GetResponse getMissingBoutCount() {
		String errorString = null;
		List<Object[]> res = adminRepo.findFightsMissingBoutData();
		return new GetResponse(HttpStatus.ACCEPTED, errorString, res);
	}
	
	@Transactional
	public GetResponse clearFightBouts(String fightOid) {
		String errorString = null;
		Object res = adminRepo.resetFightByOid(fightOid);
		return new GetResponse(HttpStatus.ACCEPTED, errorString, res);
	}
}
