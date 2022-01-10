drop table if exists short_url_mapping;

CREATE TABLE short_url_mapping (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  short_url varchar(8) NOT NULL COMMENT '短链url',
  long_url varchar(512) NOT NULL COMMENT '长链url',
  create_time bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_short_url (short_url)
) ;