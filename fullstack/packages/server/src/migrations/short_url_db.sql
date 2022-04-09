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

 Date: 03/22/2022 22:00:59 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `shorturl`
-- ----------------------------
DROP TABLE IF EXISTS `shorturl`;
CREATE TABLE `shorturl` (
  `id` varchar(36) NOT NULL COMMENT '唯一ID',
  `updateId` varchar(255) NOT NULL DEFAULT '' COMMENT '修改或者创建人',
  `created_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `url` varchar(255) NOT NULL COMMENT '原域名',
  `desc` text COMMENT '短域名描述',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0：删除 1：开启',
  `visit` int(11) NOT NULL DEFAULT '0' COMMENT '访问次数',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `s_url` varchar(8) NOT NULL DEFAULT '' COMMENT '短域名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_2d67fd4ea6c830ac37ce80f713` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `shorturl`
-- ----------------------------
BEGIN;
INSERT INTO `shorturl` VALUES ('a37fb3fc-5e6d-466d-bd4a-4728e7f2c868', '', '2022-03-22 15:17:31.035', '2022-03-22 21:57:38.000', 'http://localhost:3332/api/swagger/', 'swagger', '1', '0', '0', '73F5DE09');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
