package com.hensley.ufc.pojo.dto.location;

import com.hensley.ufc.pojo.dto.SuperAuditDto;

public class CountryDto extends SuperAuditDto {

	private String countryName;
	
	public CountryDto() {
		
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
}
