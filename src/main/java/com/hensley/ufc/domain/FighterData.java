package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hensley.ufc.enums.FighterStanceEnum;

@Entity
@Table(name = "FIGHTER")
public class FighterData extends BaseAuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6125962533007770448L;

	@Column(name = "FIGHTER_ID", nullable = false)
	private String fighterId;
	@Column(name = "FIGHTER_NAME", nullable = false)
	private String fighterName;	
	@Column(name = "REACH", nullable = true)
	private Integer reach;		
	@Column(name = "HEIGHT", nullable = false)
	private Integer height;	
	@Column(name = "DOB", nullable = true)
	private Date dob;	
	@Column(name = "STANCE", nullable = true)
	private FighterStanceEnum stance;		
	@OneToMany(mappedBy = "fighter", cascade = CascadeType.PERSIST)
	private List<FighterBoutXRefData> fighterBoutXRefs;
	
	public FighterData() {
		
	}

	public FighterData(String fighterId, String fighterName, Integer reach, Integer height, Date dob, FighterStanceEnum stance) {
		this.fighterId = fighterId;
		this.fighterName = fighterName;
		this.reach = reach;
		this.height = height;
		this.dob = dob;
		this.stance = stance;
	}
	
	/**
	 * @return the fighterId
	 */
	public String getFighterId() {
		return fighterId;
	}

	/**
	 * @param fighterId the fighterId to set
	 */
	public void setFighterId(String fighterId) {
		this.fighterId = fighterId;
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
	 * @return the reach
	 */
	public Integer getReach() {
		return reach;
	}

	/**
	 * @param reach the reach to set
	 */
	public void setReach(Integer reach) {
		this.reach = reach;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the stance
	 */
	public FighterStanceEnum getStance() {
		return stance;
	}

	/**
	 * @param stance the stance to set
	 */
	public void setStance(FighterStanceEnum stance) {
		this.stance = stance;
	}

	
	/**
	 * @return the fighterBoutXRefs
	 */
	public List<FighterBoutXRefData> getFighterBoutXRefs() {
		return fighterBoutXRefs;
	}

	/**
	 * @param fighterBoutXRefs the fighterBoutXRefs to set
	 */
	public void setFighterBoutXRefs(List<FighterBoutXRefData> fighterBoutXRefs) {
		this.fighterBoutXRefs = fighterBoutXRefs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(dob, fighterBoutXRefs, fighterId, fighterName, height, reach, stance);
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
		if (!(obj instanceof FighterData)) {
			return false;
		}
		FighterData other = (FighterData) obj;
		return Objects.equals(dob, other.dob) && Objects.equals(fighterBoutXRefs, other.fighterBoutXRefs)
				&& Objects.equals(fighterId, other.fighterId) && Objects.equals(fighterName, other.fighterName)
				&& Objects.equals(height, other.height) && Objects.equals(reach, other.reach) && stance == other.stance;
	}

}
