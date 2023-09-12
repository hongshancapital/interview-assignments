CREATE DATABASE IF NOT EXISTS CHALLENGE;

USE CHALLENGE;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for domain
-- ----------------------------
DROP TABLE IF EXISTS `domain`;
CREATE TABLE `domain` (
  `id` char(64) NOT NULL,
  `long_name` text NOT NULL,
  `short_name` varchar(8),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

