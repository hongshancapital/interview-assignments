
CREATE TABLE `short_url_mapping` (
  `id` int(13) unsigned NOT NULL AUTO_INCREMENT,
  `lurl` VARCHAR(6144) NOT NULL COMMENT '长链接',
  `lurl_hash` BIGINT COMMENT '长链接数字类型hash值，48 bit',
  `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX idx_hash (`lurl_hash`)
)