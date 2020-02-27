package com.hensley.ufc.pojo.dto;

import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

import com.hensley.ufc.pojo.dto.location.CountryDto;
import com.hensley.ufc.pojo.dto.location.LocationDto;

@Generated(value = "org.junit-tools-1.1.0")
public class LocationDtoTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(LocationDtoTest.class.toString());

	private LocationDto createTestSubject() {
		return new LocationDto();
	}

	@Test
	public void testGetCityName() throws Exception {
		LocationDto testSubject;
		String cityName = "test city";

		// default test
		testSubject = createTestSubject();
		testSubject.setCityName(cityName);

		Assert.assertEquals(cityName, testSubject.getCityName());
	}

	@Test
	public void testSetCityName() throws Exception {
		LocationDto testSubject;
		String cityName = "test city";

		// default test
		testSubject = createTestSubject();
		testSubject.setCityName(cityName);

		Assert.assertEquals(cityName, testSubject.getCityName());
	}

	@Test
	public void testGetStateName() throws Exception {
		LocationDto testSubject;
		String stateName = "test state";

		// default test
		testSubject = createTestSubject();
		testSubject.setStateName(stateName);

		Assert.assertEquals(stateName, testSubject.getStateName());
	}

	@Test
	public void testSetStateName() throws Exception {
		LocationDto testSubject;
		String stateName = "test state";

		// default test
		testSubject = createTestSubject();
		testSubject.setStateName(stateName);

		Assert.assertEquals(stateName, testSubject.getStateName());
	}

	@Test
	public void testGetCountry() throws Exception {
		LocationDto testSubject;
		CountryDto country = new CountryDto();

		// default test
		testSubject = createTestSubject();
		testSubject.setCountry(country);

		Assert.assertEquals(country, testSubject.getCountry());
	}

	@Test
	public void testSetCountry() throws Exception {
		LocationDto testSubject;
		CountryDto country = new CountryDto();

		// default test
		testSubject = createTestSubject();
		testSubject.setCountry(country);

		Assert.assertEquals(country, testSubject.getCountry());
	}
}