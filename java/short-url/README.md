## 设计思路
### 主要功能: 
1. 实现源地址转为短地址，短地址转为源地址俩个接口
2. 短域名长度最大为 8 个字符
3. 数据存储于JVM

### 接口实现
接口可以基于tcp或http协议,一般采用http协议的restful接口,可以实现更好的异构性。所以采用springmvc的接口实现。

### 短域名计算
1. 短域名计算一般采用随机排列，散列法或ID自增转换的方式。  
2. 随机排列和散列法可能产生哈希冲突,导致重复计算，影响性能。  
3. ID自增再转62进制,得到得URL永不重复,可以使用56800235584->3521614606207区间转换62进制,得到7位短地址,第八位短地址作为校验位。采用发号器提前发号入ConcurrentLinkedQueue队列,保证线程安全。可调整step步长支持分布式发号.

### 数据存储
1. 为了并发时的线程安全,数据存储于ConcurrentHashMap,采用锁机制保证数据不丢失，不覆盖。
2. 生产环境需要切换存储源为redis,mysql等支持持久化的数据库，防止数据丢失和数据过大导致的OOM问题。


## 简要流程图
![image]
(https://github.com/dongxiangcat/shortUrl/blob/master/images/shorturl.png)


## jacoco测试覆盖率
![image]
(https://github.com/dongxiangcat/shortUrl/blob/master/images/shorturl-jacoco.png)

## 性能测试
待完成

## 个人信息
董翔 18518029359 864340463@qq.com
