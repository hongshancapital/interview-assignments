CREATE DATABASE IF NOT EXISTS fullstack;

use fullstack;

CREATE TABLE IF NOT EXISTS short_url (
  short_url CHAR(8) NOT NULL PRIMARY KEY,
  origin_url VARCHAR(500) NOT NULL,
  update_time BIGINT NOT NULL
);
