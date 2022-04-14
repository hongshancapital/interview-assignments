package com.mjl.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response<Module> implements Serializable {
    private static final long serialVersionUID = -3296389567312245990L;
    private Module module;
    private boolean success;
    private String errorCode;

    public static <Module> Response<Module> success(Module module) {
        Response<Module> response = new Response();
        response.success = true;
        response.module = module;
        return response;
    }

    public static <Module> Response<Module> failed(String errorCode) {
        Response response = new Response();
        response.success = false;
        response.errorCode = errorCode;
        return response;
    }
}
