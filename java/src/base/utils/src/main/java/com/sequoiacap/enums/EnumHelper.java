package com.sequoiacap.enums;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EnumHelper
{
	private static Log log = LogFactory.getLog(EnumHelper.class);

	public static void setOrdinal(Enum object, int value)
	{
		try
		{
			Field field =
				object.getClass().getSuperclass().getDeclaredField("ordinal");
			
			field.setAccessible(true);
			field.set(object, value);
		} catch(Exception e)
		{
			log.error("failed setOrdinal: " + object, e);
		}
	}
	
	public static void setOrdinal(Class<? extends Enum> enums)
	{
		try
		{
			Method method = enums.getMethod("values");

			Enum[] values =(Enum[]) method.invoke(null);

			for(Enum value: values)
			{
				Field field = enums.getField(String.valueOf(value));

				Ordinal ordinal = field.getAnnotation(Ordinal.class);
				if (ordinal != null)
					setOrdinal(value, ordinal.value());
			}
		} catch(Exception e)
		{
			log.error("failed setOrdinal: " + enums.getName(), e);
		}
	}
}
