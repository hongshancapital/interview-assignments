# 短域名服务设计文档

## 介绍

For [interview-assignments](https://github.com/scdt-china/interview-assignments/tree/master/java)


## 设计思路

### 需求举例
当某平台需要广播某个链接时，需要对原域名进行压缩至短域名，为了切合实际我们为短域名添加一个可配置的hostname前缀（https://www.sequoiacap.com），例如： 

  - 原域名：https://github.com/scdt-china/interview-assignments/tree/master/java

  - 短域名：https://www.sequoiacap.com/l3Iq
  
### 问题解析：

1、 映射数据存储在JVM内存即可，防止内存溢出
  
  - 由于存储后还需要根据短链换回原域名，就会涉及到查询效率问题，需要考虑存储的数据结构。 
  - 考虑生成短链时的幂等性，就需要双向查询，如果用HashMap，就需要存储两份数据
  - 并发情况下，防止相同的原域名生成后会覆盖数据，需要考虑在生成短域名时，对原域名加锁
  - 考虑为防止内存溢出，缓存数据需要使用软引用或者驱逐算法进行内存回收
  
2、 短域名长度最大为 8 个字符
  
  - 长度限制8位，需要考虑可用的数量
  - 考虑防止短域名被爬虫，生成的短链不能有规则性

### 缓存选型

* 方案一：由于需要考虑查询的效率，所以选择HashMap是符合查询效率的数据结构。
再加上数据缓存在JVM，则可以考虑设计SoftHashMap继承HashMap使其value使用软引用，或者定时LRU算法清理数据。


* 方案二：Guava的CacheBuilder支持SoftReference和WeakReference值，以及其他基于大小和时间的驱逐策略。


* 方案三：[Caffeine](https://github.com/ben-manes/caffeine) (spring cache)。Spring在5.x就用Caffeine替代了Guava， caffeine cache是采用java8编写的一个高性能的缓存库。
它的实现与guava cache差不多，很多的方法甚至连注释都一模一样，API也相似，但是它相对于guava cache还是有不少的改进，使得性能高于guava cache。


以上方案我最终选择方案三。使用Caffeine作为本次缓存的数据结构，其底层也是HashMap。为防止内存溢出，需要注意的是数据驱逐问题。
Caffeine有三个纬度的驱逐方式，分别是 [大小、时间和引用](https://github.com/ben-manes/caffeine/wiki/Eviction) 。
***官方建议是通过使用基于缓存容量和时间的驱逐策略代替软引用的使用***。 [配置参考文档](https://www.javadoc.io/doc/com.github.ben-manes.caffeine/caffeine/2.9.3/com/github/benmanes/caffeine/cache/CaffeineSpec.html
)


### 短域名算法
* 首先考虑到的是MD5，但是由于产生字符串较长需要截取，所以存在随机性，也就存在重复性，直接pass


* 因为是用于域名字符，必须是数字+字母的方式，所以选择了Base62方式，先维护一个线程安全的long变量，每来一个原域名，long自增，然后利用base62将该变量转换为base62字符串。


* 因为使用自增的long变量，直接Base62容易被爬虫遍历，所以将Base62初始字符顺序随机打乱，防止被爬虫


* 对于 Base62，每一位有 62 个可能字符串，8 位则有
  
  > 62^8 = 218340105584896
  
  种组合，也是为了防止过于简单被爬虫，我们设计如果从 4 位数开始，则有
  
  > 218340105584896 - 62^4 = 218340090808560
  
  也就是说假设每秒产生 1000 个短域名 也够用 6924 年


* 假设每个原域名和短域名的映射占用512字节，如果机器内存为8G，则最多存储

  > 8G/512字节 = 16777216

* 加上时间驱逐，所以缓存初始值可配置为 15000000 可保证不会出现内存溢出

### 架构设计图

![](./架构设计图.png)




