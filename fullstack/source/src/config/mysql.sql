CREATE TABLE `host_map`(
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `host_long` varchar(256) NOT NULL DEFAULT "" COMMENT '长域名',
  `host_short` varchar(256) NOT NULL DEFAULT "" COMMENT '短域名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY idx_host_long(host_long) KEY idx_host_short(host_short)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci comment '长短域名映射表';