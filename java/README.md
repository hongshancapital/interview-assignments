tinyurl-converter
该项目主要实现长域名转短域名和短域名解析，核心接口有

长域名转换成短域名接口
通过短域名获取长域名

本项目主要是单机demo，请勿直接用于生产环境，为了便于编码调试，长域名短域名映射主要存储在内存中。
短域名核心设计

采用生成自增ID，转换成62进制生成短域名的方式处理。
只要ID不超过 281474976710656 即62的8次方，就可以保证位数在8位
该项目运行于单机环境，如考虑分布式环境，ID需要三方生成或者使用发号器，缓存需要使用分布式缓存如redis
guava 缓存存储长域名和短域名的映射关系，为了控制内存溢出，设置缓存大小
短域名生成采用预生成机制，可一定程度提高长域名转短域名接口的性能和吞吐量，实际生产中短域名生成机制可能更为复杂，预热机制效果会更好，
代码只是简单表达思想
  @Override
  public String generatorShortUrl(String longUrl) {
      ShortUrl url = null;
      try {
          url = cahced.get(longUrl, () -> {
              if (shortUrlQueue.size() == 0){
                  synchronized (this){
                      if (shortUrlQueue.size() == 0){
                          singlePool.execute(() -> {
                              //预生成1000个
                              for(int i=0; i<1000; i++){
                                  long id = idGenerator.incrId();
                                  String shortStr = toBase62(id);
                                  shortUrlQueue.add(shortStr);
                              }
                          });
                      }
                  }
              }
              String str = shortUrlQueue.take();
              ShortUrl shortUrl = new ShortUrl(str, longUrl);
              shortUrlMap.put(str, shortUrl);
              return shortUrl;
          });
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
      return url.getShortUrl();
  }

  @Override
  public String getLongUrl(String shortUrl) {
      TinyUrl tinyUrl = shortUrlMap.get(shortUrl);
      if (null == tinyUrl) {
          return null;
      }
      return tinyUrl.getLongUrl();
  }
压测方案

采用apache ab进行简单压测
ab -n 100 -c100 http://localhost:8080/api/shortUrl/getLongUrl?shortUrl=aaa
jmeter编写脚本压测