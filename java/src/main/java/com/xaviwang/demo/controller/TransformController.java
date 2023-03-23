package com.xaviwang.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xaviwang.demo.bean.IncrementalIdTransformer;
import com.xaviwang.demo.bean.RandomIdTransformer;
import com.xaviwang.demo.bean.SixtyTwoBitsIdTransformer;
import com.xaviwang.demo.bean.Transformer;

/**
 * 
 * @author xaviwang
 * These are the controller methods which offers the API calls to basic transforming algorithms.
 */
@RestController
public class TransformController {

	Transformer incrementalIdTransformer = new IncrementalIdTransformer();	
	Transformer sixtyTwoBitsIdTransformer = new SixtyTwoBitsIdTransformer();
	Transformer randomIdTransformer = new RandomIdTransformer();

	
	@RequestMapping("/incrementalIdTransformFromOriginalUrlToTinyUrl")
	public String incrementalIdTransformFromOriginalUrlToTinyUrl(String orignialUrl) {
		return incrementalIdTransformer.transformFromOriginalUrlToTinyUrl(orignialUrl);
	}
	
	@RequestMapping("/incrementalIdTransformFromTinyUrlToOriginalUrl")
	public String incrementalIdTransformFromTinyUrlToOriginalUrl(String tinyUrl) {
		return incrementalIdTransformer.transformFromTinyUrlToOriginalUrl(tinyUrl);
	}
	
	@RequestMapping("/sixtyTwoBitsIdTransformFromOriginalUrlToTinyUrl")
	public String sixtyTwoBitsIdTransformFromOriginalUrlToTinyUrl(String orignialUrl) {
		return sixtyTwoBitsIdTransformer.transformFromOriginalUrlToTinyUrl(orignialUrl);
	}
	
	@RequestMapping("/sixtyTwoBitsIdTransformFromTinyUrlToOriginalUrl")
	public String sixtyTwoBitsIdTransformFromTinyUrlToOriginalUrl(String tinyUrl) {
		return sixtyTwoBitsIdTransformer.transformFromTinyUrlToOriginalUrl(tinyUrl);
	}

	@RequestMapping("/randomIdTransformFromOriginalUrlToTinyUrl")
	public String randomIdTransformFromOriginalUrlToTinyUrl(String orignialUrl) {
		return randomIdTransformer.transformFromOriginalUrlToTinyUrl(orignialUrl);
	}
	
	@RequestMapping("/randomIdTransformFromTinyUrlToOriginalUrl")
	public String randomIdTransformFromTinyUrlToOriginalUrl(String tinyUrl) {
		return randomIdTransformer.transformFromTinyUrlToOriginalUrl(tinyUrl);
	}
}
