create table if not exists public.shorturls
(
    longurl  varchar(1024) not null,
    shorturl varchar(8)    not null
        constraint shorturls_shorturl
            unique,
    id       bigserial
        constraint shorturls_pk
            primary key
);

create table if not exists public.autoinc_shorturls
(
    id      bigserial not null
            primary key,
    longurl varchar(1024)                                                         not null
);
