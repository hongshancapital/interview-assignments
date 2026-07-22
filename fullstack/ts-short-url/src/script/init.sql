
/*创建数据库*/
create database short_url;

/*创建数据库用户*/
create user  'su'@'%' identified by 'su';

/*可选【调整用户密码存储方式(针对MySQL8.0以上的版本)】*/
alter user 'su'@'%' identified with mysql_native_password by 'su';

/*授权用户表权限*/
grant all on short_url.* to 'su'@'%';

/*选择short_url数据库*/
use short_url;

/*创建表结构*/
CREATE TABLE short_url (
    shorturl_id VARCHAR(255) COMMENT '短链接ID',
    original_url VARCHAR(255) COMMENT '长连接URL', /*chrome（谷歌）的url长度限制超过为8182*/
    create_date VARCHAR(10) COMMENT '生成日期',
    PRIMARY KEY (shorturl_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;