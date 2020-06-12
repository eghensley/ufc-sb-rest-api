package com.hensley.ufc.service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.hensley.ufc.domain.BoutData;
import com.hensley.ufc.domain.FightData;
import com.hensley.ufc.domain.FighterBoutXRefData;
import com.hensley.ufc.domain.FighterData;
import com.hensley.ufc.domain.StrikeData;
import com.hensley.ufc.enums.ParseTargetEnum;
import com.hensley.ufc.pojo.dto.fighter.FighterBoutEloScoresDto;
import com.hensley.ufc.pojo.parse.RoundScoreFighterParseStore;
import com.hensley.ufc.pojo.parse.RoundScoreParseStore;
import com.hensley.ufc.pojo.parse.SingleRoundScoreParse;
import com.hensley.ufc.pojo.request.AddBoutRoundScoreRequest;
import com.hensley.ufc.pojo.request.AddBoutWinProb;
import com.hensley.ufc.pojo.request.AddMyBookieOddsRequest;
import com.hensley.ufc.pojo.request.AddRoundMlScore;
import com.hensley.ufc.pojo.request.ParseRequest;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.pojo.response.UrlParseRequest;
import com.hensley.ufc.repository.BoutRepository;
import com.hensley.ufc.repository.FightRepository;
import com.hensley.ufc.repository.FighterBoutXRefRepository;
import com.hensley.ufc.repository.FighterRepository;
import com.hensley.ufc.repository.StrikeDataRepository;
import com.hensley.ufc.util.MappingUtils;
import com.hensley.ufc.util.UrlUtils;

@Service
public class RoundScoreService {
	private static final Logger LOG = Logger.getLogger(RoundScoreService.class.toString());
	private static final String BOUT_LOAD_ERROR = "Bout %s could not be loaded from DB.";
	private static final String BOUT_ODDS_ALREADY_COMPLETE = "Round odds already added for bout %s.";
	private static final String NO_SCORES_FOUND = "No scores available for id %s";
	private static final String ERROR_SCRAPING_BOUT = "Error scraping scores for bout %s.";

	private final ErrorService errorService;
	private final UrlUtils urlUtils;
	private final FighterRepository fighterRepo;
	private final FightRepository fightRepo;
	private final FighterBoutXRefRepository fighterXRefRepo;
	private final StrikeDataRepository strikeRepo;
	private final BoutRepository boutRepo;
	private final MappingUtils mappingUtils;
	private final EntityManager em;

	@Autowired
	public RoundScoreService(BoutRepository boutRepo, ErrorService errorService, UrlUtils urlUtils,
			FighterRepository fighterRepo, FightRepository fightRepo, MappingUtils mappingUtils,
			StrikeDataRepository strikeRepo, FighterBoutXRefRepository fighterXRefRepo, EntityManager em) {
		this.errorService = errorService;
		this.urlUtils = urlUtils;
		this.fighterRepo = fighterRepo;
		this.fightRepo = fightRepo;
		this.fighterXRefRepo = fighterXRefRepo;
		this.strikeRepo = strikeRepo;
		this.boutRepo = boutRepo;
		this.mappingUtils = mappingUtils;
		this.em = em;
	}

