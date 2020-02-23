package com.hensley.ufc.domain;

import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.junit.tools.configuration.base.MethodRef;

@Generated(value = "org.junit-tools-1.1.0")
public class LocationDataTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(LocationDataTest.class.toString());

	private LocationData createTestSubject() {
		return new LocationData();
	}

	@MethodRef(name = "getCityName", signature = "()QString;")
	@Test
	public void testGetCityName() throws Exception {
		LocationData testSubject;
		String cityName = "Herndon";

		// default test
		testSubject = createTestSubject();
		testSubject.setCityName(cityName);
		Assert.assertEquals(cityName, testSubject.getCityName());
	}

	@MethodRef(name = "setCityName", signature = "(QString;)V")
	@Test
	public void testSetCityName() throws Exception {
		LocationData testSubject;
		String cityName = "Herndon";

		// default test
		testSubject = createTestSubject();
		testSubject.setCityName(cityName);
		Assert.assertEquals(cityName, testSubject.getCityName());
	}

	@MethodRef(name = "getStateName", signature = "()QString;")
	@Test
	public void testGetStateName() throws Exception {
		LocationData testSubject;
		String stateName = "Virginia";

		// default test
		testSubject = createTestSubject();
		testSubject.setStateName(stateName);
		Assert.assertEquals(stateName, testSubject.getStateName());
	}

	@MethodRef(name = "setStateName", signature = "(QString;)V")
	@Test
	public void testSetStateName() throws Exception {
		LocationData testSubject;
		String stateName = "Virginia";

		// default test
		testSubject = createTestSubject();
		testSubject.setStateName(stateName);
		Assert.assertEquals(stateName, testSubject.getStateName());
	}

	@MethodRef(name = "getCountry", signature = "()QCountryData;")
	@Test
	public void testGetCountry() throws Exception {
		LocationData testSubject;
		CountryData country = new CountryData();

		// default test
		testSubject = createTestSubject();
		testSubject.setCountry(country);
		Assert.assertEquals(country, testSubject.getCountry());
	}

	@MethodRef(name = "setCountry", signature = "(QCountryData;)V")
	@Test
	public void testSetCountry() throws Exception {
		LocationData testSubject;
		CountryData country = new CountryData();

		// default test
		testSubject = createTestSubject();
		testSubject.setCountry(country);
		Assert.assertEquals(country, testSubject.getCountry());
	}

	@MethodRef(name = "hashCode", signature = "()I")
	@Test
	public void testHashCode() throws Exception {
		LocationData testSubject;
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.hashCode();
		Assert.assertNotNull(result);
	}

	@MethodRef(name = "equals", signature = "(QObject;)Z")
	@Test
	public void testEquals() throws Exception {
		LocationData testSubject;
		Object obj = createTestSubject();
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.equals(obj);
		Assert.assertTrue(result);
	}
	
	@MethodRef(name = "equals", signature = "(QObject;)Z")
	@Test
	public void testNotEquals() throws Exception {
		LocationData testSubject;
		LocationData obj = createTestSubject();
		obj.setCityName("test");
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.equals(obj);
		Assert.assertFalse(result);
	}
}