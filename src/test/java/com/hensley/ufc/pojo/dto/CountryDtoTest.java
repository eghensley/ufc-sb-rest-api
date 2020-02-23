package com.hensley.ufc.pojo.dto;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.junit.tools.configuration.base.MethodRef;

@Generated(value = "org.junit-tools-1.1.0")
public class CountryDtoTest {

	private CountryDto createTestSubject() {
		return new CountryDto();
	}

	@MethodRef(name = "getCountryName", signature = "()QString;")
	@Test
	public void testGetCountryName() throws Exception {
		CountryDto testSubject;
		String countryName = "test country";

		// default test
		testSubject = createTestSubject();
		testSubject.setCountryName(countryName);
		Assert.assertEquals(countryName, testSubject.getCountryName());
	}

	@MethodRef(name = "setCountryName", signature = "(QString;)V")
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