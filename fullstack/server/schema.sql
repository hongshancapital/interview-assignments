CREATE TABLE if not exists link_map_table(
  id serial primary key,
  short_link text NOT NULL,
  long_link text NOT NULL,
  create_time timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX link_index ON link_map_table(short_link);
