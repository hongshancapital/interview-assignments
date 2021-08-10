CREATE TABLE `test`.`user`  (
    `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
    `password` char(32) NOT NULL COMMENT '密码',
    `salt` char(16) NOT NULL COMMENT '盐值',
    `create_time` datetime(0) NULL COMMENT '插入时间',
    PRIMARY KEY (`id`)
) COMMENT = '用户表';