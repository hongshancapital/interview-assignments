DROP TABLE IF EXISTS `short_url`.`short_url0`;
CREATE TABLE `short_url`.`short_url0` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `origin_url` varchar(1380) DEFAULT NULL,
  `origin_url_hash` INT(11) NULL,
  `create_timestamp` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



ALTER TABLE `short_url`.`short_url0`
ADD INDEX `short_code_index` (`short_code` ASC) VISIBLE,
ADD INDEX `origin_url_hash_index` (`origin_url_hash` ASC) VISIBLE;
;


#--------------

DROP TABLE IF EXISTS `short_url`.`short_url1`;
CREATE TABLE `short_url`.`short_url1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `origin_url` varchar(1380) DEFAULT NULL,
  `origin_url_hash` INT(11) NULL,
  `create_timestamp` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



ALTER TABLE `short_url`.`short_url1`
ADD INDEX `short_code_index` (`short_code` ASC) VISIBLE,
ADD INDEX `origin_url_hash_index` (`origin_url_hash` ASC) VISIBLE;
;


#--------------

DROP TABLE IF EXISTS `short_url`.`short_url2`;
CREATE TABLE `short_url`.`short_url2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `origin_url` varchar(1380) DEFAULT NULL,
  `origin_url_hash` INT(11) NULL,
  `create_timestamp` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



ALTER TABLE `short_url`.`short_url2`
ADD INDEX `short_code_index` (`short_code` ASC) VISIBLE,
ADD INDEX `origin_url_hash_index` (`origin_url_hash` ASC) VISIBLE;
;

#--------------


DROP TABLE IF EXISTS `short_url`.`short_url3`;
CREATE TABLE `short_url`.`short_url3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `origin_url` varchar(1380) DEFAULT NULL,
  `origin_url_hash` INT(11) NULL,
  `create_timestamp` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



ALTER TABLE `short_url`.`short_url3`
ADD INDEX `short_code_index` (`short_code` ASC) VISIBLE,
ADD INDEX `origin_url_hash_index` (`origin_url_hash` ASC) VISIBLE;
;


#--------------



DROP TABLE IF EXISTS `short_url`.`short_url4`;
CREATE TABLE `short_url`.`short_url4` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `origin_url` varchar(1380) DEFAULT NULL,
  `origin_url_hash` INT(11) NULL,
  `create_timestamp` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



ALTER TABLE `short_url`.`short_url4`
ADD INDEX `short_code_index` (`short_code` ASC) VISIBLE,
ADD INDEX `origin_url_hash_index` (`origin_url_hash` ASC) VISIBLE;
;
