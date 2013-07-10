package jp.s2jdbc.extension.types;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.EasyMock;
import org.seasar.framework.unit.annotation.EasyMockType;

@RunWith(Seasar2.class)
public class LocalDateTimeTimestampTypeTest {
	@EasyMock(value = EasyMockType.DEFAULT)
	private ResultSet resultSet;
	@EasyMock(value = EasyMockType.DEFAULT)
	private CallableStatement callableStatement;
	@EasyMock(value = EasyMockType.DEFAULT)
	private PreparedStatement preparedStatement;

	private final LocalDateTimeTimestampType localDateTimeTimestampType = new LocalDateTimeTimestampType();

	private LocalDateTime[] expectLocalDateTimes = new LocalDateTime[] {
			// 2010/01/01 UTC
			new LocalDateTime(2010, 1, 1, 0, 0, 0, 0),
			// 2020/01/01 UTC
			new LocalDateTime(2020, 1, 1, 0, 0, 0, 0),
			// 2010/01/01 JST
			new LocalDateTime(2010, 1, 1, 0, 0, 0, 0),
			// 2020/01/01 JST
			new LocalDateTime(2020, 1, 1, 0, 0, 0, 0),
			// 2007/07/07 07:07:07.777 UTC
			new LocalDateTime(2007, 7, 7, 7, 7, 7, 777),
			// 2007/07/07 07:07:07.777 JST
			new LocalDateTime(2007, 7, 7, 7, 7, 7, 777), };

	private DateTimeZone expectDateTimeZone = DateTimeZone.getDefault();

	public void recordGetValueResultSet() throws Exception {
		expect(resultSet.getTimestamp(0)).andReturn(new Timestamp(0));
		expect(resultSet.getTimestamp("test")).andReturn(new Timestamp(0));
		for (LocalDateTime expectLocalDateTime : expectLocalDateTimes) {
			expect(resultSet.getTimestamp(0)).andReturn(
					new Timestamp(expectLocalDateTime.toDateTime(
							expectDateTimeZone).getMillis()));
			expect(resultSet.getTimestamp("test")).andReturn(
					new Timestamp(expectLocalDateTime.toDateTime(
							expectDateTimeZone).getMillis()));
		}
	}

	@Test
	public void getValueResultSet() throws Exception {
		assertGetValue(new LocalDateTime(0L),
				localDateTimeTimestampType.getValue(resultSet, 0));
		assertGetValue(new LocalDateTime(0L),
				localDateTimeTimestampType.getValue(resultSet, "test"));
		for (LocalDateTime expectLocalDateTime : expectLocalDateTimes) {
			assertGetValue(expectLocalDateTime,
					localDateTimeTimestampType.getValue(resultSet, 0));
			assertGetValue(expectLocalDateTime,
					localDateTimeTimestampType.getValue(resultSet, "test"));
		}
	}

	public void recordGetValueCallableStatement() throws Exception {
		expect(callableStatement.getTimestamp(0)).andReturn(new Timestamp(0));
		expect(callableStatement.getTimestamp("test")).andReturn(
				new Timestamp(0));
		for (LocalDateTime expectLocalDateTime : expectLocalDateTimes) {
			expect(callableStatement.getTimestamp(0)).andReturn(
					new Timestamp(expectLocalDateTime.toDateTime(
							expectDateTimeZone).getMillis()));
			expect(callableStatement.getTimestamp("test")).andReturn(
					new Timestamp(expectLocalDateTime.toDateTime(
							expectDateTimeZone).getMillis()));
		}
	}

	@Test
	public void getValueCallableStatement() throws Exception {
		assertGetValue(new LocalDateTime(0L),
				localDateTimeTimestampType.getValue(callableStatement, 0));
		assertGetValue(new LocalDateTime(0L),
				localDateTimeTimestampType.getValue(callableStatement, "test"));
		for (LocalDateTime expectLocalDateTime : expectLocalDateTimes) {
			assertGetValue(expectLocalDateTime,
					localDateTimeTimestampType.getValue(callableStatement, 0));
			assertGetValue(expectLocalDateTime,
					localDateTimeTimestampType.getValue(callableStatement,
							"test"));
		}
	}

	public void recordBindValuePreparedStatement() throws Exception {
		preparedStatement.setTimestamp(0, new Timestamp(0L));
		expectLastCall();
		for (LocalDateTime expectLocalDateTime : expectLocalDateTimes) {
			preparedStatement.setTimestamp(0, new Timestamp(expectLocalDateTime
					.toDateTime(expectDateTimeZone).getMillis()));
			expectLastCall();
		}
	}

	@Test
	public void bindValuePreparedStatement() throws Exception {
		localDateTimeTimestampType.bindValue(preparedStatement, 0,
				new LocalDateTime(0L));
		for (LocalDateTime expectLocalDateTime : expectLocalDateTimes) {
			localDateTimeTimestampType.bindValue(preparedStatement, 0,
					expectLocalDateTime);
		}
	}

	public void recordBindValueCallableStatement() throws Exception {
		callableStatement.setTimestamp("test", new Timestamp(0L));
		expectLastCall();
		for (LocalDateTime expectLocalDateTime : expectLocalDateTimes) {
			callableStatement.setTimestamp("test", new Timestamp(
					expectLocalDateTime.toDateTime(expectDateTimeZone)
							.getMillis()));
			expectLastCall();
		}
	}

	@Test
	public void bindValueCallableStatement() throws Exception {
		localDateTimeTimestampType.bindValue(callableStatement, "test",
				new LocalDateTime(0L));
		for (LocalDateTime expectLocalDateTime : expectLocalDateTimes) {
			localDateTimeTimestampType.bindValue(callableStatement, "test",
					expectLocalDateTime);
		}
	}

	private void assertGetValue(LocalDateTime expected, Object actual) {
		assertThat(actual, instanceOf(LocalDateTime.class));
		assertThat(((LocalDateTime) actual).toDateTime(expectDateTimeZone),
				is(expected.toDateTime(expectDateTimeZone)));
	}
}
