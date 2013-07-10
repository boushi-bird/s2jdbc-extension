package jp.s2jdbc.extension.types;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
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
public class DateTimeTimestampTypeTest {
	@EasyMock(value = EasyMockType.DEFAULT)
	private ResultSet resultSet;
	@EasyMock(value = EasyMockType.DEFAULT)
	private CallableStatement callableStatement;
	@EasyMock(value = EasyMockType.DEFAULT)
	private PreparedStatement preparedStatement;

	private final DateTimeTimestampType dateTimeTimestampType = new DateTimeTimestampType();

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
				dateTimeTimestampType.getValue(resultSet, 0));
		assertGetValue(new DateTime(0L),
				dateTimeTimestampType.getValue(resultSet, "test"));
		for (DateTime expectDateTime : expectDateTimes) {
			assertGetValue(expectDateTime,
					dateTimeTimestampType.getValue(resultSet, 0));
			assertGetValue(expectDateTime,
					dateTimeTimestampType.getValue(resultSet, "test"));
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
				dateTimeTimestampType.getValue(callableStatement, 0));
		assertGetValue(new DateTime(0L),
				dateTimeTimestampType.getValue(callableStatement, "test"));
		for (DateTime expectDateTime : expectDateTimes) {
			assertGetValue(expectDateTime,
					dateTimeTimestampType.getValue(callableStatement, 0));
			assertGetValue(expectDateTime,
					dateTimeTimestampType.getValue(callableStatement, "test"));
		}
	}

	public void recordBindValuePreparedStatement() throws Exception {
		preparedStatement.setTimestamp(0, new Timestamp(0L));
		expectLastCall();
		for (DateTime expectDateTime : expectDateTimes) {
			preparedStatement.setTimestamp(0,
					new Timestamp(expectDateTime.getMillis()));
			expectLastCall();
		}
	}

	@Test
	public void bindValuePreparedStatement() throws Exception {
		dateTimeTimestampType.bindValue(preparedStatement, 0, new DateTime(0L));
		for (DateTime expectDateTime : expectDateTimes) {
			dateTimeTimestampType.bindValue(preparedStatement, 0,
					expectDateTime);
		}
	}

	public void recordBindValueCallableStatement() throws Exception {
		callableStatement.setTimestamp("test", new Timestamp(0L));
		expectLastCall();
		for (DateTime expectDateTime : expectDateTimes) {
			callableStatement.setTimestamp("test",
					new Timestamp(expectDateTime.getMillis()));
			expectLastCall();
		}
	}

	@Test
	public void bindValueCallableStatement() throws Exception {
		dateTimeTimestampType.bindValue(callableStatement, "test",
				new DateTime(0L));
		for (DateTime expectDateTime : expectDateTimes) {
			dateTimeTimestampType.bindValue(callableStatement, "test",
					expectDateTime);
		}
	}

	private void assertGetValue(DateTime expected, Object actual) {
		assertThat(actual, instanceOf(DateTime.class));
		assertThat(((DateTime) actual).getMillis(), is(expected.getMillis()));
		// 同じタイムゾーンで比較
		DateTimeZone dateTimeZone = DateTimeZone.getDefault();
		assertThat(((DateTime) actual).toDateTime(dateTimeZone),
				is(expected.toDateTime(dateTimeZone)));
	}
}
