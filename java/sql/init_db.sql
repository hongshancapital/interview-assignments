DROP PROCEDURE IF EXISTS `init_db`;
DELIMITER //
CREATE PROCEDURE init_db()
BEGIN
    DECLARE i INT;
    SET i = 0;
    WHILE i < 100 DO
      SET @drop_table = concat('DROP TABLE IF EXISTS url_mapping_', i);
      PREPARE drop_table FROM @drop_table;
      EXECUTE drop_table;

      SET @create_table = concat(
          'CREATE TABLE url_mapping_', i,
          '(
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            url VARCHAR(300)
          ) ENGINE = InnoDB, AUTO_INCREMENT = ', i, ', CHARSET utf8 COLLATE utf8_general_ci');
      PREPARE create_table FROM @create_table;
      EXECUTE create_table;

      SET i = i + 1;
    END WHILE;

    DROP TABLE IF EXISTS initial_code;
    CREATE TABLE server_version
    (
      id BIGINT AUTO_INCREMENT PRIMARY KEY
    ) ENGINE = InnoDB;
END//
DELIMITER ;

CALL init_db();

GRANT ALL PRIVILEGES ON short_url.* TO root@192.168.1.101 IDENTIFIED BY '1235';