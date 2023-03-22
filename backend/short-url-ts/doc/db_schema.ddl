create table url
(
    id         bigserial
        primary key,
    origin     text                     not null,
    is_deleted boolean default false    not null,
    ts         timestamp with time zone not null
);

comment on column url.origin is '长链接';

comment on column url.is_deleted is '是否已删除';
