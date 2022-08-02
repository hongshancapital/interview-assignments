package com.xinerde.demo.shortlink.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * VO对象
 * 
 * @since 2022年5月19日
 * @author guihong.zhang
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class LinkVO {
	private String longLink;
	private String shortLink;
}
