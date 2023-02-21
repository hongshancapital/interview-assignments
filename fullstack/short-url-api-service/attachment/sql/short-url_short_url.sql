
CREATE TABLE `short_url` (
  `id` int NOT NULL AUTO_INCREMENT,
  `long_url` varchar(255) NOT NULL,
  `createTime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_cf34a13c86da9c2f15f56b3b95` (`long_url`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
