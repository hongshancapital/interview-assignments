# 长域名转短域名服务
后续简称 SDServer

## 项目基础结构

![img.png](public/img.png)

## 功能分析 

### 短域名存储

接受一个长域名，例如 https://github.com/scdt-china/interview-assignments/tree/master/fullstack
转换成一个短域名，如 https://shortdomian.com/dsadaf12

其中 dsadaf12 这串字符串，可以理解微是长域名的代号，通过SDServer转换生成。

这个功能的核心就是长域名对应的 `代号` 的生成：

##### 方案1：通过base64 对url进行编码处理

base64作为可解码的算法，好像很适合这个场景。但是base64生成的编码长度和域名长度强相关。所以这个方案直接就排除了

##### 方案2：通过数据库的自增ID

比较简单

在分布式场景下需要考虑锁，数据多的时候还需要考虑分库分表等


##### 方案3：雪花ID

前两种方案其实都是和要存储的长域名item本身有相关性的。不过我们这个功能其实是可以把长域名和对应的`代号`来分开考虑的

思路就是基于一个可以生成分布式全局唯一id的工具，每次有存储操作时，都去这里去获取一个id即可。

##### 雪花ID补充
由于雪花Id生成的是64位。 抛开首位不用，雪花id 理论能生成的最大值为 2 ^ 63 - 1

我们短域名的路径一般会使用62进制（0-9 a-z A-Z） 来表示，根据最大8位可以表示最大值为ZZZZZZZZ = 62 ^ 8 - 1 

下面这个值是远远小于 2 ^ 63 - 1 的。那是不是雪花id就没办法用了，也不完全是，雪花id的算法中，除去首位和时间戳位数.其余的有10位是机器id， 12位是随机数

根据实际情况，可以降低机器id和随机数：

1bit首位 + 41bit 时间戳 + 3位机器ID + 3位随机数ID

这个简化版本的雪花ID可以支持 8 个不同的worker机器，每个机器每一毫秒可以产生8个随机ID

对于长域名转短域名这种QPS不高的场景，这个算法在完美情况下可以支持64000的qps。

### 短域名获取

存储时将短域名作为 PrimaryKey。获取时通过短域名获取到对应的记录，并获取到对应的长域名URL，返回HttpStatus为301，重定向URL为长域名URL即可


### 相关Mysql表

这里union_id 设置长度为10个字节，这样如果后续有扩充需求，比如短域名增加到9位等

CREATE TABLE `t_short_domain` (
`union_id` char(10) NOT NULL COMMENT '主键ID',
`create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
`complete_url` text NOT NULL COMMENT '完整域名',
PRIMARY KEY (`union_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='短域名Item表'

### 其他优化

- 引入Sequelize等序列化工具规范Model层
