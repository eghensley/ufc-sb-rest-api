package com.hensley.ufc.domain;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

import org.junit.tools.configuration.base.MethodRef;

@Generated(value = "org.junit-tools-1.1.0")
public class BaseAuditEntityTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(BaseAuditEntityTest.class.toString());

	private BaseAuditEntity createTestSubject() {
		return new BaseAuditEntity();
	}

	@MethodRef(name = "getOid", signature = "()QString;")
	@Test
	public void testGetOid() throws Exception {
		BaseAuditEntity testSubject;
		String oid = "123";

		// default test
		testSubject = createTestSubject();
		testSubject.setOid(oid);

		Assert.assertEquals(oid, testSubject.getOid());
	}

	@MethodRef(name = "setOid", signature = "(QString;)V")
	@Test
	public void testSetOid() throws Exception {
		BaseAuditEntity testSubject;
		String oid = "123";

		// default test
		testSubject = createTestSubject();
		testSubject.setOid(oid);

		Assert.assertEquals(oid, testSubject.getOid());
	}

	@MethodRef(name = "getVersion", signature = "()QInteger;")
	@Test
	public void testGetVersion() throws Exception {
		BaseAuditEntity testSubject;
		Integer version = 0;

		// default test
		testSubject = createTestSubject();
		testSubject.setVersion(version);

		Assert.assertEquals(version, testSubject.getVersion());
	}

	@MethodRef(name = "setVersion", signature = "(QInteger;)V")
	@Test
	public void testSetVersion() throws Exception {
		BaseAuditEntity testSubject;
		Integer version = 0;

		// default test
		testSubject = createTestSubject();
		testSubject.setVersion(version);

		Assert.assertEquals(version, testSubject.getVersion());
	}

	@MethodRef(name = "getCreateUser", signature = "()QString;")
	@Test
	public void testGetCreateUser() throws Exception {
		BaseAuditEntity testSubject;
		String createUser = "test create";

		// default test
		testSubject = createTestSubject();
		testSubject.setCreateUser(createUser);

		Assert.assertEquals(createUser, testSubject.getCreateUser());
	}

	@MethodRef(name = "setCreateUser", signature = "(QString;)V")
	@Test
	public void testSetCreateUser() throws Exception {
		BaseAuditEntity testSubject;
		String createUser = "test create";

		// default test
		testSubject = createTestSubject();
		testSubject.setCreateUser(createUser);

		Assert.assertEquals(createUser, testSubject.getCreateUser());
	}

	@MethodRef(name = "getCreateTs", signature = "()QDate;")
	@Test
	public void testGetCreateTs() throws Exception {
		BaseAuditEntity testSubject;
		Date createTs = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setCreateTs(createTs);

		Assert.assertEquals(createTs, testSubject.getCreateTs());
	}

	@MethodRef(name = "setCreateTs", signature = "(QDate;)V")
	@Test
	public void testSetCreateTs() throws Exception {
		BaseAuditEntity testSubject;
		Date createTs = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setCreateTs(createTs);

		Assert.assertEquals(createTs, testSubject.getCreateTs());
	}

	@MethodRef(name = "getUpdateUser", signature = "()QString;")
	@Test
	public void testGetUpdateUser() throws Exception {
		BaseAuditEntity testSubject;
		String updateUser = "update user";

		// default test
		testSubject = createTestSubject();
		testSubject.setUpdateUser(updateUser);

		Assert.assertEquals(updateUser, testSubject.getUpdateUser());
	}

	@MethodRef(name = "setUpdateUser", signature = "(QString;)V")
	@Test
	public void testSetUpdateUser() throws Exception {
		BaseAuditEntity testSubject;
		String updateUser = "update user";

		// default test
		testSubject = createTestSubject();
		testSubject.setUpdateUser(updateUser);

		Assert.assertEquals(updateUser, testSubject.getUpdateUser());
	}

	@MethodRef(name = "getUpdateTs", signature = "()QDate;")
	@Test
	public void testGetUpdateTs() throws Exception {
		BaseAuditEntity testSubject;
		Date updateTs = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setUpdateTs(updateTs);

		Assert.assertEquals(updateTs, testSubject.getUpdateTs());
	}

	@MethodRef(name = "setUpdateTs", signature = "(QDate;)V")
	@Test
	public void testSetUpdateTs() throws Exception {
		BaseAuditEntity testSubject;
		Date updateTs = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setUpdateTs(updateTs);

		Assert.assertEquals(updateTs, testSubject.getUpdateTs());
	}

	@MethodRef(name = "hashCode", signature = "()I")
	@Test
	public void testHashCode() throws Exception {
		BaseAuditEntity testSubject;
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.hashCode();

		Assert.assertNotNull(result);
	}

	@MethodRef(name = "equals", signature = "(QObject;)Z")
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