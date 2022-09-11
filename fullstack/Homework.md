> 这里提交除源代码之外的其他作业

## 2. 单元测试代码以及单元测试覆盖率
### 单元测试代码
  见 `/src/__tests__`
### 单元测试覆盖率
  见 `/coverage/Icov-report/index.html`

## 3. API 集成测试案例以及测试结果
  由于未做过集成测试，这里又需要一定的学习时间，暂未提交，请见谅

## 4. 简单的框架设计图，以及所有做的假设

### 框架设计图
  【腾讯文档】短链服务框架图
https://docs.qq.com/slide/DYVh2c3RweWdZVUVG

### 假设罗列
  - Q：大量相同长域名多次调用短域名存储接口，会造成存储空间浪费
    A：一定时间内（比如1小时），在内存或者redis数据库中，维护一个longurl-id的数据库，查询时先看此数据库中是否存在该域名，否则存储到实际的数据库中，来获取对应ID。
  - Q：ID对应62进制来组成短链，此ID为自增型，如何解决数据存储量问题
    A：因为是自增型ID，且从长时间来看，大部分短链都是做插入操作，定期清空老旧数据即可。
  - Q：比自增性ID更好的解法
    A：做一个ID生成器，不再局限于数据库的int主键局限，同时存储也可以用到大型数据处理方案
## 5. 涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。

### 数据库结构
  主键：**id** 自增性int
  **long_url** 用于存储长url
  ![alt 数据库结构](https://fcccdn.qq.com/infoop/47f2c2e0975ff234babf421ac0e060a3_1635317150220.jpg)
### 涉及SQL
  #### insert
  `insert into short_content (long_url) values ('xxx') ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id);`
  #### select
  `select * from short_content where id = 123;`
