package boot.spring.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import boot.spring.constants.CommonConstant;
import boot.spring.utils.CodeUtil;
import boot.spring.utils.ConvertorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api")
@Api(value = "DomainChangeApi", tags = {"DomainChangeApi"})
public class DomainChangeApiController {

	public static final Logger log = LoggerFactory.getLogger(DomainChangeApiController.class);
	public final static Map<String, String> domainMap = new HashMap<>();
	public static Pattern pattern = Pattern.compile("^https?://\\S+$");
	public static String url= "http://swho.cn/";
	
  	@RequestMapping("/domainChangeAdd")
    @ApiOperation(value = "短域名存储接口", httpMethod = "POST", response = String.class)
	public String domainChangeAdd(String longUrl){
  		Map<String, Object> resultMap = new HashMap<String, Object>();
  		resultMap.put("code", CommonConstant.FAIL);
  		Runtime rt =Runtime.getRuntime();
  		long free = rt.freeMemory() >> 20;
  		if(free<5){
  			domainMap.clear();
  		}
  		
  		if(StringUtils.isBlank(longUrl)){
  			resultMap.put("code", CommonConstant.FAIL);
			resultMap.put("msg", CommonConstant.PARAMETER_ERROR);
			return JSONObject.toJSONString(resultMap);
  		}
  		if(!pattern.matcher(longUrl).matches()){
  			resultMap.put("code", CommonConstant.FAIL);
  			resultMap.put("msg", CommonConstant.FORMAT_ERROR);
  			return JSONObject.toJSONString(resultMap);
  		}
  		try {
  			Long shortUrl=CodeUtil.getCode();
  			String code=ConvertorUtil.encode10To62(shortUrl);
  			domainMap.put(code, longUrl);
  			resultMap.put("code", CommonConstant.SUCCESS);
			resultMap.put("data", url+code);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", CommonConstant.ERROR);
			resultMap.put("msg", CommonConstant.SYSTEM_ERROR);
		}
		return JSONObject.toJSONString(resultMap);
	}
  	
  	@RequestMapping("/domainChangeQuery")
  	@ApiOperation(value = "短域名读取接口", httpMethod = "POST", response = String.class)
	public String domainChangeQuery(String shortUrl){
  		Map<String, Object> resultMap = new HashMap<String, Object>();
  		resultMap.put("code", CommonConstant.FAIL);
  		
  		if(StringUtils.isBlank(shortUrl)){
  			resultMap.put("code", CommonConstant.FAIL);
			resultMap.put("msg", CommonConstant.PARAMETER_ERROR);
			return JSONObject.toJSONString(resultMap);
  		}
  		if(!pattern.matcher(shortUrl).matches()||!shortUrl.contains(url)){
  			resultMap.put("code", CommonConstant.FAIL);
  			resultMap.put("msg", CommonConstant.FORMAT_ERROR);
  			return JSONObject.toJSONString(resultMap);
  		}
  		
  		try {
  			String urlKey = shortUrl.substring(shortUrl.lastIndexOf("/")+1, shortUrl.length());
  			String LongUrl = domainMap.get(urlKey);
  			if(StringUtils.isBlank(LongUrl)){
  				resultMap.put("code", CommonConstant.FAIL);
  				resultMap.put("msg", CommonConstant.NOT_EXIST);
  			}
  			resultMap.put("code", CommonConstant.SUCCESS);
			resultMap.put("data", LongUrl);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", CommonConstant.ERROR);
			resultMap.put("msg", CommonConstant.SYSTEM_ERROR);
		}
		return JSONObject.toJSONString(resultMap);
	}

}
