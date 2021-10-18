package com.jerry.domainservice.common.dto;

public enum ResultEnum {
 
    /**
     * 200:Success
     */
    SUCCESS(200, "Success"),
    /**
     * 404: 
     */
    NOT_FOUND(404, "Resouce not found"),
    
    /**
     * 403: 
     */
    FORBIDDEN(403, "No authority"),
    /**
     * 400: 
     */
    INVALID_REQUEST(400, "Invalid Request"),
    
    PARAM_ERROR(400, "Request paramter is not correct"),
    
    /**
     * 405: 
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    /**
     * 406
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    /**
     * 500
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    /**
     * 999
     */
    UNKNOW_ERROR(999, "Unknown Rrror");
 
    private int code;
 
    private String message;
 
    private ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
 
    public int getCode() {
        return code;
    }
 
    public String getMessage() {
        return message;
    }
 
}