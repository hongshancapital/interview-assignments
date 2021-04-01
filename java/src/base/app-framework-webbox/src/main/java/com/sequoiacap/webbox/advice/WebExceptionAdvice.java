package com.sequoiacap.webbox.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sequoiacap.data.*;
import com.sequoiacap.exception.BusinessException;
import com.sequoiacap.utils.JsonHelper;
import com.sequoiacap.utils.Utils;

@ControllerAdvice(annotations = Controller.class)
public class WebExceptionAdvice
{
	private static Log log = LogFactory.getLog(WebExceptionAdvice.class);

	private Boolean turnErrorStatus = true;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.util.Date.class, new DateEditor());
        binder.registerCustomEditor(java.sql.Date.class, new DateEditor());
        binder.registerCustomEditor(java.sql.Timestamp.class, new DateEditor());
    }
	
	public WebExceptionAdvice(String turnErrorStatus)
	{
		this(Boolean.valueOf(turnErrorStatus));
	}
	
	public WebExceptionAdvice(Boolean turnErrorStatus)
	{
		this.turnErrorStatus = turnErrorStatus;
		
		log.info(String.format(
			"WebExceptionAdvice.turnErrorStatus %s",
			String.valueOf(this.turnErrorStatus)));
	}

	@ResponseBody @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String>
		handleWebException(BusinessException exception)
	{
		log.error("BusinessException@WebExceptionAdvice", exception);

		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-Type", "application/json;charset=utf-8");
		
		return new ResponseEntity<String>(
			JsonHelper.formatJson(
				exception.toResponse()), headers,
				turnErrorStatus?
					HttpStatus.INTERNAL_SERVER_ERROR: HttpStatus.OK);
	}

	@ResponseBody @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String>
		handleWebException(Exception exception)
	{
		log.error("Exception@WebExceptionAdvice", exception);

		Response<Object> response = new Response<Object>();

		response.setStatus(500);
		response.setMessage(exception.toString());

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json;charset=utf-8");
		
		return new ResponseEntity<String>(
			JsonHelper.formatJson(response), headers,
				turnErrorStatus?
					HttpStatus.INTERNAL_SERVER_ERROR: HttpStatus.OK);
	}
/*
	@ResponseBody @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Fault.class)
	public ResponseEntity<String> handleFault(SoapFault exception)
	{
		log.error("Exception@WebExceptionAdvice", exception);

		Response<Object> response = new Response<Object>();

		response.setStatus(500);
		
		try
		{
			response.setStatus(Integer.parseInt(exception.getRole()));
		} catch(Exception e)
		{ }

		response.setMessage(exception.getMessage());

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/json;charset=utf-8");
		
		return new ResponseEntity<String>(
			JsonHelper.formatJson(response), headers,
				turnErrorStatus?
					HttpStatus.INTERNAL_SERVER_ERROR: HttpStatus.OK);
	}
	
	@ResponseBody @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(SOAPFaultException.class)
	public ResponseEntity<String>
		handleSOAPFaultException(SOAPFaultException exception)
	{
		log.error("Exception@WebExceptionAdvice", exception);

		SoapFault fault =(SoapFault) exception.getCause();
		
		return handleFault(fault);
	}
*/
}
