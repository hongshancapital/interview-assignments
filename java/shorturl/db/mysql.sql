create schema shorturl;

use shorturl;

create table short_url
(
    id bigint auto_increment
        primary key,
    source_url varchar(512) null,
    create_time datetime null,
    constraint short_url_source_url_uindex
        unique (source_url)
);

alter table short_url AUTO_INCREMENT=10000;
