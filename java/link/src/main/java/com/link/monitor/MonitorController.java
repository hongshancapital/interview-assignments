package com.link.monitor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 检测服务是否可用
 *
 * @author zong_hai@126.com
 * @date 2022-01-21
 * @desc
 */
@RestController
public class MonitorController {

    @GetMapping(value = "/monitor")
    public String monitor() {
        return "I am alive link.";
    }
}
