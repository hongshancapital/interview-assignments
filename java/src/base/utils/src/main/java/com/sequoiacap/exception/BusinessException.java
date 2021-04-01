package com.sequoiacap.exception;

import java.util.ResourceBundle;

import javax.xml.namespace.QName;

import com.sequoiacap.data.Response;

import net.jhorstmann.i18n.I18N;

public class BusinessException
	extends RuntimeException
{
	private static final long serialVersionUID = 1337477185117468301L;

	private int status;
	private String message;
	private Object result;
	private Object[] args;

	public BusinessException(
		int status, String message)
	{
		this.status = status;
		this.message = message;
		this.args = args;
	}
	
	public BusinessException(
		int status, String message, Object result, Object ... args)
	{
		this.status = status;
		this.message = message;
		this.result = result;
		this.args = args;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Response<Object> toResponse()
	{
		Response<Object> response = new Response<Object>();

		response.setStatus(status);
		response.setMessage(I18N.tr(message, args));
		response.setResult(result);

		return response;
	}
}
