package com.hensley.ufc.pojo.dto;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

import com.hensley.ufc.pojo.dto.location.CountryDto;

@Generated(value = "org.junit-tools-1.1.0")
public class CountryDtoTest {

	private CountryDto createTestSubject() {
		return new CountryDto();
	}

	@Test
	public void testGetCountryName() throws Exception {
		CountryDto testSubject;
		String countryName = "test country";

		// default test
		testSubject = createTestSubject();
		testSubject.setCountryName(countryName);
		Assert.assertEquals(countryName, testSubject.getCountryName());
	}

	@Test
	public void testSetCountryName() throws Exception {
		CountryDto testSubject;
		String countryName = "test country";

		// default test
		testSubject = createTestSubject();
		testSubject.setCountryName(countryName);
		Assert.assertEquals(countryName, testSubject.getCountryName());
	}
}