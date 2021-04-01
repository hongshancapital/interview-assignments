package org.pp.dubbo.serializer;

import java.io.IOException;

import org.springframework.data.domain.PageImpl;

import com.alibaba.com.caucho.hessian.io.AbstractDeserializer;
import com.alibaba.com.caucho.hessian.io.AbstractHessianInput;
import com.sequoiacap.data.Pager;

public class PageDeserializer
	extends ObjectDeserializer<Pager>
{
	public PageDeserializer()
	{
		super(Pager.class);
	}

	public Object readObject(AbstractHessianInput in, String fieldNames[])
	    throws IOException
	{
		Pager pager =(Pager) in.readObject();

		return new PageImpl(
			pager.getResult(), pager.toPageable(),
			pager.getTotalElements());
	}
}
