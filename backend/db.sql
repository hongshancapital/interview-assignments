# id: 主键
# short_url：唯一索引
# long_url：前缀索引
create table short_url_map
(
  id          bigint unsigned auto_increment comment '主键'
    primary key,
  short_url   varchar(32)                         not null comment '短网址',
  long_url    varchar(1024)                       not null comment '原始长网址',
  create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
  constraint uniq_short_url
    unique (short_url)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  comment '短网址映射表';

create index idx_long_url
  on short_url_map (long_url(12));
