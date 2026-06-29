DROP TABLE IF EXISTS `t_short_url`;
CREATE TABLE `t_short_url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_url` varchar(100),
  `long_url` text,
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP
  PRIMARY KEY (`id`),
  UNIQUE uq(short_url),
  UNIQUE uq_long(long_url),
  KEY `long_url` (`long_url`(64))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;