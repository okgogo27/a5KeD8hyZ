package platform.converter;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import platform.utils.helper.DateUtils;

public class DateConverter implements ConditionalGenericConverter {

	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (source == null) {
			return null;
		}

		String sourceValue = (String) source;

		try {
			Date date = DateUtils.parseDate(sourceValue);
			if (Timestamp.class == targetType.getType()) {
				return new Timestamp(date.getTime());
			} else if (java.sql.Date.class == targetType.getType()) {
				return new java.sql.Date(date.getTime());
			} else if (Time.class == targetType.getType()) {
				return new Time(date.getTime());
			}
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> types = new HashSet<ConvertiblePair>();
		types.add(new ConvertiblePair(String.class, Date.class));
		types.add(new ConvertiblePair(String.class, java.sql.Date.class));
		types.add(new ConvertiblePair(String.class, Timestamp.class));
		types.add(new ConvertiblePair(String.class, Time.class));
		return types;
	}

	public boolean matches(TypeDescriptor arg0, TypeDescriptor arg1) {
		return String.class == arg0.getType() && Date.class.isAssignableFrom(arg1.getType());
	}

}
