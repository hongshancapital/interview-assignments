# A service to transform long url to it's shorter alias.

## solution
直接使用了mysql 的自增ID 作为url的短链接，并且限制短链接不超过8位，也就是1亿以内

## deps
1. yarn
2. 可以使用docker安装 mysql
3. schema 在 db.sql里
4. 配置文件在 .env 里

## Build
npm run build

## Run
npm run start
