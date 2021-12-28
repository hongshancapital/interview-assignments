#1、需求

实现短域名服务（细节可以百度/谷歌）
撰写两个 API 接口:

短域名存储接口：接受长域名信息，返回短域名信息
短域名读取接口：接受短域名信息，返回长域名信息。
限制：

短域名长度最大为 8 个字符
采用SpringBoot，集成Swagger API文档；
JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
映射数据存储在JVM内存即可，防止内存溢出；
递交作业内容

源代码(按照生产级的要求编写整洁的代码，使用gitignore过滤掉非必要的提交文件，如class文件)
Jacoco单元测试覆盖率截图(行覆盖率和分支覆盖率85%+)
文档：完整的设计思路、架构设计图以及所做的假设(Markdown格式)
加分项

系统性能测试方案以及测试结果

#2、设计思路
### 3.1 id生成规则（核心算法）
使用阉割版雪花算法生成ID，ID分为高位M和低位L，设置一个初始时间戳（如项目创建时间）initTimestamp, 根据当前时间减去初始
时间的差值做为高位M，每毫秒内的并发数做为低位L，目前配置低位占7个bit,也就是每毫秒并发数最大128，然后M向左进7位或运算L
即： M << 7) | L 得到ID

    // 这个是核心方法，通过调用nextId()方法，让当前这台机器上的snowflake算法程序生成一个全局唯一的id
    public synchronized long nextId() {
        // 这儿就是获取当前时间戳，单位是毫秒
        long timestamp = nowTimestamp();
        if (timestamp < lastTimestamp) {
        throw new RuntimeException(
            String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
            lastTimestamp - timestamp));
        }

        // 同一毫秒内累加
        if (lastTimestamp == timestamp) {

        // 累加 且不能超过最大值
        sequence = (sequence + 1) & maxSequence;
        // 如果超过最大值 等待下一毫秒
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        // 这儿记录一下最近一次生成id的时间戳，单位是毫秒
        lastTimestamp = timestamp;
        // 将当前时间戳左移sequenceBits位，空出sequence的位数，加上sequence
        return ((timestamp - initTimestamp) << sequenceBits) | sequence;
    }

    
    //下一毫秒.
    private long tilNextMillis(long lastTimestamp) {

        long timestamp = nowTimestamp();

        while (timestamp <= lastTimestamp) {
        timestamp = nowTimestamp();
        }
        return timestamp;
    }

### 3.2 短链接生成规则
将ID由10进制转换位62进制字符串S，预测在30年内该62进制的数值将不会超过7位，预留一位机器码位C （分布术的情况下在最大8位
短链接的情况下允许62台机器），得到不超过8位的短链接码 C+S


### 3.3 映射数据存储
使用guava做为缓存存储数据，为提升并发存取性能，初始化99（可配置）个cache，在存或者取的时候根据ID做运算得到被存储的cache
(id ^ (id >>> 7)) % 99 （已验证数据是均匀分布，下面有测试数据，其中7指的是每毫秒最大ID生成数的bit位数，这样可以得到
均匀的分布）得到数据 将要被存放的cache


# 4 系统架构
![img.png](https://xeducationfile-stg.oss-cn-hangzhou.aliyuncs.com/smartcall/dialogue/audio/tts/real/c6155567-dda2-4395-a68a-9fc40a5133c9_c.jpeg)

# 5 单元测试
### 5.1 测试覆盖率
![img.png](https://xeducationfile-stg.oss-cn-hangzhou.aliyuncs.com/smartcall/dialogue/audio/tts/real/3932618a-ec9a-4d64-bdcf-b191d6db2df1_a.jpeg)

### 5.2 Swagger
![img.png](https://xeducationfile-stg.oss-cn-hangzhou.aliyuncs.com/smartcall/dialogue/audio/tts/real/98b19ba0-c062-4e74-bdd7-c60d075c1f4b_b.jpeg)

# 6 性能测试
### 6.1 压测
测试工具：jmeter
测试量：100W次
机器配置：MacBook Pro Apple M1 16G
jdk:1.8
jvm:-Xmx3550m -Xms3550m -Xss128k -XX:NewRatio=4 -XX:SurvivorRatio=4 -XX:MaxPermSize=16m -XX:MaxTenuringThreshold=0
测试结果：qps:16711 平均响应时间:0m, 最大响应时间:746，错误率:0%
![img.png](https://xeducationfile-stg.oss-cn-hangzhou.aliyuncs.com/smartcall/dialogue/audio/tts/real/bc540fde-5d84-4b65-a669-fa46e985e374_d.jpeg)
结果：单机qps 1.6W,分布式情况下将会有更大规模提升

### 6.2 数据分布测试
证明 3.3中描述的数据均匀分布
![img.png](https://xeducationfile-stg.oss-cn-hangzhou.aliyuncs.com/smartcall/dialogue/audio/tts/real/a781b81f-04e9-4248-a03d-bb8da1e11228_e.jpeg)
![img.png](https://xeducationfile-stg.oss-cn-hangzhou.aliyuncs.com/smartcall/dialogue/audio/tts/real/b8a506a0-1a9a-409a-9fc3-434bb8a9e2dd_f.jpeg)

