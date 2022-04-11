
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
  `desc` text COMMENT '短域名描述',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0：删除 1：开启',
  `visit` int(11) NOT NULL DEFAULT '0' COMMENT '访问次数',
  `s_url` varchar(8) NOT NULL DEFAULT '' COMMENT '短域名',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `url` text NOT NULL COMMENT '原域名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
