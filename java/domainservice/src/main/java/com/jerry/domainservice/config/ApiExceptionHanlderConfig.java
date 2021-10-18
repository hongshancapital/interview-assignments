package com.jerry.domainservice.config;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.jerry.domainservice.common.dto.ResultDto;
import com.jerry.domainservice.common.dto.ResultEnum;

/**
 * Exception handler configuration for core.
 * 
 * @author jerry
 *
 */
@ControllerAdvice
public class ApiExceptionHanlderConfig {

	
	/**
	 * Handle no handler found exception
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseEntity<ResultDto> handleNotFound(HttpServletRequest req,NoHandlerFoundException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json").body(new ResultDto(ResultEnum.INVALID_REQUEST.getCode(),"Bad request."));
    }
	
	
	/**
	 * Handle all the other exceptions that haven't been handled.
	 * @param e
	 * @return
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<ResultDto> handleUnCatchedException(HttpServletRequest req,Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json").body(new ResultDto(ResultEnum.INVALID_REQUEST.getCode(),e.getMessage()));
	}
	
}
