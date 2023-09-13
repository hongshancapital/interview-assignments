package cn.sequoiacap.domain.entity;

import org.springframework.http.HttpStatus;

/**
 * 封装统一响应对象
 *
 * @author liuhao
 * @date 2021/12/10
 */
public class ResponseUtils {

    public static ResponseVO success() {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(HttpStatus.OK.value());
        responseVO.setMessage("success");
        return responseVO;
    }

    public static ResponseVO success(Object object) {
        ResponseVO responseVO = success();
        responseVO.setData(object);
        return responseVO;
    }


    public static ResponseVO fail(String message) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseVO.setMessage(message);
        return responseVO;
    }

}
