package org.pp.dubbo.serializer;

import java.io.IOException;

import com.alibaba.com.caucho.hessian.io.AbstractDeserializer;
import com.alibaba.com.caucho.hessian.io.AbstractHessianInput;

public abstract class ObjectDeserializer<T>
	extends AbstractDeserializer
{
	private Class<T> clazz;
	
	public ObjectDeserializer(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	public abstract Object readObject(AbstractHessianInput in, String fieldNames[])
		throws IOException;
}
