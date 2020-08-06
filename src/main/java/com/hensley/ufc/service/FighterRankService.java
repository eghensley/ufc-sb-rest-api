package com.hensley.ufc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.transform.Transformers;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hensley.ufc.domain.FighterBoutXRefData;
import com.hensley.ufc.domain.FighterData;
import com.hensley.ufc.domain.FighterRankData;
import com.hensley.ufc.enums.ParseTargetEnum;
import com.hensley.ufc.enums.WeightClassEnum;
import com.hensley.ufc.pojo.common.ApiRequestTracker;
import com.hensley.ufc.pojo.dto.rank.FighterRankBasicDto;
import com.hensley.ufc.pojo.dto.rank.FighterRankElementDto;
import com.hensley.ufc.pojo.request.ParseRequest;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.repository.FighterBoutXRefRepository;
import com.hensley.ufc.repository.FighterRankRepository;
import com.hensley.ufc.repository.FighterRepository;
import com.hensley.ufc.util.MappingUtils;

@Service
public class FighterRankService {

	private static final Logger LOG = Logger.getLogger(FighterRankService.class.toString());

	private final FighterRankRepository fighterRankRepo;
	private final MappingUtils mappingUtils;
	private final FighterBoutXRefRepository fighterBoutXRefRepo;
	private final ErrorService errorService;
	private final FighterRepository fighterRepo;
	private final EntityManager em;

	public FighterRankService(EntityManager em, FighterRepository fighterRepo, ErrorService errorService,
			FighterRankRepository fighterRankRepo, MappingUtils mappingUtils,
			FighterBoutXRefRepository fighterBoutXRefRepo) {
		this.fighterRankRepo = fighterRankRepo;
		this.mappingUtils = mappingUtils;
		this.fighterBoutXRefRepo = fighterBoutXRefRepo;
		this.errorService = errorService;
		this.fighterRepo = fighterRepo;
		this.em = em;
	}

	@Transactional
	public GetResponse getRanksForWeightClass(WeightClassEnum weightClass) {
		String errorString = null;
		List<FighterRankElementDto> response = new ArrayList<>();
		List<FighterRankData> rankList = fighterRankRepo.findByWeightClass(weightClass);
		for (FighterRankData fighterRank : rankList) {
			FighterRankElementDto fighterRankDto = (FighterRankElementDto) mappingUtils.mapToDto(fighterRank,
					FighterRankElementDto.class);
			response.add(fighterRankDto);
		}
		return new GetResponse(HttpStatus.ACCEPTED, errorString, response);
	}

	@Transactional
	public GetResponse getRankForWeightClassAndFighter(WeightClassEnum weightClass, String fighterOid) {
		String errorString = null;
		FighterRankElementDto response = new FighterRankElementDto();
		Optional<FighterRankData> savedRankOpt = fighterRankRepo.findByWeightClassAndFighterOid(weightClass, fighterOid);
		if (savedRankOpt.isPresent()) {
			FighterRankElementDto fighterRankDto = (FighterRankElementDto) mappingUtils.mapToDto(savedRankOpt.get(),
					FighterRankElementDto.class);
			return new GetResponse(HttpStatus.ACCEPTED, errorString, fighterRankDto);
		}
		return new GetResponse(HttpStatus.ACCEPTED, errorString, response);
	}

	@Transactional
	public ParseResponse updateFighterRanking(FighterRankElementDto reqPayload) {
		String errorStr = null;

		LOG.info(String.format("Updating rank for fighter: %s and weight class %s", reqPayload.getFighter().getOid(),
				reqPayload.getWeightClass()));
		
		ParseRequest req = new ParseRequest(ParseTargetEnum.RANK, null, null, reqPayload.getFighter().getOid(),
				reqPayload.getFighterBoutXRef().getOid());
		ParseResponse response = new ParseResponse(req, 1, 0, HttpStatus.OK, null);

		try {
			Optional<FighterRankData> savedRankOpt = fighterRankRepo.findByWeightClassAndFighterOid(
					reqPayload.getWeightClass(), reqPayload.getFighter().getOid());
			if (savedRankOpt.isPresent()) {
				LOG.info("existing rank found");
				FighterRankData savedRank = savedRankOpt.get();
				if (savedRank.getFightDate().compareTo(reqPayload.getFightDate()) < 0) {
					LOG.info("Updating old rank to more recent");
					savedRank.setFightDate(reqPayload.getFightDate());
					FighterBoutXRefData fbx = fighterBoutXRefRepo.findByOid(reqPayload.getFighterBoutXRef().getOid());
					savedRank.setFighterBoutXRef(fbx);
					fighterRankRepo.save(savedRank);
				} else {
					LOG.info("Existing rank more recent");

				}
				response.setItemsCompleted(1);
				return response;
			} else {
				FighterRankData newRank = new FighterRankData();
				FighterBoutXRefData fbx = fighterBoutXRefRepo.findByOid(reqPayload.getFighterBoutXRef().getOid());
				Optional<FighterData> fighter = fighterRepo.findById(reqPayload.getFighter().getOid());
				newRank.setFighter(fighter.get());
				newRank.setWeightClass(reqPayload.getWeightClass());
				newRank.setFightDate(reqPayload.getFightDate());
				newRank.setFighterBoutXRef(fbx);
				fighterRankRepo.save(newRank);
				response.setItemsCompleted(1);
				return response;
			}

		} catch (Exception e) {
			errorStr = e.getMessage();
			return errorService.handleParseError(errorStr, e, response);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Transactional
	public GetResponse getBasicRanks(ApiRequestTracker req, WeightClassEnum weightClass) {
		String errorString = null;
		try {
			String queryStr = "	select f.fighter_name as \"fighterName\", fbx.off_strike_elo_post as \"offStrikeEloPost\", fbx.def_strike_elo_post as \"defStrikeEloPost\", fbx.off_grap_elo_post as \"offGrapplingEloPost\", fbx.def_grap_elo_post as \"defGrapplingEloPost\" \n" + 
					"	from ufc2.fighter_rank fr \n" + 
					"		join ufc2.fighter_bout_xref fbx \n" + 
					"			on fbx.oid = fr.fighter_boutxref_oid \n" + 
					"		join ufc2.fighter f \n" + 
					"			on f.oid = fr.fighter_oid \n" + 
					"	where fr.weight_class=:weightClassKey \n" + 
					"	and fr.version > 5";
			Query query = em.createNativeQuery(queryStr);
			query.setParameter("weightClassKey", weightClass.dbKey);
			query.unwrap(org.hibernate.query.NativeQuery.class)
					.setResultTransformer(Transformers.aliasToBean(FighterRankBasicDto.class));
			@SuppressWarnings("unchecked")
			List<FighterRankBasicDto> res = query.getResultList();
			return new GetResponse(HttpStatus.OK, errorString, res);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}
	


}