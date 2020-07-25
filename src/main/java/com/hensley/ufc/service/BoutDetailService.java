package com.hensley.ufc.service;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hensley.ufc.domain.BoutData;
import com.hensley.ufc.domain.FighterBoutXRefData;
import com.hensley.ufc.domain.SigStrikePositionData;
import com.hensley.ufc.domain.SigStrikeTargetData;
import com.hensley.ufc.domain.StrikeData;
import com.hensley.ufc.enums.BoutOutcomeEnum;
import com.hensley.ufc.enums.FightMethodEnum;
import com.hensley.ufc.enums.ParseTargetEnum;
import com.hensley.ufc.pojo.FinishDataTransfer;
import com.hensley.ufc.pojo.request.ParseRequest;
import com.hensley.ufc.pojo.response.ParseResponse;
import com.hensley.ufc.pojo.response.UrlParseRequest;
import com.hensley.ufc.repository.BoutRepository;
import com.hensley.ufc.repository.FighterBoutXRefRepository;
import com.hensley.ufc.util.UrlUtils;

@Service
public class BoutDetailService {

	private Logger LOG = Logger.getLogger(BoutDetailService.class.toString());
	private static final String COMPLETION_MESSAGE = "Completed parsing request.  Found %s details and parsed %s details";
	private static final String ROUND_COMPLETION_MESSAGE = "Completed round parsing request for %s round %s";

	private static final String SUCCESSFUL_SAVE_MESSAGE = "Completed save of bout detail %s.";
	private static final String NO_BOUT_AVAILABLE = "Bout %s could not be loaded from DB.";

	private static final String FIGHT_DETAIL_URL = "http://www.ufcstats.com/fight-details/";
	private static final String FIGHT_DETAIL_URL_FORMAT = FIGHT_DETAIL_URL + "%s";
	private static final String ITEMS_FOUND = "%s item found";
	private static final String NO_BOUTS_FOUND = "No bout details available for id %s";
	private static final String NO_ROUNDS_FOUND = "No round details available for bout %s";

	private final UrlUtils urlUtils;
	private final FighterBoutXRefRepository fighterBoutXRefRepo;
	private final ErrorService errorService;
	private final BoutRepository boutRepo;

	@Autowired
	BoutDetailService(UrlUtils urlUtils, FighterBoutXRefRepository fighterBoutXRefRepo, ErrorService errorService,
			BoutRepository boutRepo) {
		this.urlUtils = urlUtils;
		this.fighterBoutXRefRepo = fighterBoutXRefRepo;
		this.errorService = errorService;
		this.boutRepo = boutRepo;
	}

	@Transactional
	public ParseResponse scrapeBoutDetailsFromBoutId(String fightId, String boutId) {
		String errorStr = null;
		Optional<List<FighterBoutXRefData>> fighterBoutListOpt;
		List<FighterBoutXRefData> fighterBoutList;

		ParseRequest request = new ParseRequest(ParseTargetEnum.FIGHTS, fightId, boutId, null);
		ParseResponse response = new ParseResponse(request);

		fighterBoutListOpt = fighterBoutXRefRepo.getFighterXRefByBoutId(boutId);
		if (!fighterBoutListOpt.isPresent()) {
			errorStr = String.format(NO_BOUT_AVAILABLE, fightId);
			LOG.log(Level.WARNING, errorStr);
			response.addResponseMsg(HttpStatus.NO_CONTENT, errorStr);
			return response;
		} else {
			fighterBoutList = fighterBoutListOpt.get();
		}
		try {
			Integer boutRounds = boutRepo.findBoutRounds(boutId);
			if (boutRounds.equals(fighterBoutList.get(0).getBoutDetails().size())
					&& boutRounds.equals(fighterBoutList.get(1).getBoutDetails().size())) {
				response.setItemsCompleted(0);
				response.setItemsFound(0);
				response.setStatus(HttpStatus.OK);
			} else {
				response = addBoutDetails(fighterBoutList, response);
			}
			if (response.getErrorMsg() == null && response.getItemsFound() == response.getItemsCompleted()) {
				Optional<BoutData> boutOpt = boutRepo.findByBoutId(boutId);
				BoutData bout = boutOpt.get();
				bout.setCompleted(true);
				boutRepo.save(bout);
			}
			return response;
		} catch (Exception e) {
			errorStr = e.getMessage();
			return errorService.handleParseError(errorStr, e, response);
		}
	}

