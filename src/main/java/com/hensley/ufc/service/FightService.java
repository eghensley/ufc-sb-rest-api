package com.hensley.ufc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hensley.ufc.domain.FightData;
import com.hensley.ufc.domain.LocationData;
import com.hensley.ufc.enums.GenderEnum;
import com.hensley.ufc.enums.ParseTargetEnum;
import com.hensley.ufc.pojo.common.ApiRequestTracker;
import com.hensley.ufc.pojo.dto.bet.AllBetHistoryDto;
import com.hensley.ufc.pojo.dto.bout.BoutBetDto;
import com.hensley.ufc.pojo.dto.bout.BoutDto;
import com.hensley.ufc.pojo.dto.fight.BasicFightDto;
import com.hensley.ufc.pojo.dto.fight.BasicFightInfoDto;
import com.hensley.ufc.pojo.dto.fight.FightBetDto;
import com.hensley.ufc.pojo.dto.fight.FightDto;
import com.hensley.ufc.pojo.request.ParseRequest;
import com.hensley.ufc.pojo.response.GetResponse;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.pojo.response.UrlParseRequest;
import com.hensley.ufc.repository.FightRepository;
import com.hensley.ufc.repository.FighterBoutXRefRepository;
import com.hensley.ufc.util.MappingUtils;
import com.hensley.ufc.util.ParsingUtils;
import com.hensley.ufc.util.UrlUtils;

@Service
public class FightService {

	private static final Logger LOG = Logger.getLogger(FightService.class.toString());
	private static final String COMPLETION_MESSAGE = "Completed parsing request.  Found %s fights and parsed %s fights";
	private static final String SKIP_FIGHT_ALREADY_SAVED_MESSAGE = "Fight %s already added to DB... skipping.";
	private static final String ADD_FIGHT_NOT_ALREADY_SAVED_MESSAGE = "Fight %s missing from DB... adding.";
	private static final String BEGIN_FIGHT_PARSE_MESSAGE = "Beginning parse of fightId: %s";
	private static final String FIGHT_URL = "http://www.ufcstats.com/statistics/events/completed?page=all";

	private static final String FIGHT_HTML_XPATH = "/html/body/section/div/div/div/div[2]/div/table/tbody/tr[3 <= position()]";
	private static final String FUT_FIGHT_HTML_XPATH = "/html/body/section/div/div/div/div[2]/div/table/tbody/tr[2]";

	private static final String EVENT_DETAIL_URL = "http://www.ufcstats.com/event-details/";

	private static final String NO_FIGHTS_FOUND = "No fight available for id %s";

	private final FightRepository fightRepo;
	private final UrlUtils urlUtils;
	private final ParsingUtils parsingUtils;
	private final LocationService locationService;
	private final MappingUtils mappingUtils;
	private final ErrorService errorService;
	private final FighterBoutXRefRepository fighterXRefRepo;

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

	@Autowired
	public FightService(FighterBoutXRefRepository fighterXRefRepo, FightRepository fightRepo, UrlUtils urlUtils,
			ParsingUtils parsingUtils, LocationService locationService, MappingUtils mappingUtils,
			ErrorService errorService) {
		this.fightRepo = fightRepo;
		this.urlUtils = urlUtils;
		this.parsingUtils = parsingUtils;
		this.locationService = locationService;
		this.mappingUtils = mappingUtils;
		this.errorService = errorService;
		this.fighterXRefRepo = fighterXRefRepo;
	}

	@Transactional
	public GetResponse getFights() {
		String errorString = null;
		List<String> fightList = fightRepo.findFightIdsByDateDesc();
		return new GetResponse(HttpStatus.OK, errorString, fightList);
	}

	@Transactional
	public GetResponse getRecentFights() {
		String errorString = null;
		List<BasicFightDto> fightDtoList = new ArrayList<>();
		List<String> fightList = fightRepo.findFightIdsByDateDescLimitTen();
		for (String fightId : fightList) {
			Optional<FightData> fightDataOpt = fightRepo.findByFightId(fightId);
			if (fightDataOpt.isPresent()) {
				FightData fightData = fightDataOpt.get();
				BasicFightDto fightDto = (BasicFightDto) mappingUtils.mapToDto(fightData, BasicFightDto.class);
				fightDtoList.add(fightDto);
			}
		}
		return new GetResponse(HttpStatus.OK, errorString, fightDtoList);
	}

	@Transactional
	public GetResponse getFightsWithScore() {
		String errorString = null;
		List<String> fightList = fightRepo.findFightIdsWithScore();
		return new GetResponse(HttpStatus.OK, errorString, fightList);
	}

