//package com.hensley.ufc.domain;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "VEGAS_ODDS")
//public class VegasOddsData extends BaseAuditEntity implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 4128331881047863196L;
//
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "FIGHTER_BOUT_OID", referencedColumnName = "OID", nullable = false)
//	private FighterBoutXRefData fighterBout;
//	
//	@Column(name = "ML_ODDS")
//	private Double mlOdds;
//	
//	@Column(name = "SITE_NAME")
//	private String siteName;
//	
//	public VegasOddsData() {
//		
//	}
//
//	/**
//	 * @return the fighterBout
//	 */
//	public FighterBoutXRefData getFighterBout() {
//		return fighterBout;
//	}
//
//	/**
//	 * @param fighterBout the fighterBout to set
//	 */
//	public void setFighterBout(FighterBoutXRefData fighterBout) {
//		this.fighterBout = fighterBout;
//	}
//
//	/**
//	 * @return the mlOdds
//	 */
//	public Double getMlOdds() {
//		return mlOdds;
//	}
//
//	/**
//	 * @param mlOdds the mlOdds to set
//	 */
//	public void setMlOdds(Double mlOdds) {
//		this.mlOdds = mlOdds;
//	}
//
//	/**
//	 * @return the siteName
//	 */
//	public String getSiteName() {
//		return siteName;
//	}
//
//	/**
//	 * @param siteName the siteName to set
//	 */
//	public void setSiteName(String siteName) {
//		this.siteName = siteName;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = super.hashCode();
//		result = prime * result + Objects.hash(fighterBout, mlOdds, siteName);
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (!super.equals(obj)) {
//			return false;
//		}
//		if (!(obj instanceof VegasOddsData)) {
//			return false;
//		}
//		VegasOddsData other = (VegasOddsData) obj;
//		return Objects.equals(fighterBout, other.fighterBout) && Objects.equals(mlOdds, other.mlOdds)
//				&& Objects.equals(siteName, other.siteName);
//	}
//	
//	
//}
