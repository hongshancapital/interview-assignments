/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : domain-change

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 20/03/2021 14:56:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for domain
-- ----------------------------
DROP TABLE IF EXISTS `domain`;
CREATE TABLE `domain`  (
  `original_domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原域名',
  `short_domain` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短域名',
  `expire_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '过期时间',
  `state` int(1) UNSIGNED NULL DEFAULT 1 COMMENT '状态 1正常 0冻结 -1删除',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`original_domain`) USING BTREE,
  UNIQUE INDEX `idx_doman_short`(`short_domain`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '长短域名映射表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of domain
-- ----------------------------
INSERT INTO `domain` VALUES ('http://www.baidu.com', 'qAnYvq', '2021-03-20 12:36:57', 1, NULL, '2021-03-20 12:36:57', NULL, '2021-03-20 12:36:57');
INSERT INTO `domain` VALUES ('http://www.hilaunch.com.cn', 'ayUJ3u', '2021-03-20 12:47:45', 1, NULL, '2021-03-20 12:47:45', NULL, '2021-03-20 12:47:45');
INSERT INTO `domain` VALUES ('http://www.sina.com.cn', 'reABbe', '2021-03-20 14:46:32', 1, NULL, '2021-03-20 14:46:32', NULL, '2021-03-20 14:46:32');

SET FOREIGN_KEY_CHECKS = 1;
