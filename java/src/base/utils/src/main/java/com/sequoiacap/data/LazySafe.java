package com.sequoiacap.data;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
//import org.hibernate.LazyInitializationException;

public class LazySafe
{
	public static <T> T wrap(T object)
	{
		if (object == null)
			return null;
		
		PropertyDescriptor[] descs =
			PropertyUtils.getPropertyDescriptors(object);
		
		for(PropertyDescriptor desc: descs)
		{
			try
			{
				PropertyUtils.getProperty(object, desc.getName());
			} catch(IllegalAccessException e)
			{
				continue;
			} catch(NoSuchMethodException e)
			{
				continue;
			} catch(InvocationTargetException e)
			{
				//if (e.getTargetException() instanceof LazyInitializationException)
				//	return null;

				//continue;
				return null;
			}
			
			break;
		}

		return object;
	}
	
	public static <T extends Collection> T wrap(T object)
	{
		if (object == null)
			return null;

		try
		{
			int size = object.size();
		} catch(Exception e)
		//catch(LazyInitializationException e)
		{
			return null;
		}

		return object;
	}
}
