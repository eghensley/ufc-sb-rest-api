package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.hensley.ufc.enums.BoutOutcomeEnum;

@Entity
@Table(name = "FIGHTER_BOUT_XREF")
public class FighterBoutXRefData extends BaseAuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6826845477136525067L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BOUT_OID", referencedColumnName = "OID", nullable = false)
	private BoutData bout;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIGHTER_OID", referencedColumnName = "OID", nullable = false)
	private FighterData fighter;

	@OneToMany(mappedBy = "fighterBout", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<StrikeData> boutDetails;

//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	private VegasOddsData vegasOdds;

	@Column(name = "ML_ODDS")
	private Double mlOdds;

	@Column(name = "EXP_ODDS")
	private Double expOdds;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private BfoExpectedOutcomeData bfoExpectedOutcomes;

	@Column(name = "OUTCOME")
	private BoutOutcomeEnum outcome;
	
	@Column(name = "OFF_STRIKE_ELO_PRE")
	private Double offStrikeEloPre;
	
	@Column(name = "DEF_STRIKE_ELO_PRE")
	private Double defStrikeEloPre;
	
	@Column(name = "OFF_GRAP_ELO_PRE")
	private Double offGrapplingEloPre;
	
	@Column(name = "DEF_GRAP_ELO_PRE")
	private Double defGrapplingEloPre;

	@Column(name = "OFF_STRIKE_ELO_POST")
	private Double offStrikeEloPost;
	
	@Column(name = "DEF_STRIKE_ELO_POST")
	private Double defStrikeEloPost;
	
	@Column(name = "OFF_GRAP_ELO_POST")
	private Double offGrapplingEloPost;
	
	@Column(name = "DEF_GRAP_ELO_POST")
	private Double defGrapplingEloPost;
	
	@Column(name = "POWER_STRIKE_ELO_PRE")
	private Double powerStrikeEloPre;
	
	@Column(name = "CHIN_STRIKE_ELO_PRE")
	private Double chinStrikeEloPre;
	
	@Column(name = "SUB_GRAP_ELO_PRE")
	private Double subGrapplingEloPre;
	
	@Column(name = "EVAS_GRAP_ELO_PRE")
	private Double evasGrapplingEloPre;

	@Column(name = "POWER_STRIKE_ELO_POST")
	private Double powerStrikeEloPost;
	
	@Column(name = "CHIN_STRIKE_ELO_POST")
	private Double chinStrikeEloPost;
	
	@Column(name = "SUB_GRAP_ELO_POST")
	private Double subGrapplingEloPost;
	
	@Column(name = "EVAS_GRAP_ELO_POST")
	private Double evasGrapplingEloPost;	
	
	@Column(name = "F_BET_MADE")
	private Boolean betMade;
	
	@Column(name = "F_BET_PRED")
	private Boolean betPredicted;
	
	@Column(name = "BET_AMOUNT")
	private Double betAmount;
	
	@Column(name = "BET_RESULT")
	private Double betResult;
	
	public FighterBoutXRefData() {

	}

	public boolean evalIfRoundScoresMissing() {
		boolean response = false;
		for (StrikeData roundData : boutDetails) {
			if (roundData.getScore() == null) {
				response = true;
				return response;
			}
		}
		return response;
	}

	public StrikeData getStatsByRound(Integer round) {
		List<StrikeData> potMatches = boutDetails.stream().filter(d -> d.getRound().equals(round))
				.collect(Collectors.toList());
		if (potMatches.isEmpty()) {
			throw new IllegalArgumentException(String.format("No stats for round %s", round));
		} else if (potMatches.size() > 1) {
			throw new IllegalArgumentException(String.format("Multiple matches found for round %s", round));
		} else {
			return potMatches.get(0);
		}
	}

	/**
	 * @return the bout
	 */
	public BoutData getBout() {
		return bout;
	}

	/**
	 * @param bout the bout to set
	 */
	public void setBout(BoutData bout) {
		this.bout = bout;
	}

	/**
	 * @return the fighter
	 */
	public FighterData getFighter() {
		return fighter;
	}

	public String getFighterName() {
		return this.fighter.getFighterName();
	}

	/**
	 * @param fighter the fighter to set
	 */
	public void setFighter(FighterData fighter) {
		this.fighter = fighter;
	}

	/**
	 * @return the outcome
	 */
	public BoutOutcomeEnum getOutcome() {
		return outcome;
	}

	/**
	 * @param outcome the outcome to set
	 */
	public void setOutcome(BoutOutcomeEnum outcome) {
		this.outcome = outcome;
	}

	/**
	 * @return the boutDetails
	 */
	public List<StrikeData> getBoutDetails() {
		return boutDetails;
	}

	/**
	 * @param boutDetails the boutDetails to set
	 */
	public void setBoutDetails(List<StrikeData> boutDetails) {
		for (StrikeData boutDetail : boutDetails) {
			addBoutDetail(boutDetail);
		}
	}

	public void addBoutDetail(StrikeData boutDetail) {
		if (this.boutDetails == null) {
			this.boutDetails = new ArrayList<>();
		}
		this.boutDetails.add(boutDetail);
		boutDetail.setFighterBout(this);
	}

//	/**
//	 * @return the vegasOdds
//	 */
//	public VegasOddsData getVegasOdds() {
//		return vegasOdds;
//	}
//
//	/**
//	 * @param vegasOdds the vegasOdds to set
//	 */
//	public void setVegasOdds(VegasOddsData vegasOdds) {
//		this.vegasOdds = vegasOdds;
//		vegasOdds.setFighterBout(this);
//	}

	/**
	 * @return the bfoExpectedOutcomes
	 */
	public BfoExpectedOutcomeData getBfoExpectedOutcomes() {
		return bfoExpectedOutcomes;
	}

	/**
	 * @param bfoExpectedOutcomes the bfoExpectedOutcomes to set
	 */
	public void setBfoExpectedOutcomes(BfoExpectedOutcomeData bfoExpectedOutcomes) {
		this.bfoExpectedOutcomes = bfoExpectedOutcomes;
		bfoExpectedOutcomes.setFighterBout(this);
	}

	/**
	 * @return the mlOdds
	 */
	public Double getMlOdds() {
		return mlOdds;
	}

	/**
	 * @param mlOdds the mlOdds to set
	 */
	public void setMlOdds(Double mlOdds) {
		this.mlOdds = mlOdds;
	}

	public boolean evalIfRoundStored(Integer round) {
		for (StrikeData boutDetail : boutDetails) {
			if (round.equals(boutDetail.getRound())) {
				return true;
			}
		}
		return false;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	/**
	 * @return the expOdds
	 */
	public Double getExpOdds() {
		return expOdds;
	}

	/**
	 * @param expOdds the expOdds to set
	 */
	public void setExpOdds(Double expOdds) {
		this.expOdds = expOdds;
	}

	/**
	 * @return the betMade
	 */
	public Boolean getBetMade() {
		return betMade;
	}

	/**
	 * @param betMade the betMade to set
	 */
	public void setBetMade(Boolean betMade) {
		this.betMade = betMade;
	}

	/**
	 * @return the betPredicted
	 */
	public Boolean getBetPredicted() {
		return betPredicted;
	}

	/**
	 * @param betPredicted the betPredicted to set
	 */
	public void setBetPredicted(Boolean betPredicted) {
		this.betPredicted = betPredicted;
	}

	/**
	 * @return the betAmount
	 */
	public Double getBetAmount() {
		return betAmount;
	}

	/**
	 * @param betAmount the betAmount to set
	 */
	public void setBetAmount(Double betAmount) {
		this.betAmount = betAmount;
	}

	/**
	 * @return the betResult
	 */
	public Double getBetResult() {
		return betResult;
	}

	/**
	 * @param betResult the betResult to set
	 */
	public void setBetResult(Double betResult) {
		this.betResult = betResult;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(betAmount, betMade, betPredicted, betResult, bfoExpectedOutcomes, bout,
				boutDetails, chinStrikeEloPost, chinStrikeEloPre, defGrapplingEloPost, defGrapplingEloPre,
				defStrikeEloPost, defStrikeEloPre, evasGrapplingEloPost, evasGrapplingEloPre, expOdds, fighter, mlOdds,
				offGrapplingEloPost, offGrapplingEloPre, offStrikeEloPost, offStrikeEloPre, outcome, powerStrikeEloPost,
				powerStrikeEloPre, subGrapplingEloPost, subGrapplingEloPre);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof FighterBoutXRefData)) {
			return false;
		}
		FighterBoutXRefData other = (FighterBoutXRefData) obj;
		return Objects.equals(betAmount, other.betAmount) && Objects.equals(betMade, other.betMade)
				&& Objects.equals(betPredicted, other.betPredicted) && Objects.equals(betResult, other.betResult)
				&& Objects.equals(bfoExpectedOutcomes, other.bfoExpectedOutcomes) && Objects.equals(bout, other.bout)
				&& Objects.equals(boutDetails, other.boutDetails)
				&& Objects.equals(chinStrikeEloPost, other.chinStrikeEloPost)
				&& Objects.equals(chinStrikeEloPre, other.chinStrikeEloPre)
				&& Objects.equals(defGrapplingEloPost, other.defGrapplingEloPost)
				&& Objects.equals(defGrapplingEloPre, other.defGrapplingEloPre)
				&& Objects.equals(defStrikeEloPost, other.defStrikeEloPost)
				&& Objects.equals(defStrikeEloPre, other.defStrikeEloPre)
				&& Objects.equals(evasGrapplingEloPost, other.evasGrapplingEloPost)
				&& Objects.equals(evasGrapplingEloPre, other.evasGrapplingEloPre)
				&& Objects.equals(expOdds, other.expOdds) && Objects.equals(fighter, other.fighter)
				&& Objects.equals(mlOdds, other.mlOdds)
				&& Objects.equals(offGrapplingEloPost, other.offGrapplingEloPost)
				&& Objects.equals(offGrapplingEloPre, other.offGrapplingEloPre)
				&& Objects.equals(offStrikeEloPost, other.offStrikeEloPost)
				&& Objects.equals(offStrikeEloPre, other.offStrikeEloPre) && outcome == other.outcome
				&& Objects.equals(powerStrikeEloPost, other.powerStrikeEloPost)
				&& Objects.equals(powerStrikeEloPre, other.powerStrikeEloPre)
				&& Objects.equals(subGrapplingEloPost, other.subGrapplingEloPost)
				&& Objects.equals(subGrapplingEloPre, other.subGrapplingEloPre);
	}
	
}
