package com.hensley.ufc.domain;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.1.0")
public class BaseAuditEntityTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(BaseAuditEntityTest.class.toString());

	private BaseAuditEntity createTestSubject() {
		return new BaseAuditEntity();
	}

	@Test
	public void testGetOid() throws Exception {
		BaseAuditEntity testSubject;
		String oid = "123";

		// default test
		testSubject = createTestSubject();
		testSubject.setOid(oid);

		Assert.assertEquals(oid, testSubject.getOid());
	}

	@Test
	public void testSetOid() throws Exception {
		BaseAuditEntity testSubject;
		String oid = "123";

		// default test
		testSubject = createTestSubject();
		testSubject.setOid(oid);

		Assert.assertEquals(oid, testSubject.getOid());
	}

	@Test
	public void testGetVersion() throws Exception {
		BaseAuditEntity testSubject;
		Integer version = 0;

		// default test
		testSubject = createTestSubject();
		testSubject.setVersion(version);

		Assert.assertEquals(version, testSubject.getVersion());
	}

	@Test
	public void testSetVersion() throws Exception {
		BaseAuditEntity testSubject;
		Integer version = 0;

		// default test
		testSubject = createTestSubject();
		testSubject.setVersion(version);

		Assert.assertEquals(version, testSubject.getVersion());
	}

	@Test
	public void testGetCreateUser() throws Exception {
		BaseAuditEntity testSubject;
		String createUser = "test create";

		// default test
		testSubject = createTestSubject();
		testSubject.setCreateUser(createUser);

		Assert.assertEquals(createUser, testSubject.getCreateUser());
	}

	@Test
	public void testSetCreateUser() throws Exception {
		BaseAuditEntity testSubject;
		String createUser = "test create";

		// default test
		testSubject = createTestSubject();
		testSubject.setCreateUser(createUser);

		Assert.assertEquals(createUser, testSubject.getCreateUser());
	}

	@Test
	public void testGetCreateTs() throws Exception {
		BaseAuditEntity testSubject;
		Date createTs = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setCreateTs(createTs);

		Assert.assertEquals(createTs, testSubject.getCreateTs());
	}

	@Test
	public void testSetCreateTs() throws Exception {
		BaseAuditEntity testSubject;
		Date createTs = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setCreateTs(createTs);

		Assert.assertEquals(createTs, testSubject.getCreateTs());
	}

	@Test
	public void testGetUpdateUser() throws Exception {
		BaseAuditEntity testSubject;
		String updateUser = "update user";

		// default test
		testSubject = createTestSubject();
		testSubject.setUpdateUser(updateUser);

		Assert.assertEquals(updateUser, testSubject.getUpdateUser());
	}

	@Test
	public void testSetUpdateUser() throws Exception {
		BaseAuditEntity testSubject;
		String updateUser = "update user";

		// default test
		testSubject = createTestSubject();
		testSubject.setUpdateUser(updateUser);

		Assert.assertEquals(updateUser, testSubject.getUpdateUser());
	}

	@Test
	public void testGetUpdateTs() throws Exception {
		BaseAuditEntity testSubject;
		Date updateTs = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setUpdateTs(updateTs);

		Assert.assertEquals(updateTs, testSubject.getUpdateTs());
	}

	@Test
	public void testSetUpdateTs() throws Exception {
		BaseAuditEntity testSubject;
		Date updateTs = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setUpdateTs(updateTs);

		Assert.assertEquals(updateTs, testSubject.getUpdateTs());
	}

	@Test
	public void testHashCode() throws Exception {
		BaseAuditEntity testSubject;
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.hashCode();

		Assert.assertNotNull(result);
	}

	@Test
	public void testEquals() throws Exception {
		BaseAuditEntity testSubject;
		Object obj = createTestSubject();
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.equals(obj);
		Assert.assertTrue(result);
	}
}