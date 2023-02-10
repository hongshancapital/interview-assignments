# 需求分析

## 核心任务目标

1.  提供两个 API
    1.  短域名存储接口：接受长域名信息，返回短域名信息
    2.  短域名读取接口：接受短域名信息，返回长域名信息。(注意, 只是提供转换 API, 不涉及实际跳转)
2.  额外注意事项
    1.  要求使用 TypeScript 实现
    2.  **不要过度设计**
    3.  **每一个依赖以及每一行代码都有足够充分的理由** 


## 容量评估

假设每天短链生成的量为 1 亿条，请求层面的 读 / 写 = 10 / 1，且该短链服务将运行十年

1. 每秒的写操作约为：1 亿 / 24 / 3600 $\approx$ 1157

    每秒的读操作约为：$\approx$ 1157 \* 10 $\approx$ 11570

    备注：加上业务逻辑层的读次数，真正的读操作可能会到 10w/s 数量级

2. DB 需要存储的数据条数：1 亿 \* 365 \* 10 = 3650 亿

3. 由于合法 url 不含 + - 等字符，用 [0-9,a-z,A-Z] 表示即可。如果要求短链字符长度最长为 8 个字符，a = 62<sup>8</sup> 已经远远大于 3650 亿。其实 7 位字符已经能满足需求。例如新浪微博的短链系统采用的就是 7 位字符。

# 短链生成策略
短链可以根据full_url，使用hash和encrypt_key计算出来， 此处进行加密是为了防止黑客对进行短链预测攻击， 以及提供短链冲突的解决机制：
```JavaScript
salt=sha256(timstamp).sub_string(0,8)
short_url=AES.encrypt(full_url+salt, encrypt_key).sub_string(0,8).
```

## HASH冲突避免
为了防止新生成的短链，和数据库中存在的短链冲突， 向数据库中插入新生成的短链时， 需要在数据库中查询待插入的短链是否存在冲突， 当数据库中已经存在冲突的短链记录时， 需要采用重试法， 获取新的时间戳重新生成短链。
```JavaScript
salt=sha256(get_current_timstamp()).sub_string(0,8)
short_url=AES.encrypt(full_url+salt, encrypt_key).sub_string(10,18)
let i=0
while(is_invalid_short_url(short_url)&&i<5) { //需要对重试的次数进行限制，例如5次不成功则返回失败， 此处由于hash后的地址空间相对还算充足， 故可以多试几次
  salt=sha256(get_current_timstamp()).sub_string(0,8)
  short_url=AES.encrypt(full_url+salt, encrypt_key).sub_string(10,18)
  i++
}
```
AES加密后密文前10位较为固定， 为了使生成的短链分布更加均匀， 此处选取密文中间的[10:18]为作为短链，此处也可随机取密文中的8位, 并过滤只保留[a-zA-Z0-9]中的字符作为短链。
# 数据库设计
成熟的长域名和短域相互转换系统，需要提供取消映射，并保留对应历史记录用于审计，建议将数据库字段设计成如下格式:
```sql
DROP TABLE IF EXISTS public.url_mapping;

CREATE TABLE IF NOT EXISTS public.url_mapping
(
    id BIGSERIAL,
    short_url character(10) NOT NULL,
    full_url character(255) NOT NULL,
    salt character(10) NOT NULL,
    created_at timestamp DEFAULT NOW(),
    expired_at timestamp,
    is_deleted boolean DEFAULT 'false',
    CONSTRAINT url_mapping_pkey PRIMARY KEY (short_url)
)
```
 考虑题目要求，为避免过度设计， 实际使用的数据库字段为：
 ```sql
DROP TABLE IF EXISTS public.url_mapping;

CREATE TABLE IF NOT EXISTS public.url_mapping
(
    short_url character(10) NOT NULL,
    full_url character(255) NOT NULL,
    salt character(10) NOT NULL,
    CONSTRAINT url_mapping_pkey PRIMARY KEY (short_url)
)
```
salt只在生成短链时用到， 如果不需要可以从数据库中去掉此列。
# 安全策略
用户输入的长域名和短域都需要进行参数合法性校验， 不能包含除了白名单外的任何特殊字符，以防止注入攻击。
