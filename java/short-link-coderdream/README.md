# 短域名服务方案及实现



## 1、需求

### 实现短域名服务（细节可以百度/谷歌）

撰写两个 API 接口:
- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：
- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
- 映射数据存储在JVM内存即可，防止内存溢出；

### 参考文档
1. [短网址(short URL)系统的原理及其实现](https://segmentfault.com/a/1190000012088345)
1. [高性能短链设计](https://mp.weixin.qq.com/s/YTrBaERcyjvw7A0Fg2Iegw)

## 2、系统架构



<div align="center">
	<img src="images\01.ArchitectrueDiagram.png" />
	</br>
	<span>图-1 架构设计图</span>
</div>

**架构设计说明**

1. 为提升短域名系统的高可用，本设计采用分布式架构；
2. 在服务前端采用Nginx等负载均衡器进行分流；
3. 每台服务器从ZooKeeper获取唯一的ID，作为机器ID，用于短链接的拼接；
4. 每台服务器都有短链接生成器（通过MurmurHash、62进制转换）；
5. 生成的短链接分别存储在布隆过滤器和**Guava Cache**中，供后续插入及查询；
6. **对于最后生成的短链接，机器编号占X位，MurmurHash+62进制生成的字符串占Y位（Y≤6），总共7位（X+Y≤7），满足不超过8位的需求；（后期可增加发号器，62进制，占用1位）**

## 3、设计思想及具体实现

### 3.1 详细设计

<div align="center">
	<img src="images\02.DesignDiagram.png" />
	</br>
	<span>图-2 详细设计图</span>
</div>

**详细设计说明**

1. 调用方传入长链接字符串后，短域名服务先MurmurHash生成10进制字符串，然后转换成62进制短链接，最后加上机器码作为前缀，生成最终的短链接；
2. 使用布隆过滤器判断短链接是否存在：
   1. 如果不存在（100%正确，不存在误判），则直接将短链接存入布隆过滤器，将短链接Bean（包含短链接、长链接、过期时间）存入Guava  Cache；
   2. 如果存在，则以短链接作为Key，去Guava Cache中取对象；
      1. 对象存在，则说明布隆过滤器没有误判，继续判断取出的长链接是否与新传入的长链接是否相同
         1. 相同，不做处理；
         2. 不相同，则说明发送Hash冲突了，加上冲突表示符后，重新生成短链接，然后存入布隆过滤器和Guava Cache；
      2. 如果不存在，则说明布隆过滤器误判了，需要重新生成短链接，然后存入布隆过滤器和Guava Cache；
3. 最后返回短链接给调用方

### 3.2 核心方法

```java
public String getShortLink(String longLink) {
    // 校验longLink
    if(longLink == null || "".equals(longLink)) {
        // 记录日志
        log.error("入参错误，不能为空：" + longLink);
        return "";
    }
    // 获取机器ID，这里写在文件中，后期通过ZooKeeper管理
    String machineId = fleOperateHelper.readFile("machineId");
    // 生成短链接
    String code = shortLinkComponent.createShortLinkCode(longLink);
    // 机器ID作为前缀
    code = machineId + code;
    // 创建短链接Bean
    ShortLinkBean shortLinkBean = new ShortLinkBean();
    shortLinkBean.setShortLink(code);
    shortLinkBean.setLongLink(longLink);
    shortLinkBean.setExpireTime(System.currentTimeMillis() + config.EXPIRE_SEC * 1000);
    // 如果存在（可能误判）
    if (bloomFilterHelper.mightContain(code)) {
        // 从缓存中取对象
        ShortLinkBean oldShortLinkBean = (ShortLinkBean) guavaCacheHelper.get(code);
        // 如果不存在误判为存在，则直接将新的数据写入缓存中
        if (oldShortLinkBean == null) {
            // 把短链接放入Guava缓存中
            guavaCacheHelper.put(code, shortLinkBean);
            // 把短链接放入布隆过滤器
            bloomFilterHelper.put(code);
            // 记录日志
            log.warn("布隆过滤器误判： new code: " + code + " ; new link: " + longLink);
        }
        // 如果确实存在
        else {
            String oldLongLink = oldShortLinkBean.getLongLink();
            // 判断是否Hash冲突了(code相同，长链接url不同)
            if (code.equals(oldShortLinkBean.getShortLink()) && !longLink.equals(oldLongLink)) {
                // 记录日志
                log.warn("Hash冲突, old and new code: " + code + "; old link: " + oldLongLink + " ; new link: "
                        + longLink);
                // 构造新code、新link
                // code加上枚举前缀后再取Hash，生成新的短链接
                code = machineId + shortLinkComponent.createShortLinkCode(DuplicatedEnum.DUPLICATED.getKey() + "_" + code);
                // 长链接加上前缀
                String newLongLink = DuplicatedEnum.DUPLICATED.getKey() + "_" + longLink;
                log.error("Hash冲突解决： new code: " + code + "; old link: " + oldShortLinkBean.getLongLink()
                        + " ; new link: " + newLongLink);
                // 设置新的短链接
                shortLinkBean.setShortLink(code);
                // 设置新的长链接
                shortLinkBean.setLongLink(newLongLink);
                // 把短链接放入Guava缓存中
                guavaCacheHelper.put(code, shortLinkBean);
                // 把短链接放入布隆过滤器
                bloomFilterHelper.put(code);
            }
            // 未冲突，已存在数据，不做处理，既不放到缓存中，也不放到过滤器中
            else {
                // 记录日志
                log.info("已存在： code " + code + " ; longLink: " + longLink);
            }
        }
    }
    // 通过布隆过滤器判断：如果不存在（100%正确），则直接放入缓存中
    else {
        // 把短链接放入Guava缓存中
        guavaCacheHelper.put(code, shortLinkBean);
        // 把短链接放入布隆过滤器
        bloomFilterHelper.put(code);
    }
    // 将短链接返回给调用方
    return code;
}
```

## 4、系统及测试

### 4.1 项目目录结构

<div align="center">
	<img src="images\03.Content.png" />
	</br>
	<span>图-3 目录结构图</span>
</div>


### 4.2 单元测试结果

<div align="center">
	<img src="images\04.JaCoCoReport.png" />
	</br>
	<span>图-4 单元测试结果</span>
</div>
### 4.3 Swagger

<div align="center">
	<img src="images\05.Swagger.png" />
	</br>
	<span> 图-5 Swagger </span>
</div>


### 4.4 功能测试

* 1、生成短链接

<div align="center">
	<img src="images\06.Postman_01.png" />
	</br>
	<span> 图-6 长链接生成短链接 </span>
</div>

* 2、获取长链接

<div align="center">
	<img src="images\07.Postman_02.png" />
	</br>
	<span> 图-7 短链接获取长链接 </span>
</div>

### 4.5 性能测试


* 测试工具：jmeter
* 机器配置：
  * 处理器：2.6 GHz 六核Intel Core i7
  * 内存：16 GB 2667 MHz DDR4
* JVM配置(保守配置，用2G堆内存，1.6G年轻代)：
  * 版本：JDK8
  * JVM设置：-Xms2048M -Xmx2048M -Xmn1640M -Xss1M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=5 -XX:PretenureSizeThreshold=1M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
* 测试结果：


<div align="center">
	<img src="images\08.JMeter.png" />
	</br>
	<span> 图-8 性能测试 </span>
</div>
**性能说明：**

* 平均响应时间：81ms,最大响应时间742ms
* QPS：2031
* 机器位占用一位，如果使用62进制，可以62*2K=12W QPS