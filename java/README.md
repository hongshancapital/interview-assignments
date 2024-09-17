# Short-domain-service 短域名服务

## 本服务采用分层架构思路
- domain-web 域名web层，提供http接口，适配层。
- domain-application 应用层，调用下层基础设施完成具体的业务流程。
- domain-api 抽象接口层，可对外暴露出api。比如单独生成jar推送maven私服，让其它项目能够rpc调用。
- domain-infrastructure 基础设施层，数据库、缓存、队列实现可写在此层。基础设施层不应该做与业务有关的事情，只用做数据持久化或消息推送等。

由于存储的实现方式有多种，如db, cache, mq等等。
所以域名映射关系需要考虑解耦的话，可在基础设施层定义存储类接口，db、cache、mq实现其接口，通过spi扩展类机制来注入要调用的存储方式。
这样存储方式的变更不影响域名领域这块的业务逻辑，与基础实现剥离开。只做业务逻辑的实现，增加了系统的灵活性。


## 架构图：
![](https://files.mdnice.com/user/15983/c5dd97ec-048c-4e29-8031-706d26baf606.png)

## SwaggerUi
![](https://files.mdnice.com/user/15983/a6d7df75-12fe-420c-bde7-777e4b12270e.png)

## 测试报告图：
domain-web层报告截图
![](https://files.mdnice.com/user/15983/04678bcc-f2de-41e1-aa75-01619a3d8fe8.png)

domain-infrastructure层报告截图
![](https://files.mdnice.com/user/15983/b700f2ec-9461-49cd-b28b-5a55df364415.png)


### 防内存溢出思路
由于域名映射关系存在map中，也就是jvm内存里。为防止内存溢出。
1. 可设置java堆大小;
2. 设置map淘汰策略，比如初始化map队列大小，达到最大值后清除历史key值。

### 长域名转换后的值可能相同（可理解为：key值hashcode一样）
思路：
1. 类似map中红黑树结构，单独存储映射后值相同的问题；
2. 调整转换函数，2次转换处理，相当于用多个字符串函数映射出最终的值，减少字符串碰撞概率。
             
### 映射函数参考{@link Integer#toString(int, int)}
使用hashcode值转string，使用短除法来转换进制，不够8字符，随机补全，减少字符串冲突概率。