package jp.s2jdbc.extension.types;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.LocalDate;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.DateSqlDateType;

/**
 * {@link java.sql.Date}と互換性をもつ{@link LocalDate}用の{@link ValueType}です。
 * 
 * @author boushi
 * 
 */
public class LocalDateSqlDateType extends DateSqlDateType {
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
		if (value instanceof LocalDate) {
			return toDateFromDateTime((LocalDate) value);
		}
		return super.toDate(value);
	}

	/**
	 * {@link java.sql.Date}から{@link LocalDate}を取得する.
	 * 
	 * @param date
	 *            {@link java.sql.Date}の値
	 * @return {@link LocalDate}の値
	 */
	protected LocalDate toDateTimeFromSqlDate(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		// java.sql.Date -> LocalDateの変換
		return new LocalDate(date.getTime());
	}

	/**
	 * {@link LocalDate}から{@link java.sql.Date}を取得する.
	 * 
	 * @param dateTime
	 *            {@link LocalDate}の値
	 * @return {@link java.sql.Date}の値
	 */
	protected java.util.Date toDateFromDateTime(LocalDate localDate) {
		if (localDate == null) {
			return null;
		}
		// LocalDate -> java.util.Dateの変換
		java.util.Date d = localDate.toDate();
		return d;
	}
}
