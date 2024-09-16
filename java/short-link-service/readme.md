
### 程序设计及实现
* 假设域名为：a.cn，那么短域名的访问形式为 a.cn/BAA ，共8位，可在配置文件中配置相应的域名和起始长度，例如：BAA起始长度为3844
```
# 自定义配置
app:
  short-link:
    # 缺省的生成短连接的域名，为空则取访问服务时的域名
    shortUri: a.cn
    # 起始地址
    startSerialNumber: 3844
```

* 生成的短连接保存在缓存中`ShortLinkCache`，使用谷歌的Cache实现，配置文件设置缓存大小
```
# 自定义配置
app:
  short-link:
    # 缓存短连接最大数量
    cacheSize: 100000
    # 缓存短连接过期时间，单位秒
    cacheExpire: 7200
```

### swagger访问地址
```
http://localhost:8081/swagger-ui.html
```

### Jacoco单元测试
![ Jacoco单元测试](.\shortLink.png )


### 待完善
* 实际生产环境应考虑多域名指向该服务，按域名的维度来生成
* 短连接持久化
* 接口安全性
