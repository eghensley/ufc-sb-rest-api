package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LOCATION")
public class LocationData extends BaseAuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 222529222305866485L;

	public LocationData() {
		
	}
	
	@Column(name = "CITY_NAME", nullable = false)
	private String cityName;
	@Column(name = "STATE_NAME", nullable = true)
	private String stateName;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_OID", referencedColumnName = "OID", nullable = false)
	private CountryData country;
	

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}
	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	/**
	 * @return the country
	 */
	public CountryData getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(CountryData country) {
		this.country = country;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cityName, country, stateName);
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
		if (!(obj instanceof LocationData)) {
			return false;
		}
		LocationData other = (LocationData) obj;
		return Objects.equals(cityName, other.cityName) && Objects.equals(country, other.country)
				&& Objects.equals(stateName, other.stateName);
	}
	@Override
	public String toString() {
		return "LocationData [cityName=" + cityName + ", stateName=" + stateName + ", country=" + country + "]";
	}

}
