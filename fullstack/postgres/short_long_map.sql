CREATE TABLE short_long_map (
    short_domain CHAR(8) NOT NULL PRIMARY KEY,
    long_domain VARCHAR(8182),
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX long_domain_hash_index on short_long_map using hash (long_domain);

COMMENT ON TABLE short_long_map IS '长短域名映射表';
COMMENT ON COLUMN short_long_map.short_domain IS '主键，短域名';
COMMENT ON COLUMN short_long_map.long_domain IS 'hash索引，长域名';