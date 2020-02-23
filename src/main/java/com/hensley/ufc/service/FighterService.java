package com.hensley.ufc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hensley.ufc.domain.FighterBoutXRefData;
import com.hensley.ufc.domain.FighterData;
import com.hensley.ufc.enums.BoutOutcomeEnum;
import com.hensley.ufc.enums.FighterStanceEnum;
import com.hensley.ufc.pojo.response.UrlParseRequest;
import com.hensley.ufc.repository.FighterBoutXRefRepository;
import com.hensley.ufc.repository.FighterRepository;
import com.hensley.ufc.util.ParsingUtils;
import com.hensley.ufc.util.UrlUtils;

@Service
public class FighterService {

	private static final Logger LOG = Logger.getLogger(FighterService.class.toString());
	private static final String COMPLETION_MESSAGE = "Completed parsing request.  Found %s details and parsed %s details";
	private static final String SUCCESSFUL_SAVE_MESSAGE = "Fighter %s (%s) added to DB.";

	private final FighterRepository fighterRepo;
	private final UrlUtils urlUtils;
	private final ParsingUtils parsingUtils;
	private final FighterBoutXRefRepository fighterBoutXRefRepo;

	public FighterService(FighterRepository fighterRepo, UrlUtils urlUtils, ParsingUtils parsingUtils,
			FighterBoutXRefRepository fighterBoutXRefRepo) {
		this.fighterRepo = fighterRepo;
		this.urlUtils = urlUtils;
		this.parsingUtils = parsingUtils;
		this.fighterBoutXRefRepo = fighterBoutXRefRepo;
	}

	@Transactional
	public FighterBoutXRefData linkFighterToBout(String fighterId, String boutId, BoutOutcomeEnum fighterOutcome) {
		Optional<FighterBoutXRefData> fighterXRefOpt;
		FighterBoutXRefData fighterXRef;
		FighterData fighterData;

		fighterXRefOpt = fighterBoutXRefRepo.getFighterXRefByFighterIdAndBoutId(fighterId, boutId);
		if (fighterXRefOpt.isPresent()) {
			LOG.log(Level.INFO, String.format("Fighter %s already linked to bout", fighterId));
			fighterXRef = fighterXRefOpt.get();
			return fighterXRef;
		} else {
			LOG.log(Level.INFO, String.format("Fighter %s not linked to bout.. creating link now", fighterId));

			fighterData = matchFighterToModel(fighterId);
			fighterXRef = new FighterBoutXRefData();
			fighterXRef.setFighter(fighterData);
			fighterXRef.setOutcome(fighterOutcome);
//			fighterBoutXRefRepo.save(fighterXRef);
			return fighterXRef;
		}

	}

	public FighterData matchFighterToModel(String fighterId) {
		Optional<FighterData> fighterOpt;

		LOG.info(String.format("Searching DB for fighter ID %s", fighterId));
		fighterOpt = fighterRepo.findByFighterId(fighterId);
		if (fighterOpt.isPresent()) {
			LOG.info(String.format("Fighter ID %s found.", fighterId));
			return fighterOpt.get();
		} else {
			LOG.info(String.format("Fighter ID %s not found.. adding", fighterId));
			return parseFighterData(fighterId);
		}

	}

	public FighterData parseFighterData(String fighterId) {
		String url = String.format("http://www.ufcstats.com/fighter-details/%s", fighterId);
		UrlParseRequest urlParseRequest;

		HtmlPage page;
		Integer infoFound = null;
		Integer infoCompleted = 0;
		String errorStr = null;
		List<HtmlElement> fighterHTMLList;

		FighterData fighterData;

		try {
			urlParseRequest = urlUtils.parse(url);
			errorStr = urlParseRequest.getErrorStr();
			if (urlParseRequest.getSuccess()) {

				page = urlParseRequest.getPage();
				LOG.info("Completed FIGHTER Parse");

				fighterHTMLList = page.getByXPath("/html/body/section/div/div/div[1]/ul");
				infoFound = fighterHTMLList.size();
				LOG.info(String.format("%s items found", infoFound));

				if (fighterHTMLList.isEmpty()) {
					errorStr = String.format("No fighter info found for fighterId: %s", fighterId);
					LOG.log(Level.INFO, String.format(COMPLETION_MESSAGE, infoFound, infoCompleted));
					throw new IllegalArgumentException(errorStr);
				} else {
					fighterData = new FighterData();
					parseFighterName(page, fighterData);
					parseFighterHeight(page, fighterData);
					parseFighterReach(page, fighterData);
					parseFighterStance(page, fighterData);
					parseFighterDob(page, fighterData);
					fighterData.setFighterId(fighterId);
					fighterRepo.save(fighterData);
					LOG.log(Level.INFO,
							String.format(SUCCESSFUL_SAVE_MESSAGE, fighterData.getFighterName(), fighterId));

				}
				LOG.log(Level.INFO, String.format(COMPLETION_MESSAGE, infoFound, infoCompleted));
				return fighterData;

			} else {
				LOG.log(Level.WARNING, errorStr);
				throw new IllegalArgumentException(errorStr);
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, errorStr);
			throw new IllegalArgumentException(errorStr);
		}

	}

