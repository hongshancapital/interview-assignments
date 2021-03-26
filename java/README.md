# 分布式短域名服务

短连接设计思路：

使用发号策略压缩长URL：
通过发号策略，对请求过来的长地址发一个号version，ex.第一个进来的链接发号器发0号，对应的短链接为 xx.xxx/0，第二个进来的链接发号器发1号，对应的短链接为 xx.xxx/1，以此类推。
URL的格式如下：
1) 字母表小写字母 [‘a’ to ‘z’], 一共26个字符
2) 字母表大写字母 [‘A’ to ‘Z’], 一共26个字符
3) 数字 [‘0’ to ‘9’], total 10 characters， 一共62个可能的字符，对10进制数据做62进制的转换
存储只需要存储10进制数据，不需要存储62进制，ex.第10000个长地址，短地址对应的编号是9999，通过存储自增拿到9999后，做一个10进制到62进制的转换，转成62进制数
短链接的地址不超过6位

链接校验的假设:
1)长连接的长度在20-255之间
2)链接以https或ftp开头

### 发号器逻辑设计
使用MySQL数据库自增字段(server_version中的version)实现发号功能:
项目中使用了6位62进制数进行编码，可获得500亿左右个号码空间。为了提高拓展性、并发性以及可靠性, 实现过程中对这500亿个号码空间进行划分，将500亿个号码空间划分成1000个号码段，产生1000个逻辑发号器，分别发尾号为0 ~ 999的号码, 由不同的机器发不同号码段内的号码。每发一个号码，发号器+1000。ex.以1号发号器为例，1号发号器应该发1、1001、2001等尾号为1的号码，而不是发1、2、3等。

起始号码 0~999(1000个号码段) 对应发号器 0 ~ 999(1000个逻辑发号器)。起始号码存放在server_version中，使用自增字段version分配起始号码，用于表明哪些起始号码已被其他机器上的服务占用。设计上，一个服务对应一个起始号码。每个服务在启动时会从server_version中获取一个唯一的起始号码(偏移量1000)。

同时url_mapping_x表(存放url和号码的关系)采用按起始号码分表的设计, 即url_mapping_0, url_mapping_1....url_mapping_999, 共1000张表.

由于起始号码只有就1000个，如果服务累积启动超过1000次，即当服务从server_version中获取到的起始号码大于999时，此号码无效, 也就是起始号码资源被用完了。
假设较坏的情况下,在运行过程中一个服务崩溃退出, 但是起始号码的号码空间还没有使用完, 此时等待使用该资源的服务可以进行抢占。
方案:
1)比较没有被使用的起始号码对应的url_mapping表中code字段最大值与整个号码空间的最大值的大小, 如果<整个号码空间的最大值, 则说明仍有空间(没有被使用的起始号码通过下面缓存中in-use-server-versions缓存进行控制)
2)此时分布式环境下, 多个服务可能会同时对资源进行抢占国, 所以需要使用redis实现的分布式锁进行加锁, 保证同一时刻只有一个服务可以获取起始号码逻辑


### 数据表设计

数据表设计：
分别是
1）server_version，用于存放起始号码记录
2）url_mapping_x 表，用于存放<号码，链接>数据，这里的 x 对应起始号码(0-999), 共1000张 url_mapping表。
存储过程：init_db.sql

表结构如下：

**server_version 表结构**
<table>
  <tr>
    <th>字段</th>
    <th>类型</th>
    <th>属性</th>
  </tr>
  <tr>
    <td>version</td>
    <td>int</td>
    <td>自增</td>
  </tr>
</table>

**url_mapping_x 表结构**
<table>
  <tr>
    <th>字段</th>
    <th>类型</th>
    <th>属性</th>
    <th>备注</th>
  </tr>
  <tr>
    <td>code</td>
    <td>bigint</td>
    <td>自增</td>
    <td>自增起始值等于x（0 <= x <= 999）</td>
  </tr>
  <tr>
    <td>url</td>
    <td>VARCHAR</td>
    <td></td>
    <td></td>
  </tr>
