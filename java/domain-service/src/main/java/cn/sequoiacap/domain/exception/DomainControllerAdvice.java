package cn.sequoiacap.domain.exception;

import cn.sequoiacap.domain.entity.ResponseUtils;
import cn.sequoiacap.domain.entity.ResponseVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获
 *
 * @author liuhao
 * @date 2021/12/10
 */
@ControllerAdvice
public class DomainControllerAdvice {

    /**
     * 专门处理DomainException抛出的异常信息
     *
     * @param e
     * @return
     */
    @ExceptionHandler({DomainException.class})
    @ResponseBody
    public ResponseVO handleDomainException(Exception e){
        return ResponseUtils.fail(e.getMessage());
    }
}
