use url_d1;

CREATE TABLE `urlmap` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shorturl` varchar(8) NOT NULL DEFAULT '',
  `origin` varchar(1024) NOT NULL DEFAULT '',
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
