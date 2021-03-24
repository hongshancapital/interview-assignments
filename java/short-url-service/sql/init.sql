
CREATE TABLE `short_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `short_url` varchar(10) DEFAULT NULL COMMENT '短url字符串，不含域名',
  `long_url` varchar(500) DEFAULT NULL COMMENT '原始url',
  `long_hash_code` bigint(20) DEFAULT NULL COMMENT '原始url的哈希值',
  `create_time` timestamp DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_short_url_short_url` (`short_url`) COMMENT '索引-短url',
  KEY `idx_short_url_long_hash_code` (`long_hash_code`) COMMENT '索引-原始url的哈希值'
) COMMENT='短链接表', ENGINE = INNODB;