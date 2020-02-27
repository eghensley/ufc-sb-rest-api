package com.hensley.ufc.pojo.parse;

import java.util.ArrayList;
import java.util.List;

public class RoundScoreFighterParseStore {
	
	private String webFighterName;
	private List<Integer> round1Scores;
	private List<Integer> round2Scores;
	private List<Integer> round3Scores;
	private List<Integer> round4Scores;
	private List<Integer> round5Scores;

	public RoundScoreFighterParseStore() {
		this.round1Scores = new ArrayList<>();
		this.round2Scores = new ArrayList<>();
		this.round3Scores = new ArrayList<>();
		this.round4Scores = new ArrayList<>();
		this.round5Scores = new ArrayList<>();
	}

	public List<SingleRoundScoreParse> outputRoundScores(){
		List<SingleRoundScoreParse> response = new ArrayList<>();
		
		if (!this.round1Scores.isEmpty()) {
			response.add(new SingleRoundScoreParse(1, this.round1Scores.stream().mapToInt(i -> i).sum()));
		}
		if (!this.round2Scores.isEmpty()) {
			response.add(new SingleRoundScoreParse(2, this.round2Scores.stream().mapToInt(i -> i).sum()));
		}
		if (!this.round3Scores.isEmpty()) {
			response.add(new SingleRoundScoreParse(3, this.round3Scores.stream().mapToInt(i -> i).sum()));
		}
		if (!this.round4Scores.isEmpty()) {
			response.add(new SingleRoundScoreParse(4, this.round4Scores.stream().mapToInt(i -> i).sum()));
		}
		if (!this.round5Scores.isEmpty()) {
			response.add(new SingleRoundScoreParse(5, this.round5Scores.stream().mapToInt(i -> i).sum()));
		}
		return response;
	}
	
	public void addRoundScore(Integer score, Integer round) {
		switch (round) {
		case 1:
			this.round1Scores.add(score);
			break;
		case 2:
			this.round2Scores.add(score);
			break;
		case 3:
			this.round3Scores.add(score);
			break;
		case 4:
			this.round4Scores.add(score);
			break;
		case 5:
			this.round5Scores.add(score);
			break;
		default:
			throw new IllegalArgumentException(String.format("Round %s is not supported.", round));
		}
	}

	/**
	 * @return the webFighterName
	 */
	public String getWebFighterName() {
		return webFighterName;
	}

	/**
	 * @param webFighterName the webFighterName to set
	 */
	public void setWebFighterName(String webFighterName) {
		this.webFighterName = webFighterName;
	}

}
