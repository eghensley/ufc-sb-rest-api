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

import com.hensley.ufc.enums.FightMethodEnum;
import com.hensley.ufc.enums.WeightClassEnum;

@Entity
@Table(name = "BOUT")
public class BoutData extends BaseAuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7624423476956941072L;

	@Column(name = "BOUT_ID", nullable = false)
	private String boutId;
	@Column(name = "WEIGHT_CLASS", nullable = false)
	private WeightClassEnum weightClass;
	@Column(name = "CHAMP_BOUT", nullable = false)
	private Boolean champBout;
//	@Column(name = "MAIN_EVENT", nullable = false)
//	private Boolean mainEvent;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIGHT_OID", referencedColumnName = "OID", nullable = false)
	private FightData fight;
	@Column(name = "F_COMPLETED", nullable = false)
	private Boolean completed;
	@Column(name = "SCHED_ROUNDS", nullable = false)
	private Integer schedRounds;
	@Column(name = "REFEREE", nullable = false)
	private String referee;
	@Column(name = "FINISH_ROUNDS", nullable = true)
	private Integer finishRounds;
	@Column(name = "FINISH_TIME", nullable = true)
	private Integer finishTime;
	@Column(name = "FINISH_METHOD", nullable = true)
	private FightMethodEnum finishMethod;
	@Column(name = "FINISH_DETAILS", nullable = true)
	private String finishDetails;
	@OneToMany(mappedBy = "bout", cascade = CascadeType.PERSIST)
	private List<FighterBoutXRefData> fighterBoutXRefs;
	@Column(name = "MMA_DEC_BOUT_URL", nullable = true)
	private String mmaDecBoutUrl;
	
	public BoutData() {
		this.fighterBoutXRefs = new ArrayList<>();
	}

	public BoutData(String boutId, WeightClassEnum weightClass, Boolean champBout) {
		this.boutId = boutId;
		this.weightClass = weightClass;
		this.champBout = champBout;
		this.completed = false;
		this.fighterBoutXRefs = new ArrayList<>();
	}

	public void addDetailedBoutData(Integer schedRounds, String referee, Integer finishRounds, Integer finishTime,
			FightMethodEnum finishMethod, String finishDetails) {
		this.schedRounds = schedRounds;
		this.referee = referee;
		this.finishRounds = finishRounds;
		this.finishTime = finishTime;
		this.finishMethod = finishMethod;
		this.finishDetails = finishDetails;
		this.fighterBoutXRefs = new ArrayList<>();
	}

	/**
	 * @return the boutId
	 */
	public String getBoutId() {
		return boutId;
	}

	/**
	 * @param boutId the boutId to set
	 */
	public void setBoutId(String boutId) {
		this.boutId = boutId;
	}

	/**
	 * @return the weightClass
	 */
	public WeightClassEnum getWeightClass() {
		return weightClass;
	}

	/**
	 * @param weightClass the weightClass to set
	 */
	public void setWeightClass(WeightClassEnum weightClass) {
		this.weightClass = weightClass;
	}

	/**
	 * @return the champBout
	 */
	public Boolean getChampBout() {
		return champBout;
	}

	/**
	 * @param champBout the champBout to set
	 */
	public void setChampBout(Boolean champBout) {
		this.champBout = champBout;
	}

	/**
	 * @return the fightId
	 */
	public String getFightId() {
		return fight.getFightId();
	}

	/**
	 * @return the fightOid
	 */
	public String getFightOid() {
		return fight.getOid();
	}
	
	/**
	 * @param fight the fight to set
	 */
	public void setFight(FightData fight) {
		this.fight = fight;
	}

	/**
	 * @return the completed
	 */
	public Boolean getCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return the schedRounds
	 */
	public Integer getSchedRounds() {
		return schedRounds;
	}

	/**
	 * @param schedRounds the schedRounds to set
	 */
	public void setSchedRounds(Integer schedRounds) {
		this.schedRounds = schedRounds;
	}

	/**
	 * @return the referee
	 */
	public String getReferee() {
		return referee;
	}

	/**
	 * @param referee the referee to set
	 */
	public void setReferee(String referee) {
		this.referee = referee;
	}

	/**
	 * @return the finishRounds
	 */
	public Integer getFinishRounds() {
		return finishRounds;
	}

	/**
	 * @param finishRounds the finishRounds to set
	 */
	public void setFinishRounds(Integer finishRounds) {
		this.finishRounds = finishRounds;
	}

	/**
	 * @return the finishTime
	 */
	public Integer getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(Integer finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @return the finishMethod
	 */
	public FightMethodEnum getFinishMethod() {
		return finishMethod;
	}

	/**
	 * @param finishMethod the finishMethod to set
	 */
	public void setFinishMethod(FightMethodEnum finishMethod) {
		this.finishMethod = finishMethod;
	}

	/**
	 * @return the finishDetails
	 */
	public String getFinishDetails() {
		return finishDetails;
	}

	/**
	 * @param finishDetails the finishDetails to set
	 */
	public void setFinishDetails(String finishDetails) {
		this.finishDetails = finishDetails;
	}

	/**
	 * @return the fighters
	 */
	public List<FighterData> getFighters() {
		List<FighterData> fighters = new ArrayList<>();
		for (FighterBoutXRefData fighterXref: this.fighterBoutXRefs) {
			fighters.add(fighterXref.getFighter());
		}
		return fighters;
	}

	/**
	 * @return the fighterIds
	 */
	public List<String> getFighterIds() {
		List<String> fighterIds = new ArrayList<>();
		for (FighterBoutXRefData fighterXref: this.fighterBoutXRefs) {
			fighterIds.add(fighterXref.getFighter().getFighterId());
		}
		return fighterIds;
	}
	
	/**
	 * @return the fighter
	 */
	public List<FighterBoutXRefData> getFighterBoutXRefs() {
		return fighterBoutXRefs;
	}
	
	/**
	 * @param fighterBoutXRefs the fighterBoutXRefs to set
	 */
	public void addFighterBoutXRefs(FighterBoutXRefData fighterBoutXRef) {
		if (this.fighterBoutXRefs.size() < 2) {
			fighterBoutXRef.setBout(this);
			this.fighterBoutXRefs.add(fighterBoutXRef);
		} else {
			throw new IllegalArgumentException("Cannot exceed 2 fighters for each bout.");
		}
	}

	/**
	 * @return the mmaDecBoutUrl
	 */
	public String getMmaDecBoutUrl() {
		return mmaDecBoutUrl;
	}

	/**
	 * @param mmaDecBoutUrl the mmaDecBoutUrl to set
	 */
	public void setMmaDecBoutUrl(String mmaDecBoutUrl) {
		this.mmaDecBoutUrl = mmaDecBoutUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(boutId, champBout, completed, fight, fighterBoutXRefs, finishDetails,
				finishMethod, finishRounds, finishTime, mmaDecBoutUrl, referee, schedRounds, weightClass);
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
		if (!(obj instanceof BoutData)) {
			return false;
		}
		BoutData other = (BoutData) obj;
		return Objects.equals(boutId, other.boutId) && Objects.equals(champBout, other.champBout)
				&& Objects.equals(completed, other.completed) && Objects.equals(fight, other.fight)
				&& Objects.equals(fighterBoutXRefs, other.fighterBoutXRefs)
				&& Objects.equals(finishDetails, other.finishDetails) && finishMethod == other.finishMethod
				&& Objects.equals(finishRounds, other.finishRounds) && Objects.equals(finishTime, other.finishTime)
				&& Objects.equals(mmaDecBoutUrl, other.mmaDecBoutUrl) && Objects.equals(referee, other.referee)
				&& Objects.equals(schedRounds, other.schedRounds) && weightClass == other.weightClass;
	}

	@Override
	public String toString() {
		return "BoutData [boutId=" + boutId + ", weightClass=" + weightClass + ", champBout=" + champBout + ", fight="
				+ fight + ", completed=" + completed + ", schedRounds=" + schedRounds + ", referee=" + referee
				+ ", finishRounds=" + finishRounds + ", finishTime=" + finishTime + ", finishMethod=" + finishMethod
				+ ", finishDetails=" + finishDetails + "]";
	}

}
