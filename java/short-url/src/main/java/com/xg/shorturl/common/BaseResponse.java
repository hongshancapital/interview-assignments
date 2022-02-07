package com.xg.shorturl.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xionggen
 */
@ApiModel("响应结果")
@NoArgsConstructor
@Getter
public final class BaseResponse<T> implements Serializable {

    @ApiModelProperty("是否成功")
    private boolean success;

    @ApiModelProperty("错误码")
    private Integer errorCode;

    @ApiModelProperty("错误信息")
    private String errorMsg;

    @ApiModelProperty("返回结果")
    private T result;

    private BaseResponse(Builder<T> builder) {
        this.success = builder.success;
        this.errorCode = builder.errorCode;
        this.errorMsg = builder.errorMsg;
        this.result = builder.result;
    }

    /**
     * 生成一个成功对象
     * @param <T> 返回数据类型
     * @return 构造器
     */
    public static <T> Builder<T> newSuccessResponse() {
        return new Builder<T>().success(true);
    }

    /**
     * 生成一个失败对象
     * @param <T> 返回数据类型
     * @return 构造器
     */
    public static <T> Builder<T>  newFailResponse() {
        return new Builder<T>().success(false);
    }

    public static final class Builder<R> {
        private boolean success = false;
        private Integer errorCode;
        private String errorMsg;
        private R result;

        public BaseResponse<R> build() {
            return new BaseResponse<>(this);
        }

        public Builder<R> success(boolean success) {
            this.success = success;
            this.errorCode = BaseErrorCode.SUCCESS.getCode();
            return this;
        }

        public Builder<R> errorCode(Integer errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder<R> errorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public Builder<R> result(R result) {
            this.result = result;
            return this;
        }
    }
}
