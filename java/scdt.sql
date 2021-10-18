/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : scdt

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 15/10/2021 15:55:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_generate
-- ----------------------------
DROP TABLE IF EXISTS `code_generate`;
CREATE TABLE `code_generate` (
  `current` bigint DEFAULT NULL COMMENT '自增id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='链接映射表';

-- ----------------------------
-- Records of code_generate
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for url_storage
-- ----------------------------
DROP TABLE IF EXISTS `url_storage`;
CREATE TABLE `url_storage` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `short_url` varchar(16) NOT NULL DEFAULT '' COMMENT 'ID',
  `long_url` varchar(2000) NOT NULL DEFAULT '' COMMENT 'ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_short` (`short_url`),
  KEY `k_long` (`long_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='链接记录表';

-- ----------------------------
-- Records of url_storage
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
