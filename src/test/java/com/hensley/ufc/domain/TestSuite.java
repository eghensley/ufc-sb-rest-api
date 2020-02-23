package com.hensley.ufc.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(

{ CountryDataTest.class, LocationDataTest.class, BaseAuditEntityTest.class, FightDataTest.class })
public class TestSuite { // nothing
}
