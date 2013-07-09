package jp.s2jdbc.extension.types;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.EasyMock;
import org.seasar.framework.unit.annotation.EasyMockType;

@RunWith(Seasar2.class)
public class DateTimeSqlDateTypeTest {
	@EasyMock(value = EasyMockType.DEFAULT)
	private ResultSet resultSet;
	@EasyMock(value = EasyMockType.DEFAULT)
	private CallableStatement callableStatement;
	@EasyMock(value = EasyMockType.DEFAULT)
	private PreparedStatement preparedStatement;

	private LocalDateSqlDateType dateTimeSqlDateType = new LocalDateSqlDateType();

	private LocalDate[] expectDates = new LocalDate[] {
			// toSqlDateTime(0),
			// 2010/01/01 UTC
			new LocalDate(2010, 1, 1,
					ISOChronology.getInstance(DateTimeZone.UTC)),
			// 2020/01/01 UTC
			new LocalDate(2020, 1, 1,
					ISOChronology.getInstance(DateTimeZone.UTC)),
			// 2010/01/01 JST
			new LocalDate(2010, 1, 1, ISOChronology.getInstance(DateTimeZone
					.forID("Asia/Tokyo"))),
			// 2020/01/01 JST
			new LocalDate(2020, 1, 1, ISOChronology.getInstance(DateTimeZone
					.forID("Asia/Tokyo"))),
			// 2007/07/07 UTC
			new LocalDate(2007, 7, 7,
					ISOChronology.getInstance(DateTimeZone.UTC)),
			// 2007/07/07 JST
			new LocalDate(2007, 7, 7, ISOChronology.getInstance(DateTimeZone
					.forID("Asia/Tokyo"))),
	//
	};

	public void recordGetValueResultSet() throws Exception {
		expect(resultSet.getDate(0)).andReturn(toSqlDate(0));
		expect(resultSet.getDate("test")).andReturn(toSqlDate(0));
		for (LocalDate expectDate : expectDates) {
			expect(resultSet.getDate(0)).andReturn(
					toSqlDate(expectDate.toDate()));
			expect(resultSet.getDate("test")).andReturn(
					toSqlDate(expectDate.toDate()));
		}
	}

	@Test
	public void getValueResultSet() throws Exception {
		assertGetValue(new LocalDate(0L),
				dateTimeSqlDateType.getValue(resultSet, 0));
		assertGetValue(new LocalDate(0L),
				dateTimeSqlDateType.getValue(resultSet, "test"));
		for (LocalDate expectDate : expectDates) {
			assertGetValue(expectDate,
					dateTimeSqlDateType.getValue(resultSet, 0));
			assertGetValue(expectDate,
					dateTimeSqlDateType.getValue(resultSet, "test"));
		}
	}

	public void recordGetValueCallableStatement() throws Exception {
		expect(callableStatement.getDate(0)).andReturn(toSqlDate(0));
		expect(callableStatement.getDate("test")).andReturn(toSqlDate(0));
		for (LocalDate expectDate : expectDates) {
			expect(callableStatement.getDate(0)).andReturn(
					toSqlDate(expectDate.toDate()));
			expect(callableStatement.getDate("test")).andReturn(
					toSqlDate(expectDate.toDate()));
		}
	}

	@Test
	public void getValueCallableStatement() throws Exception {
		assertGetValue(new LocalDate(0L),
				dateTimeSqlDateType.getValue(callableStatement, 0));
		assertGetValue(new LocalDate(0L),
				dateTimeSqlDateType.getValue(callableStatement, "test"));
		for (LocalDate expectDate : expectDates) {
			assertGetValue(expectDate,
					dateTimeSqlDateType.getValue(callableStatement, 0));
			assertGetValue(expectDate,
					dateTimeSqlDateType.getValue(callableStatement, "test"));
		}
	}

	public void recordBindValuePreparedStatement() throws Exception {
		preparedStatement.setDate(0, toSqlDate(0L));
		expectLastCall();
		for (LocalDate expectDate : expectDates) {
			preparedStatement.setDate(0, toSqlDate(expectDate.toDate()));
			expectLastCall();
		}
	}

	@Test
	public void bindValuePreparedStatement() throws Exception {
		dateTimeSqlDateType.bindValue(preparedStatement, 0, new LocalDate(0L));
		for (LocalDate expectDate : expectDates) {
			dateTimeSqlDateType.bindValue(preparedStatement, 0, expectDate);
		}
	}

	public void recordBindValueCallableStatement() throws Exception {
		callableStatement.setDate("test", toSqlDate(0L));
		expectLastCall();
		for (LocalDate expectDate : expectDates) {
			callableStatement.setDate("test", toSqlDate(expectDate.toDate()));
			expectLastCall();
		}
	}

	@Test
	public void bindValueCallableStatement() throws Exception {
		dateTimeSqlDateType.bindValue(callableStatement, "test", new LocalDate(
				0));
		for (LocalDate expectDate : expectDates) {
			dateTimeSqlDateType
					.bindValue(callableStatement, "test", expectDate);
		}
	}

	private void assertGetValue(LocalDate expected, Object actual) {
		assertThat(actual, instanceOf(LocalDate.class));
		assertThat(((LocalDate) actual), is(expected));
	}

	protected java.sql.Date toSqlDate(long time) {
		return toSqlDate(new java.util.Date(time));
	}

	protected java.sql.Date toSqlDate(java.util.Date date) {
		Calendar base = Calendar.getInstance();
		base.setTime(date);
		base.set(Calendar.HOUR_OF_DAY, 0);
		base.set(Calendar.MINUTE, 0);
		base.set(Calendar.SECOND, 0);
		base.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(base.getTimeInMillis());
	}
}