	@Transactional
	public ParseResponse addFightOddsUrl(String fightId, String fightUrl) {
		Optional<FightData> fightDataOpt;
		FightData fightData;
		ParseRequest fightRequest = new ParseRequest(ParseTargetEnum.ROUNDS, fightId, null, null);
		ParseResponse fightResponse = new ParseResponse(fightRequest);

		fightDataOpt = fightRepo.findByFightId(fightId);
		fightData = fightDataOpt.get();
		fightRequest.setFightId(fightData.getFightId());

		fightData.setBestFightOddsUrl(fightUrl);
		fightRepo.save(fightData);
		fightResponse.setStatus(HttpStatus.OK);
		return fightResponse;

	}

	@Transactional
	public GetResponse getFightsWithoutScore() {
		String errorString = null;
		List<FightData> fightList = fightRepo.findFightIdsWithoutScore();

		List<BasicFightDto> response = new ArrayList<>();
		for (FightData fightData : fightList) {
			BasicFightDto fightDto = (BasicFightDto) mappingUtils.mapToDto(fightData, BasicFightDto.class);
			response.add(fightDto);
		}
		return new GetResponse(HttpStatus.OK, errorString, response);
	}

	@Transactional
	public GetResponse getFightsIdsByYear(Integer year) {
		String errorString = null;
		List<String> fightList = fightRepo.findFightIdsByYear(year);
		return new GetResponse(HttpStatus.OK, errorString, fightList);
	}

