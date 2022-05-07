CREATE TABLE `url_mapping` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tiny_url` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '短链接',
  `original_url` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '原始链接',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL,
  `expire_date` datetime NOT NULL,
  `state` bit(1) DEFAULT b'0' COMMENT '账号状态：0 正常，1 异常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tiny_url` (`tiny_url`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短链接表';

