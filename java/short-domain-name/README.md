##短域名服务

### 项目简介 

一个简单的短网址服务系统，可通过 RESTful API 来生成新短网址，短网址与原网址的映射存储在 Redis 数据库中（本次使用内存存储），用户请求短网址时会被重定向到原网址。

###需求介绍

撰写两个 API 接口:

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：

- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图即刻)；
- 映射数据存储在JVM内存即可，防止内存溢出；

### 文档地址

```
采用swagger2集成BoostrapUI
localhost:8888/doc.html
```

### 测试结果

![img.png](file:///Users/fanzhaofei/github/short-domain-name/img.png?lastModify=1620139406)

###实现思路

本着不重复造轮子的原则，算法暂参考：https://github.com/scdt-china/interview-assignments/pull/146

```java
    private final AtomicLong sequence = new AtomicLong(0);
    static final char[] DIGITS =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    private static final Map<String, String> CACHE_MAP = new HashMap<>();
    @Override
    public String langToShort(String longDomain) {
        //自增的long值
        long seq = sequence.incrementAndGet();
        StringBuilder sBuilder = new StringBuilder();
        //限制8位
        for(int i = 0; i <8; i++){
            int remainder = (int) (seq % 62);
            sBuilder.append(DIGITS[remainder]);
            seq = seq / 62;
            if (seq == 0) {
                break;
            }
        }
        //存储长短域名map
        CACHE_MAP.put(sBuilder.toString(), longDomain);
        return sBuilder.toString();
    }
    @Override
    public String getLang(String shortDomain) {
        return CACHE_MAP.get(shortDomain);
    }
```

