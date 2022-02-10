package com.example.util;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 *
 *
 * @author eric
 * @version on 2017/7/10.
 */
public class ValidatorUtil {

    private static class ValidatorHolder {
        private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static <T> Set<ConstraintViolation<T>> validate(T t) {
        return ValidatorHolder.validator.validate(t);
    }

    /**
     * @Author songjunbao
     * @Description //检验入参，校验不通过抛异常
     * @Date  2019/6/19
     * @Param [t]
     * @return void
     **/
    public static <T> void checkValidateParam(T t){
        Set<ConstraintViolation<T>> result = ValidatorUtil.validate(t);
        StringBuilder resultMsg = new StringBuilder();
        if (result != null && result.size() > 0) {
            for (ConstraintViolation<T> constraintViolation : result) {
                resultMsg.append(constraintViolation.getMessage()).append(";");
            }
        }
        Preconditions.checkArgument(StringUtils.isBlank(resultMsg),resultMsg);
    }
}
