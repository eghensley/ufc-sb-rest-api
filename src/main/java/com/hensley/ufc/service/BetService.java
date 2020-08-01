package com.hensley.ufc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hensley.ufc.domain.FightData;
import com.hensley.ufc.domain.FighterBoutXRefData;
import com.hensley.ufc.enums.ParseTargetEnum;
import com.hensley.ufc.pojo.dto.fight.FightBetDto;
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

	public BetService(ErrorService errorService, MappingUtils mappingUtils,
			FighterBoutXRefRepository fighterBoutXRefRepo, FightRepository fightRepo) {
		this.mappingUtils = mappingUtils;
		this.fighterBoutXRefRepo = fighterBoutXRefRepo;
		this.errorService = errorService;
		this.fightRepo = fightRepo;
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
}
