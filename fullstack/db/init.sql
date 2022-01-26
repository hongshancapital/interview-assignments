create table code
(
    id   int auto_increment
        primary key,
    code varchar(255) not null comment '短码',
    url  varchar(255) not null comment '正常url',
    constraint IDX_3aab60cbcf5684b4a89fb46147
        unique (code),
    constraint IDX_cd2222269fa44a9319b235fc98
        unique (url)
);

