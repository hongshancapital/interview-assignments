package com.sequoiacap.exception;

import com.sequoiacap.data.Response;

public class WeistoreException
	extends BusinessException
{
	private static final long serialVersionUID = 1337477185117468301L;

	public WeistoreException(int status, String message)
	{
		super(status, message, "");
	}

	public Response<Object> toResponse()
	{
		Response<Object> response = new Response<Object>();

		response.setStatus(getStatus());
		response.setMessage(getMessage());

		return response;
	}
}
