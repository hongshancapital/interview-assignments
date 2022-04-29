package com.youming.sequoia.sdn.apipublic.controller.advice;


import com.youming.sequoia.sdn.apipublic.constant.ResponseResultMsgEnum;
import com.youming.sequoia.sdn.apipublic.exception.AuthorizationException;
import com.youming.sequoia.sdn.apipublic.exception.UnauthenticatedException;
import com.youming.sequoia.sdn.apipublic.utils.JsonTransformUtils;
import com.youming.sequoia.sdn.apipublic.utils.ResponseResultUtils;
import com.youming.sequoia.sdn.apipublic.vo.response.ResponseResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 全局异常处理，捕获所有Controller中抛出的异常。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 处理mvc参数校验错误
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public void errorParamHandler(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException ex) {
        logger.info("错误类名：" + ex.getClass().getName());
        Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations();
        logger.info("数量：" + constraintViolationSet.size());
        List<String> errorInfoList = new ArrayList<String>();
        for (ConstraintViolation<?> cMsg : constraintViolationSet) {
            logger.info("错误的方法和参数名称：" + cMsg.getPropertyPath().toString());        //能取到方法名和参数名
            //logger.info(cMsg.getInvalidValue() + ":" + cMsg.getMessage());
            errorInfoList.add(cMsg.getMessage() + ",输入值:" + cMsg.getInvalidValue());
        }
        String errorMsg = errorInfoList.stream().collect(Collectors.joining("|"));
        ResponseResultVO<Object> responseResultVO = ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.BAD_REQUEST_PARAM);
        /*
        Map<String, Object> payloadMap = new HashMap<String, Object>();
        payloadMap.put("errorInfoList", errorInfoList);
        returnMap.put("data", payloadMap);
         */
        responseResultVO.setMsg(responseResultVO.getMsg() + ":" + errorMsg);
        String returnMsg = JsonTransformUtils.toJson(responseResultVO);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setStatus(HttpServletResponse.SC_BAD_REQUEST);		//400错误
        try {
            response.getWriter().write(returnMsg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 没有token或token出错都会触发这个异常
     */
    @ExceptionHandler({UnauthenticatedException.class})
    public void unAuthenticatedHandler(HttpServletRequest request, HttpServletResponse response,
                                       UnauthenticatedException ex) {
        logger.info("request:" + request.getRequestURI() + ";UnauthenticatedException：" + ex.getMessage());
        ResponseResultVO<Object> responseResultVO = ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.NO_LOGIN);
        responseResultVO.setMsg(responseResultVO.getMsg() + ":" + ex.getMessage());
        String returnMsg = JsonTransformUtils.toJson(responseResultVO);

        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().write(returnMsg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 权限不够时触发该异常
     */
    @ExceptionHandler({AuthorizationException.class})
    public void unPermissionsHandler(HttpServletRequest request, HttpServletResponse response, AuthorizationException ex) {
        logger.info("request:" + request.getRequestURI() + ";AuthorizationException：" + ex.getMessage());
        String returnMsg = ResponseResultUtils.getResponseResultStr(ResponseResultMsgEnum.NO_PERMISSION);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().write(returnMsg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 异常时将被该方法拦截，拦截其它所有异常
     */
    @ExceptionHandler({Exception.class})
    public void errorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw, true)); // 打印完整堆栈信息到
        logger.error(sw.toString(), "SLF4J");

        ResponseResultVO<Object> responseResultVO = ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.GO_MARS);
        responseResultVO.setMsg(ex.getMessage());
        String returnMsg = JsonTransformUtils.toJson(responseResultVO);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().write(returnMsg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
