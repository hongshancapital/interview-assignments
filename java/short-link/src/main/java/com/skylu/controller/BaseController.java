package com.skylu.controller;

import com.skylu.constants.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Lu Hao
 * @version 1.0.0
 * @ClassName BaseController.java
 * @Description 基础控制器
 * @createTime 2021年12月07日 11:51:00
 */
public class BaseController {

    protected ResponseEntity<RT> buildResponse(Object object) {
        return new ResponseEntity(RT.builder().bizCode(ErrorCode.REQUEST_SUCCESS.getCode()).message(ErrorCode.REQUEST_SUCCESS.getMessage()).data(object).build(), new HttpHeaders(), HttpStatus.OK);
    }
}
