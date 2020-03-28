package com.hensley.ufc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.hensley.ufc.domain.BfoExpectedOutcomeData;
import com.hensley.ufc.domain.FightData;
import com.hensley.ufc.domain.FighterBoutXRefData;
import com.hensley.ufc.enums.ParseTargetEnum;
import com.hensley.ufc.pojo.parse.ExpOutcomeRawParse;
import com.hensley.ufc.pojo.request.ParseRequest;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.pojo.response.UrlParseRequest;
import com.hensley.ufc.repository.FightRepository;
import com.hensley.ufc.repository.FighterBoutXRefRepository;
import com.hensley.ufc.util.UrlUtils;

@Service
public class OddsService {
	private static final Logger LOG = Logger.getLogger(OddsService.class.toString());

	private final UrlUtils urlUtils;
	private final FightRepository fightRepo;
	private final ErrorService errorService;
	private final FighterBoutXRefRepository fighterBoutXrefRepo;

	private static final String FIGHT_LOAD_ERROR = "Fight %s could not be loaded from DB.";
	private static final String ERROR_ADDING_BOUTS = "Error adding bout odds to fight %s.";
	private static final String COMPLETION_MESSAGE = "Completed parsing request.  Found %s bouts and parsed %s bouts";
	private static final String EXPECTED_OUTCOME_PATH = "//*[@id=\"event-outcome-container\"]/@data-outcomes";
	private static final String ITEMS_FOUND = "%s item found";
	private static final String NO_ODDS_DATA_FOUND = "No odds data available for fight id %s";
	private static final String NO_EXP_OUTCOMES_FOUND = "No expected outcome data available for fight id %s";
	private static final String EVEN_ODDS_PATH = "/html/body/div[2]/div[1]/div/div[2]/div[3]/table/tbody/tr[@class=\"even\"]";
	private static final String ODD_ODDS_PATH = "/html/body/div[2]/div[1]/div/div[2]/div[3]/table/tbody/tr[@class=\"odd\"]";

	private static final String ODDS_PATH = "/td/a/span/span[1]";
	private static final String ODDS_FIGHTER_NAME_PATH = "/th/a/@href";

	public OddsService(UrlUtils urlUtils, FightRepository fightRepo, ErrorService errorService,
			FighterBoutXRefRepository fighterBoutXrefRepo) {
		this.urlUtils = urlUtils;
		this.fightRepo = fightRepo;
		this.errorService = errorService;
		this.fighterBoutXrefRepo = fighterBoutXrefRepo;
	}

	@Transactional
	public ParseResponse scrapeExpOutcomesFromFightOid(String fightId) {
		String errorStr = null;
		Optional<FightData> fightDataOpt;
		FightData fightData;

		ParseRequest request = new ParseRequest(ParseTargetEnum.FIGHTS, fightId, null, null);
		ParseResponse response = new ParseResponse(request);

		fightDataOpt = fightRepo.findByFightId(fightId);
		if (!fightDataOpt.isPresent()) {
			errorStr = String.format(FIGHT_LOAD_ERROR, fightId);
			LOG.log(Level.WARNING, errorStr);
			response.addResponseMsg(HttpStatus.NO_CONTENT, errorStr);
			return response;
		} else {
			fightData = fightDataOpt.get();
		}
		try {
			return addBoutExpOutcomes(fightData, response);
		} catch (Exception e) {
			errorStr = String.format(ERROR_ADDING_BOUTS, fightId);
			return errorService.handleParseError(errorStr, e, response);
		}
	}

