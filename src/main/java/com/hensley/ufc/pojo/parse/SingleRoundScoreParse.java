package com.hensley.ufc.pojo.parse;

public class SingleRoundScoreParse {
	private Integer round;
	private Integer score;

	public SingleRoundScoreParse(Integer round, Integer score) {
		this.round = round;
		this.score = score;
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
		return Double.valueOf(this.score);
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

}
