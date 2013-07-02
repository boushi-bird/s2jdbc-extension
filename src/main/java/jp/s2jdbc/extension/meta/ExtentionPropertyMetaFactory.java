package jp.s2jdbc.extension.meta;

import java.lang.reflect.Field;

import javax.persistence.Temporal;

import org.joda.time.DateTime;
import org.seasar.extension.jdbc.EntityMeta;
import org.seasar.extension.jdbc.PropertyMeta;
import org.seasar.extension.jdbc.PropertyMetaFactory;
import org.seasar.extension.jdbc.meta.PropertyMetaFactoryImpl;

/**
 * {@link PropertyMetaFactory}の実装クラスです。
 * 
 * @author boushi
 * 
 */
public class ExtentionPropertyMetaFactory extends PropertyMetaFactoryImpl {
	@Override
	protected void doTemporal(PropertyMeta propertyMeta, Field field,
			EntityMeta entityMeta) {
		if (propertyMeta.getPropertyClass() == DateTime.class) {
			Temporal temporal = field.getAnnotation(Temporal.class);
			if (temporal != null) {
				propertyMeta.setTemporalType(temporal.value());
				return;
			}
		}
		super.doTemporal(propertyMeta, field, entityMeta);
	}
}
