package com.sequoiacap.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectId
	implements Serializable
{
	private String type;
	private String id;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public static <T> ObjectId valueOf(Class<?> clazz, T targetId)
	{
		ObjectId id = new ObjectId();

		id.type = clazz.getSimpleName();
		id.id = String.valueOf(targetId);
		
		return id;
	}
	
	public static <T> ObjectId valueOf(T object)
	{
		if (object instanceof ObjectId)
			return(ObjectId) object;

		ObjectId id = new ObjectId();

		id.type = object.getClass().getSimpleName();

		try
		{
			Method getId = object.getClass().getMethod("getId");

			id.id = String.valueOf(getId.invoke(object));
		} catch(Exception e)
		{ }
		
		if (id.id == null)
		{
			try
			{
				Field getId = object.getClass().getField("id");

				id.id = String.valueOf(getId.get(object));
			} catch(Exception e)
			{ }
		}
		
		return id;
	}
}
