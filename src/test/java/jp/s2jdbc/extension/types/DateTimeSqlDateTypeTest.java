package jp.s2jdbc.extension.types;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.joda.time.DateTime;
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
			// new DateTime(0),
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
		expect(resultSet.getDate(0)).andReturn(new Date(0));
		expect(resultSet.getDate("test")).andReturn(new Date(0));
		for (LocalDate expectDate : expectDates) {
			expect(resultSet.getDate(0)).andReturn(
					new Date(expectDate.toDate().getTime()));
			expect(resultSet.getDate("test")).andReturn(
					new Date(expectDate.toDate().getTime()));
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
		expect(callableStatement.getDate(0)).andReturn(new Date(0));
		expect(callableStatement.getDate("test")).andReturn(new Date(0));
		for (LocalDate expectDate : expectDates) {
			expect(callableStatement.getDate(0)).andReturn(
					new Date(expectDate.toDate().getTime()));
			expect(callableStatement.getDate("test")).andReturn(
					new Date(expectDate.toDate().getTime()));
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
		preparedStatement.setDate(0, new Date(0L));
		expectLastCall();
		for (LocalDate expectDate : expectDates) {
			preparedStatement.setDate(0,
					new Date(expectDate.toDate().getTime()));
			expectLastCall();
		}
	}

	@Test
	public void bindValuePreparedStatement() throws Exception {
		dateTimeSqlDateType.bindValue(preparedStatement, 0, new DateTime(0L));
		for (LocalDate expectDate : expectDates) {
			dateTimeSqlDateType.bindValue(preparedStatement, 0, expectDate);
		}
	}

	public void recordBindValueCallableStatement() throws Exception {
		callableStatement.setDate("test", new Date(0L));
		expectLastCall();
		for (LocalDate expectDate : expectDates) {
			callableStatement.setDate("test", new Date(expectDate.toDate()
					.getTime()));
			expectLastCall();
		}
	}

	@Test
	public void bindValueCallableStatement() throws Exception {
		dateTimeSqlDateType.bindValue(callableStatement, "test", new LocalDate(
				0L));
		for (LocalDate expectDate : expectDates) {
			dateTimeSqlDateType
					.bindValue(callableStatement, "test", expectDate);
		}
	}

	private void assertGetValue(LocalDate expected, Object actual) {
		assertThat(actual, instanceOf(LocalDate.class));
		assertThat(((LocalDate) actual), is(expected));
	}

}
