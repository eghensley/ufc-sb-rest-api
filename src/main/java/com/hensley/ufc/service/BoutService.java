package com.hensley.ufc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlItalic;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hensley.ufc.domain.BoutData;
import com.hensley.ufc.domain.FightData;
import com.hensley.ufc.domain.FighterBoutXRefData;
import com.hensley.ufc.domain.StrikeData;
import com.hensley.ufc.enums.BoutOutcomeEnum;
import com.hensley.ufc.enums.FightMethodEnum;
import com.hensley.ufc.enums.ParseTargetEnum;
import com.hensley.ufc.enums.WeightClassEnum;
import com.hensley.ufc.pojo.dto.bout.BoutDataDto;
import com.hensley.ufc.pojo.dto.bout.BoutDto;
import com.hensley.ufc.pojo.dto.bout.BoutSummaryDto;
import com.hensley.ufc.pojo.dto.fighter.FighterBoutXRefDto;
import com.hensley.ufc.pojo.dto.strike.RoundStrikeDto;
import com.hensley.ufc.pojo.dto.strike.StrikeDto;
import com.hensley.ufc.pojo.request.AddFutureBoutSummary;
import com.hensley.ufc.pojo.request.AddRoundMlScore;
import com.hensley.ufc.pojo.request.ParseRequest;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.pojo.response.UrlParseRequest;
import com.hensley.ufc.repository.BoutRepository;
import com.hensley.ufc.repository.FightRepository;
import com.hensley.ufc.util.MappingUtils;
import com.hensley.ufc.util.UrlUtils;

@Service
public class BoutService {

	private static final Logger LOG = Logger.getLogger(BoutService.class.toString());
	private static final String COMPLETION_MESSAGE = "Completed parsing request.  Found %s bouts and parsed %s bouts";
	private static final String SUCCESSFUL_SAVE_MESSAGE = "Completed save of bout %s.";
	private static final String FIGHT_URL = "http://www.ufcstats.com/event-details/%s";

	private static final String NO_BOUTS_FOUND = "No bout available for id %s";
	private static final String FIGHT_LOAD_ERROR = "Fight %s could not be loaded from DB.";

	private static final String ERROR_ADDING_BOUTS = "Error adding bouts to fight %s.";

	private static final String BOUT_ALREADY_INCLUDED = "Bout %s already included with fight %s";
	private static final String BOUT_ADDED = "Bout %s added to fight %s";
	private static final String BOUT_ADD_FAIL = "Bout %s failed to be added to fight %s";
	private static final String START_BASIC_PARSE = "Begin basic parsing for BOUT_ID: %s";

	private static final String FIGHT_DETAIL_URL = "http://www.ufcstats.com/fight-details/";
	private static final String FIGHT_DETAIL_URL_FORMAT = FIGHT_DETAIL_URL + "%s";

	private static final String FIGHTER_DETAIL_URL = "http://www.ufcstats.com/fighter-details/";

	private static final String ITEMS_FOUND = "%s item found";

	private static final String BOUT_HTML_XPATH = "/html/body/section/div/div/table/tbody/tr";

	private final UrlUtils urlUtils;
	private final BoutRepository boutRepo;
	private final FightRepository fightRepo;
	private final MappingUtils mappingUtils;
	private final FighterService fighterService;
	private final ErrorService errorService;

	@Autowired
	BoutService(UrlUtils urlUtils, BoutRepository boutRepo, FightRepository fightRepo, MappingUtils mappingUtils,
			FighterService fighterService, ErrorService errorService) {
		this.urlUtils = urlUtils;
		this.boutRepo = boutRepo;
		this.fightRepo = fightRepo;
		this.mappingUtils = mappingUtils;
		this.fighterService = fighterService;
		this.errorService = errorService;
	}

	@Transactional
	public List<String> getBoutsFromFight(String fightId) {
		List<String> boutList = boutRepo.findBoutIdByFightId(fightId);
		return boutList;
	}

	@Transactional
	public GetResponse getBoutsMissingScores() {
		List<String> boutList = boutRepo.findBoutsMissingScores();
		return new GetResponse(HttpStatus.OK, null, boutList);
	}

