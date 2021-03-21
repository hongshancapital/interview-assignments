package com.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @类名称   Application.java
 * @类描述   <pre>SpringBoot启动类</pre>
 * @作者     zhangsheng
 * @创建时间 2021年3月21日下午9:45:24
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本           修改人 		    修改日期 		           修改内容描述
 *     ------------------------------------------------------
 *     1.00 	zhangsheng 	2021年3月21日下午9:45:24             
 *     ------------------------------------------------------
 * </pre>
 */
@EnableSwagger2
@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
