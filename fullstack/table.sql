CREATE TABLE `short_url`
(
    `id`               int(10) unsigned NOT NULL AUTO_INCREMENT,
    `url`              text             NOT NULL COMMENT '完整 URL',
    `last_access_time` timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后访问时间',
    `create_time`      timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 234470
  DEFAULT CHARSET = utf8mb4 COMMENT ='短链接';
