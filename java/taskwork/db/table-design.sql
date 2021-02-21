CREATE TABLE `t_link` (
  `id` varchar(24) NOT NULL COMMENT '主键',
  `long_url` varchar(2000) DEFAULT NULL COMMENT '长链接',
  `short_url` varchar(100) DEFAULT NULL COMMENT '短链接',
  `prefix_domain` varchar(100) DEFAULT NULL COMMENT '域名',
  `prefix_domain_type` varchar(2) DEFAULT NULL COMMENT '域名类型 与域名一一对应01，02，03类推',
  `short_url_suffix` varchar(20) DEFAULT NULL COMMENT '生成的短链接后的缩减后的n位字串。',
  PRIMARY KEY (`id`),
  KEY `t_link_prefix_domain_type_IDX` (`prefix_domain_type`,`short_url_suffix`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='长链接，短链接存储表'


CREATE TABLE `t_default_domain` (
  `code` varchar(2) NOT NULL,
  `domain_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='默认domain配置表'


