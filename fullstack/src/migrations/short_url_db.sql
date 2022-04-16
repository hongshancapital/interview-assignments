/*
 Navicat MySQL Data Transfer

 Source Server         : yuansheng
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 111.230.20.189
 Source Database       : short_url_db

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : utf-8

 Date: 04/12/2022 22:29:50 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `shorturl`
-- ----------------------------
DROP TABLE IF EXISTS `shorturl`;
CREATE TABLE `shorturl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `url` varchar(999) NOT NULL COMMENT '原域名',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0：删除 1：开启',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_2d67fd4ea6c830ac37ce80f713` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `shorturl`
-- ----------------------------
BEGIN;
INSERT INTO `shorturl` VALUES ('1', 'http://127.0.0.1:3332/api/swagger/#/Short%2520URL/ShorturlController_getshort', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
