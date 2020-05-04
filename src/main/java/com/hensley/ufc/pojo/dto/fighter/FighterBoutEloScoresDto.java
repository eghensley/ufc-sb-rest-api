package com.hensley.ufc.pojo.dto.fighter;

import com.hensley.ufc.pojo.dto.SuperAuditDto;

public class FighterBoutEloScoresDto extends SuperAuditDto {

	private Double offStrikeEloPre;
	private Double defStrikeEloPre;
	private Double offGrapplingEloPre;
	private Double defGrapplingEloPre;

	private Double offStrikeEloPost;
	private Double defStrikeEloPost;
	private Double offGrapplingEloPost;
	private Double defGrapplingEloPost;

	private Double powerStrikeEloPre;
	private Double chinStrikeEloPre;
	private Double subGrapplingEloPre;
	private Double evasGrapplingEloPre;

	private Double powerStrikeEloPost;
	private Double chinStrikeEloPost;
	private Double subGrapplingEloPost;
	private Double evasGrapplingEloPost;

	public FighterBoutEloScoresDto() {
		this.offStrikeEloPre = .15;
		this.defStrikeEloPre = .85;
		this.offGrapplingEloPre = .1;
		this.defGrapplingEloPre = .9;
		this.powerStrikeEloPre = .5;
		this.chinStrikeEloPre = .5;
		this.subGrapplingEloPre = .5;
		this.evasGrapplingEloPre = .5;
	}

	/**
	 * @return the offStrikeEloPre
	 */
	public Double getOffStrikeEloPre() {
		return offStrikeEloPre;
	}

	/**
	 * @param offStrikeEloPre the offStrikeEloPre to set
	 */
	public void setOffStrikeEloPre(Double offStrikeEloPre) {
		this.offStrikeEloPre = offStrikeEloPre;
	}

	/**
	 * @return the defStrikeEloPre
	 */
	public Double getDefStrikeEloPre() {
		return defStrikeEloPre;
	}

	/**
	 * @param defStrikeEloPre the defStrikeEloPre to set
	 */
	public void setDefStrikeEloPre(Double defStrikeEloPre) {
		this.defStrikeEloPre = defStrikeEloPre;
	}

	/**
	 * @return the offGrapplingEloPre
	 */
	public Double getOffGrapplingEloPre() {
		return offGrapplingEloPre;
	}

	/**
	 * @param offGrapplingEloPre the offGrapplingEloPre to set
	 */
	public void setOffGrapplingEloPre(Double offGrapplingEloPre) {
		this.offGrapplingEloPre = offGrapplingEloPre;
	}

	/**
	 * @return the defGrapplingEloPre
	 */
	public Double getDefGrapplingEloPre() {
		return defGrapplingEloPre;
	}

	/**
	 * @param defGrapplingEloPre the defGrapplingEloPre to set
	 */
	public void setDefGrapplingEloPre(Double defGrapplingEloPre) {
		this.defGrapplingEloPre = defGrapplingEloPre;
	}

	/**
	 * @return the offStrikeEloPost
	 */
	public Double getOffStrikeEloPost() {
		return offStrikeEloPost;
	}

	/**
	 * @param offStrikeEloPost the offStrikeEloPost to set
	 */
	public void setOffStrikeEloPost(Double offStrikeEloPost) {
		this.offStrikeEloPost = offStrikeEloPost;
	}

	/**
	 * @return the defStrikeEloPost
	 */
	public Double getDefStrikeEloPost() {
		return defStrikeEloPost;
	}

	/**
	 * @param defStrikeEloPost the defStrikeEloPost to set
	 */
	public void setDefStrikeEloPost(Double defStrikeEloPost) {
		this.defStrikeEloPost = defStrikeEloPost;
	}

	/**
	 * @return the offGrapplingEloPost
	 */
	public Double getOffGrapplingEloPost() {
		return offGrapplingEloPost;
	}

