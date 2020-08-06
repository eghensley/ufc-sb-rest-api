package com.hensley.ufc.pojo.dto.rank;

public class FighterRankBasicDto {
	private String fighterName;
	private Double offStrikeEloPost;
	private Double defStrikeEloPost;
	private Double offGrapplingEloPost;
	private Double defGrapplingEloPost;
	
	public FighterRankBasicDto() {
		
	}

	/**
	 * @return the fighterName
	 */
	public String getFighterName() {
		return fighterName;
	}

	/**
	 * @param fighterName the fighterName to set
	 */
	public void setFighterName(String fighterName) {
		this.fighterName = fighterName;
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
	
	
}
