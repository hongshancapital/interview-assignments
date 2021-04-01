package com.sequoiacap.utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonHelper
{
    public static String formatJson(Object data)
    {
        //Gson gson = new Gson();
    	Gson gson = new GsonBuilder()
    		.registerTypeAdapter(
    			Timestamp.class, new TimestampAdapter()).create();

        return gson.toJson(data);
    }

    public static <T>
        T extend(Class<T> target, Object root, Object ... args)
    {
        //Gson gson = new Gson();
    	Gson gson = new GsonBuilder()
    		.registerTypeAdapter(
    			Timestamp.class, new TimestampAdapter()).create();
        
        JsonObject jsRoot = gson.toJsonTree(root).getAsJsonObject();
        for(Object arg: args)
        {
            JsonObject jsArg = gson.toJsonTree(arg).getAsJsonObject();

            for(Map.Entry<String, JsonElement> entry: jsArg.entrySet())
            {
                String key = entry.getKey();
                JsonElement value = entry.getValue();

                if (value == null || value.isJsonNull())
                    continue;

                jsRoot.add(key, value);
            }
        }

        return gson.fromJson(jsRoot, target);
    }
    
    public static <T> T duplicate(T source)
    {
        try
        {
            return(T) BeanUtils.cloneBean(source);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static <T> T clone(T source)
    {
        return(T) convert(source, source.getClass());
    }
    
    public static <T>
        T convert(Object source, Class<T> target)
    {
        //Gson gson = new Gson();
    	Gson gson = new GsonBuilder()
    		.registerTypeAdapter(
    			Timestamp.class, new TimestampAdapter()).create();
        
        return gson.fromJson(gson.toJson(source), target);
    }

    public static <T>
        T convert(JsonElement source, Class<T> target)
    {
        //Gson gson = new Gson();
    	Gson gson = new GsonBuilder()
    		.registerTypeAdapter(
    			Timestamp.class, new TimestampAdapter()).create();
        
        return gson.fromJson(source, target);
    }
    
    public static <T>
    	T convert(String json, Class<T> target)
    {
        //Gson gson = new Gson();
    	Gson gson = new GsonBuilder()
    		.registerTypeAdapter(
    			Timestamp.class, new TimestampAdapter()).create();
        
        return gson.fromJson(json, target);
    }
    
    public static <T> ArrayList<T> toList(JsonArray array, Class<T> clazz)
    {
        ArrayList<T> object = new ArrayList<T>();

        try
        {
            //Gson gson = new Gson();
        	Gson gson = new GsonBuilder()
        		.registerTypeAdapter(
        			Timestamp.class, new TimestampAdapter()).create();

            for(int index = 0; index != array.size(); ++index)
            {
                JsonElement element = array.get(index);
                
                T item =(T) gson.fromJson(element, clazz);
                object.add(item);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return object;
    }
    
    public static <T> ArrayList<T> toList(String json, Class<T> clazz)
    {
        try
        {
            JsonArray array =(JsonArray) (new JsonParser()).parse(json);

            return toList(array, clazz);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonArray i10n(JsonArray array, Locale locale)
    {
        for(int index = 0; index != array.size(); ++index)
        {
            JsonElement element = array.get(index);
            
            if (element.isJsonArray())
            {
                element = i10n(element.getAsJsonArray(), locale);
            }
            
            if (element.isJsonObject())
            {
                element = i10n(element.getAsJsonObject(), locale);
            }
        }
        
        return array;
    }

    public static JsonObject i10n(JsonObject object, Locale locale)
    {
        String localeName = "." + locale.toString();
        
        Set<Map.Entry<String, JsonElement>> entries = object.entrySet();
        
        for(Map.Entry<String, JsonElement> entry: entries)
        {
            String name = entry.getKey();
            JsonElement element = entry.getValue();

            if (element.isJsonArray())
            {
                element = i10n(element.getAsJsonArray(), locale);
            }
            
            if (element.isJsonObject())
            {
                element = i10n(element.getAsJsonObject(), locale);
            }
            
            if (name.endsWith(localeName))
            {
                String i10nName =
                    name.substring(0, name.length() - localeName.length());
                
                object.add(i10nName, element);
            }
        }

        return object;
    }
    
    public static JsonElement parseJson(String json)
    {
        JsonParser parser = new JsonParser();

        return parser.parse(json);
    }

    public static JsonNode parseJsonToJackson(String json)
    {
    	try
    	{
	    	ObjectMapper om = new ObjectMapper();
	    	
	    	return om.readTree(json);
    	} catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
    public static <T> JsonNode toJackson(T object)
    {
    	return parseJsonToJackson(formatJson(object));
    }

    
    public static JsonElement path(JsonElement root, String path)
    {
    	if (root == null)
    		return null;

    	JsonElement value = root;

    	if (StringUtils.isBlank(path))
    		return value;

    	String[] parts = path.split("\\.");
    	for(String part: parts)
    	{
    		Integer index = null;
    		
    		int indexStart = part.indexOf("[");
    		if (indexStart != -1)
    		{
    			int indexEnd = part.indexOf("]");

    			index =
    				Integer.parseInt(
    					part.substring(indexStart + 1, indexEnd));
    			
    			part = part.substring(0, indexStart);
    		}

    		JsonElement leaf = value.getAsJsonObject().get(part);
    		if (index != null && leaf != null)
    		{
    			leaf = leaf.getAsJsonArray().get(index);
    		}
    		
    		value = leaf;
    		
    		if (value == null)
    			break;
    	}
    	
    	return value;
    }
    
    public static class TimestampAdapter
    	implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp>
    {  
        public Timestamp deserialize(JsonElement json, Type type, JsonDeserializationContext context)
        	throws JsonParseException
        {  
            if (json != null)
            {  
            	if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber())
            	{
	                try
	                {  
	                    return new Timestamp(json.getAsLong());  
	                } catch (JsonParseException e)
	                {  
	                    throw e;  
	                }
            	} else
            	if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString())
            	{
            		SimpleDateFormat tmformater =
        				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            		try
            		{
            			return new Timestamp(
            				tmformater.parse(json.getAsString()).getTime());
            		} catch(Exception e)
            		{ }
            		
            		try
            		{
            			return new Timestamp(Long.parseLong(json.getAsString()));
            		} catch(Exception e)
            		{
            			e.printStackTrace();
            		}
            	}
            }

            return null;  
        }  
  
          
        public JsonElement serialize(Timestamp value, Type type, JsonSerializationContext context)
        {  
            if (value != null)
            {  
                return new JsonPrimitive(value.getTime());  
            }
 
            return null;  
        }  
    }  
}
