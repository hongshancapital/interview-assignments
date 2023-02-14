create table short_url (
    id int auto_increment,
    origin_url varchar(256) not null,
    short_url varchar(8) not null,
    create_time DATETIME not null,
    update_time DATETIME not null,
    constraint short_url_pk primary key (id)
) comment '短域名表';

create unique index uni_origin_url on short_url (origin_url);

create unique index uni_short_url on short_url (short_url);