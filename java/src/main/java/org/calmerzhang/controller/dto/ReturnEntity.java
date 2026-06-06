package org.calmerzhang.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.calmerzhang.common.constant.RetConstant;

/**
 * 返回数据包装类
 *
 * @author calmerZhang
 * @create 2022/01/10 11:04 上午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnEntity<T> {
    private int retCode;
    private String retMsg;
    private T data;

    public static <T> ReturnEntity success(T data) {
        return ReturnEntity.builder().data(data).retCode(RetConstant.SUCCEED).retMsg(RetConstant.SUCCESS).build();
    }

    public static <T> ReturnEntity error(int code, String msg) {
        return ReturnEntity.builder().retCode(code).retMsg(msg).build();
    }
}
