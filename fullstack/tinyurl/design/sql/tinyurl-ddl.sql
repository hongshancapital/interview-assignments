CREATE DATABASE tinyurl CHARSET utf8mb4 COLLATE utf8mb4_bin;

USE tinyurl;

DROP TABLE IF EXISTS t_url;
CREATE TABLE t_url (
    id BIGINT PRIMARY KEY,
    tinyurl VARCHAR(255) UNIQUE,
    targeturl VARCHAR(1024) NOT NULL
);
