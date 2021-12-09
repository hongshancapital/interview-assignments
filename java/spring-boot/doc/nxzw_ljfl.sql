/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : nxzw_ljfl

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 16/11/2021 11:39:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_area
-- ----------------------------
DROP TABLE IF EXISTS `base_area`;
CREATE TABLE `base_area`  (
  `area_id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '区域id（主键，自增）',
  `area_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '区域代码',
  `area_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '区域名称',
  `city_code` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属城市的代码',
  PRIMARY KEY (`area_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3145 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '行政区信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for base_city
-- ----------------------------
DROP TABLE IF EXISTS `base_city`;
CREATE TABLE `base_city`  (
  `city_id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '城市id（主键，自增）',
  `city_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '城市代码',
  `city_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '城市名称',
  `province_code` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属省份的代码',
  PRIMARY KEY (`city_id`) USING BTREE,
  UNIQUE INDEX `uk_city_code`(`city_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 346 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '行政区域地州市信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for base_province
-- ----------------------------
DROP TABLE IF EXISTS `base_province`;
CREATE TABLE `base_province`  (
  `province_id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '省份id(主键 自增)',
  `province_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份名称',
  `province_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '90001',
  PRIMARY KEY (`province_id`) USING BTREE,
  UNIQUE INDEX `uk_province_code`(`province_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '省份表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`  (
  `id` int(11) NOT NULL DEFAULT 0,
  `pid` int(11) NULL DEFAULT NULL,
  `cityname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `community_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '社区名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `goods_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '物品名称',
  `goods_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '编号',
  `score` decimal(15, 0) NULL DEFAULT NULL COMMENT '单个兑换积分',
  `stock` int(20) NULL DEFAULT NULL COMMENT '库存',
  `exchange_num` int(11) NULL DEFAULT NULL COMMENT '已兑换量',
  `community_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '社区id',
  `is_upper_shelf` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否上架（0：未上架、1：已上架）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '物品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for nxzw_zhsl_employ_area
-- ----------------------------
DROP TABLE IF EXISTS `nxzw_zhsl_employ_area`;
CREATE TABLE `nxzw_zhsl_employ_area`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `province_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省级区划码',
  `province_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省级区划名称',
  `city_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市级区划码',
  `city_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市级区划名称',
  `county_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区/县级区划码',
  `county_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区/县级区划名称',
  `town_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '乡/镇/居委会级区划码',
  `town_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '乡/镇/居委会级区划名称',
  `area_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '村/街道办事处级区划码',
  `area_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '村/街道办事处级区划名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_employ_area_province_code`(`province_code`) USING BTREE,
  INDEX `idx_empploy_area_city_code`(`city_code`) USING BTREE,
  INDEX `idx_employ_area_county_code`(`county_code`) USING BTREE,
  INDEX `idx_employ_area_town_code`(`town_code`) USING BTREE,
  INDEX `idx_employ_area_area_code`(`area_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 676158 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '就业局区划表-省市区镇村-5级' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for operation_record
-- ----------------------------
DROP TABLE IF EXISTS `operation_record`;
CREATE TABLE `operation_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `operate` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '1：用户注册；2：用户绑定；3：积分兑换；4：环卫工人评分',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户类型（0：用户、1：环卫工人、2：社区管理员）',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户id',
  `qr_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '二维码编码',
  `goods_id` int(20) NULL DEFAULT NULL COMMENT '物品ID',
  `notes` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `score` decimal(15, 0) NULL DEFAULT NULL COMMENT '使用积分',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_qr_code`(`qr_code`(191), `operate`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '操作记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for province_city_district
-- ----------------------------
DROP TABLE IF EXISTS `province_city_district`;
CREATE TABLE `province_city_district`  (
  `id` int(11) NOT NULL COMMENT '地区代码',
  `pid` int(11) NULL DEFAULT NULL COMMENT '当前地区的上一级地区代码',
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '省市县数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qr_code
-- ----------------------------
DROP TABLE IF EXISTS `qr_code`;
CREATE TABLE `qr_code`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `qr_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '垃圾袋编码：和用户绑定',
  `qr_state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0：未使用、1：已绑定、2：已评分）',
  `qr_no` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '垃圾袋序号',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '链接地址',
  `notes` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `community_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '社区id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_qr_code`(`qr_code`(191)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1781 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '二维码信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `score` decimal(15, 0) NOT NULL COMMENT '积分 15位保留2位小数',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '积分表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for score_bill
-- ----------------------------
DROP TABLE IF EXISTS `score_bill`;
CREATE TABLE `score_bill`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户id',
  `operate` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '该用户的操作类型（1：积分兑换  2：环卫工人评分）',
  `goods_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '物品ID',
  `goods_num` int(20) NULL DEFAULT NULL COMMENT '物品数量',
  `qr_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '二维码编码',
  `score` decimal(15, 0) NULL DEFAULT NULL COMMENT '使用积分',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '积分流水表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户id',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `user_pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户类型（0：用户、1：环卫工人、2：社区管理员）',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '手机号',
  `user_sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户性别（0：男、1：女）',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户姓名',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '地址',
  `community_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '社区id',
  `token` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'token',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `user_statusc` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '状态 0锁定 1有效',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `login_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录来源ip地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_qr_code
-- ----------------------------
DROP TABLE IF EXISTS `user_qr_code`;
CREATE TABLE `user_qr_code`  (
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户id',
  `qr_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '二维码编码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `index_qr_code`(`qr_code`(191)) USING BTREE,
  INDEX `index_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户和二维码绑定表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
