create database `url_shorten` default character set utf8mb4;
use url_shorten;

CREATE TABLE IF NOT EXISTS `storage_url`(
   `id` INT UNSIGNED AUTO_INCREMENT,
   `longUrl` varchar(100) NOT NULL,
   `shortUrl` varchar(8) NOT NULL,
   `creatAt` datetime NOT NULL,
   PRIMARY KEY ( `id` ),
   INDEX ( `shortUrl`, `longUrl` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `sender_num_seq`(
   `id` INT UNSIGNED AUTO_INCREMENT,
   `startNum` INT NOT NULL,
   `endNum` INT NOT NULL,
   `creatAt` datetime NOT NULL,
   PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;