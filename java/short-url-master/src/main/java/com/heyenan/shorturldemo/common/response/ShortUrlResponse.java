package com.heyenan.shorturldemo.common.response;

import lombok.Data;
import lombok.Generated;

/**
 * @author heyenan
 * @description 返回对象包装类（带泛型）
 *
 * @date 2020/5/07
 */
@Data
@Generated
public class ShortUrlResponse<T> {

    /** 时间戳 */
    private long timestamp;
    /** 操作是否成功 */
    private boolean success;
    /** 操作代码 */
    private int code;
    /** 提示信息 */
    private String message;

    /** 返回的数据 */
    private T shortUrl;

    public ShortUrlResponse() {
        this(Encode.SUCCESS);
    }

    public ShortUrlResponse(Encode resultCode) {
        this.success = resultCode.isSuccess();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public static <E> ShortUrlResponse<E> success(E data) {
        ShortUrlResponse<E> baseResponse = new ShortUrlResponse<>();
        baseResponse.setData(data);
        return baseResponse;
    }

    private void setData(T data) {
        this.shortUrl = data;
    }

    public static ShortUrlResponse<Encode> error(Encode resultCode) {
        return new ShortUrlResponse<>(resultCode);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
