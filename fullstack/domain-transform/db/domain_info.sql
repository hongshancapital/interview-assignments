/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : domain

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2022-03-19 17:05:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for domain_info
-- ----------------------------
DROP TABLE IF EXISTS `domain_info`;
CREATE TABLE `domain_info` (
  `domainId` int(11) NOT NULL AUTO_INCREMENT,
  `longDomain` varchar(255) DEFAULT NULL,
  `shortDomain` varchar(8) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `domainDescription` varchar(255) DEFAULT NULL,
  `idDel` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`domainId`),
  UNIQUE KEY `shortDomainIndex` (`shortDomain`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of domain_info
-- ----------------------------
INSERT INTO `domain_info` VALUES ('1', 'www.baidufanyi.com', 'anyi.com', '2022-03-19 15:15:41', '测试域名', '0');
INSERT INTO `domain_info` VALUES ('4', 'www.baidufanyi1111.com', '1111.com', '2022-03-19 15:41:06', '测试域名', '0');
INSERT INTO `domain_info` VALUES ('5', 'www.baidufanyi111.com', 'i111.com', '2022-03-19 16:04:14', '测试域名', '0');
INSERT INTO `domain_info` VALUES ('6', 'www.baidufanyi11.com', 'yi11.com', '2022-03-19 16:06:30', '测试域名', '0');
INSERT INTO `domain_info` VALUES ('7', 'www.baidufanyi', 'idufanyi', '2022-03-19 16:10:41', '测试域名', '0');
