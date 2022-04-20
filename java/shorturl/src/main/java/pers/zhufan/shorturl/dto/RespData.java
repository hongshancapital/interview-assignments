package pers.zhufan.shorturl.dto;

import lombok.Data;
import pers.zhufan.shorturl.constant.ShortUrlConstant;

import java.io.Serializable;

@Data
public class RespData<T> implements Serializable {
    private Result result;
    private T body;

	public RespData() {
	}

	public RespData(Result result, T body) {
		this.result = result;
		this.body = body;
	}

	/**
	 * @Author zhufan
	 * @Description 获取返回消息信息
	 **/
	public String codeMsg(){
		return this.result.getMsg();
	}

	/**
	 * @Author zhufan
	 * @Description 判断当前调用状态
	 **/
	public boolean codeSuccess(){

        return ShortUrlConstant.SUCCESS.equals(this.result.getCode());

    }

	/**
	 * @Author zhufan
	 * @Description 组装失败报文对象
	 **/
	public RespData<String> fail(String msg){
		
		return new RespData<String>(new Result(ShortUrlConstant.FAIL,msg),ShortUrlConstant.EMPTY_BODY);
		
	}

	/**
	 * @Author zhufan
	 * @Description 组装失败报文对象
	 **/
	public RespData<T> fail(String msg, T body){
		
		return new RespData<T>(new Result(ShortUrlConstant.FAIL,msg),body);
		
	}

	/**
	 * @Author zhufan
	 * @Description 组装成功报文对象
	 **/
	public RespData<T> success(String msg, T body){
		
		return new RespData<T>(new Result(ShortUrlConstant.SUCCESS,msg),body);
		
	}

	/**
	 * @Author zhufan
	 * @Description 组装成功报文对象
	 **/
	public RespData<T> success(String msg){

		return new RespData<T>(new Result(ShortUrlConstant.SUCCESS,msg),body);

	}


}
