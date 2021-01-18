package com.wb.http_server.response;

import lombok.*;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Header {
    private String key;
    private String value;
}
