package xiejin.java.interview.exception;


import xiejin.java.interview.enums.ResultCode;
import xiejin.java.interview.result.IResultCode;

/**
 * @ClassName: GlobalException
 * @Description: TODO(全局异常)
 * @author xiejin
 * @date 2020年7月7日
 */
public class GlobalException extends RuntimeException {
	
	private static final long serialVersionUID = 3249921589019427751L;
	
	private int errCode = ResultCode.SERVER_ERROR.getCode();
	
    private Object data;

    public GlobalException() {
        super(ResultCode.SERVER_ERROR.getMessage());
    }

    public GlobalException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * GlobalException具体构造
     *
     * @param resultCode ： 返回码抽象的接口
     */
    public GlobalException(IResultCode resultCode) {
        this.errCode = resultCode.getCode();
    }

    /**
     * 自定义异常message
     *
     * @param resultCode
     * @param message
     */
    public GlobalException(IResultCode resultCode, String message) {
        super(message);
        this.errCode = resultCode.getCode();
    }

    /**
     * 自定义异常message
     *
     * @param resultCode
     * @param data
     */
    public GlobalException(IResultCode resultCode, Object data) {
        super(resultCode.getMessage());
        this.errCode = resultCode.getCode();
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

