DROP DATABASE IF EXISTS `rungo`;

CREATE DATABASE `rungo`;

USE `rungo`;

--
-- Table structure for table `long_to_short`
--

DROP TABLE IF EXISTS `long_to_short`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `long_to_short` (
  `id` int auto_increment primary key not null,
  `short_address` char(8) COLLATE utf8_bin DEFAULT NULL,
  `long_address` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;