# short-url
该项目主要实现长域名转换短域名，核心接口有
* 长域名转换成短域名接口
* 通过短域名获取长域名


本项目目前是单机运行，在生产环境中运行需要做分布式处理，为了减少中间件的引入（可以是redis 或者mysql等），长域名短域名映射主要存储在内存缓存中。

## 短域名核心设计
- 采用雪花算法生成ID（生产多台机器可以用多个workid），转换成62进制生成短域名的方式处理。
  - 只要ID不超过 281474976710656 即62的8次方，就可以保证位数在8位
  - 在分布式环境中，需要考虑雪花算法的workid的唯一性，并且需要让长域名和短域名的映射关系存储到redis或者mysql等其他地方
- caffeine 缓存存储长域名和短域名的映射关系，为了控制内存溢出，设置缓存大小（默认500），使用concurrentHashMap存储短域名与长域名映射关系


## 核心代码
```
  @Override
      @Cacheable(value = "shortUrl", key = "#longUrl")
      public String generatorShortUrl(String longUrl) {
          Long id = idGenerator.nextId();
          String shortUrl = BaseUtils.encode10To62(id);
          ShortUrl bean = new ShortUrl(id, shortUrl, longUrl);
          shortUrlMap.put(shortUrl, bean);
          log.info("插入新的长域名:{}", longUrl);
          return shortUrl;
      }
  
      @Override
      public String getLongUrl(String shortUrl) {
          ShortUrl bean = shortUrlMap.get(shortUrl);
          return Objects.isNull(bean) ? null : bean.getLongUrl();
      }
```

## 压测方案
- 采用apache ab进行简单压测
  - ab -n 100 -c100 http://localhost:8080/api/url/short?longUrl=aaa
- jmeter编写脚本压测