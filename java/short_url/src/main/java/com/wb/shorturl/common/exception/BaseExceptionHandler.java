package com.wb.shorturl.common.exception;

import com.wb.shorturl.common.web.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 异常处理
 * @author bing.wang
 * @date 2021/1/8
 */


@ControllerAdvice
public class BaseExceptionHandler {


	private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

	/**
	 * 全局异常捕捉处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public String errorHandler(Exception ex) {
		logger.error("捕获到Exception异常",ex);
		return "redirect:/error/500";
	}


}