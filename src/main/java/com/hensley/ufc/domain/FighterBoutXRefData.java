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

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private BfoExpectedOutcomeData bfoExpectedOutcomes;

	@Column(name = "OUTCOME")
	private BoutOutcomeEnum outcome;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(bfoExpectedOutcomes, bout, boutDetails, fighter, mlOdds, outcome);
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
		return Objects.equals(bfoExpectedOutcomes, other.bfoExpectedOutcomes) && Objects.equals(bout, other.bout)
				&& Objects.equals(boutDetails, other.boutDetails) && Objects.equals(fighter, other.fighter)
				&& Objects.equals(mlOdds, other.mlOdds) && outcome == other.outcome;
	}

}
