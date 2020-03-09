package com.hensley.ufc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.hensley.ufc.pojo.parse.RoundScoreFighterParseStore;
import com.hensley.ufc.pojo.parse.RoundScoreParseStore;
import com.hensley.ufc.pojo.parse.ScorePartialParse;
import com.hensley.ufc.pojo.parse.SingleRoundScoreParse;
import com.hensley.ufc.pojo.request.ParseRequest;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.pojo.response.UrlParseRequest;
import com.hensley.ufc.repository.BoutRepository;
import com.hensley.ufc.repository.FightRepository;
import com.hensley.ufc.repository.FighterBoutXRefRepository;
import com.hensley.ufc.repository.FighterRepository;
import com.hensley.ufc.repository.StrikeDataRepository;
import com.hensley.ufc.util.UrlUtils;

// TODO investigate save if switched fighters 

@Service
public class RoundScoreService {
	private static final Logger LOG = Logger.getLogger(RoundScoreService.class.toString());
	private static final String ERROR_ADDING_BOUTS = "Error adding bouts to fight %s.";
	private static final String FIGHT_LOAD_ERROR = "Fight %s could not be loaded from DB.";
	private static final String COMPLETION_MESSAGE = "Completed parsing request.  Found %s fights and parsed %s fights";
	private static final String JUDGE_SCORE_HTML_XPATH = "//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[3]/td/table/tbody/tr/td";
	private static final String NO_SCORES_FOUND = "No scores available for id %s";

	private static final String ERROR_SCRAPING_FIGHT = "Error scraping scores for fight %s.";

	private static final String ERROR_SCRAPING_BOUT = "Error scraping scores for bout %s.";
	private static final String ERROR_SCRAPING_ROUND = "Error scraping scores for round %s";

	private final ErrorService errorService;
	private final UrlUtils urlUtils;
	private final FighterRepository fighterRepo;
	private final FightRepository fightRepo;
	private final FighterBoutXRefRepository fighterXRefRepo;
	private final StrikeDataRepository strikeRepo;
	private final BoutRepository boutRepo;

