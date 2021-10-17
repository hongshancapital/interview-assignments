create table if not exists url_map (
ID int not null primary key auto_increment,
SHORT_URL varchar(8),
LONG_URL varchar(2000),
CREATE_TIME DATE,
MODIFY_TIME DATE);