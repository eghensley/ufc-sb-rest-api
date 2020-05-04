package com.hensley.ufc.pojo.request;

import com.hensley.ufc.pojo.dto.SuperAuditDto;

public class AddRoundMlScore extends SuperAuditDto{
	private Double koScore;
	private Double subScore;

	public AddRoundMlScore() {

	}

	/**
	 * @return the koScore
	 */
	public Double getKoScore() {
		return koScore;
	}

	/**
	 * @param koScore the koScore to set
	 */
	public void setKoScore(Double koScore) {
		this.koScore = koScore;
	}

	/**
	 * @return the subScore
	 */
	public Double getSubScore() {
		return subScore;
	}

	/**
	 * @param subScore the subScore to set
	 */
	public void setSubScore(Double subScore) {
		this.subScore = subScore;
	}

}
