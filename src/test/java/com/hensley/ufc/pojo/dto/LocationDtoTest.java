package com.hensley.ufc.pojo.dto;

import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.junit.tools.configuration.base.MethodRef;

@Generated(value = "org.junit-tools-1.1.0")
public class LocationDtoTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(LocationDtoTest.class.toString());

	private LocationDto createTestSubject() {
		return new LocationDto();
	}

	@MethodRef(name = "getCityName", signature = "()QString;")
	@Test
	public void testGetCityName() throws Exception {
		LocationDto testSubject;
		String cityName = "test city";

		// default test
		testSubject = createTestSubject();
		testSubject.setCityName(cityName);

		Assert.assertEquals(cityName, testSubject.getCityName());
	}

	@MethodRef(name = "setCityName", signature = "(QString;)V")
	@Test
	public void testSetCityName() throws Exception {
		LocationDto testSubject;
		String cityName = "test city";

		// default test
		testSubject = createTestSubject();
		testSubject.setCityName(cityName);

		Assert.assertEquals(cityName, testSubject.getCityName());
	}

	@MethodRef(name = "getStateName", signature = "()QString;")
	@Test
	public void testGetStateName() throws Exception {
		LocationDto testSubject;
		String stateName = "test state";

		// default test
		testSubject = createTestSubject();
		testSubject.setStateName(stateName);

		Assert.assertEquals(stateName, testSubject.getStateName());
	}

	@MethodRef(name = "setStateName", signature = "(QString;)V")
	@Test
	public void testSetStateName() throws Exception {
		LocationDto testSubject;
		String stateName = "test state";

		// default test
		testSubject = createTestSubject();
		testSubject.setStateName(stateName);

		Assert.assertEquals(stateName, testSubject.getStateName());
	}

	@MethodRef(name = "getCountry", signature = "()QCountryDto;")
	@Test
	public void testGetCountry() throws Exception {
		LocationDto testSubject;
		CountryDto country = new CountryDto();

		// default test
		testSubject = createTestSubject();
		testSubject.setCountry(country);

		Assert.assertEquals(country, testSubject.getCountry());
	}

	@MethodRef(name = "setCountry", signature = "(QCountryDto;)V")
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