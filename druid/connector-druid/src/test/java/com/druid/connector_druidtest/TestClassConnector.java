package com.druid.connector_druidtest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import scala.Either;

import com.druid.connector_druid.DruidConnectionImpl;
import com.druid.connector_druid.DruidManagedConnectionFactory;
import com.druid.translator_druid.DruidConnection;
import com.yahoo.sql4d.sql4ddriver.DDataSource;
import com.yahoo.sql4d.sql4ddriver.NamedParameters;
import com.yahoo.sql4d.sql4ddriver.PrettyPrint;
import com.yahoo.sql4d.sql4ddriver.rowmapper.TimeSeriesBean;

public class TestClassConnector {

	private DruidConnection druidConnection;
	
    String timeseriesContentAggHourly = "SELECT timestamp , LONG_SUM(content_views) AS content_views, LONG_SUM(shares) AS shares FROM AggTable WHERE interval BETWEEN  2014-05-20T00:00:00.000-04:00 AND 2014-05-31T23:00:00.000-04:00 AND provider_id='superprovider' AND content_type='cavideo' BREAK BY PERIOD('P1D', 'EST5EDT')  GROUP BY timestamp, content_views, shares HINT('timeseries')";
    String timeseriesUniqCount = "SELECT timestamp , LONG_SUM(all_content_seen) AS content_seen FROM UniqueCountTable WHERE interval BETWEEN  2014-05-20T00:00:00.000-04:00 AND 2014-05-31T23:00:00.000-04:00 AND provider_id='superprovider' AND content_type='cavideo' BREAK BY PERIOD('P1D', 'EST5EDT') GROUP BY timestamp,content_seen HINT('timeseries')";
    String tsJoin = String.format("%s JOIN (%s) ON (timestamp)", timeseriesContentAggHourly, timeseriesUniqCount);

	

	public TestClassConnector() {
	}

	@Before
	public void setUp() throws Exception {
		druidConnection = new DruidConnectionImpl(
				new DruidManagedConnectionFactory());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTables() {
		DDataSource source = druidConnection.getConnection();

		String tableInfo = source.aboutDataSource("wikipedia").left().get();

		System.out.println(tableInfo);
	}

	@Test
	public void testQuery() {
		String sql = "SELECT page FROM wikipedia WHERE interval BETWEEN :startT AND :endT limit 5 ";
		NamedParameters params = new NamedParameters();
		params.add("startT", new DateTime(2014, 9, 1, 0, 0));
		params.add("endT", new DateTime());
		DDataSource source = druidConnection.getConnection();
		source.setNamedParams(params);
		String queryResult = source.query(sql, false, "sql").left().get();
		assertEquals(queryResult, "test");
	}
	
	
	@Test
	public void testComplexQuery() throws Exception{
		DDataSource source = druidConnection.getConnection();

		
        Either<String, Either<List<TimeSeriesBean>, Map<Object, TimeSeriesBean>>> mapperRes = source.query(tsJoin, TimeSeriesBean.class, false);
        if (mapperRes.isLeft()) {
            throw new Exception(mapperRes.left().get());
        }
        Either<List<TimeSeriesBean>, Map<Object, TimeSeriesBean>> goodResult = mapperRes.right().get();
        if (goodResult.isLeft()) {
            PrettyPrint.print(goodResult.left().get());
        } else {
            PrettyPrint.print(new ArrayList<>(goodResult.right().get().values()));
        }
	}

}
