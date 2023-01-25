package url.converter.common;

import lombok.extern.slf4j.Slf4j;
import url.converter.common.constant.ErrorCode;
import url.converter.exception.BadRequestParamException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参数校验
 */
@Slf4j
public class ParamValidator {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validate(Object obj) throws BadRequestParamException {
        Set<ConstraintViolation<?>> violations = new HashSet<>(validator.validate(obj, Default.class));
        if (violations.size() != 0) {
            throw new BadRequestParamException(ErrorCode.BAD_REQUEST_PARAM.code(),
                "参数不合法：" + violations.stream().map(p -> p.getPropertyPath().toString() + p.getMessage())
                    .collect(Collectors.joining("|")));
        }
    }

}