</table>

### 缓存设计
本项目使用 Redis 中间件作为缓存。主要缓存数据：

在本项目中，缓存设计如下:
1)<code, url> 和 <url, code> 键值对，即url和号码的映射关系, 缓存过期时间为1小时。只要在1小时内访问该数据，过期时间会被重置。对于热点数据，该缓存策略可在一定程度上减轻数据库的压力。

2)in-use-server-versions: 缓存了所有的短链接服务正在使用的起始号码。根据发号器中起始号码分配的策略，当server_version表无法分配有效的起始号码时，需要通过比较没有被使用的起始号码对应的url_mapping中code最大值与整个号码空间的最大值的大小，来确定起始号码是否可用。
如何判断起始号码没有被使用:
除了in-use-server-versions缓存的起始号码，其他的起始号码都是未被使用的。
in-use-server-versions缓存不仅仅只是缓存了当前正在使用的起始号码，同时还使用引入过期机制，防止某个服务挂了后,相应的起始号码仍然还存储在缓存中，没有被释放。过期时间设置为5分钟

由于zset不支持该数据结构中的数据过期的机制，所以本项目实现一个过期机制:
使用zset score实现了一个过期机制，服务启动时会将起始号码写入in-use-server-versions中，并将该起始号码的 score设为当前时间。服务启动后，定时更新其使用的起始号码的score为当前时间，不断地为缓存数据续期, 防止运行中的服务的key过期。

3)server_uuid: 该缓存存储了短链接服务的UUID。不同的服务会去抢占这个缓存，并将缓存值设置为自己的UUID。作用记录当前哪个服务有权限清除in-use-server-versions缓存中过期的起始号码.
清除缓存的逻辑:


```
min = 0;
max = current_time - expired_time;
ZREMRANGEBYSCORE in-use-server-versions min max 
```


<table>
  <tr>
    <th>key</th>
    <th>value</th>
    <th>过期时间</th>
    <th>说明</th>
  </tr>
  <tr>
    <td>code</td>
    <td>url</td>
    <td>1h</td>
    <td></td>
  </tr>
<tr>
    <td>url</td>
    <td>code</td>
    <td>1h</td>
    <td></td>
  <tr>
    <td>server_uuid</td>
    <td>Java UUID String</td>
    <td>5min</td>
    <td>服务的uuid</td>
  </tr>
  <tr>
    <td>server_version_in_use</td>
    <td>zset = [0, 1, 2, ... 999]</td>
    <td></td>
    <td>缓存了所有短链接服务正在使用的起始号码，存放于zset中</td>
  </tr>
  </tr>
</table>



### 环境配置
1. 在服务器上安装好MySQL，Redis
2. 执行 init.sql中的存储过程
3. 修改 Spring Boot配置文件 application.properties 的相关属性

### 主要测试用例
1. url格式校验，url格式标准化
2. 发射器功能test
3. 长链接转短链接， 短链接转长连接test

###系统设计图
1. 流程图
https://github.com/RuohanPan/interview-assignments/blob/master/java/pics/%E7%9F%AD%E8%BF%9E%E6%8E%A5%E8%BD%AC%E9%95%BF%E8%BF%9E%E6%8E%A5.png
https://github.com/RuohanPan/interview-assignments/blob/master/java/pics/%E9%95%BF%E8%BF%9E%E6%8E%A5%E8%BD%AC%E7%9F%AD%E8%BF%9E%E6%8E%A5.png

2. 时序图
https://github.com/RuohanPan/interview-assignments/blob/master/java/pics/%E6%97%B6%E5%BA%8F%E5%9B%BE.jpg
3. 架构图
https://github.com/RuohanPan/interview-assignments/blob/master/java/pics/%E6%9E%B6%E6%9E%84.png

