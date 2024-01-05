DROP TABLE IF EXISTS `ShortLinks`;
CREATE TABLE `ShortLinks` (
  `id` INTEGER UNSIGNED auto_increment COMMENT '主键id', 
  `shortUrl` CHAR(12) NOT NULL COMMENT '无host短链，默认存储 8 个字符，剩余留作扩展使用', 
  `originalUrl` VARCHAR(4000) NOT NULL COMMENT '原始链接', 
  `userId` INTEGER UNSIGNED COMMENT '用户id', 
  `machineId` CHAR(4) COMMENT '机器id', 
  `createdAt` DATETIME NOT NULL COMMENT '创建时间', 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
ALTER TABLE `ShortLinks` ADD INDEX `short_links_short_url` (`shortUrl`);
ALTER TABLE `ShortLinks` ADD INDEX `short_links_original_url` (`originalUrl`(100));
