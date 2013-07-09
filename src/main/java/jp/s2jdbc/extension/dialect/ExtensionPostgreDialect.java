package jp.s2jdbc.extension.dialect;

import javax.persistence.TemporalType;

import jp.s2jdbc.extension.util.DateTimeValueTypes;

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
	@Override
	public ValueType getValueType(PropertyMeta propertyMeta) {
		ValueType valueType = DateTimeValueTypes.getValueType(propertyMeta);
		if (valueType != null) {
			return valueType;
		}
		return super.getValueType(propertyMeta);
	}

	@Override
	public ValueType getValueType(Class<?> clazz, boolean lob,
			TemporalType temporalType) {
		ValueType valueType = DateTimeValueTypes.getValueType(clazz,
				temporalType);
		if (valueType != null) {
			return valueType;
		}
		return super.getValueType(clazz, lob, temporalType);
	}
}
