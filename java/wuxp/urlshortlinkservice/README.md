# 项目说明

  主要提供两个API，
      把长链接转为短链接(使用base62)
      根据短链接获取长链接
      

## 假设持久化使用JPA，目前为了方便

## 假设使用mysql数据库，推进使用nosql数据库

1、mysql表数据结构为：

   CREATE TABLE `shorturl` (
     `id` bigint(20) NOT NULL, 
     `created_date` datetime(6) NOT NULL,
     `expires_date` datetime(6) DEFAULT NULL,
     `long_url` varchar(255) COLLATE utf8mb4_bin NOT NULL,
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
   
2、   

# 后续扩展方案

   [(总体架构.png)]
   如果保证高可用
   如果扩展管理需求
