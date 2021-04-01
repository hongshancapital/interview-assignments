package org.pp.dubbo.serializer;

import java.io.IOException;

import com.sequoiacap.utils.JsonHelper;
import org.springframework.data.geo.Point;

import com.alibaba.com.caucho.hessian.io.AbstractDeserializer;
import com.alibaba.com.caucho.hessian.io.AbstractHessianInput;
import com.google.gson.JsonObject;

public class GsonDeserializer
	extends AbstractDeserializer
{
	private Class<?> clazz;
	
	public GsonDeserializer(Class<?> clazz)
	{
		this.clazz = clazz;
	}
	
	public Object readObject(AbstractHessianInput in)
	    throws IOException
    {
		return JsonHelper.convert(in.readString(), clazz);
    }

	public Object readObject(AbstractHessianInput in, String []fieldNames)
	    throws IOException
	{
		return readObject(in);
	}
}
