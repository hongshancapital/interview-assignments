CREATE DATABASE `short` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use short;
CREATE TABLE `short` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `short_key` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '短域名key',
  `long_url` varchar(1000) COLLATE utf8mb4_bin NOT NULL COMMENT '长域名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;