	public ParseResponse addBoutExpOutcomes(FightData fightData, ParseResponse response) {
		String url = String.format(fightData.getBestFightOddsUrl());
		UrlParseRequest urlParseRequest;
		String errorStr;
		HtmlPage page;
		List<DomAttr> expOutcomeHTML;
		Gson gson = new Gson();

		urlParseRequest = urlUtils.parse(url);
		errorStr = urlParseRequest.getErrorStr();
		if (urlParseRequest.getSuccess()) {

			Optional<List<FighterBoutXRefData>> fighterBouts = fighterBoutXrefRepo
					.getFighterXRefByFightOid(fightData.getOid());
			page = urlParseRequest.getPage();
			LOG.info("Completed FIGHT EXPECTED OUTCOME Parse");

			expOutcomeHTML = page.getByXPath(EXPECTED_OUTCOME_PATH);
			response.setItemsFound(expOutcomeHTML.size());
			LOG.info(String.format(ITEMS_FOUND, response.getItemsFound()));

			if (expOutcomeHTML.isEmpty()) {
				errorStr = String.format(NO_EXP_OUTCOMES_FOUND, response.getRequest().getFightId());
				return errorService.handleParseError(errorStr, response);
			} else {
				for (DomAttr expOutcomeItem : expOutcomeHTML) {
					System.out.println(expOutcomeItem.getValue());
					ExpOutcomeRawParse expOutcomePojo = gson.fromJson(expOutcomeItem.getValue(),
							ExpOutcomeRawParse.class);
					expOutcomePojo.toString();
					Map<String, BfoExpectedOutcomeData> expOutcomeMap = new HashMap<>();
					for (List<List<String>> expOutcomeBout : expOutcomePojo.getData()) {
						List<String> fighterNames = expOutcomeBout.get(0);
						List<String> expOutcomes = expOutcomeBout.get(1);
						BfoExpectedOutcomeData fighter1BoutExpOutcome = new BfoExpectedOutcomeData();
						fighter1BoutExpOutcome.setDecisionWin(Double.valueOf(expOutcomes.get(0)));
						fighter1BoutExpOutcome.setFinishWin(Double.valueOf(expOutcomes.get(1)));
						fighter1BoutExpOutcome.setDraw(Double.valueOf(expOutcomes.get(2)));
						fighter1BoutExpOutcome.setFinishLoss(Double.valueOf(expOutcomes.get(3)));
						fighter1BoutExpOutcome.setDecisionLoss(Double.valueOf(expOutcomes.get(4)));
						expOutcomeMap.put(fighterNames.get(0), fighter1BoutExpOutcome);

						BfoExpectedOutcomeData fighter2BoutExpOutcome = new BfoExpectedOutcomeData();
						fighter2BoutExpOutcome.setDecisionLoss(Double.valueOf(expOutcomes.get(0)));
						fighter2BoutExpOutcome.setFinishLoss(Double.valueOf(expOutcomes.get(1)));
						fighter2BoutExpOutcome.setDraw(Double.valueOf(expOutcomes.get(2)));
						fighter2BoutExpOutcome.setFinishWin(Double.valueOf(expOutcomes.get(3)));
						fighter2BoutExpOutcome.setDecisionWin(Double.valueOf(expOutcomes.get(4)));
						expOutcomeMap.put(fighterNames.get(1), fighter2BoutExpOutcome);
						response.setItemsCompleted(response.getItemsCompleted() + 1);
					}
					matchFighterBoutByFighterName(fighterBouts.get(), expOutcomeMap);
				}
			}
			LOG.log(Level.INFO,
					String.format(COMPLETION_MESSAGE, response.getItemsFound(), response.getItemsCompleted()));
			response.addResponseMsg(HttpStatus.OK, errorStr);
			return response;
		} else {
			return errorService.handleParseError(errorStr, response);
		}
	}

	public void matchFighterBoutByFighterName(List<FighterBoutXRefData> availableBouts,
			Map<String, BfoExpectedOutcomeData> expOutcomeMap) {
		for (FighterBoutXRefData availableBout : availableBouts) {
			if (availableBout.getBfoExpectedOutcomes() != null) {
				continue;
			}
			List<Integer> scoreList = new ArrayList<>();
			Map<Integer, BfoExpectedOutcomeData> nameScores = new HashMap<>();
			for (String expOutcomeName : expOutcomeMap.keySet()) {
				Integer fighterMatch = StringUtils.getLevenshteinDistance(availableBout.getFighterName(),
						expOutcomeName);
				nameScores.put(fighterMatch, expOutcomeMap.get(expOutcomeName));
				scoreList.add(fighterMatch);
			}
			availableBout.setBfoExpectedOutcomes(nameScores.get(Collections.min(scoreList)));
			fighterBoutXrefRepo.save(availableBout);
		}
	}

	@Transactional
	public ParseResponse scrapeOddsFromFightOid(String fightId) {
		String errorStr = null;
		Optional<FightData> fightDataOpt;
		FightData fightData;

		ParseRequest request = new ParseRequest(ParseTargetEnum.FIGHTS, fightId, null, null);
		ParseResponse response = new ParseResponse(request);

		fightDataOpt = fightRepo.findByFightId(fightId);
		if (!fightDataOpt.isPresent()) {
			errorStr = String.format(FIGHT_LOAD_ERROR, fightId);
			LOG.log(Level.WARNING, errorStr);
			response.addResponseMsg(HttpStatus.NO_CONTENT, errorStr);
			return response;
		} else {
			fightData = fightDataOpt.get();
		}
		try {
			return addBoutOdds(fightData, response);
		} catch (Exception e) {
			errorStr = String.format(ERROR_ADDING_BOUTS, fightId);
			return errorService.handleParseError(errorStr, e, response);
		}
	}

