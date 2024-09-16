CREATE TABLE
    `short_url_map` (
        `id` int unsigned NOT 0 AUTO_INCREMENT,
        `lurl` char(255) CHARACTER SET ascii COLLATE ascii_general_ci DEFAULT '' COMMENT '长地址',
        `surl` char(8) CHARACTER SET ascii COLLATE ascii_general_ci DEFAULT '' COMMENT '短地址',
        `created_at` int DEFAULT 0 COMMENT '创建时间',
        PRIMARY KEY (`id`),
        KEY `surl` (`surl`)
    ) ENGINE = MyISAM DEFAULT CHARSET = ascii ROW_FORMAT = FIXED