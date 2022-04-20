图片文件加载较慢,如有图片或格式问题可访问(https://www.yuque.com/docs/share/11e8ab5c-7299-47f8-ae4e-e1b699447241?# )
# 1、需求
#### 实现短域名服务（细节可以百度/谷歌）
撰写两个 API 接口:

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：

- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
- 映射数据存储在JVM内存即可，防止内存溢出；

# 2、分析
（1）核心转换算法如何做？UUID ？ 雪花？ 自增ID？等等。
（2）转换算法是否需要判断已经生成过？如果通过本地缓存/中间件缓存判断 不可取 因为缓存的作用不是在有效期的所有时间范围内，只是针对于热点时间做的查询缓存而已。读持久化层 就更不可取。开销不可接受。
（3）应该怎么避免内存溢出？
（4）是否可配置化？
（5）内存如何保证不溢出：LRU? OR 动态报警+内存容量处理机制。

# 3、目标&思路

### 3.1 设计目标
针对于短域名的存储和读取，开发两个api接口，短链和长链的关系则直接使用内存映射，并且避免内存溢出。

### 3.2 实现的功能
（1）api接口实现对长短域名的转换和获取
（2）对于长短域名的映射缓存
（3）接口充分测试

### 3.3 权衡思路
内存：如果使用java自带的Map当做内存 则需要手动指定数据存放到LRU中，本项目中使用的Caffeine Cache 包含了最后一次使用时间过期配置 以及 软引用配置 确保缓存最大命中率。
真实业务中 对redis(缓存失效时间) 和 持久化层进行监控 动态处理数据。(分库分表动态扩容/冷数据删除或迁移)

核心算法：UUID,雪花算法都不能保证趋势单调递增。其他的知名的ID生产工具都比较依赖数据库或者其他的中间件。例如snowflake依赖数据库，美团的Leaf虽说既支持号段模式也支持snowflake模式但是都有其他依赖。
（1）使用基于雪花的思想，改造下，使得支持分布式单调递增。（暂时使用思路1）
通过redis生成分布式唯一id(暂时使用本地序列号)联合 时间戳、机器id和机房ID的标识,通过这种方式能够保证分布式id的单调递增性。
具体代码如下：
```
    /** 每一部分占用的位数 **/
    private final static long SEQUENCE_BIT = 12;   //序列号占用的位数
    private final static long MACHINE_BIT = 5;     //机器标识占用的位数
    private final static long DATA_CENTER_BIT = 5; //机房标识占用的位数
    /** 每一部分的最大值 **/
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);
    /** 每一部分向左的位移 **/
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = MACHINE_BIT + DATA_CENTER_BIT;
    // 机房ID
    private long dataCenterId;
    // 机器ID
    private long machineId;
    // 序列号
    private volatile long sequence = 0L;
    // 上一次时间戳
    private volatile long lastTimeStamp = -1L;

    /**
     * 根据指定的机房ID和机器标志ID生成指定的序列号
     * @param dataCenterId
     * @param machineId
     */
    public NumberGeneratorUtil(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new GeneralException(Status.DATA_CENTER_ID_ERROR);
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new GeneralException(Status.MACHINE_ID_ERROR);
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     * @return
     */
    public synchronized long nextId() {
        long currTimeStamp = getNewTimeStamp();
        if (currTimeStamp < lastTimeStamp) {
            // 当前时间小于上一次时间戳 直接抛异常 时针回调
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        if (currTimeStamp == lastTimeStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currTimeStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastTimeStamp = currTimeStamp;
        return (currTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT //时间戳部分
                | dataCenterId << DATA_CENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewTimeStamp();
        while (mill <= lastTimeStamp) {
            mill = getNewTimeStamp();
        }
        return mill;
    }

    private long getNewTimeStamp() {
        return System.currentTimeMillis();
    }
```

（2）保证唯一性，不用自增序列，而是使用 时间戳 +多个hash函数构成。
多个hash函数 和 时间戳 对 62取模 映射到短链各个位置上
多个hash函数保证数据的唯一性。理论上多个hash函数+时间戳单调自增的方式 使得生成的号码就是唯一。
（亲测可用）
```
/**
     * 取号
     * 1. hashcode
     * 2. fnv hash算法
     * 3. murmur3 hash算法
     *
     * 利用三种算法组合 加上日期补位组合成8位短链
     *
     * @param url
     * @return
     */
    public static String convertUrl (String url, long time) {

        if (StringUtils.isEmpty(url) || time <= 0) {
            return null;
        }
        // murmurhash算法连续hash两次计算出两个结果
        HashFunction murmur3 = Hashing.murmur3_128();
        HashCode murhash = murmur3.hashString(url, Charsets.UTF_8);
        long murlong1 = murhash.asLong();
        murhash = murmur3.hashString(String.valueOf(murlong1), Charsets.UTF_8);
        long murlong2 = murhash.asLong();


        // 两次hashcode计算出两个结果
        long hashcode = url.hashCode();
        long hashcode2 = String.valueOf(hashcode).hashCode();

        //fNVHash算法 计算出两个结果
        long fnvCode = HashUtil.fNVHash(url);
        long fnvCode2 = HashUtil.fNVHash(String.valueOf(fnvCode));

        long time2 = time + (long)(Math.random() * 10000);

        String h1 = String.valueOf((int) Math.floorMod(hashcode, CHARS.length));
        String h2 = String.valueOf((int) Math.floorMod(hashcode2, CHARS.length));
        String f1 = String.valueOf((int)Math.floorMod(fnvCode, CHARS.length));
        String f2 = String.valueOf((int)Math.floorMod(fnvCode2, CHARS.length));
        String m1 = String.valueOf((int)Math.floorMod(murlong1, CHARS.length));
        String m2 = String.valueOf((int)Math.floorMod(murlong2, CHARS.length));
        String t1 = String.valueOf((int)Math.floorMod(time, CHARS.length));
        String t2 = String.valueOf((int)Math.floorMod(time2, CHARS.length));


        // 补位
        h1 = StringUtils.leftPad(h1, 2, '0');
        h2 = StringUtils.leftPad(h2, 2, '0');
        f1 = StringUtils.leftPad(f1, 2, '0');
        f2 = StringUtils.leftPad(f2, 2, '0');
        t1 = StringUtils.leftPad(t1, 2, '0');
        t2 = StringUtils.leftPad(t2, 2, '0');
        m1 = StringUtils.leftPad(m1, 2, '0');
        m2 = StringUtils.leftPad(m2, 2, '0');


        String[] array = new String[] {h1, h2, f1, f2, t1, t2, m1, m2};
        StringBuilder sb = new StringBuilder();

        for (String s : array) {
            sb.append(CHARS[Integer.parseInt(s)]);
        }

        return sb.toString();
    }
```


# 3.4、系统架构&核心逻辑

系统架构图
![image.png](https://cdn.nlark.com/yuque/0/2022/png/2317727/1650366885579-1e7c8b8e-c39a-4ee2-ab9f-a560a1a6380e.png)

逻辑图
![image.png](https://cdn.nlark.com/yuque/0/2022/png/2317727/1650367621023-14789813-a3e8-41df-8628-67ab0a3c87a6.png)

# 4、单元&功能测试

### 4.1 单元测试完成度
![image.png](https://cdn.nlark.com/yuque/0/2022/png/2317727/1650214372508-8e589a45-9cb7-48a3-b223-12b7d2f57082.png)

### 4.2 功能测试
1、获取短链
![image.png](https://cdn.nlark.com/yuque/0/2022/png/2317727/1650295914221-b87fa1ab-ff1c-464b-95cc-10d5a389079b.png)
2、通过短链获取长链
![image.png](https://cdn.nlark.com/yuque/0/2022/png/2317727/1650295990249-7c3be1df-1c70-4dc0-992f-931e2c4eb3bc.png)

### 4.3 swagger API
![image.png](https://cdn.nlark.com/yuque/0/2022/png/2317727/1650296799173-adb40bf8-3835-4441-b326-4da0d6ad374f.png)


# 5、性能测试
**测试工具**
jmeter
**机器配置**
内存：8 GB
**JVM配置
   - 版本：JDK1.8
   - JVM设置：-Xms1024M -Xmx1024M -Xmn820M -Xss1M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=5 -XX:PretenureSizeThreshold=1M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC

**测试结果：**
![image.png](https://cdn.nlark.com/yuque/0/2022/png/2317727/1650381094067-d4e4ace8-24d1-453b-b177-b0f38dcf2fc9.png)
平均响应时间：26ms, QPS：7465


# 6、待改进优化
1.可以维护一个唯一序列号大小进行分配的阻塞队列 redis保证唯一 队列保证效率（项目中根据时间戳的）
2.真实场景下 短链业务服务化 根据不同业务 配置不同（例如位数、过期时间等等） 服务化后 各种参数支持配置化 增加项目的可维护性
3.是否可以短链通过阻塞队列进行预生成，在业务高峰期，短链预生成，直接走缓存获取，提高业务性能


# 7、作者信息
**基本信息** 

姓名：洪峥灿      电话：17319136242 

**教育背景**

2012.9--2016.6 本科 计算机科学与技术

**工作经历**

百度凤巢业务端（在职）：参与凤巢业务端广告线索营销相关业务

58同城：参与招聘平台相关业务

腾讯音乐商业广告研发部：主要参与商业广告相关业务 和 音乐支付相关业务。

2016.6--至今
