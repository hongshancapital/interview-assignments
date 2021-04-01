package com.sequoiacap.js;

import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

import org.apache.log4j.Logger;
import com.sequoiacap.utils.JsonHelper;

public class Javascript
{
	private static final Logger log = Logger.getLogger(Javascript.class);

	private static ScriptEngineManager manager = new ScriptEngineManager();

	private ScriptContext context = new SimpleScriptContext();
	private ScriptEngine engine = manager.getEngineByName("javascript");

	public void setAttribute(String name, Object value)
	{
		context.setAttribute(name, value, ScriptContext.ENGINE_SCOPE);
	}
	
	public Object removeAttribute(String name)
	{
		return context.removeAttribute(name, ScriptContext.ENGINE_SCOPE);
	}

	public Object getAttribute(String name)
	{
		return context.getAttribute(name);
	}

	public Object exec(String script)
	{
		try
		{
			return engine.eval(script, context);
		} catch(Exception e)
		{
			log.error("", e);
		}
		
		return null;
	}

	public static Object eval(String script)
	{
		return eval(script,(Map) null);
	}

	public static Object eval(String script, Map context)
	{
		Javascript javascript = new Javascript();
		
		if (context != null)
		{
			for(Object key: context.keySet())
			{
				Object value = context.get(key);
				
				javascript.setAttribute(String.valueOf(key), value);
			}
		}

		return javascript.exec(script);
	}

	public static <T> Object eval(String script, T obj)
	{
		Map map = JsonHelper.convert(obj, Map.class);

		return eval(script, map);
	}

	public static Object eval(
		String script, String name, Object value, Object ... args)
	{
		Map map = new HashMap();

		map.put(name, value);
		
		for(int index = 0; index < args.length; index += 2)
		{
			name = String.valueOf(args[index]);
			value = (index + 1) < args.length? args[index + 1]: null;

			map.put(name, value);
		}

		return eval(script, map);
	}
}
