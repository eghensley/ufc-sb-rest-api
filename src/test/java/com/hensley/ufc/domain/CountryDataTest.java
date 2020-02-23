package com.hensley.ufc.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.junit.tools.configuration.base.MethodRef;

@Generated(value = "org.junit-tools-1.1.0")
public class CountryDataTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(CountryDataTest.class.toString());

	private CountryData createTestSubject() {
		return new CountryData();
	}

	@MethodRef(name = "getCountryName", signature = "()QString;")
	@Test
	public void testGetCountryName() throws Exception {
		CountryData testSubject;
		String countryName = "test";

		// default test
		testSubject = createTestSubject();
		testSubject.setCountryName(countryName);

		Assert.assertEquals(countryName, testSubject.getCountryName());
	}

	@MethodRef(name = "setCountryName", signature = "(QString;)V")
	@Test
	public void testSetCountryName() throws Exception {
		CountryData testSubject;
		String countryName = "test";

		// default test
		testSubject = createTestSubject();
		testSubject.setCountryName(countryName);

		Assert.assertEquals(countryName, testSubject.getCountryName());
	}

	@MethodRef(name = "getLocations", signature = "()QList<QLocationData;>;")
	@Test
	public void testGetLocations() throws Exception {
		CountryData testSubject;
		List<LocationData> result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getLocations();

		Assert.assertTrue(result.size() == 0);
	}

	@MethodRef(name = "setLocations", signature = "(QList<QLocationData;>;)V")
	@Test
	public void testSetLocationsNull() throws Exception {
		CountryData testSubject;
		List<LocationData> locations = null;

		// default test
		testSubject = createTestSubject();
		testSubject.setLocations(locations);

		Assert.assertNotNull(testSubject.getLocations());
	}
	
	@MethodRef(name = "setLocations", signature = "()QList<QLocationData;>;")
	@Test
	public void testSetLocationsEmpty() throws Exception {
		CountryData testSubject;
		List<LocationData> locations = new ArrayList<>();

		// default test
		testSubject = createTestSubject();
		testSubject.setLocations(locations);

		Assert.assertNotNull(testSubject.getLocations());
	}
	
	@MethodRef(name = "setLocations", signature = "()QList<QLocationData;>;")
	@Test
	public void testSetLocations() throws Exception {
		CountryData testSubject;
		List<LocationData> locations = new ArrayList<>();
		LocationData location = new LocationData();
		location.setCityName("test");
		locations.add(location);
		
		// default test
		testSubject = createTestSubject();
		testSubject.setLocations(locations);

		Assert.assertTrue(testSubject.getLocations().size() == 1);
	}

	@MethodRef(name = "addLocation", signature = "(QLocationData;)V")
	@Test
	public void testAddLocation() throws Exception {
		CountryData testSubject;
		LocationData location = new LocationData();

		// default test
		testSubject = createTestSubject();
		testSubject.addLocation(location);
		Assert.assertTrue(testSubject.getLocations().size() == 1);
		Assert.assertTrue(testSubject.getLocations().get(0).getCountry().equals(testSubject));
	}

	@MethodRef(name = "hashCode", signature = "()I")
	@Test
	public void testHashCode() throws Exception {
		CountryData testSubject;
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.hashCode();

		Assert.assertNotNull(result);
	}

	@MethodRef(name = "equals", signature = "(QObject;)Z")
	@Test
	public void testEquals() throws Exception {
		CountryData testSubject;
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
		CountryData testSubject;
		CountryData obj = createTestSubject();
		obj.setCountryName("test");
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.equals(obj);

		Assert.assertFalse(result);
	}
}