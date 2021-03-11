package com.zxb.rungo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zxb.rungo.entity.LongToShort;
import com.zxb.rungo.service.ConversionService;
import com.zxb.rungo.service.LongToShortService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 文件上传Controller
 */
@RestController
@RequestMapping("/rungo")
public class LongToShortController {

	@Autowired
	private LongToShortService longToShortService;
	
	@Autowired
	ConversionService conversionService;

	/**
	 * 上传文件
	 */
	@ApiOperation(value = "长地址变成短地址", notes = "长地址变成短地址")
	//@ApiImplicitParam(name = "laddress", value = "长地址", required = true, dataType = "String")
	@RequestMapping(value = "/ltos", method = RequestMethod.POST)
	public HashMap<String, Object> ltos(@RequestParam("laddress") String address) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		
		if ((address == null) || (address.trim().length() == 0))
		{
			res.put("code", 500);
			res.put("message", "请输入地址！");
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("longAddress", address);
		map.put("shortAddress", conversionService.getShortAddress());		
		
		if (longToShortService.insert(map) > 0)
		{
			res.put("code", 0);
			res.put("message", "成功！");
			res.put("shortAddress", map.get("shortAddress"));
		}
		else
		{
			res.put("code", 500);
			res.put("message", "不成功！");
		}
		
		return res;
	}

	@ApiOperation(value = "短地址变长地址", notes = "短地址变长地址")
	//@ApiImplicitParam(name = "saddress", value = "短地址", required = true, dataType = "String")
	@RequestMapping(value = "/stol", method = RequestMethod.POST)
	public HashMap<String, Object> stol(@RequestParam("saddress") String address) throws Exception {
		HashMap<String, Object> res = new HashMap<String, Object>();
		
		if (address.length() > 8)
		{
			res.put("code", 500);
			res.put("message", "地址超过8个！");
			return res;
		}
			
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shortAddress", address);
		
		LongToShort longToShort = longToShortService.select(map);	
		
		res.put("code", 0);
		if (longToShort == null)
		{
			res.put("message", "此短地址没有记录！");	
		}else
		{
			res.put("message", "成功！");
			res.put("data", longToShort);
		}
		
		return res;
	}

}
