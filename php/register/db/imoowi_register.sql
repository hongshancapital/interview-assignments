/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : localhost:3306
 Source Schema         : imoowi_register

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 06/09/2021 22:40:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for reg_migration
-- ----------------------------
DROP TABLE IF EXISTS `reg_migration`;
CREATE TABLE `reg_migration` (
  `version` varchar(180) NOT NULL,
  `apply_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of reg_migration
-- ----------------------------
BEGIN;
INSERT INTO `reg_migration` VALUES ('m000000_000000_base', 1630935633);
INSERT INTO `reg_migration` VALUES ('m210906_035020_user', 1630935635);
COMMIT;

-- ----------------------------
-- Table structure for reg_user
-- ----------------------------
DROP TABLE IF EXISTS `reg_user`;
CREATE TABLE `reg_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(255) NOT NULL COMMENT '用户名',
  `Password` varchar(32) NOT NULL COMMENT '密码',
  `Salt` varchar(6) NOT NULL COMMENT '盐',
  `status` tinyint(1) DEFAULT '0' COMMENT '用户状态',
  `created_at` int(11) NOT NULL COMMENT '注册日期',
  `updated_at` int(11) NOT NULL COMMENT '最近一次修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of reg_user
-- ----------------------------
BEGIN;
INSERT INTO `reg_user` VALUES (1, 'asdfasf', '5180689eca3b090a253bc8f47fa5adc8', 'K45QG', 1, 1630936149, 1630936149);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
