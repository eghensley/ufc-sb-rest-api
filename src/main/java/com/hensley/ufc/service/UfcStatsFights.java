//package com.hensley.ufc.service;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import com.gargoylesoftware.htmlunit.html.DomAttr;
//import com.gargoylesoftware.htmlunit.html.HtmlElement;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//
//import java.util.HashMap;
//
//public class UfcStatsFights {
//	
//	public static void boutScraper() throws MalformedURLException, IOException {
//		String baseUrl = "http://www.ufcstats.com/fight-details/08af25ef4252b138";
//		String fightId = "8d04923f2db59b7f";
//		HtmlPage page = UrlToHtml.parse(baseUrl);
//		System.out.println("Completed Parse");
//		// System.out.println(page.asXml());	
//		
//		
////		HtmlElement fighterHeaderHTML = page.getFirstByXPath("/html/body/section/div/div/div[1]");
////		System.out.println(fighterHeaderHTML.asXml());
//		
//		HtmlElement favoriteNameHTML = page.getFirstByXPath("/html/body/section/div/div/div[1]/div[1]/div/h3/a");
//		String favoriteName = favoriteNameHTML.asText();
////		System.out.println(favoriteNameHTML.asXml());
//		System.out.println(favoriteName);
//		
//		HtmlElement favoriteResultHTML = page.getFirstByXPath("/html/body/section/div/div/div[1]/div[1]/i");
//		String favoriteResult = favoriteResultHTML.asText();
//		System.out.println(favoriteResult);
//		
//
//		HtmlElement dogNameHTML = page.getFirstByXPath("/html/body/section/div/div/div[1]/div[2]/div/h3/a");
//		String dogName = dogNameHTML.asText();
////		System.out.println(favoriteNameHTML.asXml());
//		System.out.println(dogName);
//		
//		HtmlElement dogResultHTML = page.getFirstByXPath("/html/body/section/div/div/div[1]/div[2]/i");
//		String dogResult = dogResultHTML.asText();
//		System.out.println(dogResult);
//		
//		
//		HtmlElement boutEndRndHTML = page.getFirstByXPath("/html/body/section/div/div/div[2]/div[2]/p[1]/i[2]");
//		int boutEndRnd = Integer.parseInt(boutEndRndHTML.asText().replace("Round: ", ""));
//		System.out.println(boutEndRnd);		
//		
//		HtmlElement boutEndTimeHTML = page.getFirstByXPath("/html/body/section/div/div/div[2]/div[2]/p[1]/i[3]");
//		String boutEndTime = boutEndTimeHTML.asText().replace("Time: ", "");
//		System.out.println(boutEndTime);	
//		
//		String[] splitBoutEndTime = boutEndTime.split(":");
//		
//		System.out.println(splitBoutEndTime[0]);	
//		
//		int boutLength = ((boutEndRnd - 1) * 300) + (60 * Integer.parseInt(splitBoutEndTime[0])) + Integer.parseInt(splitBoutEndTime[1]);
//		System.out.println(boutLength);	
//		
//		HtmlElement boutExpRndsHTML = page.getFirstByXPath("/html/body/section/div/div/div[2]/div[2]/p[1]/i[4]");
//		String boutExpRnds = boutExpRndsHTML.asText().replace("Time format: ", "");
//		System.out.println(boutExpRnds.split(" ")[0]);		
//		
////		List<String> totalHeaders = new ArrayList<String>();
////		List<HtmlElement> totalHeaderHTML = page.getByXPath("/html/body/section/div/div/section[2]/table/thead/tr/th");
////		for(HtmlElement totalHeader : totalHeaderHTML){
////			System.out.println(totalHeader.asText());	
////			totalHeaders.add(totalHeader.asText());
////		}
//		
//		
//		
//		
////		HtmlElement roundTotalsInfoHTML = page.getFirstByXPath("/html/body/section/div/div/section[3]/table/thead[2]");
////		System.out.println(roundTotalsInfoHTML.asXml());	
////		String roundTotalInfoPath = roundTotalsInfoHTML.getCanonicalXPath();
//
//		
//		
//		
//		List<HtmlElement> roundTotalsInfoHTML = page.getByXPath("/html/body/section/div/div/section[3]/table/thead[position()>=2]");
//		for (HtmlElement roundTotalSection : roundTotalsInfoHTML) {
//			String roundTotalInfoPath = roundTotalSection.getCanonicalXPath();
//			System.out.println(roundTotalInfoPath);	
//
//			
//		
////		List<HtmlElement> roundTotalsHTML = page.getByXPath(roundTotalInfoPath+"/tr[2]/td");
////		
////		Map = new HashMap<String, HtmlElement>();
//			List<String> fighterPosList = Arrays.asList("1", "2");
//			for (String fighterPos : fighterPosList) {
////			String fighterPos = "1";
//				System.out.println("---------------------------");	
//				
//				System.out.println(roundTotalInfoPath);	
//				
//				
//				System.out.println(fightId);
//				String boutId = baseUrl.replace("http://www.ufcstats.com/fight-details/", "");
//				System.out.println(boutId);
//		
//				DomAttr roundTotalFighterHTML = page.getFirstByXPath(roundTotalInfoPath+"/tr[2]/td[1]/p["+fighterPos+"]/a/@href");
//				String fighterId = roundTotalFighterHTML.getValue().replace("http://www.ufcstats.com/fighter-details/", "");
//				System.out.println(fighterId);
//				
//				HtmlElement roundTotalRoundHTML = page.getFirstByXPath(roundTotalInfoPath+"/tr/th");
//				int roundNmbr = Integer.parseInt(roundTotalRoundHTML.asText().replace("Round ", ""));
//				System.out.println(roundNmbr);	
//				
//				int roundLen = 0;
//				if (roundNmbr == boutEndRnd) {
//					roundLen = (60 * Integer.parseInt(splitBoutEndTime[0])) + Integer.parseInt(splitBoutEndTime[1]);
//				} else {
//					roundLen = 300;
//				}
//				System.out.println(roundLen);		
//				
//				
//				HtmlElement roundKdHTML = page.getFirstByXPath(roundTotalInfoPath+"/tr[2]/td[2]/p["+fighterPos+"]");
//				int knockdown = Integer.parseInt(roundKdHTML.asText());
//				System.out.println(knockdown);			
//		
//				HtmlElement roundTotalSigStrikeHTML = page.getFirstByXPath(roundTotalInfoPath+"/tr[2]/td[3]/p["+fighterPos+"]");
//				String sigStrikes = roundTotalSigStrikeHTML.asText();
//				System.out.println(sigStrikes);	
//				
//				HtmlElement roundTotalStrikesHTML = page.getFirstByXPath(roundTotalInfoPath+"/tr[2]/td[5]/p["+fighterPos+"]");
//				String totStrikes = roundTotalStrikesHTML.asText();
//				System.out.println(totStrikes);			
//				
//				HtmlElement roundTdHTML = page.getFirstByXPath(roundTotalInfoPath+"/tr[2]/td[6]/p["+fighterPos+"]");
//				String takedowns = roundTdHTML.asText();
//				System.out.println(takedowns);			
//		
//				HtmlElement roundSubAttemptHTML = page.getFirstByXPath(roundTotalInfoPath+"/tr[2]/td[8]/p["+fighterPos+"]");
//				int subAttempted = Integer.parseInt(roundSubAttemptHTML.asText());
//				System.out.println(subAttempted);			
//				
//				HtmlElement roundPassHTML = page.getFirstByXPath(roundTotalInfoPath+"/tr[2]/td[9]/p["+fighterPos+"]");		
//				int pass = Integer.parseInt(roundPassHTML.asText());
//				System.out.println(pass);	
//				
//				HtmlElement roundRevHTML = page.getFirstByXPath(roundTotalInfoPath+"/tr[2]/td[10]/p["+fighterPos+"]");		
//				int reversal = Integer.parseInt(roundRevHTML.asText());
//				System.out.println(reversal);
//				
//				FighterRoundTotals roundTotals = new FighterRoundTotals(fightId, boutId, fighterId, roundNmbr, knockdown, sigStrikes, totStrikes, takedowns, subAttempted, pass, reversal, roundLen);
//				System.out.println(roundTotals);
//			}
//		}
//		
//		
//		
//		
//		
////		List<HtmlElement> sigStrikeRoundValuesHTML = page.getByXPath("/html/body/section/div/div/section[3]/table/tbody[2]/tr");
////		for(HtmlElement sigStrikeRoundValue : sigStrikeRoundValuesHTML){
////			System.out.println(sigStrikeRoundValue.asText());	
////		}		
//		
//		
////		List<HtmlElement> roundSigStrikeInfoHTML = page.getByXPath("/html/body/section/div/div/section/table");
////		System.out.println(roundSigStrikeInfoHTML.size());
////		if(roundSigStrikeInfoHTML.isEmpty()){
////			System.out.println("No item found");
////		}
////		else {
////			System.out.println("Items found");
////			for(HtmlElement roundSigStrikeInfoHTMLItem : roundSigStrikeInfoHTML){
////				String roundSigStrikePath = roundSigStrikeInfoHTMLItem.getCanonicalXPath();
////				
////				
////				System.out.println(roundSigStrikePath);	
////				
////				
////				System.out.println(roundSigStrikeInfoHTMLItem.asXml());				
////			}
////		}
////				DomAttr boutIdHtml = boutItem.getFirstByXPath(boutPath + "/@data-link");
////				String boutId = boutIdHtml.getValue().replace("http://www.ufcstats.com/fight-details/", "");
////				System.out.println(boutId);		
//		
//		
////		List<HtmlElement> boutHTML = page.getByXPath("/html/body/section/div/div/table/tbody/tr[1]");
////
////		if(boutHTML.isEmpty()){
////			System.out.println("No item found");
////		}
////		else {
////			System.out.println("Items found");
////			for(HtmlElement boutItem : boutHTML){
////				String boutPath = boutItem.getCanonicalXPath();
////
////				DomAttr boutIdHtml = boutItem.getFirstByXPath(boutPath + "/@data-link");
////				String boutId = boutIdHtml.getValue().replace("http://www.ufcstats.com/fight-details/", "");
////				System.out.println(boutId);
//
//				
////				List<HtmlElement> boutFighterNameHTML = boutItem.getByXPath(boutPath + "/td[2]/p/a");
////				for(HtmlElement boutFighterNameItem : boutFighterNameHTML){
////					System.out.println(boutFighterNameItem.asText());
////				}
////				
////				HtmlElement boutWeightClassHTML = boutItem.getFirstByXPath(boutPath + "/td[7]/p");
////				System.out.println(boutWeightClassHTML.asText());
////				
////				List<DomAttr> boutMetaDataHTML = boutItem.getByXPath(boutPath + "/td[7]/p/img/@src");
////				boolean isChampBout = false;
////				for(DomAttr boutMetaDataItem : boutMetaDataHTML){
////					System.out.println(boutMetaDataItem.getValue());
////					if (boutMetaDataItem.getValue().contains("belt")) {
////						isChampBout = true;
////					}
//////					if boutMetaDataItem.asXml == "<img src=\"http://1e49bc5171d173577ecd-1323f4090557a33db01577564f60846c.r80.cf1.rackcdn.com/belt.png\" alt=\"\"/>"
////				}
////				
////				List<HtmlElement> boutResult = boutItem.getByXPath(boutPath+"/td[8]/p");
////				for(HtmlElement boutResultItem : boutResult){
////					System.out.println(boutResultItem.asText());
//////					if boutMetaDataItem.asXml == "<img src=\"http://1e49bc5171d173577ecd-1323f4090557a33db01577564f60846c.r80.cf1.rackcdn.com/belt.png\" alt=\"\"/>"
////				}										
////			}
////		}
//	}
//}