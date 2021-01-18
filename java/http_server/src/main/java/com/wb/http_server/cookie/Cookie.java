package com.wb.http_server.cookie;

import lombok.*;

/**
 * @author bing.wang
 * @date 2021/1/15
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cookie {
    private String key;
    private String value;
}
