CREATE TABLE `user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL COMMENT '用户名称',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `status` tinyint(2) NOT NULL DEFAULT '10' COMMENT '状态 10 正常  20 禁用',
  `create_time` timestamp NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
