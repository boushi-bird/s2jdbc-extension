package jp.s2jdbc.extension.types;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.EasyMock;
import org.seasar.framework.unit.annotation.EasyMockType;

@RunWith(Seasar2.class)
public class LocalDateSqlDateTypeTest {
	@EasyMock(value = EasyMockType.DEFAULT)
	private ResultSet resultSet;
	@EasyMock(value = EasyMockType.DEFAULT)
	private CallableStatement callableStatement;
	@EasyMock(value = EasyMockType.DEFAULT)
	private PreparedStatement preparedStatement;

	private LocalDateSqlDateType localDateSqlDateType = new LocalDateSqlDateType();

	private LocalDate[] expectDates = new LocalDate[] {
			// 2012/01/01
			new LocalDate(2012, 1, 1),
			// 2012/02/28
			new LocalDate(2012, 2, 28),
			// 2012/02/29
			new LocalDate(2012, 2, 29),
			// 2012/03/01
			new LocalDate(2012, 3, 1),
			// 2012/12/31
			new LocalDate(2012, 12, 31),
	//
	};

	public void recordGetValueResultSet() throws Exception {
		expect(resultSet.getDate(0)).andReturn(
				toSqlDate(new java.util.Date(0L)));
		expect(resultSet.getDate("test")).andReturn(
				toSqlDate(new java.util.Date(0L)));
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
				localDateSqlDateType.getValue(resultSet, 0));
		assertGetValue(new LocalDate(0L),
				localDateSqlDateType.getValue(resultSet, "test"));
		for (LocalDate expectDate : expectDates) {
			assertGetValue(expectDate,
					localDateSqlDateType.getValue(resultSet, 0));
			assertGetValue(expectDate,
					localDateSqlDateType.getValue(resultSet, "test"));
		}
	}

	public void recordGetValueCallableStatement() throws Exception {
		expect(callableStatement.getDate(0)).andReturn(
				toSqlDate(new java.util.Date(0L)));
		expect(callableStatement.getDate("test")).andReturn(
				toSqlDate(new java.util.Date(0L)));
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
				localDateSqlDateType.getValue(callableStatement, 0));
		assertGetValue(new LocalDate(0L),
				localDateSqlDateType.getValue(callableStatement, "test"));
		for (LocalDate expectDate : expectDates) {
			assertGetValue(expectDate,
					localDateSqlDateType.getValue(callableStatement, 0));
			assertGetValue(expectDate,
					localDateSqlDateType.getValue(callableStatement, "test"));
		}
	}

	public void recordBindValuePreparedStatement() throws Exception {
		preparedStatement.setDate(0, toSqlDate(new java.util.Date(0L)));
		expectLastCall();
		for (LocalDate expectDate : expectDates) {
			preparedStatement.setDate(0, toSqlDate(expectDate.toDate()));
			expectLastCall();
		}
	}

	@Test
	public void bindValuePreparedStatement() throws Exception {
		localDateSqlDateType.bindValue(preparedStatement, 0, new LocalDate(0L));
		for (LocalDate expectDate : expectDates) {
			localDateSqlDateType.bindValue(preparedStatement, 0, expectDate);
		}
	}

	public void recordBindValueCallableStatement() throws Exception {
		callableStatement.setDate("test", toSqlDate(new java.util.Date(0L)));
		expectLastCall();
		for (LocalDate expectDate : expectDates) {
			callableStatement.setDate("test", toSqlDate(expectDate.toDate()));
			expectLastCall();
		}
	}

	@Test
	public void bindValueCallableStatement() throws Exception {
		localDateSqlDateType.bindValue(callableStatement, "test",
				new LocalDate(0));
		for (LocalDate expectDate : expectDates) {
			localDateSqlDateType.bindValue(callableStatement, "test",
					expectDate);
		}
	}

	private void assertGetValue(LocalDate expected, Object actual) {
		assertThat(actual, instanceOf(LocalDate.class));
		assertThat(((LocalDate) actual), is(expected));
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
