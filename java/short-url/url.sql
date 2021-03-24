/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : mytest

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 24/03/2021 19:46:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for url
-- ----------------------------
DROP TABLE IF EXISTS `url`;
CREATE TABLE `url`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `short_url` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短地址',
  `index` int(11) NULL DEFAULT NULL COMMENT '哈希冲突后增加的序号',
  `suffix` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后缀，序号所对应的地址字符',
  `original_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_all_url`(`short_url`, `original_url`) USING BTREE,
  UNIQUE INDEX `index_url_suffix`(`short_url`, `suffix`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
