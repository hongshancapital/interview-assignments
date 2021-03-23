/*
 Navicat Premium Data Transfer

 Source Server         : localhsot
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : short_url

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 23/03/2021 19:04:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_short_url
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url`;
CREATE TABLE `t_short_url`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url
-- ----------------------------
INSERT INTO `t_short_url` VALUES (1, 'http://www.baidu.com', 'xxx', '2021-03-23 17:02:13');

-- ----------------------------
-- Table structure for t_short_url_1
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_1`;
CREATE TABLE `t_short_url_1`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_1
-- ----------------------------

-- ----------------------------
-- Table structure for t_short_url_10
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_10`;
CREATE TABLE `t_short_url_10`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_10
-- ----------------------------

-- ----------------------------
-- Table structure for t_short_url_2
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_2`;
CREATE TABLE `t_short_url_2`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_2
-- ----------------------------

-- ----------------------------
-- Table structure for t_short_url_3
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_3`;
CREATE TABLE `t_short_url_3`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_3
-- ----------------------------

-- ----------------------------
-- Table structure for t_short_url_4
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_4`;
CREATE TABLE `t_short_url_4`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_4
-- ----------------------------

-- ----------------------------
-- Table structure for t_short_url_5
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_5`;
CREATE TABLE `t_short_url_5`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_5
-- ----------------------------

-- ----------------------------
-- Table structure for t_short_url_6
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_6`;
CREATE TABLE `t_short_url_6`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_6
-- ----------------------------

-- ----------------------------
-- Table structure for t_short_url_7
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_7`;
CREATE TABLE `t_short_url_7`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_7
-- ----------------------------

-- ----------------------------
-- Table structure for t_short_url_8
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_8`;
CREATE TABLE `t_short_url_8`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_8
-- ----------------------------
INSERT INTO `t_short_url_8` VALUES (2, 'http://www.baidu.com', 'ksEZ0000', '2021-03-23 18:18:41');

-- ----------------------------
-- Table structure for t_short_url_9
-- ----------------------------
DROP TABLE IF EXISTS `t_short_url_9`;
CREATE TABLE `t_short_url_9`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_url` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '长连接',
  `code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短链接码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '短连接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_short_url_9
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