	/**
	 * @param offGrapplingEloPost the offGrapplingEloPost to set
	 */
	public void setOffGrapplingEloPost(Double offGrapplingEloPost) {
		this.offGrapplingEloPost = offGrapplingEloPost;
	}

	/**
	 * @return the defGrapplingEloPost
	 */
	public Double getDefGrapplingEloPost() {
		return defGrapplingEloPost;
	}

	/**
	 * @param defGrapplingEloPost the defGrapplingEloPost to set
	 */
	public void setDefGrapplingEloPost(Double defGrapplingEloPost) {
		this.defGrapplingEloPost = defGrapplingEloPost;
	}

	/**
	 * @return the powerStrikeEloPre
	 */
	public Double getPowerStrikeEloPre() {
		return powerStrikeEloPre;
	}

	/**
	 * @param powerStrikeEloPre the powerStrikeEloPre to set
	 */
	public void setPowerStrikeEloPre(Double powerStrikeEloPre) {
		this.powerStrikeEloPre = powerStrikeEloPre;
	}

	/**
	 * @return the chinStrikeEloPre
	 */
	public Double getChinStrikeEloPre() {
		return chinStrikeEloPre;
	}

	/**
	 * @param chinStrikeEloPre the chinStrikeEloPre to set
	 */
	public void setChinStrikeEloPre(Double chinStrikeEloPre) {
		this.chinStrikeEloPre = chinStrikeEloPre;
	}

	/**
	 * @return the subGrapplingEloPre
	 */
	public Double getSubGrapplingEloPre() {
		return subGrapplingEloPre;
	}

	/**
	 * @param subGrapplingEloPre the subGrapplingEloPre to set
	 */
	public void setSubGrapplingEloPre(Double subGrapplingEloPre) {
		this.subGrapplingEloPre = subGrapplingEloPre;
	}

	/**
	 * @return the evasGrapplingEloPre
	 */
	public Double getEvasGrapplingEloPre() {
		return evasGrapplingEloPre;
	}

	/**
	 * @param evasGrapplingEloPre the evasGrapplingEloPre to set
	 */
	public void setEvasGrapplingEloPre(Double evasGrapplingEloPre) {
		this.evasGrapplingEloPre = evasGrapplingEloPre;
	}

	/**
	 * @return the powerStrikeEloPost
	 */
	public Double getPowerStrikeEloPost() {
		return powerStrikeEloPost;
	}

	/**
	 * @param powerStrikeEloPost the powerStrikeEloPost to set
	 */
	public void setPowerStrikeEloPost(Double powerStrikeEloPost) {
		this.powerStrikeEloPost = powerStrikeEloPost;
	}

	/**
	 * @return the chinStrikeEloPost
	 */
	public Double getChinStrikeEloPost() {
		return chinStrikeEloPost;
	}

	/**
	 * @param chinStrikeEloPost the chinStrikeEloPost to set
	 */
	public void setChinStrikeEloPost(Double chinStrikeEloPost) {
		this.chinStrikeEloPost = chinStrikeEloPost;
	}

	/**
	 * @return the subGrapplingEloPost
	 */
	public Double getSubGrapplingEloPost() {
		return subGrapplingEloPost;
	}

	/**
	 * @param subGrapplingEloPost the subGrapplingEloPost to set
	 */
	public void setSubGrapplingEloPost(Double subGrapplingEloPost) {
		this.subGrapplingEloPost = subGrapplingEloPost;
	}

	/**
	 * @return the evasGrapplingEloPost
	 */
	public Double getEvasGrapplingEloPost() {
		return evasGrapplingEloPost;
	}

	/**
	 * @param evasGrapplingEloPost the evasGrapplingEloPost to set
	 */
	public void setEvasGrapplingEloPost(Double evasGrapplingEloPost) {
		this.evasGrapplingEloPost = evasGrapplingEloPost;
	}

}
