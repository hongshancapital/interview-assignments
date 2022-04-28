package demo.curURL.service;

import demo.entity.URLEntity;

/**
 * 短域名解析服务接口
 */
public interface ICurURLService {
    URLEntity find(String curURL);
}
