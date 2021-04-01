package org.pp.dubbo.serializer;

import java.io.IOException;

import com.sequoiacap.utils.JsonHelper;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.AbstractSerializer;

public class GsonSerializer
	extends AbstractSerializer
{
	@Override
	public void writeObject(Object obj, AbstractHessianOutput out)
		throws IOException
	{
		out.writeString(JsonHelper.formatJson(obj));
	}
}
