CREATE TABLE IF NOT EXISTS url_id(
  id INTEGER PRIMARY KEY NOT NULL,
  nop INTEGER
);

CREATE TABLE IF NOT EXISTS url_data(
  id INTEGER PRIMARY KEY NOT NULL,
  short varchar(8) NOT NULL,
  url varchar(2048) NOT NULL,
  createTime bigint NOT NULL,
  refreshTime bigint NOT NULL,
  UNIQUE (short, url)
);

CREATE INDEX IF NOT EXISTS idx_short ON url_data (short);
CREATE INDEX IF NOT EXISTS idx_url ON url_data (url);