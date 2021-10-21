package hongshan.demo.beans;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 结果实体
 * @class hongshan.demo.beans.Result
 * @author 许坤
 * @email klxukun@126.com
 * @time 2021年10月18日 下午10:30:55
 *
 */
public class Result implements Serializable {
	/** 成功常量代码 * */
	@ApiModelProperty("成功常量代码")
	public static final String SUCCESS = "success";
	/** 失败常量代码 * */
	@ApiModelProperty("失败常量代码")
	public static final String FAILURE = "failure";

	/** 错误代码 * */
	@ApiModelProperty("错误代码")
	private String errorCode;
	/** 错误描述 * */
	@ApiModelProperty("错误描述")
	private String descript;
	/** 结果（即域名） * */
	@ApiModelProperty("结果（即域名）")
	private String domain;

	/**
	 * 获取错误代码
	 * @return
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 设置错误代码
	 * @param errorCode
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 获取错误描述
	 * @return
	 */
	public String getDescript() {
		return descript;
	}

	/**
	 * 设置错误描述
	 * @param descript
	 */
	public void setDescript(String descript) {
		this.descript = descript;
	}

	/**
	 * 获取域名串
	 * @return
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * 设置域名串
	 * @param domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * 创建结果实例并标记为错误
	 * @return 结果实例
	 */
	public static final Result error() {
		return error(null);
	}

	/**
	 * 创建结果实例并标记为错误
	 * @param error 错误信息
	 * @return 结果实例
	 */
	public static final Result error(String error) {
		Result ret= new Result();
		ret.errorCode = FAILURE;
		ret.descript = error;
		
		return ret;
	}

	/**
	 * 创建结果实例并标记为成功
	 * @return 结果实例
	 */
	public static final Result success() {
		return success(null);
	}

	/**
	 * 创建结果实例并标记为成功和记录给定的域名
	 * @param domain 域名信息
	 * @return 结果实例
	 */
	public static final Result success(String domain) {
		Result ret= new Result();
		ret.errorCode = SUCCESS;
		ret.domain= domain;
		
		return ret;
	}
	
}
