package com.hensley.ufc.pojo.dto;

public class FighterRoundTotals {
	public final String fightOid;
	public final String boutOid;
	public final String fighterOid;
	public final int roundNmbr;
	public final int knockdown;
	public final int sigStrikeAttempted;
	public final int sigStrikeSuccessful;
	public final int totStrikeAttempted;
	public final int totStrikeSuccessful;
	public final int tdAttempted;
	public final int tdSuccessful;
	public final int subAttempted;
	public final int pass;
	public final int reversal;
	public final int roundLen;

	public FighterRoundTotals(String fightOid, String boutOid, String fighterOid, int roundNmbr, int knockdown,
			String sigStrikes, String totStrikes, String takedowns, int subAttempted, int pass, int reversal,
			int roundLen) {
		
		this.fightOid = fightOid;
		this.boutOid = boutOid;
		this.fighterOid = fighterOid;
		this.roundNmbr = roundNmbr;
		this.knockdown = knockdown;
		this.roundLen = roundLen;

		this.subAttempted = subAttempted;
		this.pass = pass;
		this.reversal = reversal;

		String[] sigStrikesArray = sigStrikes.split(" of ");
		this.sigStrikeAttempted = Integer.parseInt(sigStrikesArray[0].trim());
		this.sigStrikeSuccessful = Integer.parseInt(sigStrikesArray[1].trim());

		String[] totStrikesArray = totStrikes.split(" of ");
		this.totStrikeAttempted = Integer.parseInt(totStrikesArray[0].trim());
		this.totStrikeSuccessful = Integer.parseInt(totStrikesArray[1].trim());

		String[] tdArray = takedowns.split(" of ");
		this.tdAttempted = Integer.parseInt(tdArray[0].trim());
		this.tdSuccessful = Integer.parseInt(tdArray[1].trim());

	}
//getters and setters
}
