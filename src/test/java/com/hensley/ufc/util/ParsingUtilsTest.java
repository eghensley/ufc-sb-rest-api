package com.hensley.ufc.util;

import java.util.Date;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.1.0")
public class ParsingUtilsTest {

	private ParsingUtils createTestSubject() {
		return new ParsingUtils();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testStringToDate() throws Exception {
		ParsingUtils testSubject;
		String dateInString = "January 1, 2019";
		Date result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.stringToDate(dateInString);
		Assert.assertEquals(new Date(119, 0, 1), result);
	}
	
//	@Test
//	public void testStringToDateError() throws Exception {
//		ParsingUtils testSubject;
//		String dateInString = "1/1/2019";
//		Date result;
//
//		// default test
//		testSubject = createTestSubject();
//		result = testSubject.stringToDate(dateInString);
//		Assert.assertNull(result);
//	}
}