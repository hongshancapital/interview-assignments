package com.assignment.util;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 长短域名映射表工具类
 *
 * @Author: shican.sc
 * @Date: 2022/6/12 22:20
 * @see
 */
public class DomainMapperUtil {

    /**
     * 短域名固定前缀
     */
    public static String StartWithHTTP = "http";
    public static String shotDomainUrlPrefixHTTP = "http://";
    public static String shotDomainUrlPrefixHTTPS = "https://";

    /**
     * 短域名 -> 长域名 内存映射容器
     */
    public static Map<String, String> shotToLongdomainMapper = new ConcurrentHashMap<>();

    /**
     * 长域名 -> 短域名 内存映射容器
     */
    public static Map<String, String> longToShotdomainMapper = new ConcurrentHashMap<>();

    /**
     *  对长域名进行算法处理得到最长8位的短域名（16进制）
     *      算法思路：先取密文4位 (已存在，hash冲突) =>取8位 （已存在，hash冲突）=> 加盐时间戳进行MD5取8位
     * @param url 长域名
     * @return 返回短域名
     */
    public static String algorithmURL(String url){
        String shotDomain = longToShotdomainMapper.getOrDefault(url, "");
        if (!StringUtils.isEmpty(shotDomain)) {
            //已存在长短映射
            return shotDomain;
        }

        //不存在长短映射
        String md5ByUrl = Md5Util.getMD5ByUrl(url);
        String shot = md5ByUrl.substring(0, 4);
        String longDomian = shotToLongdomainMapper.get(shot);
        if (StringUtils.isEmpty(longDomian)) {
            return shot;
        }else {
            //hash冲突， 跳档为8
            shot = md5ByUrl.substring(0, 8);
            longDomian = shotToLongdomainMapper.get(shot);
            if (StringUtils.isEmpty(longDomian)) {
                return shot;
            }else {
                //8位也存在冲突, 加盐时间戳 => 一直到内存中不存在映射位置
                while (StringUtils.isEmpty(shot = shotToLongdomainMapper.get(Md5Util.random(url).substring(0, 8)))){
                    return shot;
                }
            }
        }
        return shot;
    }

    public static String algorithmURLWithHttp(String url){
        return shotDomainUrlPrefixHTTP + algorithmURL(url);
    }

    public static String algorithmURLWithHttps(String url){
        return shotDomainUrlPrefixHTTPS + algorithmURL(url);
    }
}
