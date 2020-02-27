package com.hensley.ufc.service;

import java.util.Optional;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

import com.hensley.ufc.domain.CountryData;
import com.hensley.ufc.domain.LocationData;
import com.hensley.ufc.repository.mock.CountryRepositoryMock;
import com.hensley.ufc.repository.mock.LocationRepositoryMock;

@Generated(value = "org.junit-tools-1.1.0")
public class LocationServiceTest {

	private static final String TEST_CITY_NAME = "TEST CITY";
	private static final String TEST_COUNTRY_NAME = "TEST COUNTRY";

	private static final String TEST_MISSING_CITY_NAME = "TEST MISSING CITY";
	private static final String TEST_MISSING_COUNTRY_NAME = "TEST MISSING COUNTRY";

	private LocationData initLocationData() {
		LocationData location = new LocationData();
		location.setCityName(TEST_CITY_NAME);

		CountryData country = new CountryData();
		country.setCountryName(TEST_COUNTRY_NAME);

		location.setCountry(country);
		return location;
	}

	private CountryData initCountryData() {
		CountryData country = new CountryData();
		country.setCountryName(TEST_COUNTRY_NAME);
		return country;
	}
	
	private LocationRepositoryMock setupLocationRepo() {
		LocationRepositoryMock locationRepo = new LocationRepositoryMock();
		locationRepo.setGetLocationByCityNameAndCountryNameResult(Optional.of(initLocationData()));
		return locationRepo;
	}

	private CountryRepositoryMock setupCountryRepo() {
		CountryRepositoryMock countryRepo = new CountryRepositoryMock();
		countryRepo.setFindByCountryNameResult(Optional.of(initCountryData()));
		return countryRepo;
	}

	private LocationService createTestSubject() {
		return new LocationService(setupLocationRepo(), setupCountryRepo());
	}

	@Test
	public void testMatchLocationToModelArrayLenTwo() throws Exception {
		LocationService testSubject;
		String[] locArray = new String[] { TEST_CITY_NAME, TEST_COUNTRY_NAME };
		LocationData result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.matchLocationToModel(locArray);

		Assert.assertEquals(initLocationData(), result);
	}

	@Test
	public void testMatchLocationToModelArrayLenThree() throws Exception {
		LocationService testSubject;
		String[] locArray = new String[] { TEST_CITY_NAME, TEST_CITY_NAME, TEST_COUNTRY_NAME };
		LocationData result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.matchLocationToModel(locArray);

		Assert.assertEquals(initLocationData(), result);
	}

	@Test
	public void testMatchLocationToModelMissingLocation() throws Exception {
		LocationService testSubject;
		String[] locArray = new String[] { TEST_MISSING_CITY_NAME, TEST_COUNTRY_NAME };
		LocationData result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.matchLocationToModel(locArray);

		Assert.assertNotEquals(initLocationData(), result);
	}

	@Test
	public void testCreateMissingLocation() throws Exception {
		LocationService testSubject;
		String stateName = TEST_MISSING_CITY_NAME;
		String cityName = TEST_MISSING_CITY_NAME;
		String countryName = TEST_MISSING_COUNTRY_NAME;
		LocationData result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.createMissingLocation(stateName, cityName, countryName);
		Assert.assertEquals(stateName, result.getStateName());
		Assert.assertEquals(cityName, result.getCityName());
		Assert.assertEquals(countryName, result.getCountry().getCountryName());
		Assert.assertNotNull(result.getOid());
	}

	@Test
	public void testCreateCountryIfMissing() throws Exception {
		LocationService testSubject;
		String countryName = TEST_COUNTRY_NAME;
		CountryData result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.createCountryIfMissing(countryName);
		Assert.assertEquals(initCountryData(), result);
	}
	
	@Test
	public void testCreateCountryIfMissingIsMissing() throws Exception {
		LocationService testSubject;
		String countryName = TEST_MISSING_COUNTRY_NAME;
		CountryData result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.createCountryIfMissing(countryName);
		Assert.assertNotEquals(initCountryData(), result);
		Assert.assertNotNull(result.getOid());
		Assert.assertNotNull(result.getCreateUser());
		Assert.assertNotNull(result.getCreateTs());
		Assert.assertNotNull(result.getVersion());
	}
}