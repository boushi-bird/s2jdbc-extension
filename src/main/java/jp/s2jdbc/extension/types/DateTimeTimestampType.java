package jp.s2jdbc.extension.types;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.joda.time.DateTime;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.DateTimestampType;

/**
 * {@link Timestamp}と互換性をもつ{@link DateTime}用の{@link ValueType}です。
 * 
 * @author boushi
 * 
 */
public class DateTimeTimestampType extends DateTimestampType {
	@Override
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return toDateTimeFromDate(resultSet.getTimestamp(index));
	}

	@Override
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {
		return toDateTimeFromDate(resultSet.getTimestamp(columnName));
	}

	@Override
	public Object getValue(CallableStatement cs, int index) throws SQLException {
		return toDateTimeFromDate(cs.getTimestamp(index));
	}

	@Override
	public Object getValue(CallableStatement cs, String parameterName)
			throws SQLException {
		return toDateTimeFromDate(cs.getTimestamp(parameterName));
	}

	@Override
	protected Date toDate(Object value) {
		if (value instanceof DateTime) {
			return toDateFromDateTime((DateTime) value);
		}
		return super.toDate(value);
	}

	/**
	 * {@link Date}から{@link DateTime}を取得する.
	 * 
	 * @param date
	 *            {@link Date}の値
	 * @return {@link DateTime}の値
	 */
	protected DateTime toDateTimeFromDate(final Date date) {
		if (date == null) {
			return null;
		}
		return new DateTime(date);
	}

	/**
	 * {@link DateTime}から{@link Date}を取得する.
	 * 
	 * @param dateTime
	 *            {@link DateTime}の値
	 * @return {@link Date}の値
	 */
	protected Date toDateFromDateTime(final DateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		return dateTime.toDate();
	}
}
