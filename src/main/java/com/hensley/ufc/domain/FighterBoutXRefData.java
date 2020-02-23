package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@Column(name = "OUTCOME")
	private BoutOutcomeEnum outcome;
	
	public FighterBoutXRefData() {
		
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
		for (StrikeData boutDetail: boutDetails) {
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
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(bout, boutDetails, fighter, outcome);
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
		return Objects.equals(bout, other.bout) && Objects.equals(boutDetails, other.boutDetails)
				&& Objects.equals(fighter, other.fighter) && outcome == other.outcome;
	}

	@Override
	public String toString() {
		return "FighterBoutXRefData [bout=" + bout + ", fighter=" + fighter + ", outcome=" + outcome + "]";
	}

}
