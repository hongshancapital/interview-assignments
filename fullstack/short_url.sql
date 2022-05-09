SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `url` (
  `id` bigint UNSIGNED NOT NULL COMMENT '编号',
  `urlCode` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短代码',
  `longUrl` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '完整url地址',
  `shortUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短网址',
  `date` datetime NOT NULL COMMENT '创建时间',
  `expireType` tinyint UNSIGNED NOT NULL DEFAULT '0' COMMENT '过期类型',
  `expires` datetime DEFAULT NULL COMMENT '过期时间',
  `refCount` bigint UNSIGNED NOT NULL DEFAULT '0' COMMENT '复用次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短网址';

ALTER TABLE `url`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `url`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号';
COMMIT;
