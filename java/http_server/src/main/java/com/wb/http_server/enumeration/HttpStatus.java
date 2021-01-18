package com.wb.http_server.enumeration;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public enum HttpStatus {
    OK(200),NOT_AUTH(401),NOT_FOUND(404),INTERNAL_SERVER_ERROR(500),BAD_REQUEST(400),MOVED_TEMPORARILY(302);
    private int code;
    HttpStatus(int code){
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
