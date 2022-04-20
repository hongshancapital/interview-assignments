package pers.zhufan.shorturl.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable{
    private static final long serialVersionUID = 1L;

    /**错误代码*/
    private String code;
    /**错误信息*/
    private String msg;

    public Result() {
    }
	public Result(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

}
