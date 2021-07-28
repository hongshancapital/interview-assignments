CREATE TABLE `data_user` (
  `id` int(10) unsigned NOT NULL COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '姓名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `created_at` int(10) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `updated_at` int(10) NOT NULL DEFAULT '0' COMMENT '修改时间',
  `deleted_at` int(10) DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学员表';