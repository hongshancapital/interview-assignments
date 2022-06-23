create schema short_url collate utf8mb4_unicode_ci;

use short_url;

create table short_urls (
    id int auto_increment primary key,
    long_url varchar(2048) not null,
    created_at timestamp default CURRENT_TIMESTAMP not null
);
