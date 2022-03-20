# 1. 项目背景
#### 实现短域名服务
撰写两个 API 接口:
- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：
- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
- 映射数据存储在JVM内存即可，防止内存溢出；
# 2. 短链设计思路
短链接必须满足以下要求：

1. 不能超过8个字符
2. 字符必须符合URL规范（比如不可为隐藏字符、换行符等特殊字符）
3. 短链接全局唯一

本项目短链生成算法思想主要用到了： Snowflake和Base64URL

**主要思路：**

1. Base64生成的字符串中，一个字符可承载6比特信息
2. 那么8个字符就需要48比特信息
3. 设计一款48比特的雪花算法，前40比特代表时间戳，中间4比特代表工作Id（不同机器之间不可重复），后4比特代表序号
4. 将雪花算法生成的48比特数据，转成长度为6的byte数组，再通过Base64URL转成长度为8的短链接

**特点：**

1. 时间戳的单位为毫秒，共计40位，可确保34.8年不会出现重复数据
2. 工作Id占有4比特，最大支持 2^4 = 16 个机器分布式部署
3. 理论上每毫秒可生成 2^4 = 16个序号，所以单机理论TPS为 2^4 * 1000 = 16000


**核心代码：**

```java
    /**
 * 将48位的Long转成长度为6的byte数组，进而转成长度为8的base64
 */
private String longToBase64(long uuid){
        byte[]bytes=new byte[6];
        bytes[0]=(byte)(uuid>>>40);
        bytes[5]=(byte)(uuid>>>32);
        bytes[2]=(byte)(uuid>>>24);
        bytes[4]=(byte)(uuid>>>16);
        bytes[3]=(byte)(uuid>>>8);
        bytes[1]=(byte)(uuid);

        return Base64.getUrlEncoder().encodeToString(bytes);
        }

private synchronized long nextId(){
        long currentTimeMillis=getCurrentTimeMillis();
        LastUUID currentUUID;
        if(lastUUID!=null&&lastUUID.lastTimeMillis==currentTimeMillis){
        // 如果uuid已经溢出，则循环等待下一毫秒获取uid
        if(bitsAllocator.isSequenceValueOverflow(lastUUID.uuid)){
        waitNextTimeMillis(lastUUID.lastTimeMillis);
        return nextId();
        }
        currentUUID=lastUUID.increment();
        }else{
        currentUUID=new LastUUID(currentTimeMillis);
        currentUUID.uuid=bitsAllocator.allocate(currentTimeMillis,workId,0); // 序号从0开始
        }

        this.lastUUID=currentUUID;
        return currentUUID.uuid;
        }
```

# 3. 系统架构图

### 3.1 架构图

![架构图.png](https://cdn.nlark.com/yuque/0/2022/png/661399/1647743101806-aae0f496-69f8-402f-a95b-1ef27e3e9e19.png#clientId=u8884b023-331a-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=186&id=uf433fa42&margin=%5Bobject%20Object%5D&name=%E6%9E%B6%E6%9E%84%E5%9B%BE.png&originHeight=371&originWidth=701&originalType=binary&ratio=1&rotation=0&showTitle=false&size=30919&status=done&style=none&taskId=u200eb1ee-2dc5-4821-97d0-e6957341b51&title=&width=350.5)
> 说明：在当前代码实现中，工作Id是随机生成的。如果应用于企业级分布式环境，需引用Zookeeper等中间件确保工作Id的全局唯一性

### 3.2 流程图

![生成短链接请求.png](https://cdn.nlark.com/yuque/0/2022/png/661399/1647697287844-b8678879-e9de-4dbc-945a-52a050700001.png#clientId=uc42c11c1-289a-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=uc739903e&margin=%5Bobject%20Object%5D&name=%E7%94%9F%E6%88%90%E7%9F%AD%E9%93%BE%E6%8E%A5%E8%AF%B7%E6%B1%82.png&originHeight=711&originWidth=311&originalType=binary&ratio=1&rotation=0&showTitle=false&size=30937&status=done&style=none&taskId=u27e73e90-8b4a-4c34-aa45-1c037a2fb0f&title=)

# 4. 关键设计

1. 雪花算法生成器，可确保34年不会重复，单机理论TPS可达1.6万
2. 最多支持16个节点分布式部署，总理论TPS可达25万
3. 短链生成器、数据持久层等均接口化，符合依赖倒置原则
4. 针对相同长连接生成短链的请求，支持幂等开关
5. 多线程安全
# 5. 测试

## 5.1 单测覆盖率
![image.png](https://cdn.nlark.com/yuque/0/2022/png/661399/1647697808332-c1199270-b164-4358-98b3-b899ede60762.png#clientId=uc42c11c1-289a-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=186&id=u95eccf70&margin=%5Bobject%20Object%5D&name=image.png&originHeight=372&originWidth=2590&originalType=binary&ratio=1&rotation=0&showTitle=false&size=136684&status=done&style=none&taskId=u989a34ff-8b0c-4161-a0b6-354e840a27d&title=&width=1295)
## 5.2 性能测试
- 运行机器：2020 MacBook Air M1 16GB
- 启动参数：-Xms2048M -Xmx2048M
- 压测客户端：Jmeter   线程数=20，循环次数=200万
> 受条件限制，压测客户端和服务端在同一台机器；已做预热；每次请求使用不同参数，避免命中缓存

压测结果：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/661399/1647700281481-d8606042-a95f-4504-bd35-05e0c4430af8.png#clientId=uc42c11c1-289a-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=224&id=u34dcfc44&margin=%5Bobject%20Object%5D&name=image.png&originHeight=448&originWidth=2304&originalType=binary&ratio=1&rotation=0&showTitle=false&size=246811&status=done&style=none&taskId=ue96e9034-2fc8-4a81-bc66-d15f910fb07&title=&width=1152)
JVM运行情况：
![image.png](https://cdn.nlark.com/yuque/0/2022/png/661399/1647700375201-3f6c9ca2-8239-4057-82fc-c289a45cdea2.png#clientId=uc42c11c1-289a-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=466&id=u4f2e646d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=932&originWidth=2016&originalType=binary&ratio=1&rotation=0&showTitle=false&size=177539&status=done&style=none&taskId=uafa71674-2ff4-44a8-9b5f-d2b36fec349&title=&width=1008)


