package com.hensley.ufc.pojo.request;

import com.hensley.ufc.pojo.dto.SuperAuditDto;

public class AddBoutRoundScoreRequest extends SuperAuditDto{
	private Integer round;
	private Double score;

	public AddBoutRoundScoreRequest() {

	}

	/**
	 * @return the round
	 */
	public Integer getRound() {
		return round;
	}

	/**
	 * @param round the round to set
	 */
	public void setRound(Integer round) {
		this.round = round;
	}

	/**
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Double score) {
		this.score = score;
	}

}
