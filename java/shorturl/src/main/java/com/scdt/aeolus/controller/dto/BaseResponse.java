package com.scdt.aeolus.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "Code")
    private ErrorCode errorCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "Message")
    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "Response")
    private BaseInnerResponse innerResponse;

    public BaseResponse(ErrorCode code, String errorMessage) {
        this.errorCode = code;
        this.errorMessage = errorMessage;
    }

    public BaseResponse(BaseInnerResponse innerResponse) {
        this.innerResponse = innerResponse;
    }

    public BaseInnerResponse getInnerResponse() {
        return innerResponse;
    }
}
