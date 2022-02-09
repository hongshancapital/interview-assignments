package pers.dongxiang.shorturl.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.dongxiang.shorturl.exception.CheckCodeExecption;
import pers.dongxiang.shorturl.exception.GlobalExecption;
import pers.dongxiang.shorturl.exception.OverLimitException;
import pers.dongxiang.shorturl.exception.UrlExecption;
import pers.dongxiang.shorturl.util.R;



@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * 全局异常.
	 *
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R exception(Exception e) {
		log.error("GlobalExecption异常信息 ex={}", e.getMessage(), e);
		return R.failed("服务异常");
	}

	@ExceptionHandler(GlobalExecption.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R exception(GlobalExecption e) {
		log.error("GlobalExecption异常信息 ex={}", e.getMessage(), e);
		return R.failed("服务异常");
	}

	@ExceptionHandler(OverLimitException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R exception(OverLimitException e) {
		log.error("OverLimitException异常信息 ex={}", e.getMessage(), e);
		return R.failed("超过最大数量限制,服务暂不可用");
	}

	@ExceptionHandler(CheckCodeExecption.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R exception(CheckCodeExecption e) {
		log.error("CheckCodeExecption异常信息 ex={}", e.getMessage(), e);
		return R.failed("校验错误,请检查链接是否正确");
	}

	@ExceptionHandler(UrlExecption.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R exception(UrlExecption e) {
		log.error("UrlExecption异常信息 ex={}", e.getMessage(), e);
		return R.failed("url格式错误,请检查链接是否正确");
	}

}