	public ParseResponse addBoutDetails(List<FighterBoutXRefData> fighterBoutList, ParseResponse response) {
		String url = String.format(FIGHT_DETAIL_URL_FORMAT, response.getRequest().getBoutId()); // 8d04923f2db59b7f
		UrlParseRequest urlParseRequest;
		String errorStr;
		HtmlPage page;
		HtmlElement boutDetailHTML;
		ParseResponse detailParseResponse;

		urlParseRequest = urlUtils.parse(url);
		errorStr = urlParseRequest.getErrorStr();
		if (urlParseRequest.getSuccess()) {

			page = urlParseRequest.getPage();
			LOG.info("Completed ROUND Parse");

			boutDetailHTML = page.getFirstByXPath("/html/body/section/div/div");
			response.setItemsFound(fighterBoutList.size());
			LOG.info(String.format(ITEMS_FOUND, response.getItemsFound()));

			if (boutDetailHTML == null) {
				errorStr = String.format(NO_BOUTS_FOUND, response.getRequest().getBoutId());
				return errorService.handleParseError(errorStr, response);
			} else {

				for (FighterBoutXRefData fighterBout : fighterBoutList) {
					detailParseResponse = parseFighterBoutDetails(boutDetailHTML, fighterBout,
							response.getRequest().getFightId());
					if (HttpStatus.OK.equals(detailParseResponse.getStatus())) {
						response.setItemsCompleted(response.getItemsCompleted() + 1);
					} else {
						errorStr = detailParseResponse.getErrorMsg();
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
	}

	private FinishDataTransfer evalFinishData(FighterBoutXRefData fighterBout) {
		FinishDataTransfer finishTransfer = new FinishDataTransfer();
		if (BoutOutcomeEnum.W.equals(fighterBout.getOutcome())) {
			FightMethodEnum method = fighterBout.getBout().getFinishMethod();
			finishTransfer.setNeedApply(method.isFinish());
			finishTransfer.setMethod(method);
			finishTransfer.setRound(fighterBout.getBout().getFinishRounds());
		}
		return finishTransfer;
	}

	@Transactional
	public ParseResponse parseFighterBoutDetails(HtmlElement boutDetailHTML, FighterBoutXRefData fighterBout,
			String fightId) {
		List<HtmlElement> boutRoundsHtml;
		String errorStr = null;
		ParseResponse roundDetailResponse;
		Integer roundParsed = 1;
		FinishDataTransfer finishTransfer;

		ParseRequest boutDetailRequest = new ParseRequest(ParseTargetEnum.ROUNDS, fightId,
				fighterBout.getBout().getBoutId(), fighterBout.getFighter().getFighterId());
		ParseResponse boutDetailResponse = new ParseResponse(boutDetailRequest);

		try {
			finishTransfer = evalFinishData(fighterBout);
			boutRoundsHtml = boutDetailHTML
					.getByXPath("/html/body/section/div/div/section[3]/table/thead[2 <= position()]/tr[2]");
			boutDetailResponse.setItemsFound(boutRoundsHtml.size());
			LOG.info(String.format(ITEMS_FOUND, boutDetailResponse.getItemsFound()));

			if (boutRoundsHtml.isEmpty()) {
				errorStr = String.format(NO_ROUNDS_FOUND, boutDetailResponse.getRequest().getBoutId());
				return errorService.handleParseError(errorStr, boutDetailResponse);
			} else {

				for (HtmlElement roundItem : boutRoundsHtml) {
					if (!fighterBout.evalIfRoundStored(roundParsed)) {
						roundDetailResponse = parseRound(roundItem, fighterBout, boutDetailRequest, roundParsed,
								finishTransfer);
						if (HttpStatus.OK.equals(roundDetailResponse.getStatus())) {
							boutDetailResponse.setItemsCompleted(boutDetailResponse.getItemsCompleted() + 1);
						} else {
							errorStr = roundDetailResponse.getErrorMsg();
							break;
						}
					} else {
						boutDetailResponse.setItemsCompleted(boutDetailResponse.getItemsCompleted() + 1);
					}
					roundParsed += 1;
				}
			}
			fighterBoutXRefRepo.save(fighterBout);
			LOG.log(Level.INFO, String.format(SUCCESSFUL_SAVE_MESSAGE, fighterBout.getBout().getBoutId()));
			boutDetailResponse.addResponseMsg(HttpStatus.OK, errorStr);
			return boutDetailResponse;
		} catch (Exception e) {
			return errorService.handleParseError(e.getLocalizedMessage(), e, boutDetailResponse);
		}
	}

	public ParseResponse parseRound(HtmlElement roundItem, FighterBoutXRefData fighterBout,
			ParseRequest boutDetailRequest, Integer roundParsed, FinishDataTransfer finishTransfer) {

		ParseResponse roundResponse = new ParseResponse(boutDetailRequest);
		StrikeData strikeData;
		String roundPath;
		Integer fighterRow;
		try {
			roundPath = roundItem.getCanonicalXPath();
//			System.out.println(roundItem.asXml());
			strikeData = new StrikeData();
			strikeData.setRound(roundParsed);
			fighterRow = findFighterRow(roundItem, fighterBout, roundPath);

			parseSigStrike(roundItem, strikeData, fighterRow, roundParsed);
			parseKnockdowns(roundItem, roundPath, strikeData, fighterRow);
			parseTakedowns(roundItem, roundPath, strikeData, fighterRow);
			parseSubmission(roundItem, roundPath, strikeData, fighterRow);
			parsePass(roundItem, roundPath, strikeData, fighterRow);
			parseReversal(roundItem, roundPath, strikeData, fighterRow);
			parseTotStrikes(roundItem, roundPath, strikeData, fighterRow);
			parseKoSub(strikeData, finishTransfer, roundParsed);
			fighterBout.addBoutDetail(strikeData);
			roundResponse.setStatus(HttpStatus.OK);
			LOG.log(Level.INFO,
					String.format(ROUND_COMPLETION_MESSAGE, fighterBout.getFighter().getFighterName(), roundParsed));
			return roundResponse;
		} catch (Exception e) {
			return errorService.handleParseError(e.getLocalizedMessage(), e, roundResponse);
		}
	}

	private Integer findFighterRow(HtmlElement roundItem, FighterBoutXRefData fighterBout, String roundPath) {
		List<HtmlElement> roundFighterHtmlList = roundItem.getByXPath(roundPath + "/td[1]/p/a");
		ListIterator<HtmlElement> iterator = roundFighterHtmlList.listIterator();
		while (iterator.hasNext()) {
			Integer tempIndex = iterator.nextIndex();
			String rowName = iterator.next().asText().trim();
			String fighterName = fighterBout.getFighter().getFighterName();
			if (rowName.equals(fighterName)
					|| ("Zhalgas Zhumagulov".equals(rowName) && "Zhalgas Zhamagulov".equals(fighterName)) 
					|| ("Constantinos Philippou".equals(rowName) && "Costas Philippou".equals(fighterName))
					|| ("Ben Alloway".equals(rowName) && "Benny Alloway".equals(fighterName))
					|| ("Brad Scott".equals(rowName) && "Bradley Scott".equals(fighterName))
					|| ("Antonio Rodrigo Nogueira".equals(rowName) && "Minotauro Nogueira".equals(fighterName))
					|| ("Nicholas Musoke".equals(rowName) && "Nico Musoke".equals(fighterName))
					|| ("Robert Whiteford".equals(rowName) && "Rob Whiteford".equals(fighterName))
					|| ("Rafael Cavalcante".equals(rowName) && "Rafael Feijao".equals(fighterName))
					|| ("Josh Sampo".equals(rowName) && "Joshua Sampo".equals(fighterName))
					|| ("William Macario".equals(rowName) && "William Patolino".equals(fighterName))
					|| ("Ramiro Hernandez".equals(rowName) && "Junior Hernandez".equals(fighterName))
					|| ("Lipeng Zhang".equals(rowName) && "Zhang Lipeng".equals(fighterName))
					|| ("Anying Wang".equals(rowName) && "An Ying Wang".equals(fighterName))
					|| ("Robert McDaniel".equals(rowName) && "Bubba McDaniel".equals(fighterName))
					|| ("Edimilson Souza".equals(rowName) && "Kevin Souza".equals(fighterName))
					|| ("Roldan Sangcha'an".equals(rowName) && "Roldan Sangcha-an".equals(fighterName))
					|| ("Juan Manuel Puig".equals(rowName) && "Juan Puig".equals(fighterName))
					|| ("Tiago dos Santos e Silva".equals(rowName) && "Tiago Trator".equals(fighterName))
					|| ("Guangyou Ning".equals(rowName) && "Ning Guangyou".equals(fighterName))
					|| ("Yuta Sasaki".equals(rowName) && "Ulka Sasaki".equals(fighterName))
					|| ("Wendell Oliveira Marques".equals(rowName) && "Wendell Oliveira".equals(fighterName))
					|| ("Rodolfo Rubio Perez".equals(rowName) && "Rodolfo Rubio".equals(fighterName))
					|| ("Humberto Brown Morrison".equals(rowName) && "Humberto Brown".equals(fighterName))
					|| ("Heather Clark".equals(rowName) && "Heather Jo Clark".equals(fighterName))
					|| ("Seo Hee Ham".equals(rowName) && "Seohee Ham".equals(fighterName))
					|| ("Emily Kagan".equals(rowName) && "Emily Peters Kagan".equals(fighterName))
					|| ("Timothy Johnson".equals(rowName) && "Tim Johnson".equals(fighterName))
					|| ("Mirko Filipovic".equals(rowName) && "Mirko Cro Cop".equals(fighterName))
					|| ("Aleksandra Albu".equals(rowName) && "Alexandra Albu".equals(fighterName))
					|| ("Quinton Jackson".equals(rowName) && "Rampage Jackson".equals(fighterName))
					|| ("Glaico Franca Moreira".equals(rowName) && "Glaico Franca".equals(fighterName))
					|| ("Matheus Nicolau Pereira".equals(rowName) && "Matheus Nicolau".equals(fighterName))
					|| ("Vernon Ramos Ho".equals(rowName) && "Vernon Ramos".equals(fighterName))
					|| ("Alvaro Herrera Mendoza".equals(rowName) && "Alvaro Herrera".equals(fighterName))
					|| ("Bruno Rodrigues".equals(rowName) && "Bruno Korea".equals(fighterName))
					|| ("Marco Polo Reyes".equals(rowName) && "Polo Reyes".equals(fighterName))
					|| ("Leonardo Guimaraes".equals(rowName) && "Leonardo Augusto Leleco".equals(fighterName))
					|| ("Joseph Gigliotti".equals(rowName) && "Joe Gigliotti".equals(fighterName))
					|| ("Alex Ricci".equals(rowName) && "Alessandro Ricci".equals(fighterName))
					|| ("Jim Wallhead".equals(rowName) && "Jimmy Wallhead".equals(fighterName))
					|| ("Cristiane Justino".equals(rowName) && "Cris Cyborg".equals(fighterName))
					|| ("Chan-Mi Jeon".equals(rowName) && "Chanmi Jeon".equals(fighterName))
					|| ("Robert Sanchez".equals(rowName) && "Roberto Sanchez".equals(fighterName))
					|| ("Azunna Anyanwu".equals(rowName) && "Zu Anyanwu".equals(fighterName))
					|| ("Carlo Pedersoli Jr.".equals(rowName) && "Carlo Pedersoli".equals(fighterName))
					|| ("Grigory Popov".equals(rowName) && "Grigorii Popov".equals(fighterName))
					|| ("Jin Soo Son".equals(rowName) && "Jinsoo Son".equals(fighterName))
					|| ("Jin Soo Son".equals(rowName) && "Jinsoo Son".equals(fighterName))
					
				) {

				return tempIndex;
			}
		}
		LOG.log(Level.WARNING, roundItem.asXml());
		LOG.log(Level.WARNING, roundPath.toString());

		throw new IllegalArgumentException("Could not match stat row to fighters");
	}

	private void parseKnockdowns(HtmlElement roundItem, String roundPath, StrikeData strikeData, Integer fighterRow) {
		Integer knockdowns;
		DomText knockdownHtml = roundItem
				.getFirstByXPath(roundPath + String.format("/td[2]/p[%s]/text()", fighterRow + 1));
		knockdowns = Integer.valueOf(knockdownHtml.asText().trim());
		LOG.info(String.format("Round knockdowns: %s", knockdowns));

		if (knockdowns == null) {
			throw new IllegalArgumentException("Round knockdowns failed to parse");
		}
		strikeData.setKnockdowns(knockdowns);
	}

	private void parseTotStrikes(HtmlElement roundItem, String roundPath, StrikeData strikeData, Integer fighterRow) {
		Integer totalStrikeAttempt;
		Integer totalStrikeSuccess;
		String[] totalStrikeRaw;
		DomText totalStrikeHtml = roundItem
				.getFirstByXPath(roundPath + String.format("/td[5]/p[%s]/text()", fighterRow + 1));
		totalStrikeRaw = totalStrikeHtml.asText().trim().split(" of ");

		totalStrikeSuccess = Integer.valueOf(totalStrikeRaw[0].trim());
		totalStrikeAttempt = Integer.valueOf(totalStrikeRaw[1].trim());

		if (totalStrikeAttempt == null) {
			throw new IllegalArgumentException("Round total strike attempts failed to parse");
		}
		if (totalStrikeSuccess == null) {
			throw new IllegalArgumentException("Round total strike succeeded failed to parse");
		}

		LOG.info(String.format("Round total strikes: %s attempted, %s successfull", totalStrikeAttempt,
				totalStrikeSuccess));

		strikeData.setTotStrikeAttempted(totalStrikeAttempt);
		strikeData.setTotStrikeSuccessful(totalStrikeSuccess);
	}

	private void parseTakedowns(HtmlElement roundItem, String roundPath, StrikeData strikeData, Integer fighterRow) {
		Integer takedownAttempt;
		Integer takedownSuccess;
		String[] takedownRaw;
		DomText takedownHtml = roundItem
				.getFirstByXPath(roundPath + String.format("/td[6]/p[%s]/text()", fighterRow + 1));
		takedownRaw = takedownHtml.asText().trim().split(" of ");

		takedownSuccess = Integer.valueOf(takedownRaw[0].trim());
		takedownAttempt = Integer.valueOf(takedownRaw[1].trim());

		if (takedownAttempt == null) {
			throw new IllegalArgumentException("Round takedown attempts failed to parse");
		}
		if (takedownSuccess == null) {
			throw new IllegalArgumentException("Round takedown succeeded failed to parse");
		}

		LOG.info(String.format("Round takedowns: %s attempted, %s successfull", takedownAttempt, takedownSuccess));

		strikeData.setTakedownAttempted(takedownAttempt);
		strikeData.setTakedownSuccessful(takedownSuccess);
	}

	private void parseSubmission(HtmlElement roundItem, String roundPath, StrikeData strikeData, Integer fighterRow) {
		Integer submissionAttempt;
		DomText submissionHtml = roundItem
				.getFirstByXPath(roundPath + String.format("/td[8]/p[%s]/text()", fighterRow + 1));
		submissionAttempt = Integer.valueOf(submissionHtml.asText().trim());

		if (submissionAttempt == null) {
			throw new IllegalArgumentException("Round submission attempts failed to parse");
		}

		LOG.info(String.format("Round submission attempts: %s", submissionAttempt));

		strikeData.setSubmissionAttempted(submissionAttempt);
	}

	private void parsePass(HtmlElement roundItem, String roundPath, StrikeData strikeData, Integer fighterRow) {
		Integer pass;
		DomText passHtml = roundItem.getFirstByXPath(roundPath + String.format("/td[9]/p[%s]/text()", fighterRow + 1));
		pass = Integer.valueOf(passHtml.asText().trim());

		if (pass == null) {
			throw new IllegalArgumentException("Round pass failed to parse");
		}

		LOG.info(String.format("Round pass: %s", pass));

		strikeData.setPassSuccessful(pass);
	}

	private void parseReversal(HtmlElement roundItem, String roundPath, StrikeData strikeData, Integer fighterRow) {
		Integer reversal;
		DomText reversalHtml = roundItem
				.getFirstByXPath(roundPath + String.format("/td[10]/p[%s]/text()", fighterRow + 1));
		reversal = Integer.valueOf(reversalHtml.asText().trim());

		if (reversal == null) {
			throw new IllegalArgumentException("Round reversal failed to parse");
		}

		LOG.info(String.format("Round reversal: %s", reversal));

		strikeData.setReversalSuccessful(reversal);
	}

	private void parseKoSub(StrikeData strikeData, FinishDataTransfer finishTransfer, Integer roundParsed) {
		if (finishTransfer.getNeedApply()) {
			if (roundParsed.equals(finishTransfer.getRound())) {
				if (FightMethodEnum.KO_TKO.equals(finishTransfer.getMethod())) {
					strikeData.setTkoKo(1);
					strikeData.setSubmissionSuccessful(0);
				} else if (FightMethodEnum.SUB.equals(finishTransfer.getMethod())) {
					strikeData.setTkoKo(0);
					strikeData.setSubmissionSuccessful(1);
				} else if (FightMethodEnum.DOC_STOP.equals(finishTransfer.getMethod())) {
					strikeData.setTkoKo(1);
					strikeData.setSubmissionSuccessful(0);
				} else {
					throw new IllegalArgumentException("Failure to apply finish values.");
				}
			} else if (roundParsed > finishTransfer.getRound()) {
				throw new IllegalArgumentException("Failure to apply finish values (round > finish round).");
			} else {
				strikeData.setTkoKo(0);
				strikeData.setSubmissionSuccessful(0);
			}
		} else {
			strikeData.setTkoKo(0);
			strikeData.setSubmissionSuccessful(0);
		}
	}

	private void parseSigStrike(HtmlElement roundItem, StrikeData strikeData, Integer fighterRow, Integer roundParsed) {
//		String roundSigStrikePath = String.format("/html/body/section/div/div/section[5]/table/thead[2]/tr[%s]",
//				roundParsed + 1);
		String roundSigStrikePath = String.format("/html/body/section/div/div/section[5]/table/thead[%s]/tr[2]",
				roundParsed + 1);
		HtmlElement sigStrikeItem = roundItem.getFirstByXPath(roundSigStrikePath);
		SigStrikePositionData sigStrikePositionData = new SigStrikePositionData();
		SigStrikeTargetData sigStrikeTargetData = new SigStrikeTargetData();
//		LOG.info(sigStrikeItem.asXml());
		parseHeadSigStrike(sigStrikeItem, roundSigStrikePath, sigStrikeTargetData, fighterRow);
		parseBodySigStrike(sigStrikeItem, roundSigStrikePath, sigStrikeTargetData, fighterRow);
		parseLegSigStrike(sigStrikeItem, roundSigStrikePath, sigStrikeTargetData, fighterRow);
		strikeData.setSigStrikeTarget(sigStrikeTargetData);

		parseDistanceSigStrike(sigStrikeItem, roundSigStrikePath, sigStrikePositionData, fighterRow);
		parseClinchSigStrike(sigStrikeItem, roundSigStrikePath, sigStrikePositionData, fighterRow);
		parseGroundSigStrike(sigStrikeItem, roundSigStrikePath, sigStrikePositionData, fighterRow);
		strikeData.setSigStrikePosition(sigStrikePositionData);

	}

	private void parseHeadSigStrike(HtmlElement sigStrikeItem, String roundSigStrikePath,
			SigStrikeTargetData sigStrikeTargetData, Integer fighterRow) {
		Integer headAttempt;
		Integer headSuccess;
		String[] headRaw;
		DomText headHtml = sigStrikeItem
				.getFirstByXPath(roundSigStrikePath + String.format("/td[4]/p[%s]/text()", fighterRow + 1));
		headRaw = headHtml.asText().trim().split(" of ");

		headSuccess = Integer.valueOf(headRaw[0].trim());
		headAttempt = Integer.valueOf(headRaw[1].trim());

		if (headAttempt == null) {
			throw new IllegalArgumentException("Round significant head attempts failed to parse");
		}
		if (headSuccess == null) {
			throw new IllegalArgumentException("Round significant head succeeded failed to parse");
		}

		LOG.info(String.format("Round head significant strikes: %s attempted, %s successfull", headAttempt,
				headSuccess));

		sigStrikeTargetData.setHeadStrikeAttemped(headAttempt);
		sigStrikeTargetData.setHeadStrikeSuccessful(headSuccess);
	}

	private void parseBodySigStrike(HtmlElement sigStrikeItem, String roundSigStrikePath,
			SigStrikeTargetData sigStrikeTargetData, Integer fighterRow) {
		Integer bodyAttempt;
		Integer bodySuccess;
		String[] bodyRaw;
		DomText bodyHtml = sigStrikeItem
				.getFirstByXPath(roundSigStrikePath + String.format("/td[5]/p[%s]/text()", fighterRow + 1));
		bodyRaw = bodyHtml.asText().trim().split(" of ");

		bodySuccess = Integer.valueOf(bodyRaw[0].trim());
		bodyAttempt = Integer.valueOf(bodyRaw[1].trim());

		if (bodyAttempt == null) {
			throw new IllegalArgumentException("Round significant body attempts failed to parse");
		}
		if (bodySuccess == null) {
			throw new IllegalArgumentException("Round significant body succeeded failed to parse");
		}

		LOG.info(String.format("Round body significant strikes: %s attempted, %s successfull", bodyAttempt,
				bodySuccess));

		sigStrikeTargetData.setBodyStrikeAttemped(bodyAttempt);
		sigStrikeTargetData.setBodyStrikeSuccessful(bodySuccess);
	}

	private void parseLegSigStrike(HtmlElement sigStrikeItem, String roundSigStrikePath,
			SigStrikeTargetData sigStrikeTargetData, Integer fighterRow) {
		Integer legAttempt;
		Integer legSuccess;
		String[] legRaw;
		DomText legHtml = sigStrikeItem
				.getFirstByXPath(roundSigStrikePath + String.format("/td[6]/p[%s]/text()", fighterRow + 1));
		legRaw = legHtml.asText().trim().split(" of ");

		legSuccess = Integer.valueOf(legRaw[0].trim());
		legAttempt = Integer.valueOf(legRaw[1].trim());

		if (legAttempt == null) {
			throw new IllegalArgumentException("Round significant leg attempts failed to parse");
		}
		if (legSuccess == null) {
			throw new IllegalArgumentException("Round significant leg succeeded failed to parse");
		}

		LOG.info(String.format("Round leg significant strikes: %s attempted, %s successfull", legAttempt, legSuccess));

		sigStrikeTargetData.setLegStrikeAttemped(legAttempt);
		sigStrikeTargetData.setLegStrikeSuccessful(legSuccess);
	}

	private void parseDistanceSigStrike(HtmlElement sigStrikeItem, String roundSigStrikePath,
			SigStrikePositionData sigStrikePositionData, Integer fighterRow) {
		Integer distanceAttempt;
		Integer distanceSuccess;
		String[] distanceRaw;
		DomText distanceHtml = sigStrikeItem
				.getFirstByXPath(roundSigStrikePath + String.format("/td[7]/p[%s]/text()", fighterRow + 1));
		distanceRaw = distanceHtml.asText().trim().split(" of ");

		distanceSuccess = Integer.valueOf(distanceRaw[0].trim());
		distanceAttempt = Integer.valueOf(distanceRaw[1].trim());

		if (distanceAttempt == null) {
			throw new IllegalArgumentException("Round significant distance attempts failed to parse");
		}
		if (distanceSuccess == null) {
			throw new IllegalArgumentException("Round significant distance succeeded failed to parse");
		}

		LOG.info(String.format("Round distance significant strikes: %s attempted, %s successfull", distanceAttempt,
				distanceSuccess));

		sigStrikePositionData.setDistanceStrikeAttemped(distanceAttempt);
		sigStrikePositionData.setDistanceStrikeSuccessful(distanceSuccess);
	}

	private void parseClinchSigStrike(HtmlElement sigStrikeItem, String roundSigStrikePath,
			SigStrikePositionData sigStrikePositionData, Integer fighterRow) {
		Integer clinchAttempt;
		Integer clinchSuccess;
		String[] clinchRaw;
		DomText clinchHtml = sigStrikeItem
				.getFirstByXPath(roundSigStrikePath + String.format("/td[8]/p[%s]/text()", fighterRow + 1));
		clinchRaw = clinchHtml.asText().trim().split(" of ");

		clinchSuccess = Integer.valueOf(clinchRaw[0].trim());
		clinchAttempt = Integer.valueOf(clinchRaw[1].trim());

		if (clinchAttempt == null) {
			throw new IllegalArgumentException("Round significant clinch attempts failed to parse");
		}
		if (clinchSuccess == null) {
			throw new IllegalArgumentException("Round significant clinch succeeded failed to parse");
		}

		LOG.info(String.format("Round clinch significant strikes: %s attempted, %s successfull", clinchAttempt,
				clinchSuccess));

		sigStrikePositionData.setClinchStrikeAttemped(clinchAttempt);
		sigStrikePositionData.setClinchStrikeSuccessful(clinchSuccess);
	}

	private void parseGroundSigStrike(HtmlElement sigStrikeItem, String roundSigStrikePath,
			SigStrikePositionData sigStrikePositionData, Integer fighterRow) {
		Integer groundAttempt;
		Integer groundSuccess;
		String[] groundRaw;
		DomText groundHtml = sigStrikeItem
				.getFirstByXPath(roundSigStrikePath + String.format("/td[9]/p[%s]/text()", fighterRow + 1));
		groundRaw = groundHtml.asText().trim().split(" of ");

		groundSuccess = Integer.valueOf(groundRaw[0].trim());
		groundAttempt = Integer.valueOf(groundRaw[1].trim());

		if (groundAttempt == null) {
			throw new IllegalArgumentException("Round significant ground attempts failed to parse");
		}
		if (groundSuccess == null) {
			throw new IllegalArgumentException("Round significant ground succeeded failed to parse");
		}

		LOG.info(String.format("Round ground significant strikes: %s attempted, %s successfull", groundAttempt,
				groundSuccess));

		sigStrikePositionData.setGroundStrikeAttemped(groundAttempt);
		sigStrikePositionData.setGroundStrikeSuccessful(groundSuccess);
	}
}
