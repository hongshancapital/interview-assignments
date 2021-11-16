package pers.jenche.convertdomain.core;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.jenche.convertdomain.dto.ResponseResultDTO;

/**
 *  Copyright (c) 2020 By www.jenche.cn
 * @author jenche E-mail:jenchecn@outlook.com
 * @date 2020/5/24 16:47
 * @description 定义异常处理输出的Controller
 */
@ControllerAdvice
public class ExceptionToController {
    @ExceptionHandler({SystemException.class})
    @ResponseBody
    public ResponseResultDTO ServiceException(SystemException ex) throws SystemException {
        ResponseResultDTO result = new ResponseResultDTO();
        result.setCode(ex.getErrCode());
        result.setMessage(ex.getMessage());
        return new ResponseResult<String>(result).toDTO();
    }
}