	@Transactional
	public GetResponse getFightBetDto(String fightId) {
		Optional<FightData> fightDataOpt;
		FightData fightData;
		FightBetDto fightDto;
		String errorString = null;

		try {
			fightDataOpt = fightRepo.findByFightId(fightId);
			if (fightDataOpt.isPresent()) {
				fightData = fightDataOpt.get();
				fightDto = (FightBetDto) mappingUtils.mapToDto(fightData, FightBetDto.class);
				return new GetResponse(HttpStatus.OK, errorString, fightDto);
			} else {
				errorString = String.format(NO_FIGHTS_FOUND, fightId);
				LOG.log(Level.WARNING, errorString);
				return new GetResponse(HttpStatus.OK, errorString, null);
			}
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}

	@Transactional
	public GetResponse getFightBasicDto(ApiRequestTracker req) {
		Optional<FightData> fightDataOpt;
		FightData fightData;
		BasicFightInfoDto fightDto;
		String errorString = null;

		try {
			fightDataOpt = fightRepo.findByFightId(req.getFightId());
			if (fightDataOpt.isPresent()) {
				fightData = fightDataOpt.get();
				fightDto = (BasicFightInfoDto) mappingUtils.mapToDto(fightData, BasicFightInfoDto.class);
				return new GetResponse(HttpStatus.OK, errorString, fightDto);
			} else {
				errorString = String.format(NO_FIGHTS_FOUND, req.getFightId());
				LOG.log(Level.WARNING, errorString);
				return new GetResponse(HttpStatus.OK, errorString, null);
			}
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}
	
	@Transactional
	public GetResponse getFightDto(String fightId) {
		Optional<FightData> fightDataOpt;
		FightData fightData;
		FightDto fightDto;
		String errorString = null;

		try {
			fightDataOpt = fightRepo.findByFightId(fightId);
			if (fightDataOpt.isPresent()) {
				fightData = fightDataOpt.get();
				fightDto = (FightDto) mappingUtils.mapToDto(fightData, FightDto.class);
				for (BoutDto bout : fightDto.getBouts()) {
					BoutBetDto betInfo = new BoutBetDto();
					if (Boolean.FALSE.equals(betFemale) && GenderEnum.FEMALE == bout.getGender()) {
						betInfo.setBet(false);
						betInfo.setNotes("Model does not currently predict for female fights");
						bout.addBetInfo(betInfo);
						continue;
					}
					try {
						Integer f1PrevFights = fighterXRefRepo.getCountPreviousFights(
								bout.getFighterBoutXRefs().get(0).getFighter().getOid(), fightDto.getOid());
						Integer f2PrevFights = fighterXRefRepo.getCountPreviousFights(
								bout.getFighterBoutXRefs().get(1).getFighter().getOid(), fightDto.getOid());

						if (f1PrevFights == 0) {
							betInfo.setBet(false);
							betInfo.setNotes(String.format("No previous UFC fights for %s",
									bout.getFighterBoutXRefs().get(0).getFighter().getFighterName()));
							bout.addBetInfo(betInfo);
							continue;
						}
						if (f2PrevFights == 0) {
							betInfo.setBet(false);
							betInfo.setNotes(String.format("No previous UFC fights for %s",
									bout.getFighterBoutXRefs().get(1).getFighter().getFighterName()));
							bout.addBetInfo(betInfo);
							continue;
						}

						if (bout.getFighterBoutXRefs().get(0).getExpOdds() == null
								|| bout.getFighterBoutXRefs().get(1).getExpOdds() == null) {
							betInfo.setBet(false);
							betInfo.setNotes("No Expected odds available");
							bout.addBetInfo(betInfo);
							continue;
						}

						if (bout.getFighterBoutXRefs().get(0).getMlOdds() == null
								|| bout.getFighterBoutXRefs().get(1).getMlOdds() == null) {
							betInfo.setBet(false);
							betInfo.setNotes("No Vegas odds available");
							bout.addBetInfo(betInfo);
							continue;
						}

						Double f1Diff = (bout.getFighterBoutXRefs().get(0).getExpOdds() * 100)
								- bout.getFighterBoutXRefs().get(0).getMlOdds();
						Double f2Diff = (bout.getFighterBoutXRefs().get(1).getExpOdds() * 100)
								- bout.getFighterBoutXRefs().get(1).getMlOdds();

						boolean useF1 = f1Diff > f2Diff;

						if (useF1) {
//							System.out.println(String.format("%s vs %s",
//									bout.getFighterBoutXRefs().get(0).getFighter().getFighterName(),
//									bout.getFighterBoutXRefs().get(0).getFighter().getFighterName()));
							betInfo.setOddsDiff(f1Diff);
							betInfo.setVegasOdds(bout.getFighterBoutXRefs().get(0).getMlOdds());
							betInfo.setPredProb(bout.getFighterBoutXRefs().get(0).getExpOdds() * 100);
							betInfo.setPredWinner(bout.getFighterBoutXRefs().get(0).getFighter().getFighterName());
							betInfo.setWagerWeight(oddDiffToWager(betInfo.getOddsDiff(), f1PrevFights, f2PrevFights));
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
//							System.out.println(String.format("%s vs %s",
//									bout.getFighterBoutXRefs().get(0).getFighter().getFighterName(),
//									bout.getFighterBoutXRefs().get(0).getFighter().getFighterName()));
							betInfo.setOddsDiff(f2Diff);
							betInfo.setVegasOdds(bout.getFighterBoutXRefs().get(1).getMlOdds());
							betInfo.setPredProb(bout.getFighterBoutXRefs().get(1).getExpOdds() * 100);
							betInfo.setPredWinner(bout.getFighterBoutXRefs().get(1).getFighter().getFighterName());
							betInfo.setWagerWeight(oddDiffToWager(betInfo.getOddsDiff(), f1PrevFights, f2PrevFights));
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

						if (f1PrevFights < prevFightFloor) {
							betInfo.setBet(false);
							String msg = String.format("%s has %s fights, less than required %s",
									bout.getFighterBoutXRefs().get(0).getFighter().getFighterName(), f1PrevFights,
									prevFightFloor);
							LOG.info(msg);
							betInfo.setNotes(msg);
						}
						if (f2PrevFights < prevFightFloor) {
							betInfo.setBet(false);
							String msg = String.format("%s has %s fights, less than required %s",
									bout.getFighterBoutXRefs().get(1).getFighter().getFighterName(), f2PrevFights,
									prevFightFloor);
							LOG.info(msg);
							betInfo.setNotes(msg);
						}

						if (f1PrevFights > prevFightCeiling) {
							betInfo.setBet(false);
							String msg = String.format("%s has %s fights, more than ceiling of %s",
									bout.getFighterBoutXRefs().get(0).getFighter().getFighterName(), f1PrevFights,
									prevFightCeiling);
							LOG.info(msg);
							betInfo.setNotes(msg);
						}
						if (f2PrevFights > prevFightCeiling) {
							betInfo.setBet(false);
							String msg = String.format("%s has %s fights, more than ceiling of %s",
									bout.getFighterBoutXRefs().get(1).getFighter().getFighterName(), f2PrevFights,
									prevFightCeiling);
							LOG.info(msg);
							betInfo.setNotes(msg);
						}

						bout.addBetInfo(betInfo);
					} catch (Exception ee) {
						LOG.log(Level.WARNING, ee.getLocalizedMessage(), ee);
					}
				}
				return new GetResponse(HttpStatus.OK, errorString, fightDto);
			} else {
				errorString = String.format(NO_FIGHTS_FOUND, fightId);
				LOG.log(Level.WARNING, errorString);
				return new GetResponse(HttpStatus.OK, errorString, null);
			}
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}
	
	@Transactional
	public ParseResponse futFightScraper() {
		UrlParseRequest urlParseRequest;

		String baseUrl = FIGHT_URL;

		HtmlPage page;
		List<HtmlElement> fightHtml;
		String fightId = "ALL";
		String errorStr = null;
		boolean continueParsing;

		ParseRequest request = new ParseRequest(ParseTargetEnum.FIGHTS, fightId, null, null);
		ParseResponse response = new ParseResponse(request);

		try {
			urlParseRequest = urlUtils.parse(baseUrl);
			errorStr = urlParseRequest.getErrorStr();
			if (urlParseRequest.getSuccess()) {
				page = urlParseRequest.getPage();
				LOG.info("Completed FIGHTS Parse");

				fightHtml = page.getByXPath(FUT_FIGHT_HTML_XPATH);
				response.setItemsFound(fightHtml.size());
				LOG.info(String.format("%s item found", response.getItemsFound()));

				if (fightHtml.isEmpty()) {
					errorStr = String.format(NO_FIGHTS_FOUND, baseUrl);
					response.addResponseMsg(HttpStatus.NO_CONTENT, errorStr);
					return response;
				}

				else {
					for (HtmlElement fightItem : fightHtml) {
						continueParsing = parseFightDetails(fightItem);
						if (continueParsing) {
							response.setItemsCompleted(response.getItemsCompleted() + 1);
						} else {
							break;
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
		} catch (Exception e) {
			errorStr = String.format("Error adding fights to DB");
			return errorService.handleParseError(errorStr, e, response);
		}

	}

	@Transactional
	public ParseResponse fightScraper() {
		UrlParseRequest urlParseRequest;

		String baseUrl = FIGHT_URL;

		HtmlPage page;
		List<HtmlElement> fightHtml;
		String fightId = "ALL";
		String errorStr = null;
		boolean continueParsing;

		ParseRequest request = new ParseRequest(ParseTargetEnum.FIGHTS, fightId, null, null);
		ParseResponse response = new ParseResponse(request);

		try {
			urlParseRequest = urlUtils.parse(baseUrl);
			errorStr = urlParseRequest.getErrorStr();
			if (urlParseRequest.getSuccess()) {
				page = urlParseRequest.getPage();
				LOG.info("Completed FIGHTS Parse");

				fightHtml = page.getByXPath(FIGHT_HTML_XPATH);
				response.setItemsFound(fightHtml.size());
				LOG.info(String.format("%s item found", response.getItemsFound()));

				if (fightHtml.isEmpty()) {
					errorStr = String.format(NO_FIGHTS_FOUND, baseUrl);
					response.addResponseMsg(HttpStatus.NO_CONTENT, errorStr);
					return response;
				}

				else {
					for (HtmlElement fightItem : fightHtml) {
						continueParsing = parseFightDetails(fightItem);
						if (continueParsing) {
							response.setItemsCompleted(response.getItemsCompleted() + 1);
						} else {
							break;
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
		} catch (Exception e) {
			errorStr = String.format("Error adding fights to DB");
			return errorService.handleParseError(errorStr, e, response);
		}

	}

	
	@Transactional
	public ParseResponse nonUfcfightScraper(String fightId) {
		UrlParseRequest urlParseRequest;

		String baseUrl = String.format("http://www.ufcstats.com/event-details/%s", fightId);

		HtmlPage page;
		String errorStr = null;

		ParseRequest request = new ParseRequest(ParseTargetEnum.FIGHTS, fightId, null, null);
		ParseResponse response = new ParseResponse(request);

		try {
			if (!ifNewFight(fightId)) {
				LOG.info(String.format(SKIP_FIGHT_ALREADY_SAVED_MESSAGE, fightId));
				errorStr = String.format(SKIP_FIGHT_ALREADY_SAVED_MESSAGE, fightId);
				return errorService.handleParseError(errorStr, response);
			} else {
				LOG.info(String.format(ADD_FIGHT_NOT_ALREADY_SAVED_MESSAGE, fightId));
				urlParseRequest = urlUtils.parse(baseUrl);
				errorStr = urlParseRequest.getErrorStr();
				if (urlParseRequest.getSuccess()) {
					page = urlParseRequest.getPage();
					LOG.info("Completed FIGHTS Parse");

					HtmlElement fightLocHtml = page.getFirstByXPath("/html/body/section/div/div/div[1]/ul/li[2]");
					String[] locArray = fightLocHtml.asText().split(",");
					LocationData location = locationService.matchLocationToModel(locArray);
					HtmlElement fightTitleHtml = page.getFirstByXPath("/html/body/section/div/h2/span");
					String fightName = fightTitleHtml.asText();
					if (fightName == null) {
						throw new IllegalArgumentException("Fight name failed to parse");
					}
					LOG.info(String.format("Fight Name: %s", fightName));
					HtmlElement fightDateHtml = page.getFirstByXPath("/html/body/section/div/div/div[1]/ul/li[1]");
					String fightDateHtmlTxt = fightDateHtml.asText().replace("Date: ", "");
					Date fightDate = parsingUtils.stringToDate(fightDateHtmlTxt);
					if (fightDate == null) {
						throw new IllegalArgumentException("Fight date failed to parse");
					}
					LOG.info(String.format("Fight Date: %s", fightDate));
					
					FightData fightData = new FightData(fightId, fightName, fightDate, location);
					fightRepo.save(fightData);				
					
					response.setItemsFound(1);
					LOG.info(String.format("%s item found", response.getItemsFound()));


					LOG.log(Level.INFO,
							String.format(COMPLETION_MESSAGE, response.getItemsFound(), response.getItemsCompleted()));
					response.addResponseMsg(HttpStatus.OK, errorStr);
					return response;
				} else {
					return errorService.handleParseError(errorStr, response);
				}
			}
		} catch (Exception e) {
			errorStr = String.format("Error adding fight to DB");
			return errorService.handleParseError(errorStr, e, response);
		}
	}
	
	
	public boolean parseFightDetails(HtmlElement fightHtml) {
		String fightPath = fightHtml.getCanonicalXPath();
		String fightId;
		LocationData location;

		try {
			HtmlElement fightLocHtml = fightHtml.getFirstByXPath(fightPath + "/td[2]");
			if (fightLocHtml != null) {
				DomAttr fightIdHref = fightHtml.getFirstByXPath(fightPath + "/td[1]/i/a/@href");

				fightId = fightIdHref.getValue().replace(EVENT_DETAIL_URL, "");
				LOG.info(String.format(BEGIN_FIGHT_PARSE_MESSAGE, fightId));

				if (!ifNewFight(fightId)) {
					LOG.info(String.format(SKIP_FIGHT_ALREADY_SAVED_MESSAGE, fightId));

				} else {
					LOG.info(String.format(ADD_FIGHT_NOT_ALREADY_SAVED_MESSAGE, fightId));
					String[] locArray = fightLocHtml.asText().split(",");
					location = locationService.matchLocationToModel(locArray);
					addFight(fightId, fightPath, location, fightHtml);
				}
			}
			return true;
		} catch (Exception e) {
			LOG.log(Level.WARNING, e.getLocalizedMessage(), e);
			return false;
		}

	}

	public void addFight(String fightId, String fightPath, LocationData location, HtmlElement fightHtml) {
		String fightName = parseFightName(fightHtml, fightPath);
		Date fightDate = parseFightDate(fightHtml, fightPath);
		FightData fightData = new FightData(fightId, fightName, fightDate, location);
//		LOG.info(fightData.toString());
		fightRepo.save(fightData);
	}

	private Date parseFightDate(HtmlElement fightHtml, String fightPath) {
		Date fightDate;
		HtmlElement fightDateHtml = fightHtml.getFirstByXPath(fightPath + "/td[1]/i/span");
		fightDate = parsingUtils.stringToDate(fightDateHtml.asText());
		if (fightDate == null) {
			throw new IllegalArgumentException("Fight date failed to parse");
		}
		LOG.info(String.format("Fight Date: %s", fightDate));
		return fightDate;
	}

	private String parseFightName(HtmlElement fightHtml, String fightPath) {
		String fightName;
		HtmlElement fightTitleHtml = fightHtml.getFirstByXPath(fightPath + "/td[1]/i/a");
		fightName = fightTitleHtml.asText();
		if (fightName == null) {
			throw new IllegalArgumentException("Fight name failed to parse");
		}
		LOG.info(String.format("Fight Name: %s", fightName));
		return fightName;
	}

	private boolean ifNewFight(String fightId) {
		Optional<FightData> fightData = fightRepo.findByFightId(fightId);
		if (fightData.isPresent()) {
			return false;
		} else {
			return true;
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
