/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : novel_plus

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2022-05-06 13:26:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for url_sender_num
-- ----------------------------
DROP TABLE IF EXISTS `url_sender_num`;
CREATE  TABLE  `url_sender_num` (
                               `id` int(11) unsigned  NOT  NULL AUTO_INCREMENT COMMENT '自增id',
                               `start_num` bigint(20) NOT  NULL  COMMENT '起始ID，id*1000 - 9999',
                               `end_num` bigint(20) NOT  NULL  COMMENT '结束ID，id*1000',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT  CHARSET=utf8mb4 COMMENT='短链发号射表';


-- ----------------------------
-- Table structure for short_url_map
-- ----------------------------
DROP TABLE IF EXISTS `short_url_map`;
CREATE TABLE `short_url_map` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `surl` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '短链',
                             `lurl` varchar(128) NOT NULL COMMENT '长链',
                             `lmd5` char(32) DEFAULT NULL COMMENT '长链md5，用于优化索引',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `expires_time` datetime DEFAULT NULL COMMENT '过期时间',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='短链-长链映射表';
