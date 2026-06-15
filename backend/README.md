### 思考
实现方式有好几种：ID自增，雪花id，摘要算法，随机数等
前两种比较好，后两种重复概率大
我只是简单的做了一个ID自增实现，利用id自增方法将链接换成了长字符串和短字符串的之间的转换

### 存储
存储方式很多中，Mysql、Mongodb、Redis、ElasticSearch等，具体情况要根据需求和数据量做分析，数据量很大可以用ElasticSearch，数据量小就无所谓啦
我只做了一个简单的Mysql存储


### 框架
ts + express + mysql（sequelize） + jest
(如何用nestjs框架效果更好)

### 数据库
### 数据库表设计根据场景还可以增加其他字段（全域名、失效日期、点击次数等字段）
CREATE SCHEMA `hs` ;
CREATE TABLE `short_links` (
`id` int NOT NULL AUTO_INCREMENT,
`short_url` varchar(100) DEFAULT '' COMMENT '短网址',
`original_url` varchar(100) DEFAULT '',
`domin` varchar(45) DEFAULT '' COMMENT '地址前缀',
`expire_time` timestamp NULL DEFAULT NULL COMMENT '失效时间',
`createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
`deletedAt` timestamp NULL DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

### 项目启动和单元测试
启动命令： npm run dev
单元测试： npm test
接口地址：demo
长转短：'http://localhost:1234/api/findShortUrl/参数'
短转长：'http://localhost:1234/api/findLongUrl/参数'




