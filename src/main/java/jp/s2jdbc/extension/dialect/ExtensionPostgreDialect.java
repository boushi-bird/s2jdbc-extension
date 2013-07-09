package jp.s2jdbc.extension.dialect;

import javax.persistence.TemporalType;

import jp.s2jdbc.extension.types.DateTimeTimestampType;
import jp.s2jdbc.extension.types.LocalDateSqlDateType;
import jp.s2jdbc.extension.types.LocalTimeTimeType;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.seasar.extension.jdbc.PropertyMeta;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.dialect.PostgreDialect;

/**
 * PostgreSQL用の方言をあつかうクラスを拡張したクラスです。
 * 
 * @author boushi
 * 
 */
public class ExtensionPostgreDialect extends PostgreDialect {
	public static final ValueType DATETIME_TIMESTAMP = new DateTimeTimestampType();
	public static final ValueType LOCALDATE_SQLDATE = new LocalDateSqlDateType();
	public static final ValueType LOCALTIME_TIME = new LocalTimeTimeType();

	@Override
	public ValueType getValueType(PropertyMeta propertyMeta) {
		TemporalType temporalType = propertyMeta.getTemporalType();
		if (temporalType != null) {
			ValueType valueType = getValueType(propertyMeta.getPropertyClass(),
					temporalType);
			if (valueType != null) {
				return valueType;
			}
		}
		return super.getValueType(propertyMeta);
	}

	@Override
	public ValueType getValueType(Class<?> clazz, boolean lob,
			TemporalType temporalType) {
		if (temporalType != null) {
			ValueType valueType = getValueType(clazz, temporalType);
			if (valueType != null) {
				return valueType;
			}
		}
		return super.getValueType(clazz, lob, temporalType);
	}

	protected ValueType getValueType(Class<?> clazz, TemporalType temporalType) {
		if (DateTime.class == clazz) {
			switch (temporalType) {
			case TIMESTAMP:
				return DATETIME_TIMESTAMP;
				// case DATE:
				// return DATETIME_SQLDATE;
				// case TIME:
				// return DATETIME_TIME;
			default:
				return null;
			}
		}
		if (LocalDate.class == clazz && temporalType == TemporalType.DATE) {
			return LOCALDATE_SQLDATE;
		}
		if (LocalDate.class == clazz && temporalType == TemporalType.TIME) {
			return LOCALTIME_TIME;
		}
		return null;
	}
}
