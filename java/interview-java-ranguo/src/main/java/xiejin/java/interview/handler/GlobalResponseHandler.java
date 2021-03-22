package xiejin.java.interview.handler;



import jdk.nashorn.internal.objects.Global;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import xiejin.java.interview.enums.ResultCode;
import xiejin.java.interview.enums.ResultEnum;
import xiejin.java.interview.exception.GlobalException;
import xiejin.java.interview.result.ResultJson;


import javax.naming.AuthenticationException;

/**
 * @author xiejin
 * @date 2020/7/23 15:41
 */
@RestControllerAdvice
public class GlobalResponseHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof GlobalException) {
            GlobalException globalException = (GlobalException) ex;
            return new ResponseEntity(ResultJson.error(ResultEnum.get(globalException.getErrCode())), status);
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) ex;
            return new ResponseEntity<>(new ResultJson<>(ResultCode.SERVER_ERROR, ex.getMessage()), status);
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            return new ResponseEntity<>(ResultJson.error(ResultCode.BAD_REQUEST, exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            return new ResponseEntity<>(ResultJson.error(ResultCode.BAD_REQUEST, "参数转换失败"), status);
        } else if (ex instanceof BindException) {
            BindException bindException = (BindException) ex;
            String message = bindException.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(ResultJson.error(ResultCode.BAD_REQUEST, message), status);
        }
        return new ResponseEntity<>(new ResultJson<>(ResultCode.SERVER_ERROR, ex.getMessage()), status);

    }

    @ExceptionHandler({GlobalException.class, AuthenticationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultJson sendErrorResponse_UserDefined(Exception exception) {
        if (exception.getCause() instanceof GlobalException ) {
            Integer errorCode = ((GlobalException) exception.getCause()).getErrCode();
            String message = exception.getCause().getMessage();
            return ResultJson.error(ResultEnum.get(errorCode), message);
        } else if (exception instanceof GlobalException) {
            Integer errorCode = ((GlobalException) exception).getErrCode();
            String message = exception.getMessage();
            return ResultJson.error(ResultEnum.get(errorCode), message);
        }
        return ResultJson.error(ResultCode.SERVER_ERROR, exception.getCause().getMessage());
    }
}
