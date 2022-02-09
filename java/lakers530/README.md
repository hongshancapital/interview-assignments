# ShortUrl

短链接生成


# 短域名接口文档
http://localhost:8888/swagger-ui/index.html


# 接口【生成短域名】
url地址：http://localhost:8888/common/shortUrl/genShortUrl

参数：longUrl：长域名

测试示例：
http://localhost:8888/common/shortUrl/genShortUrl?longUrl=https://www.baidu.com/

返回值：
{"result":1,"code":"","msg":"","shortCode":"12345678"}

# 接口【获取长域名】
url地址：http://localhost:8888/common/shortUrl/getLongUrl

参数：shortCode：短编码

测试示例：
http://localhost:8888/common/shortUrl/getLongUrl?shortCode=12345678

返回值：
{"result":1,"code":"","msg":"","longUrl":"https://www.baidu.com/"}


# 短域名算法设计
雪花算法

数据中心id+机器id+时间戳




