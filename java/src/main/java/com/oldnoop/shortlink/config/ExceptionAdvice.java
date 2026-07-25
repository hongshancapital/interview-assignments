package com.oldnoop.shortlink.config;

import com.oldnoop.shortlink.model.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler({ RuntimeException.class })
	@ResponseBody
	public ApiResult<?> handleEx(Exception e) {
		return ApiResult.fail(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "服务器异常");
	}

}