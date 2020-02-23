package com.hensley.ufc.pojo.response;

import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.junit.tools.configuration.base.MethodRef;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Generated(value = "org.junit-tools-1.1.0")
public class UrlParseRequestTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(UrlParseRequestTest.class.toString());

	private UrlParseRequest createTestSubject() {
		return new UrlParseRequest();
	}

	@MethodRef(name = "UrlParseRequest", signature = "()V")
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

	@MethodRef(name = "getPage", signature = "()QHtmlPage;")
	@Test
	public void testGetPage() throws Exception {
		UrlParseRequest testSubject;
		HtmlPage result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getPage();
		Assert.assertNull(result);
	}

	@MethodRef(name = "getErrorStr", signature = "()QString;")
	@Test
	public void testGetErrorStr() throws Exception {
		UrlParseRequest testSubject;
		String errorStr = "test error";

		// default test
		testSubject = createTestSubject();
		testSubject.setErrorStr(errorStr);
		Assert.assertEquals(errorStr, testSubject.getErrorStr());
	}

	@MethodRef(name = "setErrorStr", signature = "(QString;)V")
	@Test
	public void testSetErrorStr() throws Exception {
		UrlParseRequest testSubject;
		String errorStr = "test error";

		// default test
		testSubject = createTestSubject();
		testSubject.setErrorStr(errorStr);
		Assert.assertEquals(errorStr, testSubject.getErrorStr());
	}

	@MethodRef(name = "getSuccess", signature = "()QBoolean;")
	@Test
	public void testGetSuccess() throws Exception {
		UrlParseRequest testSubject;
		Boolean success = true;

		// default test
		testSubject = createTestSubject();
		testSubject.setSuccess(success);
		Assert.assertTrue(testSubject.getSuccess());
	}

	@MethodRef(name = "setSuccess", signature = "(QBoolean;)V")
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