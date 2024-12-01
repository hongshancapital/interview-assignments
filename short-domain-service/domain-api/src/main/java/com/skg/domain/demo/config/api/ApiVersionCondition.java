package com.skg.domain.demo.config.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 版本号匹配筛选器
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    private static final Logger logger = LoggerFactory.getLogger(ApiVersionCondition.class);


    /**
     * 路径中版本的正则表达式匹配， 这里用 /1.0的形式
     */
    private static final Pattern VERSION_PREFIX_PATTERN = Pattern.compile("(v([1-9][.][0-9]))");
    private String apiVersion;

    public ApiVersionCondition(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (m.find()) {
            //获取版本号的dubbo数字
            String version = m.group(1);

            if (version.substring(1).equals(this.apiVersion)) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 优先匹配最新的版本号  注意 ASCII 比较注意格式
        return other.getApiVersion().compareTo(this.apiVersion);

    }

    public String getApiVersion() {
        return apiVersion;

    }
   /* ///api/expiredMoney/v1.0/queryByMonth
    public static void main(String[] args){
        Matcher m = VERSION_PREFIX_PATTERN.matcher("/v1.0/");
        logger.info("测试匹配规则：{}" , m.find());
    }
*/

}
