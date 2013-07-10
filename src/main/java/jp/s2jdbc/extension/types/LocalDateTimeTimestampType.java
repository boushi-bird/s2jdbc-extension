package jp.s2jdbc.extension.types;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.LocalDateTime;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.DateTimestampType;

/**
 * {@link Timestamp}と互換性をもつ{@link LocalDateTime}用の{@link ValueType}です。
 * 
 * @author boushi
 * 
 */
public class LocalDateTimeTimestampType extends DateTimestampType {
	@Override
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return toLocalDateTimeFromDate(resultSet.getTimestamp(index));
	}

	@Override
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {
		return toLocalDateTimeFromDate(resultSet.getTimestamp(columnName));
	}

	@Override
	public Object getValue(CallableStatement cs, int index) throws SQLException {
		return toLocalDateTimeFromDate(cs.getTimestamp(index));
	}

	@Override
	public Object getValue(CallableStatement cs, String parameterName)
			throws SQLException {
		return toLocalDateTimeFromDate(cs.getTimestamp(parameterName));
	}

	@Override
	protected Date toDate(Object value) {
		if (value instanceof LocalDateTime) {
			return toDateFromLocalDateTime((LocalDateTime) value);
		}
		return super.toDate(value);
	}

	/**
	 * {@link Date}から{@link LocalDateTime}を取得する.
	 * 
	 * @param date
	 *            {@link Date}の値
	 * @return {@link LocalDateTime}の値
	 */
	protected LocalDateTime toLocalDateTimeFromDate(final Date date) {
		if (date == null) {
			return null;
		}
		return new LocalDateTime(date);
	}

	/**
	 * {@link LocalDateTime}から{@link Date}を取得する.
	 * 
	 * @param localDateTime
	 *            {@link LocalDateTime}の値
	 * @return {@link Date}の値
	 */
	protected Date toDateFromLocalDateTime(final LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		return localDateTime.toDate();
	}
}