	@Autowired
	public RoundScoreService(BoutRepository boutRepo, StrikeDataRepository strikeRepo,
			FighterBoutXRefRepository fighterXRefRepo, ErrorService errorService, UrlUtils urlUtils,
			FighterRepository fighterRepo, FightRepository fightRepo) {
		this.errorService = errorService;
		this.urlUtils = urlUtils;
		this.fighterRepo = fighterRepo;
		this.fightRepo = fightRepo;
		this.fighterXRefRepo = fighterXRefRepo;
		this.strikeRepo = strikeRepo;
		this.boutRepo = boutRepo;
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
						try {
							String fightName = fightPage.asText().split(":")[fightPage.asText().split(":").length - 1]
									.trim().replace(".", "");
							matchFightByName(fightName, baseUrl.getValue());
						} catch (Exception e) {
							String fightName2 = fightPage.asText().trim().replace(".", "");
							matchFightByName(fightName2, baseUrl.getValue());
						}
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

		Optional<List<FightData>> rawFightData = fightRepo.findFightByFuzzyName("%" + fightName);
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
		if (fightData.getCompleted()) {
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
		fighterOpt = fighterRepo.findByFighterName(fighterNameHtml.asText());
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
		HtmlPage page;
		ParseResponse response = new ParseResponse(request);
		List<FighterBoutXRefData> boutXrefList;
		String judgePath;
		List<HtmlTableRow> judgeRoundScores;
		String roundPath;

		Optional<BoutData> boutDataOpt = boutRepo.findByOid(boutId);
		BoutData boutData = boutDataOpt.get();

		String baseUrl = boutData.getMmaDecBoutUrl();
		UrlParseRequest urlParseRequest = urlUtils.parse(baseUrl);
		String errorStr = urlParseRequest.getErrorStr();
		if (urlParseRequest.getSuccess()) {
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
				// TODO investigate save if switched fighters 
				RoundScoreFighterParseStore extractedScores = scoreStore.matchFighterName(boutXref.getFighterName());
				List<SingleRoundScoreParse> roundScoreList = extractedScores.outputRoundScores();
				for (SingleRoundScoreParse roundScore : roundScoreList) {
					StrikeData roundStats = boutXref.getStatsByRound(roundScore.getRound());
					roundStats.setScore(roundScore.getScore());
				}
			}
			response.setStatus(HttpStatus.OK);
			return response;
		} else {
			return errorService.handleParseError(errorStr, response);
		}
	}

//	@Transactional
//	public ParseResponse scrapeScoresFromBout(String fightNameRaw, String baseUrl, ParseRequest fightRequest) {
//		String errorStr = null;
//		HtmlPage page;
//		UrlParseRequest urlParseRequest;
//		List<HtmlElement> round1ScoreHtml;
//		List<HtmlElement> round2ScoreHtml;
//		List<HtmlElement> round3ScoreHtml;
//		List<HtmlElement> round4ScoreHtml;
//		List<HtmlElement> round5ScoreHtml;
//		FighterData fighter1;
//		FighterData fighter2;
//
//		ParseRequest request = new ParseRequest(ParseTargetEnum.ROUNDS, null, null, null);
//		ParseResponse response = new ParseResponse(request);
//
//		try {
//
//			urlParseRequest = urlUtils.parse("http://mmadecisions.com/" + baseUrl);
//			errorStr = urlParseRequest.getErrorStr();
//			if (urlParseRequest.getSuccess()) {
//				page = urlParseRequest.getPage();
//				LOG.info("Completed FIGHTS Parse");
//
//				round1ScoreHtml = page.getByXPath(
//						"//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[3]");
//				round2ScoreHtml = page.getByXPath(
//						"//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[4]");
//				round3ScoreHtml = page.getByXPath(
//						"//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[5]");
//				round4ScoreHtml = page.getByXPath(
//						"//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[6]");
//				round5ScoreHtml = page.getByXPath(
//						"//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[7]");
//
//				Integer itemsFound = round1ScoreHtml.size() + round2ScoreHtml.size() + round3ScoreHtml.size()
//						+ round4ScoreHtml.size() + round5ScoreHtml.size();
//				response.setItemsFound(itemsFound);
//				LOG.info(String.format("%s item found", response.getItemsFound()));
//
//				if (itemsFound == 0) {
//					errorStr = String.format(NO_SCORES_FOUND, baseUrl);
//					response.addResponseMsg(HttpStatus.NO_CONTENT, errorStr);
//					return response;
//				}
//
//				else {
//					HashMap<String, String> nameMap = new HashMap<>();
//					fighter1 = matchFighterFromName("1", page, nameMap);
//					fighter2 = matchFighterFromName("2", page, nameMap);
//
//					matchRoundScores(fighter1, fighter2, fightData, round1ScoreHtml, 1);
//					matchRoundScores(fighter1, fighter2, fightData, round2ScoreHtml, 2);
//					matchRoundScores(fighter1, fighter2, fightData, round3ScoreHtml, 3);
//					matchRoundScores(fighter1, fighter2, fightData, round4ScoreHtml, 4);
//					matchRoundScores(fighter1, fighter2, fightData, round5ScoreHtml, 5);
//
//				}
//				LOG.log(Level.INFO,
//						String.format(COMPLETION_MESSAGE, response.getItemsFound(), response.getItemsCompleted()));
//				response.addResponseMsg(HttpStatus.OK, errorStr);
//				return response;
//			} else {
//				return errorService.handleParseError(errorStr, response);
//			}
//		} catch (
//
//		Exception e) {
//			errorStr = String.format(ERROR_SCRAPING_BOUT, "XXX");
//			return errorService.handleParseError(errorStr, e, response);
//		}
//	}

//	public void adfasdfa(HtmlPage page, Integer round) {
//		List<HtmlElement> roundScoreHtml;
//
//		roundScoreHtml = page.getByXPath(String.format(
//				"//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[%s]",
//				round + 2));
//
//	}
//
//	public void matchRoundScores(FighterData fighter1, FighterData fighter2, FightData fightData,
//			List<HtmlElement> roundScoreHtml, Integer round) {
//
//		String errorStr;
//
//		try {
//			if (!roundScoreHtml.isEmpty()) {
//
//				StrikeData fighter1RoundStrike = matchRoundStrikeData(fighter1, fightData, round);
//				StrikeData fighter2RoundStrike = matchRoundStrikeData(fighter2, fightData, round);
//
//				if (fighter1RoundStrike != null & fighter2RoundStrike != null) {
//					Double fighter1RoundScore = 0.0;
//					Double fighter2RoundScore = 0.0;
//					for (HtmlElement rnd1Score : roundScoreHtml) {
//						String xpath = rnd1Score.getCanonicalXPath();
//						HtmlTableDataCell fighter1Score = rnd1Score.getFirstByXPath(xpath + "/td[2]");
//						HtmlTableDataCell fighter2Score = rnd1Score.getFirstByXPath(xpath + "/td[3]");
//						if (fighter1Score == null || fighter2Score == null) {
//							break;
//						}
//
//						fighter1RoundScore += Integer.valueOf(fighter1Score.asText());
//						fighter2RoundScore += Integer.valueOf(fighter2Score.asText());
//					}
//					fighter1RoundStrike.setScore(fighter1RoundScore);
//					fighter2RoundStrike.setScore(fighter2RoundScore);
//					strikeRepo.save(fighter1RoundStrike);
//					strikeRepo.save(fighter2RoundStrike);
//				}
//			}
//		} catch (
//
//		Exception e) {
//			errorStr = String.format(ERROR_SCRAPING_ROUND, round);
//			return errorService.handleParseError(errorStr, e, response);
//		}
//	}
//
//	public StrikeData matchRoundStrikeData(FighterData fighter, FightData fightData, Integer round) {
//		StrikeData fighterRoundData;
//		Optional<List<StrikeData>> fighterRoundDataOpt;
//
//		fighterRoundDataOpt = strikeRepo.getStrikeDataByFighterAndFightAndRound(fighter.getOid(), fightData.getOid(),
//				round);
//		if (fighterRoundDataOpt.isPresent()) {
//			fighterRoundData = fighterRoundDataOpt.get().get(0);
//			return fighterRoundData;
//		} else {
//			throw new IllegalArgumentException(
//					String.format("Fighter %s bout data could not be found for fight %s and round %s.",
//							fighter.getFighterName(), fightData.getFightName(), round));
//		}
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	

//	@Transactional
//	public List<ScorePartialParse> fetchFightScoreParse(String fightNameRaw, String baseUrl) {
//		UrlParseRequest urlParseRequest;
//		HtmlPage page;
//		List<HtmlElement> boutPages;
//		String errorStr;
//		FightData fightData;
//		Integer boutsFound;
//		ParseRequest fightRequest = new ParseRequest(ParseTargetEnum.ROUNDS, null, null, null);
//		List<ScorePartialParse> response = new ArrayList<>();
//
//		String boutXpath = "//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[2]/tbody/tr/td[1]/b/a";
//
//		fightData = matchFightByName(fightNameRaw);
//		if (fightData.getCompleted()) {
//			return response;
//		}
//		LOG.info(String.format("Matched name %s to fightId %s", fightNameRaw, fightData.getOid()));
//
//		fightRequest.setFightId(fightData.getFightId());
//		ParseResponse fightResponse = new ParseResponse(fightRequest);
//
//		urlParseRequest = urlUtils.parse("http://mmadecisions.com/" + baseUrl);
//		errorStr = urlParseRequest.getErrorStr();
//		if (urlParseRequest.getSuccess()) {
//			page = urlParseRequest.getPage();
//			LOG.info("Completed FIGHTS Parse");
//			boutPages = page.getByXPath(boutXpath);
//			boutsFound = boutPages.size();
//			fightResponse.setItemsFound(boutsFound);
//			for (HtmlElement boutPage : boutPages) {
//				String xPath = boutPage.getCanonicalXPath();
//				DomAttr boutUrl = boutPage.getFirstByXPath(xPath + "/@href");
//
//				fightResponse.setItemsCompleted(fightResponse.getItemsCompleted() + 1);
////				scrapeScoresFromBout(fightData, boutUrl.getValue(), fightRequest);
//			}
//			fightResponse.setStatus(HttpStatus.ACCEPTED);
//			return fightResponse;
//		} else {
//			return errorService.handleParseError(errorStr, fightResponse);
//		}
//
//	}
//
//	@Transactional
//	public ParseResponse scrapeJudgeScores() {
//		String errorStr = null;
//		UrlParseRequest urlParseRequest;
//		HtmlPage page;
//		List<HtmlElement> fightPages;
//		String year = "2019";
//		Integer fightsFound;
//
//		ParseRequest request = new ParseRequest(ParseTargetEnum.ROUNDS, null, null, null);
//		ParseResponse response = new ParseResponse(request);
//
//		String yearUrl = String.format("http://mmadecisions.com/decisions-by-event/%s/", year);
//		String eventXpath = "//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[2]/tbody/tr/td[2]/a";
//
//		urlParseRequest = urlUtils.parse(yearUrl);
//
//		if (urlParseRequest.getSuccess()) {
//			page = urlParseRequest.getPage();
//			LOG.info(String.format("Completed Parse of fights for year %s", year));
//			fightPages = page.getByXPath(eventXpath);
//			fightsFound = fightPages.size();
//			response.setItemsFound(fightsFound);
//			for (HtmlElement fightPage : fightPages) {
//				if (fightPage.asText().toUpperCase().contains("UFC")) {
//					String xPath = fightPage.getCanonicalXPath();
//					DomAttr baseUrl = fightPage.getFirstByXPath(xPath + "/@href");
//					scrapeFightScores(fightPage.asText(), baseUrl.getValue());
//				}
//				response.setItemsCompleted(response.getItemsCompleted() + 1);
//			}
//		}
//		response.setStatus(HttpStatus.ACCEPTED);
//		return response;
//	}
//
//	public ParseResponse scrapeFightScores(String fightNameRaw, String baseUrl) {
//		UrlParseRequest urlParseRequest;
//		HtmlPage page;
//		List<HtmlElement> boutPages;
//		String errorStr;
//		FightData fightData;
//		Integer boutsFound;
//		ParseRequest fightRequest = new ParseRequest(ParseTargetEnum.ROUNDS, null, null, null);
//
//		String boutXpath = "//*[@id=\"container_outer\"]/table[2]/tbody/tr/td[1]/table[2]/tbody/tr/td[1]/b/a";
//
//		try {
//			fightData = matchFightByName(fightNameRaw);
//			LOG.info(String.format("Matched name %s to fightId %s", fightNameRaw, fightData.getOid()));
//
//			fightRequest.setFightId(fightData.getFightId());
//			ParseResponse fightResponse = new ParseResponse(fightRequest);
//
//			urlParseRequest = urlUtils.parse("http://mmadecisions.com/" + baseUrl);
//			errorStr = urlParseRequest.getErrorStr();
//			if (urlParseRequest.getSuccess()) {
//				page = urlParseRequest.getPage();
//				LOG.info("Completed FIGHTS Parse");
//				boutPages = page.getByXPath(boutXpath);
//				boutsFound = boutPages.size();
//				fightResponse.setItemsFound(boutsFound);
//				for (HtmlElement boutPage : boutPages) {
//					String xPath = boutPage.getCanonicalXPath();
//					DomAttr boutUrl = boutPage.getFirstByXPath(xPath + "/@href");
//
//					fightResponse.setItemsCompleted(fightResponse.getItemsCompleted() + 1);
////				scrapeScoresFromBout(fightData, boutUrl.getValue(), fightRequest);
//				}
//				fightResponse.setStatus(HttpStatus.ACCEPTED);
//				return fightResponse;
//			} else {
//				return errorService.handleParseError(errorStr, fightResponse);
//			}
//		} catch (Exception e) {
//			ParseResponse fightResponse = new ParseResponse(fightRequest);
//			errorStr = String.format(ERROR_SCRAPING_FIGHT, fightNameRaw);
//			return errorService.handleParseError(errorStr, e, fightResponse);
//		}
//	}
//
//
//
//	public FighterBoutXRefData matchFighterBoutData(FighterData fighter, FightData fightData) {
//		Optional<List<FighterBoutXRefData>> fighterXrefOpt;
//		List<FighterBoutXRefData> fighterXrefList;
//		FighterBoutXRefData fighterXref;
//
//		fighterXrefOpt = fighterXRefRepo.getFighterXRefByFighterOidFightOid(fighter.getOid(), fightData.getOid());
//		if (fighterXrefOpt.isPresent()) {
//			fighterXrefList = fighterXrefOpt.get();
//			fighterXref = fighterXrefList.get(0);
//			return fighterXref;
//		} else {
//			throw new IllegalArgumentException(String.format("Fighter %s bout data could not be found for fight %s",
//					fighter.getFighterName(), fightData.getFightName()));
//		}
//	}
//

}
