package org.seasar.extension.jdbc.meta;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import jp.s2jdbc.extension.meta.ExtentionPropertyMetaFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.extension.jdbc.EntityMetaFactory;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class EntityMetaFactoryTest {
	private EntityMetaFactory entityMetaFactory;

	@Test
	public void available() throws Exception {
		assertThat(entityMetaFactory, notNullValue());
	}

	@Test
	public void checkInstance() throws Exception {
		assertThat(entityMetaFactory, instanceOf(EntityMetaFactoryImpl.class));
		assertThat(
				((EntityMetaFactoryImpl) entityMetaFactory).propertyMetaFactory,
				instanceOf(ExtentionPropertyMetaFactory.class));
	}
}
