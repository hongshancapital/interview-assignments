package me.huchao.sd.web;

import lombok.Getter;

/**
 * @author huchao36
 */
@Getter
public class Resp<T> {

    private String msg;
    private int code;
    private T data;

    public Resp() {
        super();
    }

    public Resp(int code) {
        this();
        this.code = code;
    }

    public Resp<T> msg(String msg){
        this.msg = msg;
        return this;
    }

    public Resp<T> data(T data){
        this.data = data;
        return this;
    }
}
