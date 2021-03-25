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

### 数据表设计

数据表设计：
分别是
1）server_version，用于存放初始号码记录
2）url_mapping_x 表，用于存放<号码，链接>数据，这里的 x 对应初始号码(0-999), 共1000张 url_mapping表。

发号器实现：初始号码 0 ~ 999 对应发号器 0 ~ 999。
初始号码单独存放于数据库的的server_version表中，version使用自增字段分配初始号码，用于表明哪些初始号码已被其他机器上的服务占用, 一个服务对应一个初始号码。
每个服务在启动阶段会从server_version表中获取唯一的初始version。

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
    <td>自增初始值等于x（0 <= x <= 999）</td>
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
    <td>缓存了所有短链接服务正在使用的初始号码，存放于zset中</td>
  </tr>
  </tr>
</table>

在本项目中，
1)缓存存储了<code, url> 和 <url, code> 键值对，过期时间为1小时。只要在1小时内访问该数据，过期时间会被重置。
2)初始号码分配的策略，当server_version表无法分配有效的初始号码(0-999)时，就需要通过比较没有被使用的初始号码对应的url_mapping表中 code 字段最大值与整个号码空间的最大值的大小，来确定初始号码是否可用。其中没有被使用的初始号码即，in-use-server-version 缓存不仅仅只是缓存了当前正在使用的初始号码，同时还要引入过期机制，防止某个服务挂了后，相应的初始号码仍然还存储在缓存中，没有被释放。本项目使用 zset score 实现了一个过期机制，服务启动时会将初始号码写入 in-use-server-version中，将该初始号码的score设为当前时间。服务启动后，会定时更新其使用的初始号码的score 为当前时间，这样就不会被清理程序清理掉。
3)server_uuid:为实现分布式服务，对每个生成随机数并缓存uuid以便竞争redis实现的分布式锁

存储过程：init_db.sql

### 环境配置
1. 在服务器上安装好MySQL，Redis
2. 执行 init.sql中的存储过程
3. 修改 Spring Boot配置文件 application.properties 的相关属性

### 主要测试用例
1. url格式校验，url格式标准化
2. 发射器功能test
3. 长链接转短链接， 短链接转长连接test
