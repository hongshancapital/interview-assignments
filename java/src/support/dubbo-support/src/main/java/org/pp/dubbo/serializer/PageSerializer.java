package org.pp.dubbo.serializer;

import java.io.IOException;

import org.springframework.data.domain.Page;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.AbstractSerializer;
import com.sequoiacap.data.Pager;

public class PageSerializer
	extends ObjectSerializer<Page>
{
	public PageSerializer()
	{
		super(Page.class);
	}

	@Override
	public String[] getFields()
	{
		return new String[] {
			"pager"
		};
	}

	@Override
	public void writeField(
		Page obj, String field, AbstractHessianOutput out)
		throws IOException
	{
		Pager pager = Pager.valueOf((Page) obj);

		out.writeObject(pager);
	}
}
