package jp.s2jdbc.extension.dialect;

import javax.persistence.TemporalType;

import jp.s2jdbc.extension.types.DateTimeTimeType;
import jp.s2jdbc.extension.types.DateTimeTimestampType;
import jp.s2jdbc.extension.types.LocalDateSqlDateType;

import org.joda.time.DateTime;
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
	public static final ValueType DATETIME_SQLDATE = new LocalDateSqlDateType();
	public static final ValueType DATETIME_TIME = new DateTimeTimeType();

	@Override
	public ValueType getValueType(PropertyMeta propertyMeta) {
		TemporalType temporalType = propertyMeta.getTemporalType();
		if (temporalType != null
				&& DateTime.class == propertyMeta.getPropertyClass()) {
			switch (propertyMeta.getTemporalType()) {
			case TIMESTAMP:
				return DATETIME_TIMESTAMP;
			case DATE:
				return DATETIME_SQLDATE;
			case TIME:
				return DATETIME_TIME;
			default:
				break;
			}
		}
		return super.getValueType(propertyMeta);
	}

	@Override
	public ValueType getValueType(Class<?> clazz, boolean lob,
			TemporalType temporalType) {
		if (temporalType != null && DateTime.class == clazz) {
			switch (temporalType) {
			case TIMESTAMP:
				return DATETIME_TIMESTAMP;
			case DATE:
				return DATETIME_SQLDATE;
			case TIME:
				return DATETIME_TIME;
			default:
				break;
			}
		}
		return super.getValueType(clazz, lob, temporalType);
	}
}
