CREATE TABLE `shortdomain` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(2000) NOT NULL COMMENT '长链接url',
  `crc32` int(10) unsigned NOT NULL COMMENT 'url的crc32值',
  `version` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT 'flag生成算法版本号',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1-正常;0-禁用',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `crc32` (`crc32`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;