	@Transactional
	public GetResponse getBoutsDateAsc() {
		List<String> boutList = boutRepo.findBoutsDateAsc();
		return new GetResponse(HttpStatus.OK, null, boutList);
	}

	@Transactional
	public GetResponse get2019BoutsDateAsc() {
		List<String> boutList = boutRepo.find2019BoutsDateAsc();
		return new GetResponse(HttpStatus.OK, null, boutList);
	}

	@Transactional
	public GetResponse getNewBoutsDateAsc() {
		List<String> boutList = boutRepo.findNewBoutsDateAsc();
		return new GetResponse(HttpStatus.OK, null, boutList);
	}

	@Transactional
	public GetResponse getBoutSummary(String boutOid) {
		String errorString = "";
		List<BoutSummaryDto> resp = new ArrayList<>();
		Optional<BoutData> boutDataOpt = boutRepo.findByOid(boutOid);

		if (boutDataOpt.isPresent()) {
			BoutDto boutDto = (BoutDto) mappingUtils.mapToDto(boutDataOpt.get(), BoutDto.class);

			return new GetResponse(HttpStatus.ACCEPTED, errorString, boutDto);
		} else {
			errorString = String.format(NO_BOUTS_FOUND, boutOid);
			LOG.log(Level.WARNING, errorString);
			return new GetResponse(HttpStatus.ACCEPTED, errorString, null);
		}
	}

