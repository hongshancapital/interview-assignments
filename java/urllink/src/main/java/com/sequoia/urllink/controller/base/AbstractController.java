package com.sequoia.urllink.controller.base;


import com.sequoia.urllink.base.exception.InvalidBusinessException;
import com.sequoia.urllink.base.exception.InvalidParamException;
import com.sequoia.urllink.base.model.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 目前添加通用的，基本的验证方法；对于特殊业务，
 * 需要根据情况编写
 * @author liuhai
 * @date 2022.4.15
 */
public class AbstractController {
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LocalValidatorFactoryBean validator;



    /**
     * 通用的异常处理
     *
     * @param callback callback
     * @return ResultMessage  exception handling
     */
    protected <T> ResultMessage<T> handleException(Supplier<ResultMessage<T>> callback, HttpServletRequest request) {
        try {
            return callback.get();
        } catch (InvalidParamException | InvalidBusinessException ex) {
            logger.error(ex.getMessage(), ex);
            return ResultMessage.createFailMessage(ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResultMessage.createErrorMessage(ResultMessage.ResultCode.ERROR.getName());
        }
    }

    /**
     * 通用的异常处理
     *
     * @param callback callback
     * @return ResultMessage  exception handling
     */
    protected <T> ResultMessage<T> handleException(Supplier<ResultMessage<T>> callback) {
        try {
            return callback.get();
        } catch (InvalidParamException | InvalidBusinessException ex) {
            logger.error(ex.getMessage(), ex);
            ResultMessage response = ResultMessage.createFailMessage(ex.getMessage());
            return response;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ResultMessage response = ResultMessage.createErrorMessage(ResultMessage.ResultCode.ERROR.getName());
            return response;
        }
    }

    /**
     * 验证实体类参数
     *
     * @param target    target
     * @param separator separator
     * @throws InvalidParamException
     */
    protected void validatorBean(Object target, String... separator) {
        StringBuilder error = new StringBuilder();
        String sep = separator.length > 0 ? separator[0] : ";";

        Set<ConstraintViolation<Object>> errorMsgSet = validator.validate(target);
        for (ConstraintViolation<Object> obj : errorMsgSet) {
            if (error.length() > 0) {
                error.append(sep);
            }
            error.append(obj.getMessage());
        }

        String errorMsg = error.toString();
        if (StringUtils.isNotBlank(errorMsg)) {
            throw new InvalidParamException(errorMsg);
        }
    }
}
