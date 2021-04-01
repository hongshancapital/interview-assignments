package com.sequoiacap.utils;

import java.util.HashMap;

import org.springframework.core.NamedInheritableThreadLocal;

public class NamedMap
{
	protected HashMap<String, HashMap<String, String>> namedMap =
		new HashMap<String, HashMap<String, String>>();
	
	private ThreadLocal<String> currentName = null;
	private String defaultName = null;

	public NamedMap(String defaultName)
	{
		defaultName = defaultName;

		currentName =
			new NamedInheritableThreadLocal<String>(
				String.format("namedmap.%d", this.hashCode()));
	}

	public String fetchName()
	{
		String name = currentName.get();
		
		if (name == null)
			name = defaultName;

		return name;
	}

	public String switchName(String name)
	{
		currentName.set(name);

		return name;
	}

	public HashMap<String, String> fetchMap()
	{
		String name = fetchName();
		
		HashMap<String, String> map = namedMap.get(name);
		if (map == null)
		{
			synchronized(this)
			{
				map = namedMap.get(name);
				
				if (map == null)
					namedMap.put(name, map = new HashMap<String, String>());
			}
		}

		return map;
	}

	public String getValue(String key)
	{
		return fetchMap().get(key);
	}

	public Boolean getBooleanValue(String key)
	{
		String strValue = getValue(key);

		if (strValue != null)
		{
			try
			{
				if ("true".equalsIgnoreCase(strValue) ||
					"on".equalsIgnoreCase(strValue) ||
					"yes".equalsIgnoreCase(strValue))
					return true;

				if ("false".equalsIgnoreCase(strValue) ||
					"off".equalsIgnoreCase(strValue) ||
					"no".equalsIgnoreCase(strValue))
					return false;

				return Long.parseLong(strValue) != 0l;
			} catch(Exception e)
			{ }
		}

		return false;
	}

	public Long getLongValue(String key)
	{
		String strValue = getValue(key);
		
		if (strValue != null)
		{
			try
			{
				return Long.parseLong(strValue);
			} catch(Exception e)
			{ }
		}
		
		return 0l;
	}
	
	public Double getDoubleValue(String key)
	{
		String strValue = getValue(key);
		
		if (strValue != null)
		{
			try
			{
				return Double.parseDouble(strValue);
			} catch(Exception e)
			{ }
		}
		
		return 0.0;
	}
}
