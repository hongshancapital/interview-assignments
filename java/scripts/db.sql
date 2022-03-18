
CREATE TABLE `link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `md5` varchar(64) DEFAULT NULL,
  `url` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `link_idx0` (`md5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf-8;