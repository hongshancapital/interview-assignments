# PHP Engineer Assignment

## 用户注册 Demo

### 需求

- 浏览器通过 `localhost:3000` 可以访问到一个用户注册页
- 该页是一个表单，有三个字段：
  - Username
  - Password
  - Repeat Password
- 还有一个 Submit 按钮
  - 点击后，发送请求 `POST localhost:3000/api/register`
  - 该 API 会验证如下条件：
    - Username 是否符合要求：
      - 只能以英文字母或下划线开头
      - 只能包含英文字母，下划线或数字
    - Password 是否符合要求：
      - 长度在 6 位以上
      - 不能含有 3 位或以上的连续数字，如 123、234 等
      - 必须有大写字母，小写字母或数字中的两项
  - 根据 API 结果在注册页展示相关信息
  
### 备注
  
- 网页不需要美观，只需满足需求即可
- 需要设置用户数据库/提供数据库相关信息
- 可以使用任意 php 框架，但不要提交框架本身


# 项目说明
使用php 框架lumen

## nginx配置
```server {
    listen 3000;
    server_name localhost:3000;
    root /Users/yaoran/data/www/scdt-lumen/public;

    access_log /usr/local/var/log/nginx/scdt-luemn_access.log;
    error_log  /usr/local/var/log/nginx/scdt-luemn_error.log;

    add_header X-Frame-Options "SAMEORIGIN";
    add_header X-XSS-Protection "1; mode=block";
    add_header X-Content-Type-Options "nosniff";

    index index.html index.htm index.php;

    charset utf-8;

    location / {
        try_files $uri $uri/ /index.php?$query_string;
    }


    location = /favicon.ico { access_log off; log_not_found off; }
    location = /robots.txt  { access_log off; log_not_found off; }

    error_page 404 /index.php;

    location ~ \.php$ {
        #fastcgi_split_path_info ^(.+\.php)(/.+)$;
        #fastcgi_pass unix:/var/run/php/php7.1-fpm.sock;
	fastcgi_pass 127.0.0.1:9000;
        fastcgi_index index.php;
        fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        include fastcgi_params;
	include fastcgi.conf;
    }

    location ~ /\.(?!well-known).* {
        deny all;
    }
}
```

## api 说明
1. 访问http://localhost:3000 到注册页
2. 验证Controller : app/Http/Controllers/RegisterController.php
3. 数据库配置文件 config/database.php
## 数据库 & 表结构
```
CREATE TABLE `users` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `username` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',
   `passwd` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码',
   `created_at` int(11) DEFAULT NULL COMMENT '创建时间',
   `updated_at` int(11) DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;`
```
