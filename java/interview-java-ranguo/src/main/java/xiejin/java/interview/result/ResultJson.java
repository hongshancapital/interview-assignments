package xiejin.java.interview.result;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xiejin.java.interview.enums.ResultCode;

import java.io.Serializable;

/**
 * @author kinbug
 * RESTful API 返回类型 ,统一返回处理
 * Created at 2018/3/8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultJson<T> implements Serializable{

    private static final long serialVersionUID = 783015033603078674L;

    private int code;
    
    private String message;
    
    private T data;
    
    public static ResultJson<Object> SUCCESS = new ResultJson<>(ResultCode.SUCCESS);
    
    public static ResultJson<Object> ERROR = new ResultJson<>(ResultCode.SERVER_ERROR);


    //成功
    public static  <T> ResultJson<T> success(T data) {
        return new ResultJson<T>(ResultCode.SUCCESS, data);
    }


    //失败
    public static <T> ResultJson<T> error(IResultCode resultCode, T data) {
    	return  new ResultJson<T>(resultCode,data);
    }
    
    public static <T> ResultJson<T> error(IResultCode resultCode) {
    	return  error(resultCode, null);
    }
    
    public ResultJson (IResultCode resultCode) {
        setResultCode(resultCode);
    }

    public ResultJson (IResultCode resultCode,T data) {
        setResultCode(resultCode);
        this.data = data;
    }

    public void setResultCode(IResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
