<!--
 * @Author: your name
 * @Date: 2021-10-18 11:32:38
 * @LastEditTime: 2021-10-18 11:43:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit 
 interview-assignments/java/README.md
-->
# 短链接服务
本项目提供长短链接转换服务，主要对外提供下列两个服务接口：
* 长域名转换成短域名服务
* 短域名解析获取长域名服务

<br>
# 项目备注
本项目只考虑单机运行，不考虑分布式多机运行，没有使用到数据库和redis等中间件，所以数据缓存直接使用的内存。

## 核心设计
- 短链接映射key采用自增ID方案，转换成62进制。
  - 只要ID不超过 281474976710656 即62的8次方，就可以保证位数在8位
- 使用guava 
  - 缓存存储长域短域名的映射关系，为防止内存溢出和可扩展性，通过配置文件对缓存最大size进行设置
  - 为了取短域名换长域名检索更快，采用concurrentHashMap存储倒排



```
  @Override
    public String generatorShortUrl(String longUrl) throws ExecutionException {
        UrlVo shortUrl;
        shortUrl = cached.get(longUrl, () -> {
            long id = idGeneratorService.incrId();
            String urlKey = toBase62(id);
            UrlVo urlValue = new UrlVo(id, urlKey, longUrl);
            shortUrlMap.put(urlKey, urlValue);
            return urlValue;
        });
        return shortUrl.getShortUrl();
    }

  @Override
    public String parseLongUrl(String shortUrl) {
        UrlVo urlVo = shortUrlMap.get(shortUrl);
        if (null == urlVo) {
            return null;
        }
        return urlVo.getLongUrl();
    }
```

## 压测方案
- 采用apache ab进行简单压测
  - ab -n 100 -c100 http://localhost:8080/api/tinyUrl/getLongUrl?shortUrl=aaa
- jmeter编写脚本压测