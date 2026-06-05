CREATE TABLE if not exists `url_map` (
 `id` bigint(20) NOT NULL,
 `short_url` varchar(255) NOT NULL COMMENT '短链接',
 `origin_url` varchar(255) NOT NULL COMMENT '原始链接地址',
 `create_time` datetime NOT NULL,
 `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
 PRIMARY KEY (`id`),
 UNIQUE KEY `index_unique_short_url` (`short_url`) USING BTREE
) ;