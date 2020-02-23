package com.hensley.ufc.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.junit.tools.configuration.base.MethodRef;

@Generated(value = "org.junit-tools-1.1.0")
public class FightDataTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(FightDataTest.class.toString());

	private FightData createTestSubject() {
		return new FightData();
	}

	@MethodRef(name = "FightData", signature = "()QString;")
	@Test
	public void testFightData() throws Exception {
		FightData testSubject;
		String fightId = "123";
		String fightName = "test fight";
		Date fightDate = new Date();
		LocationData location = new LocationData();
		location.setStateName("test state");

		// default test
		testSubject = new FightData(fightId, fightName, fightDate, location);
		Assert.assertEquals(fightId, testSubject.getFightId());
		Assert.assertEquals(fightName, testSubject.getFightName());
		Assert.assertEquals(fightDate, testSubject.getFightDate());
		Assert.assertEquals(location, testSubject.getLocation());
		Assert.assertFalse(testSubject.getCompleted());
		Assert.assertTrue(testSubject.getBouts().size()==0);

	}
	
	@MethodRef(name = "getFightId", signature = "()QString;")
	@Test
	public void testGetFightId() throws Exception {
		FightData testSubject;
		String fightId = "123";

		// default test
		testSubject = createTestSubject();
		testSubject.setFightId(fightId);

		Assert.assertEquals(fightId, testSubject.getFightId());
	}

	@MethodRef(name = "setFightId", signature = "(QString;)V")
	@Test
	public void testSetFightId() throws Exception {
		FightData testSubject;
		String fightId = "123";

		// default test
		testSubject = createTestSubject();
		testSubject.setFightId(fightId);

		Assert.assertEquals(fightId, testSubject.getFightId());
	}

	@MethodRef(name = "getLocation", signature = "()QLocationData;")
	@Test
	public void testGetLocation() throws Exception {
		FightData testSubject;
		LocationData location = new LocationData();

		// default test
		testSubject = createTestSubject();
		testSubject.setLocation(location);

		Assert.assertEquals(location, testSubject.getLocation());
	}

	@MethodRef(name = "setLocation", signature = "(QLocationData;)V")
	@Test
	public void testSetLocation() throws Exception {
		FightData testSubject;
		LocationData location = new LocationData();

		// default test
		testSubject = createTestSubject();
		testSubject.setLocation(location);

		Assert.assertEquals(location, testSubject.getLocation());
	}

	@MethodRef(name = "getFightName", signature = "()QString;")
	@Test
	public void testGetFightName() throws Exception {
		FightData testSubject;
		String fightName = "test";

		// default test
		testSubject = createTestSubject();
		testSubject.setFightName(fightName);

		Assert.assertEquals(fightName, testSubject.getFightName());
	}

	@MethodRef(name = "setFightName", signature = "(QString;)V")
	@Test
	public void testSetFightName() throws Exception {
		FightData testSubject;
		String fightName = "test";

		// default test
		testSubject = createTestSubject();
		testSubject.setFightName(fightName);

		Assert.assertEquals(fightName, testSubject.getFightName());
	}

	@MethodRef(name = "getFightDate", signature = "()QDate;")
	@Test
	public void testGetFightDate() throws Exception {
		FightData testSubject;
		Date fightDate = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setFightDate(fightDate);

		Assert.assertEquals(fightDate, testSubject.getFightDate());
	}

	@MethodRef(name = "setFightDate", signature = "(QDate;)V")
	@Test
	public void testSetFightDate() throws Exception {
		FightData testSubject;
		Date fightDate = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setFightDate(fightDate);

		Assert.assertEquals(fightDate, testSubject.getFightDate());
	}

	@MethodRef(name = "getBouts", signature = "()QList<QBoutData;>;")
	@Test
	public void testGetBouts() throws Exception {
		FightData testSubject;
		List<BoutData> result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getBouts();

		Assert.assertTrue(result.size() == 0);
	}

	@MethodRef(name = "setBouts", signature = "(QList<QBoutData;>;)V")
	@Test
	public void testSetBoutsNull() throws Exception {
		FightData testSubject;
		List<BoutData> bouts = null;

		// default test
		testSubject = createTestSubject();
		testSubject.setBouts(bouts);

		Assert.assertTrue(testSubject.getBouts().size() == 0);
	}
	
	@MethodRef(name = "setBouts", signature = "(QList<QBoutData;>;)V")
	@Test
	public void testSetBoutsEmpty() throws Exception {
		FightData testSubject;
		List<BoutData> bouts = new ArrayList<>();

		// default test
		testSubject = createTestSubject();
		testSubject.setBouts(bouts);

		Assert.assertTrue(testSubject.getBouts().size() == 0);
	}
	
	@MethodRef(name = "setBouts", signature = "(QList<QBoutData;>;)V")
	@Test
	public void testSetBouts() throws Exception {
		FightData testSubject;
		List<BoutData> bouts = new ArrayList<>();
		BoutData bout = new BoutData();
		bouts.add(bout);
		
		// default test
		testSubject = createTestSubject();
		testSubject.setBouts(bouts);

		Assert.assertTrue(testSubject.getBouts().size() == 1);
	}

	@MethodRef(name = "addBout", signature = "(QBoutData;)V")
	@Test
	public void testAddBout() throws Exception {
		FightData testSubject;
		BoutData bout = new BoutData();

		// default test
		testSubject = createTestSubject();
		testSubject.addBout(bout);

		Assert.assertTrue(testSubject.getBouts().size() == 1);
//		Assert.assertTrue(testSubject.getBouts().get(0).getFight().equals(testSubject));
	}

	@MethodRef(name = "getCompleted", signature = "()QBoolean;")
	@Test
	public void testGetCompleted() throws Exception {
		FightData testSubject;
		Boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getCompleted();

		Assert.assertFalse(result);
	}

	@MethodRef(name = "setCompleted", signature = "(QBoolean;)V")
	@Test
	public void testSetCompleted() throws Exception {
		FightData testSubject;
		Boolean completed = true;

		// default test
		testSubject = createTestSubject();
		testSubject.setCompleted(completed);

		Assert.assertEquals(completed, testSubject.getCompleted());
	}

	@MethodRef(name = "hashCode", signature = "()I")
	@Test
	public void testHashCode() throws Exception {
		FightData testSubject;
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.hashCode();

		Assert.assertNotNull(result);
	}

	@MethodRef(name = "equals", signature = "(QObject;)Z")
	@Test
	public void testEquals() throws Exception {
		FightData testSubject;
		Object obj = createTestSubject();
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.equals(obj);

		Assert.assertTrue(result);
	}
	
	@MethodRef(name = "equals", signature = "(QObject;)Z")
	@Test
	public void testNotEquals() throws Exception {
		FightData testSubject;
		FightData obj = createTestSubject();
		obj.setCompleted(true);
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.equals(obj);

		Assert.assertFalse(result);
	}

	@MethodRef(name = "toString", signature = "()QString;")
	@Test
	public void testToString() throws Exception {
		FightData testSubject;
		String result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.toString();

		Assert.assertNotNull(result);
	}
}