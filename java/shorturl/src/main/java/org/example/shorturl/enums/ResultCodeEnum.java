package org.example.shorturl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.shorturl.constants.HttpStatusConstant;

/**
 * @author bai
 * @date 2021/7/1 9:20
 * 错误码由三段构成 ERR_A_BBBB 其中 A为错误类型,Y:表示业务异常类型,X表示技术异常类型
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum
        implements IResultCode {
    
    /** 操作成功 */
    SUCCESS(HttpStatusConstant.HTTP_OK, "", "成功"),
    /** 业务异常 */
    FAILURE(HttpStatusConstant.HTTP_INTERNAL_ERROR, "ERR-Y-9999", "业务异常! {}"),
    /** 服务器异常 */
    ERR_X_0000(HttpStatusConstant.HTTP_INTERNAL_ERROR, "ERR-X-0000", "系统异常! 请联系管理员!"),
    ;
    /** code编码 */
    final Integer code;
    /** 错误信息编码 */
    final String appCode;
    /** 中文信息描述 */
    final String message;
}
