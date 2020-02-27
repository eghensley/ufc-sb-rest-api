package com.hensley.ufc.pojo.dto.location;

import com.hensley.ufc.pojo.dto.SuperAuditDto;

public class LocationDto extends SuperAuditDto {

	private String cityName;
	private String stateName;
	private CountryDto country;
	
	public LocationDto() {
		
	}

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
	public CountryDto getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(CountryDto country) {
		this.country = country;
	}
	
}
