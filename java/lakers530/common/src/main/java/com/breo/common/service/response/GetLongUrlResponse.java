package com.breo.common.service.response;

import com.breo.common.dto.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetLongUrlResponse extends Response{

	/** 长地址 */
	private String longUrl;
	
}
