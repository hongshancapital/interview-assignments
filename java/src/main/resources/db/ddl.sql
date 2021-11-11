create table url_mapping
(
    id           bigint auto_increment comment '自增主键'
        primary key,
    short_key    varchar(6)   default ''                not null comment '短链key',
    original_url varchar(512) default ''                null comment '原始url',
    expire_time  datetime     default CURRENT_TIMESTAMP not null comment 'key失效日期',
    ctime        datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    utime        datetime     default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint url_mapping_original_url_uindex
        unique (original_url)
)
    comment '链接映射表';
