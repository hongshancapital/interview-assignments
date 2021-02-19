
CREATE TABLE `short_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `long_url` varchar(200) NOT NULL COMMENT '长地址url',
  `short_url` varchar(50) NOT NULL COMMENT '短地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `long_url` (`long_url`),
  UNIQUE KEY `short_url` (`short_url`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=8 COMMENT='二维码链接映射表';

