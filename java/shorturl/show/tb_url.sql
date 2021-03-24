/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 30/09/2019 11:37:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_url
-- ----------------------------
DROP TABLE IF EXISTS `tb_url`;
CREATE TABLE `tb_url`  (
  `uuid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shorl_url_id` varchar(9) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩短后的短址id',
  `long_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原网址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_view` datetime(0) NULL DEFAULT NULL COMMENT '上一次访问时间',
  `view_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问密码',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
