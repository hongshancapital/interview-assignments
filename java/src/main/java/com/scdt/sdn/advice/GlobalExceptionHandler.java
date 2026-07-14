package com.scdt.sdn.advice;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scdt.sdn.constants.SdnConstants;
import com.scdt.sdn.dto.ResponseDto;
import com.scdt.sdn.exceptions.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseDto globalException(HttpServletResponse response, RuntimeException ex) {
        ResponseDto dto = new ResponseDto();
        dto.setCode(response.getStatus());
        dto.setMessage(ex.getMessage());
        return dto;
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseDto globalException(HttpServletResponse response, NotFoundException ex) {
        ResponseDto dto = new ResponseDto();
        dto.setCode(SdnConstants.CODE500);
        dto.setMessage(ex.getMessage());
        return dto;
    }
}