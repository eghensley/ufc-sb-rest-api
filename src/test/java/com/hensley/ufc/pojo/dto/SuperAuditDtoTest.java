package com.hensley.ufc.pojo.dto;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.1.0")
public class SuperAuditDtoTest {

	private SuperAuditDto createTestSubject() {
		return new SuperAuditDto();
	}

	@Test
	public void testGetId() throws Exception {
		SuperAuditDto testSubject;
		String id = "123";

		// default test
		testSubject = createTestSubject();
		testSubject.setOid(id);
		Assert.assertEquals(id, testSubject.getOid());
	}

	@Test
	public void testSetId() throws Exception {
		SuperAuditDto testSubject;
		String id = "123";

		// default test
		testSubject = createTestSubject();
		testSubject.setOid(id);
		Assert.assertEquals(id, testSubject.getOid());
	}
}