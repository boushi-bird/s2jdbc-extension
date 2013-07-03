package jp.s2jdbc.extension.types;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.DateSqlDateType;

/**
 * {@link java.sql.Date}と互換性をもつ{@link DateTime}用の{@link ValueType}です。
 * 
 * @author boushi
 * 
 */
public class DateTimeSqlDateType extends DateSqlDateType {
	@Override
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return toDateTimeFromSqlDate(resultSet.getDate(index));
	}

	@Override
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {
		return toDateTimeFromSqlDate(resultSet.getDate(columnName));
	}

	@Override
	public Object getValue(CallableStatement cs, int index) throws SQLException {
		return toDateTimeFromSqlDate(cs.getDate(index));
	}

	@Override
	public Object getValue(CallableStatement cs, String parameterName)
			throws SQLException {
		return toDateTimeFromSqlDate(cs.getDate(parameterName));
	}

	@Override
	protected java.util.Date toDate(Object value) {
		if (value instanceof DateTime) {
			return toSqlDateFromDateTime((DateTime) value);
		}
		return super.toDate(value);
	}

	/**
	 * {@link java.sql.Date}から{@link DateTime}を取得する.
	 * 
	 * @param date
	 *            {@link java.sql.Date}の値
	 * @return {@link DateTime}の値
	 */
	private DateTime toDateTimeFromSqlDate(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		// FIXME java.sql.Date -> DateTimeの変換
		return null;
	}

	/**
	 * {@link DateTime}から{@link java.sql.Date}を取得する.
	 * 
	 * @param dateTime
	 *            {@link DateTime}の値
	 * @return {@link java.sql.Date}の値
	 */
	private java.sql.Date toSqlDateFromDateTime(DateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		// FIXME DateTime -> java.sql.Dateの変換
		return null;
	}
}
