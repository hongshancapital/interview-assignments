package com.bighero.demo.shortdns.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bighero.demo.shortdns.domain.exception.AdapterException;
import com.bighero.demo.shortdns.domain.exception.BusinessException;
import com.bighero.demo.shortdns.messages.RespMsg;
import com.bighero.demo.shortdns.url.app.exception.ApplicationException;

/**
 * 异常统一处理方法
 * 
 * @author bighero
 *
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
	/**
	 * 全局捕获异常，只要作用在@RequestMapping方法上，所有的信息都会被捕捉到
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public RespMsg<?> errorHandler(Exception ex) {
		RespMsg errorMsg = new RespMsg();
		errorMsg.setSysStatus("-1");
		errorMsg.setSysMsg(ex.getMessage());
		errorMsg.setSysCode(HttpStatus.BAD_GATEWAY.value());
		return errorMsg;
	}

	/**
	 * 自定义异常
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = BusinessException.class)
	public RespMsg errorBussinessHandler(BusinessException ex) {
		RespMsg errorMsg = new RespMsg();
		errorMsg.setSysStatus("1");
		errorMsg.setSysCode(HttpStatus.OK.value());
		errorMsg.setSysMsg("ok");
		errorMsg.setAppCode(ex.getCode());
		errorMsg.setAppStatus("-1");
		errorMsg.setAppMsg(ex.getMsg());
		return errorMsg;
	}

	/**
	 * 自定义异常
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = ApplicationException.class)
	public RespMsg errorApplicationHandler(ApplicationException ex) {
		RespMsg errorMsg = new RespMsg();
		errorMsg.setSysStatus("-1");
		errorMsg.setSysCode(HttpStatus.BAD_GATEWAY.value());
		errorMsg.setSysMsg(ex.getMessage());
		errorMsg.setAppCode("");
		errorMsg.setAppStatus("");
		errorMsg.setAppMsg("");
		return errorMsg;
	}
}
