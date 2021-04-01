package com.sequoiacap.data;

import java.util.Locale;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import com.sequoiacap.exception.BusinessException;

import net.jhorstmann.i18n.I18N;

public class ResponseAdapterFuture<T>
	extends AdapterFuture<T, Response<T>>
{
	private Locale locale = Locale.getDefault();

	public ResponseAdapterFuture(Future<T> future, Locale locale)
	{
		super(future);
		
		locale = locale;
	}

	protected void visit(Response<T> response)
	{ }

	@Override
	protected Response<T> adapter(T res, Throwable e)
	{
		Response<T> response = new Response<T>();

		if (res != null)
		{
			response.setResult(res);
		} else
		{
			if (e instanceof BusinessException)
			{
				BusinessException be =(BusinessException) e;
				
				response.setStatus(be.getStatus());
				response.setMessage(
					StringUtils.isNotBlank(be.getMessage())?
						I18N.tr(locale, be.getMessage()): "");
			} else
			{
				response.setStatus(500);
				response.setMessage(
					StringUtils.isNotBlank(e.getMessage())?
						I18N.tr(locale, e.getMessage()): e.toString());
			}
		}

		visit(response);

		return response;
	}
}
