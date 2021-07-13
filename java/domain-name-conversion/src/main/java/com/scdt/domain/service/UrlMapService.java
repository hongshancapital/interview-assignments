package com.scdt.domain.service;

import com.google.common.base.Preconditions;
import com.scdt.domain.constant.CommonConstant;
import com.scdt.domain.utils.CacheUtil;
import com.scdt.domain.utils.ConversionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlMapService {

    private static AtomicLong counter = new AtomicLong(0);

    private final UrlValidator urlValidator = new UrlValidator(new String[]{CommonConstant.HTTP_PROTOCOL,
            CommonConstant.HTTPS_PROTOCOL});

    /**
     * 长链接转短链接
     * @param url 长链接
     * @return 短链接
     */
    public String longToShort(String url) {
        long num = counter.incrementAndGet();
        verifyLongToShort(num, url);
        String code = ConversionUtils.encode62(num);
        String longToShort = CacheUtil.getLongToShort(url);
        Preconditions.checkState(StringUtils.isBlank(longToShort),"当前链接对应的短链接已经存在");
        CacheUtil.putShortToLong(code, url);
        CacheUtil.putLongToShort(url, code);
        return code;
    }

    /**
     * 根据短链接获取长链接
     * @param code 短链接
     * @return 长链接
     */
    public String shortToLong(String code) {
        verifyShortToLong(code);
        String longUrl = CacheUtil.getShortToLong(code);
        Preconditions.checkState(StringUtils.isNotBlank(longUrl),"链接已过期或者不存在");
        return longUrl;
    }

    /**
     * 较验长链接转短链接
     * @param num 十进制数
     * @param url 长链接地址
     */
    private void verifyLongToShort(long num, String url) {
        boolean valid = urlValidator.isValid(url);
        Preconditions.checkArgument(valid,"输入的地址不正确");
        double pow = Math.pow(62, 8);
        Preconditions.checkArgument(pow >= num ,"短域名已经达到最大长度限制");
    }

    /**
     * 较验短链接转长链接参数
     * @param code 短链接
     */
    private void verifyShortToLong(String code) {
        Preconditions.checkArgument(StringUtils.isNotBlank(code) && code.length() >= 5
                && code.length() <= 8,"短链接格式不正确");
    }

    public static void setCounter(AtomicLong counter) {
        UrlMapService.counter = counter;
    }
}
