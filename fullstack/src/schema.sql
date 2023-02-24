-- ----------------------------
--  Table structure for `url`
-- ----------------------------
DROP TABLE IF EXISTS `url`;
CREATE TABLE `url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `long_url` text,
  PRIMARY KEY (`id`),
  KEY `long_url` (`long_url`(64))
) ENGINE=InnoDB AUTO_INCREMENT 300000 DEFAULT CHARSET=utf8mb4;