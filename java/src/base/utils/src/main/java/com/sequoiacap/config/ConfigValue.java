package com.sequoiacap.config;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import com.sequoiacap.utils.ApplicationContextHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.TypeConverter;

public class ConfigValue
{
	private String scope;
	private String key;
	private String defaultVal;

	public ConfigValue(String key)
	{
		this.key = key;

		if (!Character.isJavaIdentifierPart(this.key.charAt(0)))
		{
			this.scope = this.key.substring(0, 1);
			this.key = this.key.substring(1);
		}

		int index = this.key.indexOf(":");
		if (index != -1)
		{
			this.defaultVal = this.key.substring(index + 1);
			this.key = this.key.substring(0, index);
		}
	}

	private String resolve(String scope)
	{
		ConfigurableBeanFactory factory =
			ApplicationContextHolder.getBean(ConfigurableBeanFactory.class);
		if (factory == null)
			return null;
		
		String result = null;
		
		try
		{
			result =
				factory.resolveEmbeddedValue(String.format("%s{%s}", scope, key));
		} catch(Exception e)
		{ }
		
		return result;
	}
	
	public String resolve()
	{
		String result = null;

		if (StringUtils.isNotBlank(scope))
		{
			result = resolve(scope);
		} else
		{
			result = resolve("$");

			if (result == null)
				result = resolve("@");
		}

		if (result == null)
			result = defaultVal;

		return result;
	}

	public String get()
	{
		return resolve();
	}

	public Boolean getBoolean()
	{
		return get(Boolean.class);
	}
	
	public Short getShort()
	{
		return get(Short.class);
	}
	
	public Integer getInteger()
	{
		return get(Integer.class);
	}
	
	public Long getLong()
	{
		return get(Long.class);
	}
	
	public Double getDouble()
	{
		return get(Double.class);
	}
	
	public BigDecimal getBigDecimal()
	{
		return get(BigDecimal.class);
	}
	
	public BigInteger getBigInteger()
	{
		return get(BigInteger.class);
	}

	public <T> T get(Class<T> clazz)
	{
		String value = get();
		if (value == null)
			return null;

		ConfigurableBeanFactory factory =
			ApplicationContextHolder.getBean(ConfigurableBeanFactory.class);
		
		TypeConverter typeConverter = factory.getTypeConverter();

		return typeConverter.convertIfNecessary(value, clazz);
	}
}
