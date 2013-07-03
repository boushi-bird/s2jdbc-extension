package jp.s2jdbc.extension.types;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

import org.joda.time.DateTime;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.DateTimeType;

/**
 * {@link Time}と互換性をもつ{@link DateTime}用の{@link ValueType}です。
 * 
 * @author boushi
 * 
 */
public class DateTimeTimeType extends DateTimeType {
	@Override
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return toDateTimeFromTime(resultSet.getTime(index));
	}

	@Override
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {
		return toDateTimeFromTime(resultSet.getTime(columnName));
	}

	@Override
	public Object getValue(CallableStatement cs, int index) throws SQLException {
		return toDateTimeFromTime(cs.getTime(index));
	}

	@Override
	public Object getValue(CallableStatement cs, String parameterName)
			throws SQLException {
		return toDateTimeFromTime(cs.getTime(parameterName));
	}

	@Override
	protected Date toDate(Object value) {
		if (value instanceof DateTime) {
			return toTimeFromDateTime((DateTime) value);
		}
		return super.toDate(value);
	}

	/**
	 * {@link Time}から{@link DateTime}を取得する.
	 * 
	 * @param time
	 *            {@link Time}の値
	 * @return {@link DateTime}の値
	 */
	protected DateTime toDateTimeFromTime(final Time time) {
		if (time == null) {
			return null;
		}
		// FIXME Time -> DateTimeの変換
		return null;
	}

	/**
	 * {@link DateTime}から{@link Time}を取得する.
	 * 
	 * @param dateTime
	 *            {@link DateTime}の値
	 * @return {@link Time}の値
	 */
	protected Time toTimeFromDateTime(final DateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		// FIXME DateTime -> Timeの変換
		return null;
	}
}
