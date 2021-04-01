package com.sequoiacap.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanBuilder<T>
{
	private static Logger log =
		LoggerFactory.getLogger(BeanBuilder.class);

	private T bean;

	public BeanBuilder(T bean)
	{
		this.bean = bean;
	}
	
	public BeanBuilder(Class<T> clazz)
	{
		try
		{
			bean = clazz.newInstance();
		} catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public <VT> BeanBuilder<T> set(String field, VT value)
	{
		try
		{
			BeanUtils.setProperty(bean, field, value);
		} catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		
		return this;
	}

	public T toBean()
	{
		return bean;
	}

	public static <T> BeanBuilder<T> newInstance(Class<T> clazz)
	{
		return new BeanBuilder<T>(clazz);
	}

	public static <T> BeanBuilder<T> newInstance(T clazz)
	{
		return new BeanBuilder<T>(clazz);
	}
}
