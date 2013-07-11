package jp.s2jdbc.extension.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class DateTimeValueTypesTest {

	@Test
	public void getValueType() throws Exception {
		assertThat(DateTimeValueTypes.getValueType(DateTime.class,
				TemporalType.TIMESTAMP),
				is(DateTimeValueTypes.DATETIME_TIMESTAMP));
		// // FIXME DateTime <-> java.sql.Date
		// assertThat(DateTimeValueTypes.getValueType(DateTime.class,
		// TemporalType.DATE), is(DateTimeValueTypes.DATETIME_SQLDATE));
		// // FIXME DateTime <-> java.sql.Time
		// assertThat(DateTimeValueTypes.getValueType(DateTime.class,
		// TemporalType.TIME), is(DateTimeValueTypes.DATETIME_TIME));
		assertThat(DateTimeValueTypes.getValueType(DateTime.class, null),
				is(DateTimeValueTypes.DATETIME_TIMESTAMP));

		assertThat(DateTimeValueTypes.getValueType(LocalDateTime.class,
				TemporalType.TIMESTAMP),
				is(DateTimeValueTypes.LOCALDATETIME_TIMESTAMP));
		// // FIXME LocalDateTime <-> java.sql.Date
		// assertThat(DateTimeValueTypes.getValueType(LocalDateTime.class,
		// TemporalType.DATE),
		// is(DateTimeValueTypes.LOCALDATETIME_SQLDATE));
		// // FIXME LocalDateTime <-> java.sql.Time
		// assertThat(DateTimeValueTypes.getValueType(LocalDateTime.class,
		// TemporalType.TIME), is(DateTimeValueTypes.LOCALDATETIME_TIME));
		assertThat(DateTimeValueTypes.getValueType(LocalDateTime.class, null),
				is(DateTimeValueTypes.LOCALDATETIME_TIMESTAMP));

		// // FIXME DateMidnight <-> java.sql.Date
		// assertThat(DateTimeValueTypes.getValueType(DateMidnight.class,
		// TemporalType.TIMESTAMP),
		// is(DateTimeValueTypes.DATEMIDNIGHT_SQLDATE));
		// assertThat(DateTimeValueTypes.getValueType(DateMidnight.class,
		// TemporalType.DATE), is(DateTimeValueTypes.DATEMIDNIGHT_SQLDATE));
		// assertThat(DateTimeValueTypes.getValueType(DateMidnight.class,
		// TemporalType.TIME), is(DateTimeValueTypes.DATEMIDNIGHT_SQLDATE));
		// assertThat(DateTimeValueTypes.getValueType(DateMidnight.class, null),
		// is(DateTimeValueTypes.DATEMIDNIGHT_SQLDATE));

		assertThat(DateTimeValueTypes.getValueType(LocalDate.class,
				TemporalType.TIMESTAMP),
				is(DateTimeValueTypes.LOCALDATE_SQLDATE));
		assertThat(DateTimeValueTypes.getValueType(LocalDate.class,
				TemporalType.DATE), is(DateTimeValueTypes.LOCALDATE_SQLDATE));
		assertThat(DateTimeValueTypes.getValueType(LocalDate.class,
				TemporalType.TIME), is(DateTimeValueTypes.LOCALDATE_SQLDATE));
		assertThat(DateTimeValueTypes.getValueType(LocalDate.class, null),
				is(DateTimeValueTypes.LOCALDATE_SQLDATE));

		assertThat(DateTimeValueTypes.getValueType(LocalTime.class,
				TemporalType.TIMESTAMP), is(DateTimeValueTypes.LOCALTIME_TIME));
		assertThat(DateTimeValueTypes.getValueType(LocalTime.class,
				TemporalType.DATE), is(DateTimeValueTypes.LOCALTIME_TIME));
		assertThat(DateTimeValueTypes.getValueType(LocalTime.class,
				TemporalType.TIME), is(DateTimeValueTypes.LOCALTIME_TIME));
		assertThat(DateTimeValueTypes.getValueType(LocalTime.class, null),
				is(DateTimeValueTypes.LOCALTIME_TIME));
	}
}
