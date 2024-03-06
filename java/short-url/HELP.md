

# 短链接服务设计文档



### 需求设计场景
做一个短链接生成器，可以将一个长链接缩短成一个短链接




### 设计原理
当我们在浏览器里输入 http://t.cn/RlB2PdD时DNS首先解析获得 http://t.cn 的 IP 地址当 DNS 获得 IP 地址以后（比如：192.168.225.72），
会向这个地址发送 HTTP GET 请求，查询短码 RlB2PdD，http://t.cn 服务器会通过短码 RlB2PdD 获取对应的长 URL请求通过 HTTP 301 转到对应的长 URL https://t.cn/test#12345



### 业务流程图

![Alt text]()