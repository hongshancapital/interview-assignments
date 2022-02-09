package com.breo.common.service.response;

import com.breo.common.dto.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GenShortUrlResponse extends Response{

	/** 短编码 */
	private String shortCode;
	
}
