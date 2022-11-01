create table short_url.short_url_info
(
    id          int auto_increment
        primary key,
    url         varchar(1024)                       not null,
    url_hash    binary(16)                          not null,
    create_time timestamp default CURRENT_TIMESTAMP null,
    constraint id
        unique (id)
);

create index short_url_info_url_hash_index
    on short_url.short_url_info (url_hash);

