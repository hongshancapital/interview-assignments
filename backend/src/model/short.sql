/*
 Navicat Premium Data Transfer

 Source Server         : mysel_test
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : short_db

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 12/03/2023 12:22:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for short
-- ----------------------------
DROP TABLE IF EXISTS `short`;
CREATE TABLE `short` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `short_url` char(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '8位短链',
  `original_url` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL COMMENT '原始链接',
  `unixstamps` bigint NOT NULL DEFAULT '1678289345233' COMMENT '时间戳',
  `create_at` datetime NOT NULL,
  `update_at` datetime NOT NULL,
  `version` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_short` (`short_url`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
