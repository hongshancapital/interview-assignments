CREATE DATABASE IF NOT EXISTS yf_test DEFAULT CHARACTER SET utf8mb4;
DEFAULT COLLATE utf8mb4_unicode_ci;

use yf_test;

CREATE TABLE `short_url_tab` (
`id` BIGINT unsigned NOT NULL AUTO_INCREMENT,
`long_url` varchar(2048) NOT NULL default '',
`created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

