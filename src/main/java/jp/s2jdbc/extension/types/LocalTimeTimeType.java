package jp.s2jdbc.extension.types;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.DateTimeType;

/**
 * {@link Time}と互換性をもつ{@link LocalTime}用の{@link ValueType}です。
 * 
 * @author boushi
 * 
 */
public class LocalTimeTimeType extends DateTimeType {
	@Override
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return toLocalTimeFromTime(resultSet.getTime(index));
	}

	@Override
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {
		return toLocalTimeFromTime(resultSet.getTime(columnName));
	}

	@Override
	public Object getValue(CallableStatement cs, int index) throws SQLException {
		return toLocalTimeFromTime(cs.getTime(index));
	}

	@Override
	public Object getValue(CallableStatement cs, String parameterName)
			throws SQLException {
		return toLocalTimeFromTime(cs.getTime(parameterName));
	}

	@Override
	protected Date toDate(Object value) {
		if (value instanceof LocalTime) {
			return toDateFromLocalTime((LocalTime) value);
		}
		return super.toDate(value);
	}

	/**
	 * {@link Time}から{@link LocalTime}を取得する.
	 * 
	 * @param time
	 *            {@link Time}の値
	 * @return {@link LocalTime}の値
	 */
	protected LocalTime toLocalTimeFromTime(final Time time) {
		if (time == null) {
			return null;
		}
		// Time -> LocalTimeの変換
		return new LocalTime(time.getTime());
	}

	/**
	 * {@link LocalTime}から{@link Time}を取得する.
	 * 
	 * @param dateTime
	 *            {@link LocalTime}の値
	 * @return {@link Date}の値
	 */
	protected Date toDateFromLocalTime(final LocalTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		// LocalTime -> Dateの変換
		return new LocalDate(0).toDateTime(dateTime).toDate();
	}
}
