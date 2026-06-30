package demo.oriURL.service;

import demo.entity.URLEntity;

/**
 * 短域名生成服务接口
 */
public interface IOriURLService {
    URLEntity trans(String oriURL);
}
