package jp.s2jdbc.extension.types;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.EasyMock;
import org.seasar.framework.unit.annotation.EasyMockType;

@RunWith(Seasar2.class)
public class DateTimeTimeTypeTest {
	@EasyMock(value = EasyMockType.DEFAULT)
	private ResultSet resultSet;
	@EasyMock(value = EasyMockType.DEFAULT)
	private CallableStatement callableStatement;

	private DateTimeTimeType dateTimeTimeType = new DateTimeTimeType();

	private DateTime[] expectDateTimes = new DateTime[] {
			// new DateTime(0),
			// 2010/01/01 UTC
			new DateTime(2010, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC),
			// 2020/01/01 UTC
			new DateTime(2020, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC),
			// 2010/01/01 JST
			new DateTime(2010, 1, 1, 0, 0, 0, 0,
					DateTimeZone.forID("Asia/Tokyo")),
			// 2020/01/01 JST
			new DateTime(2020, 1, 1, 0, 0, 0, 0,
					DateTimeZone.forID("Asia/Tokyo")),
			// 2007/07/07 07:07:07.777 UTC
			new DateTime(2007, 7, 7, 7, 7, 7, 777, DateTimeZone.UTC),
			// 2007/07/07 07:07:07.777 JST
			new DateTime(2007, 7, 7, 7, 7, 7, 777,
					DateTimeZone.forID("Asia/Tokyo")),
	//
	};

	public void recordGetValueResultSet() throws Exception {
		expect(resultSet.getTimestamp(0)).andReturn(new Timestamp(0));
		expect(resultSet.getTimestamp("test")).andReturn(new Timestamp(0));
		for (DateTime expectDateTime : expectDateTimes) {
			expect(resultSet.getTimestamp(0)).andReturn(
					new Timestamp(expectDateTime.getMillis()));
			expect(resultSet.getTimestamp("test")).andReturn(
					new Timestamp(expectDateTime.getMillis()));
		}
	}

	@Test
	public void getValueResultSet() throws Exception {
		assertGetValue(new DateTime(0L),
				dateTimeTimeType.getValue(resultSet, 0));
		assertGetValue(new DateTime(0L),
				dateTimeTimeType.getValue(resultSet, "test"));
		for (DateTime expectDateTime : expectDateTimes) {
			assertGetValue(expectDateTime,
					dateTimeTimeType.getValue(resultSet, 0));
			assertGetValue(expectDateTime,
					dateTimeTimeType.getValue(resultSet, "test"));
		}
	}

	public void recordGetValueCallableStatement() throws Exception {
		expect(callableStatement.getTimestamp(0)).andReturn(new Timestamp(0));
		expect(callableStatement.getTimestamp("test")).andReturn(
				new Timestamp(0));
		for (DateTime expectDateTime : expectDateTimes) {
			expect(callableStatement.getTimestamp(0)).andReturn(
					new Timestamp(expectDateTime.getMillis()));
			expect(callableStatement.getTimestamp("test")).andReturn(
					new Timestamp(expectDateTime.getMillis()));
		}
	}

	@Test
	public void getValueCallableStatement() throws Exception {
		assertGetValue(new DateTime(0L),
				dateTimeTimeType.getValue(callableStatement, 0));
		assertGetValue(new DateTime(0L),
				dateTimeTimeType.getValue(callableStatement, "test"));
		for (DateTime expectDateTime : expectDateTimes) {
			assertGetValue(expectDateTime,
					dateTimeTimeType.getValue(callableStatement, 0));
			assertGetValue(expectDateTime,
					dateTimeTimeType.getValue(callableStatement, "test"));
		}
	}

	private void assertGetValue(DateTime expected, Object actual) {
		assertThat(actual, instanceOf(DateTime.class));
		assertThat(((DateTime) actual).toDateTime(DateTimeZone.UTC),
				is(expected.toDateTime(DateTimeZone.UTC)));
	}

}
