package com.scdt.shorturl.service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.scdt.shorturl.config.SnowflakeConfig;
import com.scdt.shorturl.generator.SnowflakeGenerator;
import com.scdt.shorturl.util.NumericConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 长地址和短地址互相转换的服务
 */
@Service
@Api(value="ShortUrlService|长地址转短地址服务")
public class ShortUrlService{
	public static final Map<String,Long>long2shortMap=new ConcurrentHashMap<String,Long>();//长地址和短地址编号的KV，模拟数据库或NoSql存储。
	public static final Map<Long,String>short2longMap=new ConcurrentHashMap<Long,String>();//短地址编号和长地址的KV，模拟数据库或NoSql存储。之所以又加短长KV是为了方便检索。
	
	/**属性配置**/
	@Value("${snowflake.workerId.shortUrl:0}")private Long workerId;
	@Value("${snowflake.serviceId.shortUrl:0}")private Long serviceId;
	
	/**自动装配**/
	@Autowired private SnowflakeConfig snowflakeConfig;
	
	/**发号器**/
	private SnowflakeGenerator snowflakeGenerator;
	
	/**
	 * 获取发号器
	 * @author 周小建
	 * @param workerId
	 * @param serviceId
	 * @return SnowflakeGenerator
	 */
	private SnowflakeGenerator getSerialGenerator(Long workerId,Long serviceId){
		return SnowflakeGenerator.getInstance(
				snowflakeConfig.getServiceIdBits(),
				snowflakeConfig.getWorkerIdBits(),
				snowflakeConfig.getSequenceBits(),
				snowflakeConfig.getTwepoch(),
				workerId,
				serviceId);
	}
	
	/**
	 * 根据属性文件配置的serialGeneratorName动态确定SerialGenerator发号器
	 * @author 周小建
	 */
	@PostConstruct
	public void init(){
		snowflakeGenerator=getSerialGenerator(serviceId,workerId);
	}
	
	/**
	 * 长地址转短地址，其中serviceId和workerId参数如果为空则表示使用对象属性自带的发号器，否则通过参数获取对应的发号器。
	 * 之所以存在serviceId和workerId两个参数，是为了实现通用，满足单实例情况的默认值和多实例情况的自定义值。
	 * @author 周小建
	 * @param originalUrl
	 * @param workerId
	 * @param serviceId
	 * @return String
	 */
	@ApiOperation(value="长地址转短地址",notes="originalUrl必填且须是较长的地址（不带前缀），workerId和serviceId选填但不能超过阈值。")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="originalUrl",value="长地址",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="workerId",value="机器id",required=false,dataType="Long"),
		@ApiImplicitParam(paramType="query",name="serviceId",value="服务id",required=false,dataType="Long")
	})
	public String long2short(String originalUrl,Long workerId,Long serviceId){
		Long serialNumeric=long2shortMap.get(originalUrl);
		if(serialNumeric==null){
			final String originalUrlMd5=DigestUtils.md5Hex(originalUrl);
			//虽然使用ConcurrentHashMap，但外部的get和put并非原子，所以仍要加锁，而且是针对originalUrlMd5.intern()细粒度加锁。
			synchronized(originalUrlMd5.intern()){
				serialNumeric=long2shortMap.get(originalUrl);//再次获取，因为有可能第一次get和加锁之间有其它线程已经写入。
				if(serialNumeric==null){
					//根据serviceId和workerId两个参数是否存在，动态决定使用属性自带发号器，还是重新获取对应发号器。
					SnowflakeGenerator snowflakeGenerator=workerId!=null&&serviceId!=null?getSerialGenerator(workerId,serviceId):this.snowflakeGenerator;
					serialNumeric=snowflakeGenerator.generate(originalUrlMd5);//交给具体发号器得到一个唯一且递增的编号
					short2longMap.putIfAbsent(serialNumeric,originalUrl);//编号和长地址的KV，只存储编号而不用存储实际的短地址，因为编号和短地址存在一一对应的关系。
					long2shortMap.putIfAbsent(originalUrl,serialNumeric);//长地址和编号的KV，只存储编号即可而不用存储实际的短地址，因为编号和短地址存在一一对应的关系，设置过期时间是为了防止无限增长
				}
			}
		}
		return NumericConvertUtil.toOtherNumber(serialNumeric);
	}
	
	/**
	 * 短地址转长地址，将将短地址转换为数字再从short2longMap直接获取对应的长地址
	 * @author 周小建
	 * @param shortUrl
	 * @return String
	 */
	@ApiOperation(value="短地址转长地址",notes="短地址转长地址")
	@ApiImplicitParams({@ApiImplicitParam(paramType="query",name="shortUrl",value="短地址",required=false,dataType="String")})
	public String short2long(String shortUrl){
		return short2longMap.get(NumericConvertUtil.toDecimalNumber(shortUrl));//将短地址转换为数字再转为长地址
	}
}