# TypeScript Fullstack Engineer Assignment

### 如何运行
```BASH
$ docker compose up
# 等待所有服务运行成功
# 浏览器访问 http://localhost:3000
fullstack-nodejs-1  | [nodemon] starting `node dist/server.js dist/server.js`
fullstack-nodejs-1  | Server listening on port: 3000
```

### 集成案例
docker container成功运行后，浏览器访问 http://localhost:3000
![preview.png](https://github.com/lijingbo8119/interview-assignments/raw/master/fullstack/preview.png)

### 数据库相关
数据库采用MySQL，由docker compose初始化mysql service时自动创建Schema，如下：
```BASH
$ cat docker-compose.yml | head -n 12
```
```YAML
version: "3.9"  # optional since v1.27.0
services:

  mysql:
    image: mysql
    volumes:
      - ./docker/init.sql:/docker-entrypoint-initdb.d/init.sql // 初始化数据库
    ports:
      - "3306:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=dev_root_password
      - TZ=Asia/Shanghai
```
```SQL
create schema short_url collate utf8mb4_unicode_ci;

use short_url;

create table short_urls (
    id int auto_increment primary key,
    long_url varchar(2048) not null,
    created_at timestamp default CURRENT_TIMESTAMP not null
);
```

### 基础架构
采用前后端分离的形式进行开发
* 前端：React + TypeScript，入口文件: src/client/index.tsx
* 后端：Express + TypeScript，入口文件: src/server/server.ts
* 通信：前端通过axios（fetch API）与后端接口通信

### 算法
采用英文字母a-z大小写与数字的组合，做10进制与64位转换。
```TypeScript
export class Shorter {
    // 字符表打乱，让生成的short_path看上去没有那么有规律。
    private static readonly alphabet: string[] = [
        '8', 'M', 'j', 'e', '0', 'O', 'l', '4', 'u', 'N',
        's', 'x', 'g', 'B', 'd', 'h', 'a', 'p', 'C', 'G',
        'b', 'Y', '7', 'f', 'F', 'W', 'w', 'k', 'c', 'X',
        'V', 'v', 'i', 'n', 'z', 'R', '2', 'E', 'T', 'y',
        'I', 'o', 'U', 'K', 't', 'm', 'q', 'L', 'H', 'S',
        '6', '1', 'Z', '3', 'Q', '5', 'J', 'D', 'r', 'A',
        '9', 'P',
    ];

    // id加上一个基本整数，保证生成的short_path最短为5位
    private static readonly baseNum: number = 100000000;

    // MySQL主键id转为short_path
    public static idToStr(id: number): string {
        let num = id + Shorter.baseNum;
        let arr: number[] = [];
        while (num > 0) {
            arr.push(Math.trunc(num % 62));
            num = Math.trunc(num / 62);
        }
        let str = '';
        for (let i = arr.length - 1; i >= 0; i--) {
            str = str + Shorter.alphabet[arr[i]]
        }
        return str
    }

    // short_path转为MySQL主键id
    public static strToId(str: string): number {
        let arr: number[] = str.split('').reverse().map((s: string) => Shorter.findIndex(s))
        let num = 0;
        for (let i = arr.length - 1; i >= 0; i--) {
            num += arr[i] * Math.pow(62, i)
        }
        return num - Shorter.baseNum
    }

    private static findIndex(s: string): number {
        for (let i = 0; i < Shorter.alphabet.length; i++) {
            if (s === Shorter.alphabet[i]) {
                return i;
            }
        }
        return -1;
    }
}
```

### Test

server模块中的routes由tests模块中的http.ts脚本进行模拟http请求测试。

```BASH
$ docker exec -it fullstack-nodejs-1 /bin/sh
$ npm run test:coverage

--------------------------------------|---------|----------|---------|---------|-------------------
File                                  | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
--------------------------------------|---------|----------|---------|---------|-------------------
All files                             |   63.55 |    34.78 |   73.68 |   62.83 |                   
 models/file:/var/www/html/src/models |   96.29 |    66.66 |     100 |   96.29 |                   
  http.ts                             |     100 |     87.5 |     100 |     100 | 16                
  shortUrl.ts                         |   92.85 |       50 |     100 |   92.85 | 23                
 server                               |       0 |        0 |       0 |       0 |                   
  routes.ts                           |       0 |        0 |       0 |       0 | 8-69              
  server.ts                           |       0 |        0 |       0 |       0 | 4-10              
 server/file:/var/www/html/src/server |      98 |      100 |     100 |   97.82 |                   
  db.ts                               |     100 |      100 |     100 |     100 |                   
  lib.ts                              |      96 |      100 |     100 |   95.23 | 43                
  repositroy.ts                       |     100 |      100 |     100 |     100 |                   
--------------------------------------|---------|----------|---------|---------|-------------------

=============================== Coverage summary ===============================
Statements   : 63.55% ( 75/118 )
Branches     : 34.78% ( 16/46 )
Functions    : 73.68% ( 14/19 )
Lines        : 62.83% ( 71/113 )
================================================================================
```


