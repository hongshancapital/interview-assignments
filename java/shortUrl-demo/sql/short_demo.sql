CREATE TABLE `url` (
  `id` bigint(20) unsigned NOT NULL,
  `short_url` varchar(12) DEFAULT NULL,
  `long_url` varchar(512) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `surl_unique` (`short_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;