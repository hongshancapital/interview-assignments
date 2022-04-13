CREATE TABLE `short_link_config` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `short_link` varchar(8) NOT NULL DEFAULT '' COMMENT '短域名域名',
  `long_link` varchar(512) NOT NULL DEFAULT '' COMMENT'长域名域名',
  `long_link_hash` varchar(32) NOT NULL DEFAULT '' COMMENT'长域名域名hash',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_sl` (`short_link`),
  KEY `idx_llh` (`long_link_hash`)
) COMMENT='短域名映射表';
