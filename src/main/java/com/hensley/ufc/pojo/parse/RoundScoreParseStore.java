package com.hensley.ufc.pojo.parse;

import org.apache.commons.lang.StringUtils;

public class RoundScoreParseStore {
	private RoundScoreFighterParseStore fighter1;
	private RoundScoreFighterParseStore fighter2;

	public RoundScoreParseStore() {
		this.fighter1 = new RoundScoreFighterParseStore();
		this.fighter2 = new RoundScoreFighterParseStore();
	}

	public void addScoreToFighter(Integer fighter, Integer round, Integer score) {
		switch (fighter) {
		case 1:
			this.fighter1.addRoundScore(score, round);
			break;
		case 2:
			this.fighter2.addRoundScore(score, round);
			break;

		default:
			throw new IllegalArgumentException(String.format("Fighter %s not supported", fighter));
		}
	}

	public void addNameToFighter(String webFighterName, Integer fighter) {
		switch (fighter) {
		case 1:
			this.fighter1.setWebFighterName(webFighterName);
			break;
		case 2:
			this.fighter2.setWebFighterName(webFighterName);
			break;

		default:
			throw new IllegalArgumentException(String.format("Fighter %s not supported", fighter));
		}
	}

	public RoundScoreFighterParseStore matchFighterName(String actName) {
		Integer fighter1Match = StringUtils.getLevenshteinDistance(actName, this.fighter1.getWebFighterName());
		Integer fighter2Match = StringUtils.getLevenshteinDistance(actName, this.fighter2.getWebFighterName());
		// TODO investigate save if switched fighters 
		if (fighter1Match > fighter2Match) {
			return this.fighter2;
		} else if (fighter2Match > fighter1Match) {
			return this.fighter1;
		} else {
			throw new IllegalArgumentException(String.format("Cannot match fighter name (%s) to extracted... %s & %s",
					actName, this.fighter1.getWebFighterName(), this.fighter2.getWebFighterName()));
		}
	}
}
