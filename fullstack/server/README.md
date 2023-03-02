### 红杉考试

说明：

启动服务
```bash
yarn start
```

<br />

项目打包
```bash
yarn build
```

<br />

创建数据库 SQL `creatsql.sql` 

<br />

``` sql
CREATE TABLE if not exists link_map_table(
  id serial primary key,
  short_link text NOT NULL,
  long_link text NOT NULL,
);

CREATE UNIQUE INDEX link_index ON link_map_table(short_link);
``` 