CREATE TABLE `convert_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `url_long` text NOT NULL COMMENT '长地址',
  `url_long_md5` varchar(32) NOT NULL COMMENT '长地址查询字段，避免长地址超长影响请求耗时',
  `url_short` varchar(100) NOT NULL COMMENT '短地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间 ',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `long_index` (`url_long_md5`),
  KEY `short_index` (`url_short`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='长短连接转换表';
