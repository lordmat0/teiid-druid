package com.druid.connector_druidtest;

import static org.junit.Assert.*;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.druid.connector_druid.DruidConnectionImpl;
import com.druid.connector_druid.DruidManagedConnectionFactory;
import com.druid.translator_druid.DruidConnection;
import com.yahoo.sql4d.sql4ddriver.DDataSource;
import com.yahoo.sql4d.sql4ddriver.NamedParameters;

public class TestClassConnector {
	
	private DruidConnection druidConnection;

	public TestClassConnector() {
	}

	@Before
	public void setUp() throws Exception {
		druidConnection = new DruidConnectionImpl(new DruidManagedConnectionFactory());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQuery() {
		String sql = "SELECT * FROM wikipedia WHERE interval BETWEEN :startT AND :endT";
		NamedParameters params = new NamedParameters();
		params.add("startT", new DateTime(2014, 9, 1, 0, 0));
		params.add("endT", new DateTime());
		DDataSource source = druidConnection.getConnection();
		source.setNamedParams(params);
		String queryResult = source.query(sql).left().get();
		assertEquals(queryResult, "test");
	}

}
