## 短域名服务

使用 mysql 作为数据存储，当然也可也采用其他数据库作为数据服务，可能 redis 更好点
没有使用 ORM 数据模型第三方模块
使用 nanoid 库生成短连接

## 数据库

```sql
CREATE TABLE `url` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `short_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `short_url` (`short_url`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

## 安装使用

- `cd short_url && npm install`
- `npm run dev`
