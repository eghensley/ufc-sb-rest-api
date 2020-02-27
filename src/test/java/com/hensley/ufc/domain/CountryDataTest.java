package com.hensley.ufc.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.1.0")
public class CountryDataTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(CountryDataTest.class.toString());

	private CountryData createTestSubject() {
		return new CountryData();
	}

	@Test
	public void testGetCountryName() throws Exception {
		CountryData testSubject;
		String countryName = "test";

		// default test
		testSubject = createTestSubject();
		testSubject.setCountryName(countryName);

		Assert.assertEquals(countryName, testSubject.getCountryName());
	}

	@Test
	public void testSetCountryName() throws Exception {
		CountryData testSubject;
		String countryName = "test";

		// default test
		testSubject = createTestSubject();
		testSubject.setCountryName(countryName);

		Assert.assertEquals(countryName, testSubject.getCountryName());
	}

	@Test
	public void testGetLocations() throws Exception {
		CountryData testSubject;
		List<LocationData> result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getLocations();

		Assert.assertTrue(result.size() == 0);
	}

	@Test
	public void testSetLocationsNull() throws Exception {
		CountryData testSubject;
		List<LocationData> locations = null;

		// default test
		testSubject = createTestSubject();
		testSubject.setLocations(locations);

		Assert.assertNotNull(testSubject.getLocations());
	}
	
	@Test
	public void testSetLocationsEmpty() throws Exception {
		CountryData testSubject;
		List<LocationData> locations = new ArrayList<>();

		// default test
		testSubject = createTestSubject();
		testSubject.setLocations(locations);

		Assert.assertNotNull(testSubject.getLocations());
	}
	
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

	@Test
	public void testHashCode() throws Exception {
		CountryData testSubject;
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.hashCode();

		Assert.assertNotNull(result);
	}

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