package com.hensley.ufc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRY")
public class CountryData extends BaseAuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6744006593316030589L;

	@Column(name = "COUNTRY_NAME", nullable = false)
	private String countryName;
	@OneToMany(mappedBy = "country")
	private List<LocationData> locations;

	public CountryData() {
		this.locations = new ArrayList<>();
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

	/**
	 * @return the locations
	 */
	public List<LocationData> getLocations() {
		return locations;
	}

	/**
	 * @param locations the locations to set
	 */
	public void setLocations(List<LocationData> locations) {
		if (locations != null) {
			for (LocationData location : locations) {
				addLocation(location);
			}
		}
	}

	public void addLocation(LocationData location) {
		this.locations.add(location);
		location.setCountry(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(countryName, locations);
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
		if (!(obj instanceof CountryData)) {
			return false;
		}
		CountryData other = (CountryData) obj;
		return Objects.equals(countryName, other.countryName) && Objects.equals(locations, other.locations);
	}

	@Override
	public String toString() {
		return "CountryData [countryName=" + countryName + ", locations=" + locations + "]";
	}

}
