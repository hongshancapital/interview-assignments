CREATE TABLE if not exists link_map_table(
  id serial primary key,
  short_link text NOT NULL,
  long_link text NOT NULL,
);

CREATE UNIQUE INDEX link_index ON link_map_table(short_link);
