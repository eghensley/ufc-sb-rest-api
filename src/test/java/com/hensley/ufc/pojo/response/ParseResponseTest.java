package com.hensley.ufc.pojo.response;

import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.hensley.ufc.pojo.request.ParseRequest;

@Generated(value = "org.junit-tools-1.1.0")
public class ParseResponseTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(ParseResponseTest.class.toString());

	private ParseResponse createTestSubject() {
		return new ParseResponse();
	}

	@Test
	public void testParseResponse() throws Exception {
		ParseResponse testSubject;
		ParseRequest request = new ParseRequest();
		Integer itemsFound = 1;
		Integer itemsCompleted = 0;
		HttpStatus status = HttpStatus.ACCEPTED;
		String errorMsg = "test error";
		
		// default test
		testSubject = new ParseResponse(request, itemsFound, itemsCompleted, status, errorMsg);
		
		Assert.assertEquals(request, testSubject.getRequest());
		Assert.assertEquals(itemsFound, testSubject.getItemsFound());
		Assert.assertEquals(itemsCompleted, testSubject.getItemsCompleted());
		Assert.assertEquals(status, testSubject.getStatus());
		Assert.assertEquals(errorMsg, testSubject.getErrorMsg());
		Assert.assertNotNull(testSubject.getTimestamp());

	}
	
	@Test
	public void testGetRequest() throws Exception {
		ParseResponse testSubject;
		ParseRequest request = new ParseRequest();

		// default test
		testSubject = createTestSubject();
		testSubject.setRequest(request);

		Assert.assertEquals(request, testSubject.getRequest());
	}

	@Test
	public void testSetRequest() throws Exception {
		ParseResponse testSubject;
		ParseRequest request = new ParseRequest();

		// default test
		testSubject = createTestSubject();
		testSubject.setRequest(request);

		Assert.assertEquals(request, testSubject.getRequest());
	}

	@Test
	public void testGetItemsFound() throws Exception {
		ParseResponse testSubject;
		Integer itemsFound = 0;

		// default test
		testSubject = createTestSubject();
		testSubject.setItemsFound(itemsFound);

		Assert.assertEquals(itemsFound, testSubject.getItemsFound());
	}

	@Test
	public void testSetItemsFound() throws Exception {
		ParseResponse testSubject;
		Integer itemsFound = 0;

		// default test
		testSubject = createTestSubject();
		testSubject.setItemsFound(itemsFound);

		Assert.assertEquals(itemsFound, testSubject.getItemsFound());
	}

	@Test
	public void testGetItemsCompleted() throws Exception {
		ParseResponse testSubject;
		Integer itemsCompleted = 0;

		// default test
		testSubject = createTestSubject();
		testSubject.setItemsCompleted(itemsCompleted);

		Assert.assertEquals(itemsCompleted, testSubject.getItemsCompleted());
	}

	@Test
	public void testSetItemsCompleted() throws Exception {
		ParseResponse testSubject;
		Integer itemsCompleted = 0;

		// default test
		testSubject = createTestSubject();
		testSubject.setItemsCompleted(itemsCompleted);

		Assert.assertEquals(itemsCompleted, testSubject.getItemsCompleted());
	}
}