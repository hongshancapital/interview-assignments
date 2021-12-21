package com.scdt.shorturl.exception;

import com.scdt.shorturl.model.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {

	private static final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

	@ExceptionHandler(BizInValidException.class)
	public ResponseEntity handleBizInValidException(BizInValidException ex) {
		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Res(Res.ErrorCode.INVALID.getCode(),ex.getMessage()));
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(Exception ex) {
		log.error("system exception！",ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Res(Res.ErrorCode.ERROR.getCode(),"system exception！"));
	}

}