	@Transactional
	public GetResponse getBoutDto(String boutId) {
		Optional<BoutData> boutDataOpt;
		BoutData boutData;
		BoutDto boutDto;
		String errorString = null;

		try {
			boutDataOpt = boutRepo.findByBoutId(boutId);
			if (boutDataOpt.isPresent()) {
				boutData = boutDataOpt.get();
				boutDto = (BoutDto) mappingUtils.mapToDto(boutData, BoutDto.class);
				return new GetResponse(HttpStatus.ACCEPTED, errorString, boutDto);
			} else {
				errorString = String.format(NO_BOUTS_FOUND, boutId);
				LOG.log(Level.WARNING, errorString);
				return new GetResponse(HttpStatus.ACCEPTED, errorString, null);
			}
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);

		}
	}

	@Transactional
	public GetResponse getBoutDataDto(String boutId) {
		Optional<BoutData> boutDataOpt;
		BoutData boutData;
		BoutDto boutDto;
		String errorString = null;

		try {
			boutDataOpt = boutRepo.findByBoutId(boutId);
			if (boutDataOpt.isPresent()) {
				boutData = boutDataOpt.get();
				List<RoundStrikeDto> response = new ArrayList<>();
				boutDto = (BoutDto) mappingUtils.mapToDto(boutData, BoutDto.class);
				FighterBoutXRefDto fighter1 = boutDto.getFighterBoutXRefs().get(0);
				FighterBoutXRefDto fighter2 = boutDto.getFighterBoutXRefs().get(1);

				Map<Integer, RoundStrikeDto> fighter1Map = new HashMap<>();
				Map<Integer, RoundStrikeDto> fighter2Map = new HashMap<>();

				for (StrikeDto fighter1Round : fighter1.getBoutDetails()) {
					RoundStrikeDto fighter1RoundStrike = (RoundStrikeDto) mappingUtils.mapToDto(fighter1Round,
							RoundStrikeDto.class);
					fighter1RoundStrike.setBoutOid(boutData.getOid());
					fighter1RoundStrike.setFighterOid(fighter1.getFighter().getFighterId());
					fighter1RoundStrike.setWeightClass(boutData.getWeightClass());
					if (fighter1Round.getRound() < boutDto.getFinishRounds()) {
						fighter1RoundStrike.setSeconds(300);
					} else {
						fighter1RoundStrike
								.setSeconds(boutDto.getFinishTime() - ((fighter1Round.getRound() - 1) * 300));
					}
					fighter1Map.put(fighter1Round.getRound(), fighter1RoundStrike);
				}

				for (StrikeDto fighter2Round : fighter2.getBoutDetails()) {
					RoundStrikeDto fighter2RoundStrike = (RoundStrikeDto) mappingUtils.mapToDto(fighter2Round,
							RoundStrikeDto.class);
					fighter2RoundStrike.setBoutOid(boutData.getOid());
					fighter2RoundStrike.setFighterOid(fighter2.getFighter().getFighterId());
					fighter2RoundStrike.setWeightClass(boutData.getWeightClass());
					if (fighter2Round.getRound() < boutDto.getFinishRounds()) {
						fighter2RoundStrike.setSeconds(300);
					} else {
						fighter2RoundStrike
								.setSeconds(boutDto.getFinishTime() - ((fighter2Round.getRound() - 1) * 300));
					}
					fighter2Map.put(fighter2Round.getRound(), fighter2RoundStrike);
				}

				if (fighter1Map.size() != fighter2Map.size()) {
					return new GetResponse(HttpStatus.ACCEPTED, errorString, response);
				}
				for (StrikeDto fighter2DefRound : fighter1.getBoutDetails()) {
					RoundStrikeDto fighter2FullRound = fighter2Map.get(fighter2DefRound.getRound());
					fighter2FullRound.setDefStats(fighter2DefRound);
					response.add(fighter2FullRound);
				}

				for (StrikeDto fighter1DefRound : fighter2.getBoutDetails()) {
					RoundStrikeDto fighter1FullRound = fighter1Map.get(fighter1DefRound.getRound());
					fighter1FullRound.setDefStats(fighter1DefRound);
					response.add(fighter1FullRound);
				}

				return new GetResponse(HttpStatus.ACCEPTED, errorString, response);
			} else {
				errorString = String.format(NO_BOUTS_FOUND, boutId);
				LOG.log(Level.WARNING, errorString);
				return new GetResponse(HttpStatus.ACCEPTED, errorString, null);
			}
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);

		}
	}

	@Transactional
	public ParseResponse scrapeBoutsFromFightId(String fightId) {
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
			if (fightData.getCompleted()) {
				return response;
			}
		}
		try {
			response = addBoutData(fightData, response);
			if (response.getErrorMsg() == null && response.getItemsCompleted() == response.getItemsFound()) {
				fightData.setCompleted(true);
				fightRepo.save(fightData);
			}
			return response;
		} catch (Exception e) {
			errorStr = String.format(ERROR_ADDING_BOUTS, fightId);
			return errorService.handleParseError(errorStr, e, response);
		}
	}

	public ParseResponse addBoutData(FightData fightData, ParseResponse response) {
		String url = String.format(FIGHT_URL, response.getRequest().getFightId()); // 8d04923f2db59b7f
		UrlParseRequest urlParseRequest;
		String errorStr;
		HtmlPage page;
		List<HtmlElement> boutHTML;
		ParseResponse detailParseResponse;

		urlParseRequest = urlUtils.parse(url);
		errorStr = urlParseRequest.getErrorStr();
		if (urlParseRequest.getSuccess()) {

			page = urlParseRequest.getPage();
			LOG.info("Completed BOUT Parse");

			boutHTML = page.getByXPath(BOUT_HTML_XPATH);
			response.setItemsFound(boutHTML.size());
			LOG.info(String.format(ITEMS_FOUND, response.getItemsFound()));

			if (boutHTML.isEmpty()) {
				errorStr = String.format(NO_BOUTS_FOUND, response.getRequest().getFightId());
				return errorService.handleParseError(errorStr, response);
			} else {

				for (HtmlElement boutItem : boutHTML) {
					detailParseResponse = parseBout(boutItem, fightData);
					if (HttpStatus.OK.equals(detailParseResponse.getStatus())) {
						response.setItemsCompleted(response.getItemsCompleted() + 1);
					} else {
						errorStr = detailParseResponse.getErrorMsg();
//						break;
					}
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

	public BoutData mergeBoutData(BoutData boutData) {
		Optional<BoutData> existBoutDataOpt = boutRepo.findByBoutId(boutData.getBoutId());
		BoutData existBoutData = existBoutDataOpt.get();
		existBoutData.setChampBout(boutData.getChampBout());
		existBoutData.setWeightClass(boutData.getWeightClass());
		return existBoutData;
	}
	
	public ParseResponse parseBout(HtmlElement boutItem, FightData fightData) {
		BoutData boutData;
		String boutId;
		ParseResponse detailParseResponse;

		ParseRequest boutDetailRequest = new ParseRequest(ParseTargetEnum.BOUTS, fightData.getFightId(), null, null);
		ParseResponse boutDetailResponse = new ParseResponse(boutDetailRequest);

		try {
			boutData = parseBoutBasic(boutItem);
			if (boutData.getWeightClass() == null) {
				LOG.info("Missing weight class.... defaulted to Catch Weight");
				boutData.setWeightClass(WeightClassEnum.CW);
			}
			boutId = boutData.getBoutId();
			
			if ("0fcf42b68f2f0fa3".equals(boutId)) {
				LOG.info("STOP");
			}
			boutDetailResponse.getRequest().setBoutId(boutId);
			if (fightData.ifBoutIdInc(boutId)) {
				LOG.log(Level.INFO, String.format(BOUT_ALREADY_INCLUDED, boutId, boutDetailRequest.getFightId()));
				if (fightData.ifBoutIdReqCompletion(boutId)) {
					boutData = mergeBoutData(boutData);
				} else {
					boutDetailResponse.setStatus(HttpStatus.OK);
					return boutDetailResponse;
				}
			}
			detailParseResponse = parseBoutDetail(boutDetailResponse, boutData);
			if (HttpStatus.OK.equals(detailParseResponse.getStatus())) {
				if (boutData.getFightOid() == null) {
					fightData.addBout(boutData);
					LOG.log(Level.INFO, String.format(BOUT_ADDED, boutId, boutDetailRequest.getFightId()));
				}
				boutRepo.save(boutData);
				LOG.log(Level.INFO, String.format(SUCCESSFUL_SAVE_MESSAGE, boutId));
				return detailParseResponse;

			} else {
				LOG.log(Level.WARNING, String.format(BOUT_ADD_FAIL, boutId, boutDetailRequest.getFighterId()));
				return errorService.handleParseError(boutDetailResponse.getErrorMsg(), boutDetailResponse);
			}
		} catch (Exception e) {
			return errorService.handleParseError(e.getLocalizedMessage(), e, boutDetailResponse);
		}
	}

	public BoutData parseBoutBasic(HtmlElement boutItem) {
		WeightClassEnum weightClass;
		String boutId;
		Boolean champBout = false;

		String boutPath = boutItem.getCanonicalXPath();
		boutId = parseBoutId(boutItem, boutPath);
		weightClass = parseWeightClass(boutItem, boutPath);

		List<HtmlImage> champBoutHtmlList = boutItem.getByXPath(boutPath + "/td[7]/p/img");
		for (HtmlImage champBoutHtml : champBoutHtmlList) {
			String imgSource = champBoutHtml.getSrcAttribute();
			if ("http://1e49bc5171d173577ecd-1323f4090557a33db01577564f60846c.r80.cf1.rackcdn.com/fight.png"
					.contentEquals(imgSource)) {
				continue;
			} else if ("http://1e49bc5171d173577ecd-1323f4090557a33db01577564f60846c.r80.cf1.rackcdn.com/perf.png"
					.contentEquals(imgSource)) {
				continue;
			} else if ("http://1e49bc5171d173577ecd-1323f4090557a33db01577564f60846c.r80.cf1.rackcdn.com/ko.png"
					.contentEquals(imgSource)) {
				continue;
			} else if ("http://1e49bc5171d173577ecd-1323f4090557a33db01577564f60846c.r80.cf1.rackcdn.com/sub.png"
					.contentEquals(imgSource)) {
				continue;
			} else if ("http://1e49bc5171d173577ecd-1323f4090557a33db01577564f60846c.r80.cf1.rackcdn.com/belt.png"
					.contentEquals(imgSource)) {
				champBout = true;
			} else {
				throw new IllegalArgumentException("Handle these!!!");
			}
		}

		return new BoutData(boutId, weightClass, champBout);
	}

	public ParseResponse parseBoutDetail(ParseResponse boutDetailResponse, BoutData boutData) {
		UrlParseRequest urlParseRequest;

		String boutSummaryHeaderPath;
		HtmlPage page;
		HtmlElement boutDetailHTML;
		String errorStr = null;

		try {
			urlParseRequest = urlUtils
					.parse(String.format(FIGHT_DETAIL_URL_FORMAT, boutDetailResponse.getRequest().getBoutId()));
			errorStr = urlParseRequest.getErrorStr();
			if (urlParseRequest.getSuccess()) {
				page = urlParseRequest.getPage();
				LOG.info("Completed BOUT DETAIL Parse");

				boutDetailHTML = page.getFirstByXPath("/html/body/section/div/div/div[2]");

				if (boutDetailHTML == null) {
					boutDetailResponse.setItemsFound(0);
					String errorMsg = String.format(ITEMS_FOUND, boutDetailResponse.getItemsFound());
					LOG.info(errorMsg);
					boutDetailResponse.addResponseMsg(HttpStatus.INTERNAL_SERVER_ERROR,
							String.format(ITEMS_FOUND, errorMsg));
					return boutDetailResponse;
				} else {
					boutDetailResponse.setItemsFound(1);
				}
				LOG.info(String.format(ITEMS_FOUND, boutDetailResponse.getItemsFound()));
				boutSummaryHeaderPath = boutDetailHTML.getCanonicalXPath();

				parseMethod(boutDetailHTML, boutSummaryHeaderPath, boutData);
				parseActTime(boutDetailHTML, boutSummaryHeaderPath, boutData);
				parseSchedRounds(boutDetailHTML, boutSummaryHeaderPath, boutData);
				parseReferee(boutDetailHTML, boutSummaryHeaderPath, boutData);
				parseBoutDetails(boutDetailHTML, boutSummaryHeaderPath, boutData);
				parseFighters(boutDetailHTML, boutData, false);

				boutDetailResponse.addResponseMsg(HttpStatus.OK, errorStr);
				return boutDetailResponse;
			} else {
				LOG.log(Level.WARNING, errorStr);
				return errorService.handleParseError(errorStr, boutDetailResponse);
			}
		} catch (Exception e) {
			return errorService.handleParseError(e.getMessage(), e, boutDetailResponse);
		}
	}
	
	@Transactional
	public ParseResponse scrapeBoutsFromFutureFightId(String fightId) {
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
			if (fightData.getCompleted()) {
				return response;
			}
		}
		try {
			response = addFutureBoutData(fightData, response);
			if (response.getErrorMsg() == null && response.getItemsCompleted() == response.getItemsFound()) {
				fightData.setCompleted(false);
				fightRepo.save(fightData);
			}
			return response;
		} catch (Exception e) {
			errorStr = String.format(ERROR_ADDING_BOUTS, fightId);
			return errorService.handleParseError(errorStr, e, response);
		}
	}

	public ParseResponse addFutureBoutData(FightData fightData, ParseResponse response) {
		String url = String.format(FIGHT_URL, response.getRequest().getFightId()); // 8d04923f2db59b7f
		UrlParseRequest urlParseRequest;
		String errorStr;
		HtmlPage page;
		List<HtmlElement> boutHTML;
		ParseResponse detailParseResponse;

		urlParseRequest = urlUtils.parse(url);
		errorStr = urlParseRequest.getErrorStr();
		if (urlParseRequest.getSuccess()) {

			page = urlParseRequest.getPage();
			LOG.info("Completed BOUT Parse");

			boutHTML = page.getByXPath(BOUT_HTML_XPATH);
			response.setItemsFound(boutHTML.size());
			LOG.info(String.format(ITEMS_FOUND, response.getItemsFound()));

			if (boutHTML.isEmpty()) {
				errorStr = String.format(NO_BOUTS_FOUND, response.getRequest().getFightId());
				return errorService.handleParseError(errorStr, response);
			} else {

				for (HtmlElement boutItem : boutHTML) {
					detailParseResponse = parseFutureBout(boutItem, fightData);
					if (HttpStatus.OK.equals(detailParseResponse.getStatus())) {
						response.setItemsCompleted(response.getItemsCompleted() + 1);
					} else {
						errorStr = detailParseResponse.getErrorMsg();
//						break;
					}
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

	public ParseResponse parseFutureBout(HtmlElement boutItem, FightData fightData) {
		BoutData boutData;
		String boutId;
		ParseResponse detailParseResponse;

		ParseRequest boutDetailRequest = new ParseRequest(ParseTargetEnum.BOUTS, fightData.getFightId(), null, null);
		ParseResponse boutDetailResponse = new ParseResponse(boutDetailRequest);

		try {
			boutData = parseBoutBasic(boutItem);
			boutId = boutData.getBoutId();
			boutDetailResponse.getRequest().setBoutId(boutId);
			if (fightData.ifBoutIdInc(boutId)) {
				LOG.log(Level.INFO, String.format(BOUT_ALREADY_INCLUDED, boutId, boutDetailRequest.getFightId()));
				boutDetailResponse.setStatus(HttpStatus.OK);
				return boutDetailResponse;
			}
			detailParseResponse = parseFutureBoutDetail(boutDetailResponse, boutData);
			if (HttpStatus.OK.equals(detailParseResponse.getStatus())) {
				fightData.addBout(boutData);
				LOG.log(Level.INFO, String.format(BOUT_ADDED, boutId, boutDetailRequest.getFightId()));
				boutRepo.save(boutData);
				LOG.log(Level.INFO, String.format(SUCCESSFUL_SAVE_MESSAGE, boutId));
				return detailParseResponse;

			} else {
				LOG.log(Level.WARNING, String.format(BOUT_ADD_FAIL, boutId, boutDetailRequest.getFighterId()));
				return errorService.handleParseError(boutDetailResponse.getErrorMsg(), boutDetailResponse);
			}
		} catch (Exception e) {
			return errorService.handleParseError(e.getLocalizedMessage(), e, boutDetailResponse);
		}
	}

	public ParseResponse parseFutureBoutDetail(ParseResponse boutDetailResponse, BoutData boutData) {
		UrlParseRequest urlParseRequest;

		String boutSummaryHeaderPath;
		HtmlPage page;
		HtmlElement boutDetailHTML;
		String errorStr = null;

		try {
			urlParseRequest = urlUtils
					.parse(String.format(FIGHT_DETAIL_URL_FORMAT, boutDetailResponse.getRequest().getBoutId()));
			errorStr = urlParseRequest.getErrorStr();
			if (urlParseRequest.getSuccess()) {
				page = urlParseRequest.getPage();
				LOG.info("Completed BOUT DETAIL Parse");

				boutDetailHTML = page.getFirstByXPath("/html/body/section/div/div/div[2]");

				if (boutDetailHTML == null) {
					boutDetailResponse.setItemsFound(0);
					String errorMsg = String.format(ITEMS_FOUND, boutDetailResponse.getItemsFound());
					LOG.info(errorMsg);
					boutDetailResponse.addResponseMsg(HttpStatus.INTERNAL_SERVER_ERROR,
							String.format(ITEMS_FOUND, errorMsg));
					return boutDetailResponse;
				} else {
					boutDetailResponse.setItemsFound(1);
				}
				LOG.info(String.format(ITEMS_FOUND, boutDetailResponse.getItemsFound()));
				boutSummaryHeaderPath = boutDetailHTML.getCanonicalXPath();

//				parseMethod(boutDetailHTML, boutSummaryHeaderPath, boutData);
//				parseActTime(boutDetailHTML, boutSummaryHeaderPath, boutData);
//				parseSchedRounds(boutDetailHTML, boutSummaryHeaderPath, boutData);
//				parseReferee(boutDetailHTML, boutSummaryHeaderPath, boutData);
//				parseBoutDetails(boutDetailHTML, boutSummaryHeaderPath, boutData);
				parseFighters(boutDetailHTML, boutData, true);

				boutDetailResponse.addResponseMsg(HttpStatus.OK, errorStr);
				return boutDetailResponse;
			} else {
				LOG.log(Level.WARNING, errorStr);
				return errorService.handleParseError(errorStr, boutDetailResponse);
			}
		} catch (Exception e) {
			return errorService.handleParseError(e.getMessage(), e, boutDetailResponse);
		}
	}

	private WeightClassEnum parseWeightClass(HtmlElement boutItem, String boutPath) {
		WeightClassEnum weightClass;
		HtmlElement boutWeightClassHTML = boutItem.getFirstByXPath(boutPath + "/td[7]/p");
		weightClass = WeightClassEnum.valueOfDesc(boutWeightClassHTML.asText());
		LOG.info(String.format("Weight class: %s", weightClass));
		return weightClass;
	}

	private String parseBoutId(HtmlElement boutItem, String boutPath) {
		String boutId;
		DomAttr boutIdHtml = boutItem.getFirstByXPath(boutPath + "/@data-link");
		boutId = boutIdHtml.getValue().replace(FIGHT_DETAIL_URL, "");
		LOG.info(String.format(START_BASIC_PARSE, boutId));
		return boutId;
	}

	private void parseMethod(HtmlElement boutDetailHTML, String boutSummaryHeaderPath, BoutData boutData) {
		DomText methodHtml = boutDetailHTML.getFirstByXPath(boutSummaryHeaderPath + "/div[2]/p[1]/i[1]/i[2]/text()");
		FightMethodEnum method = FightMethodEnum.valueOfDesc(methodHtml.asText().trim());
		LOG.info(String.format("Win method: %s", method));
		if (method == null) {
			throw new IllegalArgumentException("Bout finish method failed to parse");
		}
		boutData.setFinishMethod(method);
	}

	private void parseActTime(HtmlElement boutDetailHTML, String boutSummaryHeaderPath, BoutData boutData) {
		Integer actRound;
		Integer actTime;

		HtmlItalic actRoundHtml = boutDetailHTML.getFirstByXPath(boutSummaryHeaderPath + "/div[2]/p[1]/i[2]");
		actRound = Integer.valueOf(actRoundHtml.getChildNodes().get(2).asText().trim());
		LOG.info(String.format("Actual rounds: %s", actRound));
		if (actRound == null) {
			throw new IllegalArgumentException("Bout finish rounds failed to parse");
		}
		boutData.setFinishRounds(actRound);

		HtmlItalic actTimeHtml = boutDetailHTML.getFirstByXPath(boutSummaryHeaderPath + "/div[2]/p[1]/i[3]");
		String[] actTimeString = actTimeHtml.getChildNodes().get(2).asText().trim().split(":");
		Integer actTimeMin = Integer.valueOf(actTimeString[0]);
		if (actRound > 1) {
			actTimeMin += ((actRound - 1) * 5);
		}

		Integer actTimeSec = Integer.valueOf(actTimeString[1]);
		actTime = (actTimeMin * 60) + actTimeSec;
		if (actTime <= 0) {
			throw new IllegalArgumentException("Bout finish time failed to parse");
		}
		LOG.info(String.format("Actual time: %s", actTime));
		boutData.setFinishTime(actTime);
	}

	private void parseSchedRounds(HtmlElement boutDetailHTML, String boutSummaryHeaderPath, BoutData boutData) {
		Integer schedRounds;
		HtmlItalic schedRoundsHtml = boutDetailHTML.getFirstByXPath(boutSummaryHeaderPath + "/div[2]/p[1]/i[4]");
		schedRounds = Integer.valueOf(schedRoundsHtml.getChildNodes().get(2).asText().trim().split(" ")[0]);
		LOG.info(String.format("Scheduled rounds: %s", schedRounds));
		if (schedRounds == null) {
			throw new IllegalArgumentException("Bout scheduled rounds failed to parse");
		}
		boutData.setSchedRounds(schedRounds);
	}

	private void parseReferee(HtmlElement boutDetailHTML, String boutSummaryHeaderPath, BoutData boutData) {
		String referee;
		DomText refereeHtml = boutDetailHTML.getFirstByXPath(boutSummaryHeaderPath + "/div[2]/p[1]/i[5]/span/text()");
		referee = refereeHtml.asText().trim();
		if (referee == null) {
			throw new IllegalArgumentException("Bout referee failed to parse");
		}
		LOG.info(String.format("Referee: %s", referee));
		boutData.setReferee(referee);
	}

	private void parseBoutDetails(HtmlElement boutDetailHTML, String boutSummaryHeaderPath, BoutData boutData) {
		String finishDetails;

		HtmlElement finishDetailHtml = boutDetailHTML.getFirstByXPath(boutSummaryHeaderPath + "/div[2]/p[2]");
//		LOG.info(finishDetailHtml.asXml());
		finishDetails = finishDetailHtml.asText().trim();
		if (finishDetails == null) {
			throw new IllegalArgumentException("Bout finish details failed to parse");
		}
		LOG.info(String.format("Finish details: %s", finishDetails));
		boutData.setFinishDetails(finishDetails);
	}

	private void parseFighters(HtmlElement boutDetailHTML, BoutData boutData, boolean future) {
		FighterBoutXRefData fighterXref = new FighterBoutXRefData();
		String fighterId = null;
		String fighterName = null;
		BoutOutcomeEnum fighterOutcome;

		List<HtmlElement> fightersHtmlList = boutDetailHTML.getByXPath("/html/body/section/div/div/div[1]/div");
		for (HtmlElement fighterHtml : fightersHtmlList) {
			String fighterHtmlPath = fighterHtml.getCanonicalXPath();

			if (future) {
				fighterOutcome = null;
			} else {
				HtmlElement fighterOutcomeHtml = boutDetailHTML.getFirstByXPath(fighterHtmlPath + "/i");

//				LOG.info(fighterOutcomeHtml.asXml());
				fighterOutcome = BoutOutcomeEnum.valueOf(fighterOutcomeHtml.asText().trim());
				if (fighterOutcome == null) {
					throw new IllegalArgumentException("Fighter outcome failed to parse");
				}
			}

			DomAttr fighterIdHtml = boutDetailHTML.getFirstByXPath(fighterHtmlPath + "/div/h3/a/@href");
			DomText fighterNameHtml = boutDetailHTML.getFirstByXPath(fighterHtmlPath + "/div/h3/span/text()");

			if (fighterNameHtml != null) {
				fighterName = fighterNameHtml.asText().trim();
			}
			if (fighterIdHtml != null) {
//				LOG.info(fighterIdHtml.asXml());
				fighterId = fighterIdHtml.getValue().replace(FIGHTER_DETAIL_URL, "");
			} else if ("Leo Kuntz".equals(fighterName)) {
				fighterId = "6905d45bd71b50a9";
			} else if ("Joe Merritt".equals(fighterName)) {
				fighterId = "e82d5f54daa85a7e";
			} else if ("Lewis Gonzalez".equals(fighterName)) {
				fighterId = "2ff2e6e5abea3e53";
			} else if ("Goran Reljic".equals(fighterName)) {
				fighterId = "132f860d02953f4c";
			} else if ("Maciej Jewtuszko".equals(fighterName)) {
				fighterId = "6b5b9be990774f0e";
			} else if ("Yoshiyuki Yoshida".equals(fighterName)) {
				fighterId = "ab8900ae1f1dfd4c";
			} else if ("Dustin Jacoby".equals(fighterName)) {
				fighterId = "e4277e87a789d687";
			} else if ("Chris Tickle".equals(fighterName)) {
				fighterId = "1d1b85389a05df69";
			} else if ("Ryan Roberts".equals("fighterName")) {
				fighterId = "35dc6220b113b7ec";
			} else {
				fighterId = fighterService.fuzzyMatchFighterIdByName(fighterName);
				LOG.info(String.format("Fighter ID (%s) missing from html... fell back to fuzzy match.", fighterId));
			}

			if (fighterId == null) {
				throw new IllegalArgumentException("Fighter id failed to parse");
			}
			fighterXref = fighterService.linkFighterToBout(fighterId, boutData.getBoutId(), fighterOutcome);
			if (fighterXref != null) {
				boutData.addFighterBoutXRefs(fighterXref);
				LOG.info(String.format("Fighter: %s", fighterXref.getFighter().getFighterName()));
			}
			
		}
	}
	
	@Transactional
	public ParseResponse addFutBoutSum(AddFutureBoutSummary request) {
		ParseResponse response = new ParseResponse();
		response.setItemsFound(1);
		try {
			Optional<BoutData> updatedBoutDataOpt = boutRepo.findByOid(request.getOid());
			BoutData updateBoutData = updatedBoutDataOpt.get();
			updateBoutData.setChampBout(request.getChampBout());
			updateBoutData.setSchedRounds(request.getSchedRounds());
			boutRepo.save(updateBoutData);
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
