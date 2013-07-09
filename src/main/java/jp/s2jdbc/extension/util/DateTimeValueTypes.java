package jp.s2jdbc.extension.util;

import javax.persistence.TemporalType;

import jp.s2jdbc.extension.types.DateTimeTimestampType;
import jp.s2jdbc.extension.types.LocalDateSqlDateType;
import jp.s2jdbc.extension.types.LocalTimeTimeType;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.seasar.extension.jdbc.PropertyMeta;
import org.seasar.extension.jdbc.ValueType;

/**
 * 日時関連の{@link ValueType}を扱うクラス
 * 
 * @author boushi
 * 
 */
public class DateTimeValueTypes {
	public static final ValueType DATETIME_TIMESTAMP = new DateTimeTimestampType();
	public static final ValueType LOCALDATE_SQLDATE = new LocalDateSqlDateType();
	public static final ValueType LOCALTIME_TIME = new LocalTimeTimeType();

	private DateTimeValueTypes() {
	}

	/**
	 * {@link ValueType}を返します。
	 * 
	 * @param propertyMeta
	 * @return {@link ValueType}
	 */
	public static ValueType getValueType(PropertyMeta propertyMeta) {
		return getValueType(propertyMeta.getPropertyClass(),
				propertyMeta.getTemporalType());
	}

	/**
	 * {@link ValueType}を返します。
	 * 
	 * @param clazz
	 * @param temporalType
	 * @return {@link ValueType}
	 */
	public static ValueType getValueType(Class<?> clazz,
			TemporalType temporalType) {
		if (DateTime.class == clazz) {
			if (temporalType != null) {
				switch (temporalType) {
				case TIMESTAMP:
					return DATETIME_TIMESTAMP;
					// // TODO DateTime <-> java.sql.Date
					// case DATE:
					// return DATETIME_SQLDATE;
					// // TODO DateTime <-> java.sql.Time
					// case TIME:
					// return DATETIME_TIME;
				default:
					return DATETIME_TIMESTAMP;
				}
			} else {
				return DATETIME_TIMESTAMP;
			}
		}
		// // TODO LocalDateTime <-> java.sql.Timestamp
		// // TODO LocalDateTime <-> java.sql.Date
		// // TODO LocalDateTime <-> java.sql.Time
		// if (LocalDateTime.class == clazz) {
		// if (temporalType != null) {
		// switch (temporalType) {
		// case TIMESTAMP:
		// return LOCALDATETIME_TIMESTAMP;
		// case DATE:
		// return LOCALDATETIME_SQLDATE;
		// case TIME:
		// return LOCALDATETIME_TIME;
		// default:
		// return LOCALDATETIME_TIMESTAMP;
		// }
		// } else {
		// return LOCALDATETIME_TIMESTAMP;
		// }
		// }
		// // TODO DateMidnight <-> java.sql.Date
		// if (DateMidnight.class == clazz) {
		// return DATEMIDNIGHT_SQLDATE;
		// }
		if (LocalDate.class == clazz) {
			return LOCALDATE_SQLDATE;
		}
		if (LocalTime.class == clazz) {
			return LOCALTIME_TIME;
		}
		return null;
	}

}
