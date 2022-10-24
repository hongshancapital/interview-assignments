--env: sqlite3

--create table
CREATE TABLE IF NOT EXISTS `url_map`
(
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `short_url` VARCHAR(8) NOT NULL UNIQUE,
  `origin_url` TEXT NOT NULL,
  `origin_hash` INTEGER NOT NULL UNIQUE,
  `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);