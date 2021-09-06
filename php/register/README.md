# 用户注册项目 demo
    本项目基于 Yii 2 Basic
## 项目依赖
- composer
- Yii2 Basic
- docker-compose
- 假设代码目录地址为 ~/wwwroot/php/scdt-china/php/register
## 部署方法
- 部署 docker-commpose
```.env

version: '3'
networks:
  local-net-imoowi:
    driver: bridge
services:
  mysql5.6:
    container_name: mysql5.6
    image: mysql:5.6
    ports:
      - 3306:3306
    environment:
      MYSQL_ROOT_PASSWORD: 123456
    restart: always
    networks:
      - local-net-imoowi
  nginx:
    container_name: nginx
    image: nginx:1.19.2
    ports:
      - 3000:3000
    volumes:
      - ~/wwwroot:/data/wwwroot
      - $PWD/nginx/conf.d:/etc/nginx/conf.d
    depends_on:
      - mysql5.6
      - redis
      - php7.4
    restart: always
    networks:
      - local-net-imoowi
  php7.4:
    container_name: php7.4
    image: imoowi/php7.4:v3
    ports:
      - 9000:9000
    volumes:
      - ~/wwwroot:/data/wwwroot
    depends_on:
      - redis
    restart: always
    networks:
      - local-net-imoowi
  redis:
    container_name: redis
    image: redis:6.2.1
    ports:
      - 6379:6379
    restart: always
    networks:
      - local-net-imoowi
```

- 部署 nginx vhost
```.env
#--------------localhost-------------#
    server {
        listen       3000;
        server_name  localhost;
#       access_log /var/log/nginx/localhost.access.log;
        error_log /var/log/nginx/localhost.error.log;
        autoindex on;               
        autoindex_exact_size off;           
        autoindex_localtime on; 
        index index.php index.html index.htm;
        location / {
            root   /data/wwwroot/php/scdt-china/php/register/web;
            index  index.php index.html index.htm;
                if ( !-e $request_filename ){
                        rewrite ^/(.*) /index.php last;

                }
            gzip on;
            gzip_min_length 1k;
            gzip_buffers 4 16k;
            gzip_comp_level 5;
            gzip_types text/plain application/x-javascript text/css application/xml text/javascript application/x-httpd-php;
       }
    
        location ~ \.php$ {
            root   /data/wwwroot/php/scdt-china/php/register/web;
            fastcgi_pass php7.4:9000;
            fastcgi_index /index.php;
            fastcgi_split_path_info       ^(.+\.php)(/.+)$;
            fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
            include fastcgi_params;
        }
        location ~ /\.ht{                                         
             deny all;                                            
         } 
    }
#--------------/localhost------------#

```

## 启动项目
- 启动 web 服务，安装 vendor
```.env
cd ~/wwwroot/php/scdt-china/php/register
docker-compose up -d
docker exec -i php7.4 sh -c "cd /data/wwwroot/php/scdt-china/php/register && composer install" 
```
- 启动数据库，导入 sql 文件
```.env
数据库文件在 db 文件夹里
./db/imoowi_register.sql
```
- 在浏览其中访问 http://localhost:3000