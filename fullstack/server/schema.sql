CREATE TABLE if not exists link_map_table(
  id serial primary key,
  short_link VARCHAR(100) NOT NULL,
  long_link VARCHAR(1000) NOT NULL,
  create_at timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX link_index ON link_map_table(short_link);
