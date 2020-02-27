package com.hensley.ufc.pojo.response;

import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Generated(value = "org.junit-tools-1.1.0")
public class UrlParseRequestTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(UrlParseRequestTest.class.toString());

	private UrlParseRequest createTestSubject() {
		return new UrlParseRequest();
	}

	@Test
	public void testUrlParseRequest() throws Exception {
		UrlParseRequest testSubject;
		HtmlPage page = null;
		String errorStr = "test error";
		Boolean success = true;

		// default test
		testSubject = new UrlParseRequest(page, errorStr, success);
		Assert.assertEquals(page, testSubject.getPage());
		Assert.assertEquals(errorStr, testSubject.getErrorStr());
		Assert.assertEquals(success, testSubject.getSuccess());
	}

	@Test
	public void testGetPage() throws Exception {
		UrlParseRequest testSubject;
		HtmlPage result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getPage();
		Assert.assertNull(result);
	}

	@Test
	public void testGetErrorStr() throws Exception {
		UrlParseRequest testSubject;
		String errorStr = "test error";

		// default test
		testSubject = createTestSubject();
		testSubject.setErrorStr(errorStr);
		Assert.assertEquals(errorStr, testSubject.getErrorStr());
	}

	@Test
	public void testSetErrorStr() throws Exception {
		UrlParseRequest testSubject;
		String errorStr = "test error";

		// default test
		testSubject = createTestSubject();
		testSubject.setErrorStr(errorStr);
		Assert.assertEquals(errorStr, testSubject.getErrorStr());
	}

	@Test
	public void testGetSuccess() throws Exception {
		UrlParseRequest testSubject;
		Boolean success = true;

		// default test
		testSubject = createTestSubject();
		testSubject.setSuccess(success);
		Assert.assertTrue(testSubject.getSuccess());
	}

	@Test
	public void testSetSuccess() throws Exception {
		UrlParseRequest testSubject;
		Boolean success = true;

		// default test
		testSubject = createTestSubject();
		testSubject.setSuccess(success);
		Assert.assertTrue(testSubject.getSuccess());
	}
}