	public ParseResponse addBoutOdds(FightData fightData, ParseResponse response) {
		String url = String.format(fightData.getBestFightOddsUrl());
		UrlParseRequest urlParseRequest;
		String errorStr;
		HtmlPage page;
		List<HtmlElement> fighterOddsHTML;
		List<HtmlElement> oddsHtml;
		
		urlParseRequest = urlUtils.parse(url);
		errorStr = urlParseRequest.getErrorStr();
		if (urlParseRequest.getSuccess()) {

			Optional<List<FighterBoutXRefData>> fighterBouts = fighterBoutXrefRepo
					.getFighterXRefByFightOid(fightData.getOid());
			page = urlParseRequest.getPage();
			LOG.info("Completed FIGHT ODDS Parse");

			fighterOddsHTML = page.getByXPath(EVEN_ODDS_PATH);
			fighterOddsHTML.addAll(page.getByXPath(ODD_ODDS_PATH));
			response.setItemsFound(fighterOddsHTML.size());
			LOG.info(String.format(ITEMS_FOUND, response.getItemsFound()));

			if (fighterOddsHTML.isEmpty()) {
				errorStr = String.format(NO_ODDS_DATA_FOUND, response.getRequest().getFightId());
				return errorService.handleParseError(errorStr, response);
			} else {
				Map<String, Double> fighterBoutOddsMap = new HashMap<>();
				for (HtmlElement fighterOddsItem : fighterOddsHTML) {
					String fighterOddsRoot = fighterOddsItem.getCanonicalXPath();
					DomAttr fighterNameHtml = page.getFirstByXPath(fighterOddsRoot + ODDS_FIGHTER_NAME_PATH);
					String fighterName = fighterNameHtml.getValue().replace("/fighters/", "");
					fighterName = fighterName.substring(0, fighterName.lastIndexOf("-"));
					oddsHtml = page.getByXPath(fighterOddsRoot+ODDS_PATH);
					
					List<Double> oddsList = new ArrayList<>();
					for (HtmlElement oddsElement: oddsHtml) {
						String oddsString = oddsElement.asText().replace("+", "");
						if (oddsString.isEmpty()) {
							continue;
						}
						oddsList.add(convertOddsToPercent(Integer.valueOf(oddsString)));
					}
					if (oddsList.isEmpty()) {
						continue;
					}
					Double fighterBoutOdds = (Double) oddsList.stream().mapToDouble(val -> val).average().orElse(0.0);
					fighterBoutOddsMap.put(fighterName, fighterBoutOdds);
					response.setItemsCompleted(response.getItemsCompleted() + 1);
				}
				matchOddsFighterBoutByFighterName(fighterBouts.get(), fighterBoutOddsMap);
			}
			LOG.log(Level.INFO,
					String.format(COMPLETION_MESSAGE, response.getItemsFound(), response.getItemsCompleted()));
			response.addResponseMsg(HttpStatus.OK, errorStr);
			return response;
		} else {
			return errorService.handleParseError(errorStr, response);
		}
	}

	public void matchOddsFighterBoutByFighterName(List<FighterBoutXRefData> availableBouts,
			Map<String, Double> fighterBoutOddsMap) {
		for (FighterBoutXRefData availableBout : availableBouts) {
			if (availableBout.getMlOdds() != null) {
				continue;
			}
			List<Integer> scoreList = new ArrayList<>();
			Map<Integer, Double> nameScores = new HashMap<>();
			for (String oddsName : fighterBoutOddsMap.keySet()) {
				Integer fighterMatch = StringUtils.getLevenshteinDistance(availableBout.getFighterName(),
						oddsName);
				nameScores.put(fighterMatch, fighterBoutOddsMap.get(oddsName));
				scoreList.add(fighterMatch);
			}
			availableBout.setMlOdds(nameScores.get(Collections.min(scoreList)));
			fighterBoutXrefRepo.save(availableBout);
		}
	}
	
	public Double convertOddsToPercent(Integer odds) {
		if (odds > 0) {
			return (100.0 / (Double.valueOf(odds) + 100.0)) * 100.0;
		} else {
			return (Double.valueOf(odds) * -1)/((Double.valueOf(odds) * -1) + 100.0) * 100;
		}
	}
}