	public GetResponse getLastFBX(String fighterOid, String fightIdx) {
		FighterBoutEloScoresDto responseData;
		String errorString;
		try {
			Optional<List<FighterBoutXRefData>> prevFbxData = fighterXRefRepo.getPrevFBX(fighterOid, fightIdx);
			if (prevFbxData.isPresent() && !CollectionUtils.isEmpty(prevFbxData.get())
					&& prevFbxData.get().get(0).getOffStrikeEloPre() != null) {
				responseData = (FighterBoutEloScoresDto) mappingUtils.mapToDto(prevFbxData.get().get(0),
						FighterBoutEloScoresDto.class);
				return new GetResponse(HttpStatus.ACCEPTED, null, responseData);
			} else {
				String noPreviousDataFound = String.format("No previous bout found for fighter %s before fight %s",
						fighterOid, fightIdx);
				responseData = new FighterBoutEloScoresDto();
				return new GetResponse(HttpStatus.ACCEPTED, noPreviousDataFound, responseData);
			}
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}

	public GetResponse getCountLastFights(String fighterOid, String fightIdx) {
		String errorString;
		try {
			Integer prevFightCounts = fighterXRefRepo.getCountPreviousFights(fighterOid, fightIdx);
			return new GetResponse(HttpStatus.ACCEPTED, null, prevFightCounts);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}

	@Transactional
	public ParseResponse clearEloScores() {
		ParseRequest request = new ParseRequest(ParseTargetEnum.ROUNDS, null, null, null);
		ParseResponse response = new ParseResponse(request);
		Query query = em.createNativeQuery(
				"update ufc2.fighter_bout_xref set def_grap_elo_post = null, def_grap_elo_pre = null, def_strike_elo_post = null, def_strike_elo_pre = null, off_grap_elo_post = null, off_grap_elo_pre = null, off_strike_elo_post = null, off_strike_elo_pre = null, chin_strike_elo_post = null, chin_strike_elo_pre = null, evas_grap_elo_pre = null, evas_grap_elo_post = null, power_strike_elo_post = null, power_strike_elo_pre = null, sub_grap_elo_post = null, sub_grap_elo_pre = null");
		int resp = query.executeUpdate();
		response.setItemsFound(1);
		response.setItemsCompleted(resp);
		response.setStatus(HttpStatus.ACCEPTED);
		return response;
	}

	@Transactional
	public ParseResponse addFightScoreUrl(String year) {
		UrlParseRequest urlParseRequest;
		HtmlPage page;
		List<HtmlElement> fightPages;

		ParseRequest request = new ParseRequest(ParseTargetEnum.ROUNDS, null, null, null);
		ParseResponse response = new ParseResponse(request);

		String yearUrl = String.format("http://mmadecisions.com/decisions-by-event/%s/", year);
		String eventXpath = "//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[2]/tbody/tr/td[2]/a";

		urlParseRequest = urlUtils.parse(yearUrl);

		if (urlParseRequest.getSuccess()) {
			page = urlParseRequest.getPage();
			LOG.info(String.format("Completed Parse of fights for year %s", year));
			fightPages = page.getByXPath(eventXpath);
			for (HtmlElement fightPage : fightPages) {
				try {
					if (fightPage.asText().toUpperCase().contains("UFC")) {
						response.setItemsFound(response.getItemsFound() + 1);
						String xPath = fightPage.getCanonicalXPath();
						DomAttr baseUrl = fightPage.getFirstByXPath(xPath + "/@href");
						String fightName2 = fightPage.asText().trim();
						matchFightByName(fightName2, baseUrl.getValue());
					}
					response.setItemsCompleted(response.getItemsCompleted() + 1);
				} catch (Exception e) {
					errorService.handleParseError(e.getLocalizedMessage(), e, response);
				}
			}
		}
		response.setStatus(HttpStatus.ACCEPTED);
		return response;
	}

	public void matchFightByName(String fightName, String baseUrl) {
		FightData fightData;

		Optional<List<FightData>> rawFightData = fightRepo.findFightByFuzzyName(fightName);
		if (rawFightData.isPresent() & rawFightData.get().size() == 1) {

			fightData = rawFightData.get().get(0);
			LOG.info(String.format("Matched name %s to fightId %s", fightName, fightData.getOid()));

			fightData.setMmaDecFightUrl("http://mmadecisions.com/" + baseUrl);
			fightRepo.save(fightData);
		} else {
			throw new IllegalArgumentException(String.format("No fight matched for fight name %s", fightName));
		}
	}

	@Transactional
	public ParseResponse addManualBoutScore(AddBoutRoundScoreRequest request) {
		ParseResponse response = new ParseResponse();
		response.setItemsFound(1);
		try {
			StrikeData updatedStrikeData = (StrikeData) mappingUtils.mapToModel(request, StrikeData.class);
			strikeRepo.save(updatedStrikeData);
			response.setItemsCompleted(1);
			response.setStatus(HttpStatus.valueOf(200));
			return response;
		} catch (Exception e) {
			response.setItemsCompleted(0);
			response.addResponseMsg(HttpStatus.valueOf(500), e.getLocalizedMessage());
			return response;
		}
	}

	@Transactional
	public ParseResponse addEloScore(FighterBoutEloScoresDto request) {
		ParseResponse response = new ParseResponse();
		response.setItemsFound(1);
		try {
			FighterBoutXRefData prevXRefData = fighterXRefRepo.findByOid(request.getOid());

			prevXRefData.setOffStrikeEloPre(request.getOffStrikeEloPre());
			prevXRefData.setDefStrikeEloPre(request.getDefStrikeEloPre());
			prevXRefData.setOffStrikeEloPost(request.getOffStrikeEloPost());
			prevXRefData.setDefStrikeEloPost(request.getDefStrikeEloPost());

			prevXRefData.setOffGrapplingEloPre(request.getOffGrapplingEloPre());
			prevXRefData.setDefGrapplingEloPre(request.getDefGrapplingEloPre());
			prevXRefData.setOffGrapplingEloPost(request.getOffGrapplingEloPost());
			prevXRefData.setDefGrapplingEloPost(request.getDefGrapplingEloPost());

			prevXRefData.setSubGrapplingEloPre(request.getSubGrapplingEloPre());
			prevXRefData.setEvasGrapplingEloPre(request.getEvasGrapplingEloPre());
			prevXRefData.setSubGrapplingEloPost(request.getSubGrapplingEloPost());
			prevXRefData.setEvasGrapplingEloPost(request.getEvasGrapplingEloPost());

			prevXRefData.setPowerStrikeEloPre(request.getPowerStrikeEloPre());
			prevXRefData.setChinStrikeEloPre(request.getChinStrikeEloPre());
			prevXRefData.setPowerStrikeEloPost(request.getPowerStrikeEloPost());
			prevXRefData.setChinStrikeEloPost(request.getChinStrikeEloPost());

			fighterXRefRepo.save(prevXRefData);
			response.setItemsCompleted(1);
			response.setStatus(HttpStatus.valueOf(200));
			return response;
		} catch (Exception e) {
			response.setItemsCompleted(0);
			response.addResponseMsg(HttpStatus.valueOf(500), e.getLocalizedMessage());
			return response;
		}
	}

	@Transactional
	public ParseResponse addMlRoundScore(AddRoundMlScore request) {
		ParseResponse response = new ParseResponse();
		response.setItemsFound(1);
		try {
			StrikeData updatedStrikeData = strikeRepo.findByOid(request.getOid());
			updatedStrikeData.setKoScore(request.getKoScore());
			updatedStrikeData.setSubmissionScore(request.getSubScore());
			strikeRepo.save(updatedStrikeData);
			response.setItemsCompleted(1);
			response.setStatus(HttpStatus.valueOf(200));
			return response;
		} catch (Exception e) {
			response.setItemsCompleted(0);
			response.addResponseMsg(HttpStatus.valueOf(500), e.getLocalizedMessage());
			return response;
		}
	}

	@Transactional
	public ParseResponse addMlBoutScore(AddBoutWinProb request) {
		ParseResponse response = new ParseResponse();
		response.setItemsFound(1);
		try {
			FighterBoutXRefData updatedFighterBoutData = fighterXRefRepo.findByOid(request.getOid());
			updatedFighterBoutData.setExpOdds(request.getExpOdds());
			fighterXRefRepo.save(updatedFighterBoutData);
			response.setItemsCompleted(1);
			response.setStatus(HttpStatus.valueOf(200));
			return response;
		} catch (Exception e) {
			response.setItemsCompleted(0);
			response.addResponseMsg(HttpStatus.valueOf(500), e.getLocalizedMessage());
			return response;
		}
	}

	@Transactional
	public ParseResponse addBoutScoreUrl(String fightOid) {
		UrlParseRequest urlParseRequest;
		HtmlPage page;
		List<HtmlElement> boutPages;
		String errorStr;
		FightData fightData;
		Integer boutsFound;
		ParseRequest fightRequest = new ParseRequest(ParseTargetEnum.ROUNDS, fightOid, null, null);
		ParseResponse fightResponse = new ParseResponse(fightRequest);

		String boutXpath = "//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[2]/tbody/tr/td[1]/b/a";

		fightData = fightRepo.getOne(fightOid);
		if (fightData.evalBoutDecCompleted()) {
			String msg = String.format("All bouts for fight %s have judge decision URL's", fightData.getFightName());
			LOG.info(msg);
			fightResponse.setErrorMsg(msg);
			fightResponse.setStatus(HttpStatus.ACCEPTED);
			return fightResponse;
		}

		fightRequest.setFightId(fightData.getFightId());

		urlParseRequest = urlUtils.parse(fightData.getMmaDecFightUrl());
		errorStr = urlParseRequest.getErrorStr();
		if (urlParseRequest.getSuccess()) {
			page = urlParseRequest.getPage();
			LOG.info("Completed FIGHT Parse");
			boutPages = page.getByXPath(boutXpath);
			boutsFound = boutPages.size();
			fightResponse.setItemsFound(boutsFound);
			for (HtmlElement boutPage : boutPages) {
				String xPath = boutPage.getCanonicalXPath();
				DomAttr boutUrl = boutPage.getFirstByXPath(xPath + "/@href");
				BoutData bout = linkBout(fightData, boutUrl.getValue(), fightRequest);
				if (bout != null) {
					bout.setMmaDecBoutUrl("http://mmadecisions.com/" + boutUrl.getValue());
					boutRepo.save(bout);
					fightResponse.setItemsCompleted(fightResponse.getItemsCompleted() + 1);
				}
			}
			fightResponse.setStatus(HttpStatus.ACCEPTED);
			return fightResponse;
		} else {
			return errorService.handleParseError(errorStr, fightResponse);
		}

	}

	public BoutData linkBout(FightData fightData, String baseUrl, ParseRequest fightRequest) {
		String errorStr = null;
		HtmlPage page;
		UrlParseRequest urlParseRequest;
		FighterData fighter1;
		FighterData fighter2;
		try {
			urlParseRequest = urlUtils.parse("http://mmadecisions.com/" + baseUrl);
			errorStr = urlParseRequest.getErrorStr();
			if (urlParseRequest.getSuccess()) {
				page = urlParseRequest.getPage();
				LOG.info("Completed BOUTS Parse");
				BoutData boutData = null;
				fighter1 = matchFighterFromName("1", page);
				if (fighter1 != null) {
					boutData = matchBoutData(fighter1, fightData);
				}
				if (boutData == null) {
					fighter2 = matchFighterFromName("2", page);
					if (fighter2 != null) {
						boutData = matchBoutData(fighter2, fightData);
					}
				}
				return boutData;
			} else {
				LOG.info(errorStr);
				return null;
			}
		} catch (

		Exception e) {
			LOG.info(e.getLocalizedMessage());
			return null;
		}
	}

	public FighterData matchFighterFromName(String fighterNum, HtmlPage page) {
		FighterData fighter;
		DomText fighterNameHtml;
		Optional<FighterData> fighterOpt;
		String trKey = null;

		if ("1".equals(fighterNum)) {
			trKey = "1";
		} else {
			trKey = "3";
		}
		fighterNameHtml = page.getFirstByXPath(String.format(
				"//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[1]/td[1]/table/tbody/tr[%s]/td[%s]/a/text()",
				trKey, fighterNum));
		fighterOpt = fighterRepo
				.findByFuzzyFighterName(Normalizer.normalize(fighterNameHtml.asText(), Normalizer.Form.NFD));
		if (fighterOpt.isPresent()) {
			fighter = fighterOpt.get();
			return fighter;
		} else {
			LOG.info(String.format("Fighter %s could not be matched to DB.", fighterNameHtml.asText()));
			return null;
		}
	}

	public BoutData matchBoutData(FighterData fighter, FightData fightData) {
		BoutData boutData;
		Optional<List<BoutData>> boutDataOpt;

		boutDataOpt = boutRepo.findBoutIdByFightIdAndFighterId(fightData.getOid(), fighter.getOid());
		if (boutDataOpt.isPresent()) {
			boutData = boutDataOpt.get().get(0);
			return boutData;
		} else {
			LOG.info(String.format("Fighter %s bout data could not be found for fight %s.", fighter.getFighterName(),
					fightData.getFightName()));
			return null;
		}
	}

	@Transactional
	public ParseResponse addBoutScores(String boutId) {
		ParseRequest request = new ParseRequest(ParseTargetEnum.ROUNDS, null, boutId, null);
		ParseResponse response = new ParseResponse(request);

		Optional<BoutData> boutDataOpt = boutRepo.findByOid(boutId);
		if (!boutDataOpt.isPresent()) {
			String msg = String.format(BOUT_LOAD_ERROR, boutId);
			LOG.info(msg);
			response.setErrorMsg(msg);
			response.setStatus(HttpStatus.valueOf(500));
			return response;
		}
		BoutData boutData = boutDataOpt.get();

		if (!boutData.evalIfBoutScoresMissing()) {
			String msg = String.format(BOUT_ODDS_ALREADY_COMPLETE, boutId);
			LOG.info(msg);
			response.setErrorMsg(msg);
			response.setStatus(HttpStatus.valueOf(200));
			return response;
		}

		String baseUrl = boutData.getMmaDecBoutUrl();
		UrlParseRequest urlParseRequest = urlUtils.parse(baseUrl);
		String errorStr = urlParseRequest.getErrorStr();
		if (urlParseRequest.getSuccess()) {
			return scrapeScoresFromBout(boutData, urlParseRequest, errorStr, response, baseUrl);
		} else {
			return errorService.handleParseError(errorStr, response);
		}
	}

	public ParseResponse scrapeScoresFromBout(BoutData boutData, UrlParseRequest urlParseRequest, String errorStr,
			ParseResponse response, String baseUrl) {
		HtmlPage page;
		List<FighterBoutXRefData> boutXrefList;
		String judgePath;
		List<HtmlTableRow> judgeRoundScores;
		String roundPath;

		try {
			page = urlParseRequest.getPage();
			LOG.info("Completed SCORES Parse");
			RoundScoreParseStore scoreStore = new RoundScoreParseStore();

			Integer fighterNameNmbr = 0;
			List<HtmlElement> webFighterNames = page.getByXPath(
					"//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[1]/td[1]/table/tbody/tr/td/a");
			for (HtmlElement webFighterName : webFighterNames) {
				fighterNameNmbr += 1;
				scoreStore.addNameToFighter(webFighterName.asText().trim(), fighterNameNmbr);
			}

			List<HtmlTableDataCell> judgeScoreList = page.getByXPath(
					"//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[3]/td/table/tbody/tr/td");

			Integer itemsFound = judgeScoreList.size();
			response.setItemsFound(itemsFound);
			LOG.info(String.format("%s item found", response.getItemsFound()));
			if (itemsFound == 0) {
				errorStr = String.format(NO_SCORES_FOUND, baseUrl);
				response.addResponseMsg(HttpStatus.NO_CONTENT, errorStr);
				return response;
			}
			for (HtmlTableDataCell judgeScores : judgeScoreList) {
				judgePath = judgeScores.getCanonicalXPath();
				judgeRoundScores = page.getByXPath(judgePath + "/table/tbody/tr[@class=\"decision\"]");
				Integer round = 0;
				for (HtmlTableRow roundScores : judgeRoundScores) {
					round += 1;
					roundPath = roundScores.getCanonicalXPath();
					List<HtmlElement> fighterScores = page.getByXPath(roundPath + "/td");
					Integer fighterNmbr = 1;
					scoreStore.addScoreToFighter(fighterNmbr, round,
							Integer.valueOf(fighterScores.get(fighterNmbr).asText()));
					fighterNmbr = 2;
					scoreStore.addScoreToFighter(fighterNmbr, round,
							Integer.valueOf(fighterScores.get(fighterNmbr).asText()));
				}
			}
			boutXrefList = boutData.getFighterBoutXRefs();
			for (FighterBoutXRefData boutXref : boutXrefList) {
				RoundScoreFighterParseStore extractedScores = scoreStore.matchFighterName(boutXref.getFighterName());
				List<SingleRoundScoreParse> roundScoreList = extractedScores.outputRoundScores();
				for (SingleRoundScoreParse roundScore : roundScoreList) {
					StrikeData roundStats = boutXref.getStatsByRound(roundScore.getRound());
					roundStats.setScore(roundScore.getScore());
				}
			}
			response.setStatus(HttpStatus.OK);
			return response;
		} catch (Exception e) {
			errorStr = String.format(ERROR_SCRAPING_BOUT, boutData.getBoutId());
			LOG.info(errorStr);
			return errorService.handleParseError(errorStr, e, response);
		}
	}

	@Transactional
	public ParseResponse addManualMlOdds(AddMyBookieOddsRequest request) {
		ParseResponse response = new ParseResponse();
		response.setItemsFound(1);
		try {
			FighterBoutXRefData fbx = fighterXRefRepo.findByOid(request.getOid());
			fbx.setMlOdds(request.getMlOdds());
			fighterXRefRepo.save(fbx);
			response.setItemsCompleted(1);
			response.setStatus(HttpStatus.valueOf(200));
			return response;
		} catch (Exception e) {
			response.setItemsCompleted(0);
			response.addResponseMsg(HttpStatus.valueOf(500), e.getLocalizedMessage());
			return response;
		}
	}
}