	private void parseFighterName(HtmlPage page, FighterData fighterData) {
		String name;
		DomText nameHtml = page.getFirstByXPath("/html/body/section/div/h2/span[1]/text()");
		LOG.info(nameHtml.asXml());
		name = nameHtml.asText().trim();
		if (name == null) {
			throw new IllegalArgumentException("Fighter name failed to parse");
		}
		fighterData.setFighterName(name);
		LOG.info(String.format("Name: %s", name));
	}

	private void parseFighterHeight(HtmlPage page, FighterData fighterData) {
		Integer height;
		String[] heightRaw;
		Integer heightInch;
		Integer heightFeet;

		HtmlElement heightHtml = page.getFirstByXPath("/html/body/section/div/div/div[1]/ul/li[1]");
		LOG.info(heightHtml.asXml());
		heightRaw = heightHtml.asText().replace(" ", "").replace("\"", "").replace("Height:", "").split("'");
		heightFeet = Integer.valueOf(heightRaw[0]);
		heightInch = Integer.valueOf(heightRaw[1]);
		height = (heightFeet * 12) + heightInch;
		if (height <= 0) {
			throw new IllegalArgumentException("Fighter height failed to parse");
		}
		LOG.info(String.format("Height: %s", height));
		fighterData.setHeight(height);
	}

	private void parseFighterReach(HtmlPage page, FighterData fighterData) {
		Integer reach;
		String reachRaw;
		HtmlElement reachHtml = page.getFirstByXPath("/html/body/section/div/div/div[1]/ul/li[3]");
		LOG.info(reachHtml.asXml());
		reachRaw = reachHtml.asText().replace(" ", "").replace("\"", "").replace("Reach:", "");
		if ("--".contentEquals(reachRaw)) {
			reach = null;
		} else {
			reach = Integer.valueOf(reachRaw);
		}
		if (reach == null) {
			LOG.warning("Fighter reach failed to parse");
		} else {
		LOG.info(String.format("Reach: %s", reach));
		}
		fighterData.setReach(reach);
	}

	private void parseFighterStance(HtmlPage page, FighterData fighterData) {
		FighterStanceEnum stance;
		String stanceRaw;
		HtmlElement stanceHtml = page.getFirstByXPath("/html/body/section/div/div/div[1]/ul/li[4]");
		LOG.info(stanceHtml.asXml());
		stanceRaw = stanceHtml.asText().toUpperCase().replace("STANCE:", "").trim();
		stance = FighterStanceEnum.valueOfDesc(stanceRaw);
		if (stance == null) {
			LOG.warning("Fighter stance failed to parse");
		} else {
		LOG.info(String.format("Stance: %s", stance));
		}
		fighterData.setStance(stance);
	}

	private void parseFighterDob(HtmlPage page, FighterData fighterData) {
		Date dob;
		String dobRaw;
		HtmlElement dobHtml = page.getFirstByXPath("/html/body/section/div/div/div[1]/ul/li[5]");
		LOG.info(dobHtml.asXml());
		dobRaw = dobHtml.asText().replace("DOB:", "").trim();
		dob = parsingUtils.stringToDate(dobRaw);
		if (dob == null) {
			throw new IllegalArgumentException("Fighter DOB failed to parse");
		}
		LOG.info(String.format("DOB: %s", dob));
		fighterData.setDob(dob);
	}
}
