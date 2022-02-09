package hongshan.demo.controller;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hongshan.demo.beans.Result;
import hongshan.demo.storage.DomainCache;
import hongshan.demo.utils.TextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 控制类
 * @class hongshan.demo.controller.DomainController
 * @author 许坤
 * @email klxukun@126.com
 * @time 2021年10月18日 下午10:28:07
 *
 */
@Api(tags="长短域名转换控制类",description="为客户端提供长/短域名的转换、存储和提取接口")
@RestController
public class DomainController {	
	private static Logger logger = LoggerFactory.getLogger(DomainController.class);
	/**
	 * 根据长域名获取短域名，如果不存在，则保存
	 * @param longUri 长域名
	 * @return 返回处理结果实例，参见<a href="hongshan.demo.beans.Result">Result</a>
	 */
	@ApiOperation(value = "根据长域名获取短域名", notes = "")
	@RequestMapping(value="/lts", method= {RequestMethod.GET, RequestMethod.POST})
	public Result longToShort(@RequestParam("url") @ApiParam(name="url", value="长域名", required=true)String longUri) {
		logger.info("recieved long uri:"+ longUri);
		Result ret= null;
		try {
			ret = Result.success(DomainCache.getShortUri(longUri));
		}catch(NoSuchAlgorithmException ex) {
			ret= Result.error(ex.getMessage());
			logger.error("长域名转换为短域名失败",ex);
		}
		
		return ret;
	}
	/**
	 * 根据短域名获取长域名
	 * @param shortUri 短域名
	 * @return  返回处理结果实例，参见<a href="hongshan.demo.beans.Result">Result</a>
	 */
	@ApiOperation(value = "根据短域名获取长域名", notes = "")
	@RequestMapping(value="/stl/{url}", method= {RequestMethod.GET, RequestMethod.POST})
	public Result shortToLong(@PathVariable("url")@ApiParam(name="url", value="短域名", required=true)String shortUri) {		
		logger.info("recieved short uri:"+ shortUri);
		return Result.success(DomainCache.getLongUri(shortUri));

	}	
}
