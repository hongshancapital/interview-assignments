###jacoco
![avatar](https://cwc.clouderwork.com/clouderworkorg/c5bbe7a4d3d941fa9d25aa08c2830d57.jpg)

###框架结构
    * 1、使用springboot2.1 + swagger

###设计思路
    * 1、根据雪花算法生成唯一号码，然后进行10进制转换
    * 2、生成短连接跟长链接的对应关系存入map ,重写了lrus,定期淘汰短连接
    * 3、可以加入accessKey,accessSecrt，限制 防止刷接口。

### restful 风格
    项目启动，通过http://localhost:8080/swagger-ui.html#/ 访问swagger
    * 1、http://localhost:8080/v1.0/create/shortUrl
    * 2、http://localhost:8080/v1.0/get/longUrl
    
