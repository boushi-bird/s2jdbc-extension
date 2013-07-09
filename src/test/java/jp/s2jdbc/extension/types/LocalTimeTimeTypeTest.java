package jp.s2jdbc.extension.types;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Calendar;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.joda.time.chrono.ISOChronology;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.EasyMock;
import org.seasar.framework.unit.annotation.EasyMockType;

@RunWith(Seasar2.class)
public class LocalTimeTimeTypeTest {
	@EasyMock(value = EasyMockType.DEFAULT)
	private ResultSet resultSet;
	@EasyMock(value = EasyMockType.DEFAULT)
	private CallableStatement callableStatement;
	@EasyMock(value = EasyMockType.DEFAULT)
	private PreparedStatement preparedStatement;

	private LocalTimeTimeType localTimeTimeType = new LocalTimeTimeType();

	private LocalTime[] expectTimes = new LocalTime[] {
			// new DateTime(0),
			// 01:30:00 UTC
			new LocalTime(1, 30, 0, 0,
					ISOChronology.getInstance(DateTimeZone.UTC)),
			// 12:00:00 UTC
			new LocalTime(12, 0, 0, 0,
					ISOChronology.getInstance(DateTimeZone.UTC)),
			// 01:00:00 JST
			new LocalTime(1, 30, 0, 0, ISOChronology.getInstance(DateTimeZone
					.forID("Asia/Tokyo"))),
			// 12:00:00 JST
			new LocalTime(12, 0, 0, 0, ISOChronology.getInstance(DateTimeZone
					.forID("Asia/Tokyo"))),
			// 07:07:07.777 UTC
			new LocalTime(7, 7, 7, 777,
					ISOChronology.getInstance(DateTimeZone.UTC)),
			// 07:07:07.777 JST
			new LocalTime(7, 7, 7, 777, ISOChronology.getInstance(DateTimeZone
					.forID("Asia/Tokyo"))),
	//
	};

	public void recordGetValueResultSet() throws Exception {
		expect(resultSet.getTime(0)).andReturn(toTime(0));
		expect(resultSet.getTime("test")).andReturn(toTime(0));
		for (LocalTime expectTime : expectTimes) {
			expect(resultSet.getTime(0)).andReturn(toTime(expectTime));
			expect(resultSet.getTime("test")).andReturn(toTime(expectTime));
		}
	}

	@Test
	public void getValueResultSet() throws Exception {
		assertGetValue(new LocalTime(0L),
				localTimeTimeType.getValue(resultSet, 0));
		assertGetValue(new LocalTime(0L),
				localTimeTimeType.getValue(resultSet, "test"));
		for (LocalTime expectTime : expectTimes) {
			assertGetValue(expectTime, localTimeTimeType.getValue(resultSet, 0));
			assertGetValue(expectTime,
					localTimeTimeType.getValue(resultSet, "test"));
		}
	}

	public void recordGetValueCallableStatement() throws Exception {
		expect(callableStatement.getTime(0)).andReturn(toTime(0));
		expect(callableStatement.getTime("test")).andReturn(toTime(0));
		for (LocalTime expectTime : expectTimes) {
			expect(callableStatement.getTime(0)).andReturn(toTime(expectTime));
			expect(callableStatement.getTime("test")).andReturn(
					toTime(expectTime));
		}
	}

	@Test
	public void getValueCallableStatement() throws Exception {
		assertGetValue(new LocalTime(0L),
				localTimeTimeType.getValue(callableStatement, 0));
		assertGetValue(new LocalTime(0L),
				localTimeTimeType.getValue(callableStatement, "test"));
		for (LocalTime expectTime : expectTimes) {
			assertGetValue(expectTime,
					localTimeTimeType.getValue(callableStatement, 0));
			assertGetValue(expectTime,
					localTimeTimeType.getValue(callableStatement, "test"));
		}
	}

	public void recordBindValuePreparedStatement() throws Exception {
		preparedStatement.setTime(0, toTime(0L));
		expectLastCall();
		for (LocalTime expectTime : expectTimes) {
			preparedStatement.setTime(0, toTime(expectTime));
			expectLastCall();
		}
	}

	@Test
	public void bindValuePreparedStatement() throws Exception {
		localTimeTimeType.bindValue(preparedStatement, 0, new LocalTime(0L));
		for (LocalTime expectTime : expectTimes) {
			localTimeTimeType.bindValue(preparedStatement, 0, expectTime);
		}
	}

	public void recordBindValueCallableStatement() throws Exception {
		callableStatement.setTime("test", toTime(0L));
		expectLastCall();
		for (LocalTime expectTime : expectTimes) {
			callableStatement.setTime("test", toTime(expectTime));
			expectLastCall();
		}
	}

	@Test
	public void bindValueCallableStatement() throws Exception {
		localTimeTimeType
				.bindValue(callableStatement, "test", new LocalTime(0));
		for (LocalTime expectTime : expectTimes) {
			localTimeTimeType.bindValue(callableStatement, "test", expectTime);
		}
	}

	private void assertGetValue(LocalTime expected, Object actual) {
		assertThat(actual, instanceOf(LocalTime.class));
		assertThat(((LocalTime) actual), is(expected));
	}

	protected Time toTime(LocalTime localTime) {
		return toTime(localTime.toDateTimeToday().toDate());
	}

	protected Time toTime(long time) {
		return toTime(new java.util.Date(time));
	}

	protected Time toTime(java.util.Date date) {
		Calendar base = Calendar.getInstance();
		base.setTime(date);
		base.set(Calendar.YEAR, 1970);
		base.set(Calendar.MONTH, Calendar.JANUARY);
		base.set(Calendar.DATE, 1);
		return new Time(base.getTimeInMillis());
	}
}
