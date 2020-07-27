package com.hensley.ufc.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import javax.persistence.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.repository.AdminRepository;

@Service
public class AdminService {

	private final AdminRepository adminRepo;
	private final EntityManager em;
	
	public AdminService(AdminRepository adminRepo, EntityManager em) {
		this.adminRepo = adminRepo;
		this.em = em;
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
//		Query q = em.createNativeQuery("select ufc2.clear_fight_info_funct(:fightOid)");
//		q.setParameter("fightOid", fightOid);
//		q.();
		
		StoredProcedureQuery query = em
			    .createStoredProcedureQuery("ufc2.clear_fight_info_funct")
			    .registerStoredProcedureParameter(1, 
			    		String.class, ParameterMode.IN)
			    .registerStoredProcedureParameter(2, 
			        Integer.class, ParameterMode.OUT)
			    .setParameter(1, fightOid);

		query.getResultList();
			
//		StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("clear_fight_info");
//		// set parameters
//		storedProcedure.registerStoredProcedureParameter("fight_idx", String.class, ParameterMode.IN);
//		storedProcedure.setParameter("fight_idx", fightOid);
//		// execute SP
//		storedProcedure.execute();
////		Object res = adminRepo.resetFightByOid(fightOid);
		Object res = null;
		String errorString = null;
		return new GetResponse(HttpStatus.ACCEPTED, errorString, res);
	}
}
