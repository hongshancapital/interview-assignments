package com.breo.common.service.request;

import com.breo.common.dto.Request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetLongUrlRequest extends Request{

	/** 短编码 */
	private String shortCode;
	
	
	
}
