package jp.s2jdbc.extension.dialect;

import javax.persistence.TemporalType;

import jp.s2jdbc.extension.types.DateTimeTimestampType;

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

	@Override
	public ValueType getValueType(PropertyMeta propertyMeta) {
		TemporalType temporalType = propertyMeta.getTemporalType();
		if (temporalType != null
				&& DateTime.class == propertyMeta.getPropertyClass()) {
			switch (propertyMeta.getTemporalType()) {
			case TIMESTAMP:
				return DATETIME_TIMESTAMP;
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
			default:
				break;
			}
		}
		return super.getValueType(clazz, lob, temporalType);
	}
}
