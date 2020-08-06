package com.hensley.ufc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hensley.ufc.domain.FightData;
import com.hensley.ufc.domain.FighterBoutXRefData;
import com.hensley.ufc.enums.GenderEnum;
import com.hensley.ufc.enums.ParseTargetEnum;
import com.hensley.ufc.pojo.common.ApiRequestTracker;
import com.hensley.ufc.pojo.dto.bet.AllBetHistoryDto;
import com.hensley.ufc.pojo.dto.bet.FightBetDto;
import com.hensley.ufc.pojo.dto.bout.BoutBetDto;
import com.hensley.ufc.pojo.dto.fight.FightBetHistoryDto;
import com.hensley.ufc.pojo.dto.fighter.FighterXrefBetHistDto;
import com.hensley.ufc.pojo.request.ParseRequest;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.repository.FightRepository;
import com.hensley.ufc.repository.FighterBoutXRefRepository;
import com.hensley.ufc.util.MappingUtils;

@Service
public class BetService {
	private static final Logger LOG = Logger.getLogger(BetService.class.toString());

	private final MappingUtils mappingUtils;
	private final FighterBoutXRefRepository fighterBoutXRefRepo;
	private final ErrorService errorService;
	private final FightRepository fightRepo;
	private final EntityManager em;

	@Value("${parameters.bet.diff_ceiling}")
	private Double diffCeiling;
	@Value("${parameters.bet.diff_floor}")
	private Double diffFloor;
	@Value("${parameters.bet.prev_fight_floor}")
	private Integer prevFightFloor;
	@Value("${parameters.bet.prev_fight_ceiling}")
	private Integer prevFightCeiling;
	@Value("${parameters.bet.bet_intercept}")
	private Double betIntercept;
	@Value("${parameters.bet.conf_diff_lin}")
	private Double confDiffLin;
	@Value("${parameters.bet.conf_diff_quad}")
	private Double confDiffQuad;
	@Value("${parameters.bet.num_fight_lin}")
	private Double numFightLin;
	@Value("${parameters.bet.num_fight_quad}")
	private Double numFightQuad;
	@Value("${parameters.bet.bet_ceiling}")
	private Integer betCeiling;
	@Value("${parameters.bet.bet_female}")
	private Boolean betFemale;

	public BetService(EntityManager em, ErrorService errorService, MappingUtils mappingUtils,
			FighterBoutXRefRepository fighterBoutXRefRepo, FightRepository fightRepo) {
		this.mappingUtils = mappingUtils;
		this.fighterBoutXRefRepo = fighterBoutXRefRepo;
		this.errorService = errorService;
		this.fightRepo = fightRepo;
		this.em = em;
	}

	@Transactional
	public GetResponse getBetForBout(String xRefOid) {
		String errorString = null;
		FighterBoutXRefData savedBet = fighterBoutXRefRepo.findByOid(xRefOid);
		FighterXrefBetHistDto fighterBetDto = (FighterXrefBetHistDto) mappingUtils.mapToDto(savedBet,
				FighterXrefBetHistDto.class);
		return new GetResponse(HttpStatus.ACCEPTED, errorString, fighterBetDto);
	}

	@Transactional
	public ParseResponse updateFighterBet(FighterXrefBetHistDto reqPayload) {
		String errorStr = null;

		LOG.info(String.format("Updating bet for fighter: %s", reqPayload.getFighter().getFighterName()));

		ParseRequest req = new ParseRequest(ParseTargetEnum.BET, null, null, reqPayload.getFighter().getOid(),
				reqPayload.getOid());
		ParseResponse response = new ParseResponse(req, 1, 0, HttpStatus.OK, null);

		try {
			FighterBoutXRefData savedBet = fighterBoutXRefRepo.findByOid(reqPayload.getOid());
			if (savedBet.getBetMade() != null) {
				LOG.info("existing bet found");
			} else {
				LOG.info("No existing bet found");
			}
			savedBet.setBetAmount(reqPayload.getBetAmount());
			savedBet.setBetMade(reqPayload.getBetMade());
			savedBet.setBetPredicted(reqPayload.getBetPredicted());
			savedBet.setBetResult(reqPayload.getBetResult());
			fighterBoutXRefRepo.save(savedBet);
			response.setItemsCompleted(1);
			return response;
		} catch (Exception e) {
			errorStr = e.getMessage();
			return errorService.handleParseError(errorStr, e, response);
		}
	}

