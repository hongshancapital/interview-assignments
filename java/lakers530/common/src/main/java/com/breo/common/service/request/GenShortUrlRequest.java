package com.breo.common.service.request;

import com.breo.common.dto.Request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GenShortUrlRequest extends Request{

	/** 长域名地址 */
	private String longUrl;
	
	
	
}
