package com.java.assignment.web.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author jacy
 */
@ApiModel("返回数据")
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int NO_LOGIN = -1;

    public static final int SUCCESS = 200;

    public static final int FAIL = 500;

    public static final int NO_PERMISSION = -2;

    @ApiModelProperty("描述")
    private String msg = "操作成功";

    private R r;

    @ApiModelProperty("code")
    private int code = SUCCESS;


    @ApiModelProperty("对象")
    private Object data;

    public R() {
        super();
        r = this;
        this.data = "";
    }

    public R(Object data) {
        super();
        r = this;
        if (data == null) {
            this.data = "";
        } else {
            this.data = data;
        }

    }

    public R(Object data, String msg) {
        super();
        if (data == null) {
            this.data = "";
        } else {
            this.data = data;
        }
        this.msg = msg;
    }

    public R(int code, Object data, String msg) {
        super();
        this.code = code;
        if (data == null) {
            this.data = "";
        } else {
            this.data = data;
        }
        this.msg = msg;
    }

    public R<T> data(Object o) {
        if (r == null) {
            if (this != null) {
                r = this;
            } else {
                r = new R();
            }
        }
        setD(o);
        return r;
    }

    private void setD(Object o) {
        if (o == null) {
            r.data = "";
        } else {
            r.data = o;
        }
    }

    public R<T> code(Integer code) {
        if (r == null) {
            if (this != null) {
                r = this;
            } else {
                r = new R();
            }
        }
        r.code = code;
        return r;
    }

    public R<T> msg(String msg) {
        if (r == null) {
            if (this != null) {
                r = this;
            } else {
                r = new R();
            }
        }
        r.msg = msg;
        return r;
    }


    public R<T> success(Object o, String msg, Integer code) {
        if (r == null) {
            if (this != null) {
                r = this;
            } else {
                r = new R();
            }
        }
        r.code = code;
        setD(o);
        r.msg = msg;
        return r;
    }

    public R<T> success(Object o, String msg) {
        if (r == null) {
            if (this != null) {
                r = this;
            } else {
                r = new R();
            }
        }
        setD(o);
        r.msg = msg;
        return r;
    }

    public R<T> success(String msg) {
        if (r == null) {
            if (this != null) {
                r = this;
            } else {
                r = new R();
            }
        }
        r.data = "";
        r.msg = msg;
        return r;
    }

    public R<T> fail(Object o, String msg) {
        if (r == null) {
            if (this != null) {
                r = this;
            } else {
                r = new R();
            }
        }
        r.code = FAIL;
        setD(o);
        r.msg = msg;
        return r;
    }

    public R<T> fail(String msg) {
        if (r == null) {
            r = new R();
        }
        r.code = FAIL;
        r.data = "";
        r.msg = msg;
        return r;
    }

    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
        this.data = "";
    }


    public R(Integer code, String message) {
        super();
        this.code = code;
        this.msg = message;
        this.data = "";
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
