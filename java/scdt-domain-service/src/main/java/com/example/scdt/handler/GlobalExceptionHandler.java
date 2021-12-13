package com.example.scdt.handler;

import com.example.scdt.constant.CommonRespCode;
import com.example.scdt.dto.HttpResponseDTO;
import com.example.scdt.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器, 应用端在configuration的自定类中 集成基础类，扩展注解方法
 * @author
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * error级别异常拦截
	 */
	@ResponseBody
	@ExceptionHandler(value = Throwable.class)
	public HttpResponseDTO<?> exceptionHandler(Throwable e) {
		logger.error("服务异常,throwable:{}",e.getMessage(),e);
		return HttpResponseDTO.fail(CommonRespCode.UNKONWN_ERROR.getCode(),
				CommonRespCode.UNKONWN_ERROR.getMsg(),"数据处理异常");
	}

	/**
	 * 对不能单独处理的异常进行统一处理
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public HttpResponseDTO<?> exceptionHandler(Exception e) {
		logger.error("服务异常,Exception:{},堆栈信息:",e.getMessage(),e);
		return HttpResponseDTO.fail(CommonRespCode.UNKONWN_ERROR.getCode(),
				CommonRespCode.UNKONWN_ERROR.getMsg(),"数据处理异常");
	}

	/**
	 * 对业务异常进行处理，自定义的业务异常可以打印日志
	 */
	@ResponseBody
	@ExceptionHandler(value = BusinessException.class)
	public HttpResponseDTO<?> exceptionHandler(BusinessException e){
		return HttpResponseDTO.fail(e.getCode(),e.getMessage(),"");
	}

}