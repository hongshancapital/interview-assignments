package com.config;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;

@Getter
public class ResultException extends RuntimeException {
    /**
     * 业务异常信息信息
     */
    ResultStatus resultStatus;
    /**
     * 信息描述
     */
    private String message;



    public ResultException(ResultStatus resultStatus) {
        super(resultStatus.getMessage());
        this.resultStatus = resultStatus;
    }

    public ResultException(String message) {
        this.message = message;
        this.resultStatus = ResultStatus.BAD_REQUEST;
    }

    public ResultException(ResultStatus resultStatus, String message) {
        this.message = StringUtils.isNotBlank(message) ? message : resultStatus.getMessage();
        this.resultStatus = resultStatus;
    }
}
