package org.pp.dubbo.serializer;

import java.io.IOException;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.AbstractSerializer;

public abstract class ObjectSerializer<T>
	extends AbstractSerializer
{
	private Class<T> clazz;
	
	public ObjectSerializer(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public void writeObject(Object obj, AbstractHessianOutput out)
		throws IOException
	{
		int def = out.writeObjectBegin(clazz.getName());

		String[] fields = getFields();
		
		if (def < -1)
		{
			for(String field: fields)
			{
				out.writeString(field);
				writeField((T) obj, field, out);
			}
			
			out.writeMapEnd();

			return;
		} else
		if (def == -1)
		{
			out.writeClassFieldLength(fields.length);

			for(String field: fields)
				out.writeString(field);

			out.writeObjectBegin(clazz.getName());
		}

		for(String field: fields)
		{
			writeField((T) obj, field, out);
		}
	}
	
	public abstract String[] getFields();
	public abstract void writeField(T obj, String field, AbstractHessianOutput out)
		throws IOException;
}
