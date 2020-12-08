create database shortlink_test;
create user 'shortlink'@'127.0.0.1' identified by '12345678';
grant all privileges  on shortlink_test.* to 'shortlink'@'127.0.0.1';

CREATE TABLE `link` (
  `id` int NOT NULL AUTO_INCREMENT comment '主键',
  `short_link` varchar(8) not null default '' comment 'short link',
  `original_link` varchar(256) not null default '' comment 'original link',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  key idx_short_link_original_link(`short_link`,`original_link`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;