	@Transactional
	public GetResponse getFightBetDto() {
		String errorString = null;
		List<FightBetHistoryDto> resp = new ArrayList<>();
		try {
			List<String> betFightIds = fightRepo.findBetFights();
			for (String betFightId : betFightIds) {
				Optional<FightData> fightDataOpt = fightRepo.findByFightId(betFightId);
				if (fightDataOpt.isPresent()) {
					FightData fightData = fightDataOpt.get();
					FightBetHistoryDto fightDto = (FightBetHistoryDto) mappingUtils.mapToDto(fightData,
							FightBetHistoryDto.class);
					if (!CollectionUtils.isEmpty(fightDto.getBouts())) {
						resp.add(fightDto);
					}
				}
			}
			return new GetResponse(HttpStatus.OK, errorString, resp);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}

	@SuppressWarnings("deprecation")
	@Transactional
	public GetResponse getBetHistory(ApiRequestTracker req) {
		String errorString = null;
		try {
			String queryStr = "select f.fight_name as \"fightName\", f.fight_date as \"fightDate\", sum(fbx.bet_result) as \"result\" from fighter_bout_xref fbx "
					+ "join bout b on b.oid = fbx.bout_oid " + "join fight f on f.oid = b.fight_oid "
					+ "where fbx.f_bet_made is true " + "group by f.fight_name, f.fight_date "
					+ "order by f.fight_date asc";
			Query query = em.createNativeQuery(queryStr);
			query.unwrap(org.hibernate.query.NativeQuery.class)
					.setResultTransformer(Transformers.aliasToBean(AllBetHistoryDto.class));
			@SuppressWarnings("unchecked")
			List<AllBetHistoryDto> res = query.getResultList();
			return new GetResponse(HttpStatus.OK, errorString, res);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}

	@SuppressWarnings("deprecation")
	@Transactional
	public GetResponse getBetPreds(ApiRequestTracker req) {
		String errorString = null;
		List<BoutBetDto> response = new ArrayList<>();
		try {
			String queryStr = "select b.oid as \"boutOid\", fbx.ml_odds as \"mlOdds\", fbx.exp_odds as \"expOdds\", ff.fighter_name as \"fighterName\", fight_cnt.cnt as \"count\", b.weight_class as \"weightClass\" \n"
					+ "	from ufc2.fighter_bout_xref fbx \n" + "		join ufc2.bout b on fbx.bout_oid = b.oid\n"
					+ "		join ufc2.fight f on f.oid = b.fight_oid\n"
					+ "		join ufc2.fighter ff on ff.oid = fbx.fighter_oid\n"
					+ "		join (select fbx1.fighter_oid as fighter2_oid, count(fbx1.oid) as cnt\n"
					+ "				from ufc2.fighter_bout_xref fbx1 \n" + "				join ufc2.bout b1\n"
					+ "						on fbx1.bout_oid = b1.oid \n" + "					join ufc2.fight f1\n"
					+ "						on b1.fight_oid = f1.oid \n"
					+ "					where f1.fight_date < \n" + "						(select f2.fight_date \n"
					+ "						 	from ufc2.fight f2 \n"
					+ "						 		where f2.fight_id=:anchor_fight \n" + "						)\n"
					+ "				group by fbx1.fighter_oid) fight_cnt "
					+ "			on fight_cnt.fighter2_oid = ff.oid\n" + "		where f.fight_id=:reference_fight \n"
					+ "		and fbx.exp_odds is not null " + " and fight_cnt.cnt >=:fightCnt";
			Query query = em.createNativeQuery(queryStr);
			query.setParameter("reference_fight", req.getFightId());
			query.setParameter("anchor_fight", req.getFightId().toString());
			query.setParameter("fightCnt", prevFightFloor);

			query.unwrap(org.hibernate.query.NativeQuery.class)
					.setResultTransformer(Transformers.aliasToBean(FightBetDto.class));
			@SuppressWarnings("unchecked")
			List<FightBetDto> res = query.getResultList();
			Map<String, List<FightBetDto>> resMap = new HashMap<>();
			for (FightBetDto potBet : res) {
				if (!resMap.containsKey(potBet.getBoutOid())) {
					resMap.put(potBet.getBoutOid(), new ArrayList<>());
				}
				resMap.get(potBet.getBoutOid()).add(potBet);
			}

			for (String k : resMap.keySet()) {
				if (resMap.get(k).size() == 2 && (Boolean.TRUE.equals(betFemale)
						|| GenderEnum.MALE == resMap.get(k).get(0).getWeightClass().gender)) {
					BoutBetDto betInfo = new BoutBetDto();
					betInfo.setOid(k);
					betInfo.setBoutName(String.format("%s vs %s", resMap.get(k).get(0).getFighterName(), resMap.get(k).get(1).getFighterName()));
					Double f1Diff = (resMap.get(k).get(0).getExpOdds() * 100) - resMap.get(k).get(0).getMlOdds();
					Double f2Diff = (resMap.get(k).get(1).getExpOdds() * 100) - resMap.get(k).get(1).getMlOdds();

					boolean useF1 = f1Diff > f2Diff;

					if (useF1) {
						betInfo.setOddsDiff(f1Diff);
						betInfo.setVegasOdds(resMap.get(k).get(0).getMlOdds());
						betInfo.setPredProb(resMap.get(k).get(0).getExpOdds() * 100);
						betInfo.setPredWinner(resMap.get(k).get(0).getFighterName());
						betInfo.setWagerWeight(oddDiffToWager(betInfo.getOddsDiff(),
								Integer.valueOf(resMap.get(k).get(0).getCount().toString()),
								Integer.valueOf(resMap.get(k).get(1).getCount().toString())));
						if (f1Diff < 0) {
							betInfo.setBet(false);
							betInfo.setNotes("No advantage exists at current odds");
						} else if (f1Diff < diffFloor * 100) {
							betInfo.setBet(false);
							betInfo.setNotes("Advantage below modal threshold");
						} else if (f1Diff > diffCeiling * 100) {
							betInfo.setBet(false);
							betInfo.setNotes("Advantage above modal threshold");
						} else {
							betInfo.setBet(true);
						}
					} else {
						betInfo.setOddsDiff(f2Diff);
						betInfo.setVegasOdds(resMap.get(k).get(1).getMlOdds());
						betInfo.setPredProb(resMap.get(k).get(1).getExpOdds() * 100);
						betInfo.setPredWinner(resMap.get(k).get(1).getFighterName());
						betInfo.setWagerWeight(oddDiffToWager(betInfo.getOddsDiff(),
								Integer.valueOf(resMap.get(k).get(0).getCount().toString()),
								Integer.valueOf(resMap.get(k).get(1).getCount().toString())));
						if (f2Diff < 0) {
							betInfo.setBet(false);
							betInfo.setNotes("No advantage exists at current odds");
						} else if (f2Diff < diffFloor * 100) {
							betInfo.setBet(false);
							betInfo.setNotes("Advantage below modal threshold");
						} else if (f2Diff > diffCeiling * 100) {
							betInfo.setBet(false);
							betInfo.setNotes("Advantage above modal threshold");
						} else {
							betInfo.setBet(true);
						}
					}
					response.add(betInfo);
				}
			}

			return new GetResponse(HttpStatus.OK, errorString, response);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}

	private Double oddDiffToWager(Double oddsDiff, Integer nFight1, Integer nFight2) {
		Double bet = betIntercept + (confDiffLin * oddsDiff) + (confDiffQuad * (oddsDiff * oddsDiff))
				+ (numFightLin * nFight1 * 2) + (numFightQuad * (nFight1 * nFight1) * 2) + (numFightLin * nFight2 * 2)
				+ (numFightQuad * (nFight2 * nFight2) * 2);

		if (bet > betCeiling) {
			return betCeiling.doubleValue();
		} else {
			return bet;
		}

	}
}
