package com.tinyurl.response;

public class ResponseUtil {
    public static <T> ResponseEntity<T> buildResponse(ResponseCode responseCode, T data) {
        return buildResponse(responseCode.getValue(), responseCode.getMessage(), data);
    }

    public static <T> ResponseEntity<T> buildResponse(Integer code, String message, T data) {
        ResponseEntity<T> responseModel = new ResponseEntity<>();
        if (ResponseCode.CODE_SUCCESS.getValue().compareTo(code) == 0) {
            responseModel.setSuccess(true);
        } else {
            responseModel.setSuccess(false);
        }
        responseModel.setCode(code.toString());
        responseModel.setMessage(message);
        if (data != null) {
            responseModel.setData(data);
        }
        return responseModel;
    }

}
