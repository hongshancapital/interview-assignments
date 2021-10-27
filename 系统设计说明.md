# 设计思路

## 长短链映射策略选择
### hash
如果将长链接生成一个hash值，但是哈希方法中，有可能会有冲突，2个长域名会映射到同一个短域名中。另外哈希方法是单方向的，从长域名得到短域名，但从短域名无法得到长域名。
### 发号
过发号策略，给每一个过来的长地址，发一个唯一的号码。<br>
通过号码来进行映射：originalURL -> id -> shortURL<br>
这样既能保证每个不同的长链接对应不同的id，还是可以通过id找到shortURL，通过shortURL找到id，这样就可以从长域名得到短域名，但从短域名得到长域名。<br>
在选择生成唯一号码时，也比较容易实现，小型系统直接用mysql的自增索引就搞定了。如果是大型应用，可以考虑各种分布式key-value系统做发号器。不停的自增就行了。<br>
### 因此选择发号策略来实现

##本系统具体实现方式
###发号器
AtomicLong id；<br>
每来一个新链接不停的进行自增
###LRU缓存
LRUCache cache；<br>
由于映射数据存储在JVM内存中，为了防止内存溢出，选择LRU缓存策略<br>
数据结构：<br>
选择双向链表 + map结构，维持LRU策略，<br>
另外加了一下map，用于缓存，维持长链接与ID之间的对应关系<br>
```java
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private long size;
    private Map<Long, Node> map;
    private Map<String, Long> index;
    private Node head;
    private Node tail;
```
###节点数据结构
URL
```java
    private long id; // 号码
    private String shortURL; //短链
    private String originalURL; //长链
```
###idToShortURL
核心是将十进制转换成62进制，62进制中每一位在数组[A−Z,a−z,0−9] 映射到到一个具体值，就生成了一个短连接
###shortURLtoID
进行反序列化，将62进制的短链接还原成十进制。

##系统架构图
![image1](./src/main/resources/image/架构图.png)

##单元测试
![image2](./src/main/resources/image/单元测试.png)

##性能测试
内存： 7.5GiB<br>
CPU： Intel® Core™ i3-4030U CPU @ 1.90GHz × 4<br>
OS： Ubuntu 20.04.2 LTS 64-bit<br>
测试工具: apache-jmeter-5.4.1<br>
线程数： 10000<br>
循环： 100<br>
1.queryShortURL-短域名存储接口<br>
![image2](./src/main/resources/image/短域名存储接口-性能测试.png)
2.queryOriginalURL-短域名读取接口<br>
![image2](./src/main/resources/image/短域名读取接口-性能测试.png)

##扩展
高并发架构设计情况下，可以选择redis或者雪花算法来做发号器，将数据存储到数据库中，在数据库之上可以使用redis做一层缓存，可以使用布隆过滤器来判断长链是否已存在，这样避免大量请求同时到达数据库，另外使用一致性hash来解决分布式存储问题，大概思路从原域名获得一个哈希值，根据这个哈希值找到对应的机器，然后当做单设备的情况处理就行，跟单机有点差别在于，需要将哈希值和短链接结合起来作为最终短域名





