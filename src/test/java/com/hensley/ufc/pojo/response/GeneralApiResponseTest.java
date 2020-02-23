package com.hensley.ufc.pojo.response;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.junit.tools.configuration.base.MethodRef;
import org.springframework.http.HttpStatus;

@Generated(value = "org.junit-tools-1.1.0")
public class GeneralApiResponseTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(GeneralApiResponseTest.class.toString());

	private GeneralApiResponse createTestSubject() {
		return new GeneralApiResponse();
	}

	@MethodRef(name = "getStatus", signature = "()QHttpStatus;")
	@Test
	public void testGetStatus() throws Exception {
		GeneralApiResponse testSubject;
		HttpStatus status = HttpStatus.ACCEPTED;

		// default test
		testSubject = createTestSubject();
		testSubject.setStatus(status);

		Assert.assertEquals(status, testSubject.status);
	}

	@MethodRef(name = "setStatus", signature = "(QHttpStatus;)V")
	@Test
	public void testSetStatus() throws Exception {
		GeneralApiResponse testSubject;
		HttpStatus status = HttpStatus.ACCEPTED;

		// default test
		testSubject = createTestSubject();
		testSubject.setStatus(status);

		Assert.assertEquals(status, testSubject.status);
	}

	@MethodRef(name = "getTimestamp", signature = "()QDate;")
	@Test
	public void testGetTimestamp() throws Exception {
		GeneralApiResponse testSubject;
		Date timestamp = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setTimestamp(timestamp);

		Assert.assertEquals(timestamp, testSubject.getTimestamp());
	}

	@MethodRef(name = "setTimestamp", signature = "(QDate;)V")
	@Test
	public void testSetTimestamp() throws Exception {
		GeneralApiResponse testSubject;
		Date timestamp = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setTimestamp(timestamp);

		Assert.assertEquals(timestamp, testSubject.getTimestamp());
	}

	@MethodRef(name = "getErrorMsg", signature = "()QString;")
	@Test
	public void testGetErrorMsg() throws Exception {
		GeneralApiResponse testSubject;
		String errorMsg = "test error";

		// default test
		testSubject = createTestSubject();
		testSubject.setErrorMsg(errorMsg);

		Assert.assertEquals(errorMsg, testSubject.getErrorMsg());
	}

	@MethodRef(name = "setErrorMsg", signature = "(QString;)V")
	@Test
	public void testSetErrorMsg() throws Exception {
		GeneralApiResponse testSubject;
		String errorMsg = "test error";

		// default test
		testSubject = createTestSubject();
		testSubject.setErrorMsg(errorMsg);

		Assert.assertEquals(errorMsg, testSubject.getErrorMsg());
	}
}