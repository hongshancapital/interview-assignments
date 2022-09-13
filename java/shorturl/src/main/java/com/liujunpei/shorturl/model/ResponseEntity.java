package com.liujunpei.shorturl.model;

import lombok.Data;

/**
 * @author 刘俊佩
 * @date 2022/1/26 下午2:19
 */
@Data
public class ResponseEntity {
    private Integer code;
    private String result;
    private String message;
}
