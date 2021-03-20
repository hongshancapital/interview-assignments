CREATE TABLE `tb_domain` (
  `long_domain` varchar(52) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `short_domain` varchar(18) NOT NULL,
  PRIMARY KEY (`long_domain`),
  KEY `idx_long` (`short_domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;