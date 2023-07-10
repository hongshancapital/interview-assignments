- # 短域名服务方案及实现




## 1、需求

### 实现短域名服务（细节可以百度/谷歌）

撰写两个 API 接口:
- 短域名存储接口：接受长域名信息、返回短域名信息
- 短域名读取接口：接受短域名信息、返回长域名信息。

限制：
- 短域名长度最大为 8 个字符
- 采用SpringBoot、集成Swagger API文档；
- JUnit编写单元测试、使用Jacoco生成测试报告(测试报告提交截图)；
- 映射数据存储在JVM内存即可、防止内存溢出；

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

1. 为提升短域名系统的高可用、本设计采用分布式架构；
2. 在服务前端采用Nginx等负载均衡器进行分流；
3. 每台服务器从ZooKeeper获取唯一的ID、作为机器ID、用于短链接的拼接；
4. 每台服务器都有短链接生成器（通过MurmurHash、62进制转换）；
5. 生成的短链接分别存储在布隆过滤器和**Guava Cache**中、供后续插入及查询；
6. **对于最后生成的短链接、机器编号占X位、MurmurHash+62进制生成的字符串占Y位（Y≤6）、总共7位（X+Y≤7）、满足不超过8位的需求；（后期可增加发号器、62进制、占用1位）**

## 3、设计思想及具体实现

### 3.1 详细设计

<div align="center">
	<img src="images\02.DesignDiagram.png" />
	</br>
	<span>图-2 详细设计图</span>
</div>

**详细设计说明**

1. 调用方传入长链接字符串后、短域名服务先MurmurHash生成10进制字符串、然后转换成62进制短链接、最后加上机器码作为前缀、生成最终的短链接；
2. 使用布隆过滤器判断短链接是否存在：
   1. 如果不存在（100%正确、不存在误判）、则直接将短链接存入布隆过滤器、将短链接Bean（包含短链接、长链接、过期时间）存入Guava  Cache；
   2. 如果存在、则以短链接作为Key、去Guava Cache中取对象；
      1. 对象存在、则说明布隆过滤器没有误判、继续判断取出的长链接是否与新传入的长链接是否相同
         1. 相同、不做处理；
         2. 不相同、则说明发送Hash冲突了、加上冲突表示符后、重新生成短链接、然后存入布隆过滤器和Guava Cache；
      2. 如果不存在、则说明布隆过滤器误判了、需要重新生成短链接、然后存入布隆过滤器和Guava Cache；
3. 最后返回短链接给调用方

### 3.2 核心方法

```java
public String getShortLink(String longLink) {
    // 校验longLink
    if(longLink == null || "".equals(longLink)) {
        // 记录日志
        log.error("入参错误、不能为空：" + longLink);
        return "";
    }
    // 获取机器ID、这里写在文件中、后期通过ZooKeeper管理
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
        // 如果不存在误判为存在、则直接将新的数据写入缓存中
        if (oldShortLinkBean == null) {
            // 把短链接放入Guava缓存中
            guavaCacheHelper.put(code、shortLinkBean);
            // 把短链接放入布隆过滤器
            bloomFilterHelper.put(code);
            // 记录日志
            log.warn("布隆过滤器误判： new code: " + code + " ; new link: " + longLink);
        }
        // 如果确实存在
        else {
            String oldLongLink = oldShortLinkBean.getLongLink();
            // 判断是否Hash冲突了(code相同、长链接url不同)
            if (code.equals(oldShortLinkBean.getShortLink()) && !longLink.equals(oldLongLink)) {
                // 记录日志
                log.warn("Hash冲突、old and new code: " + code + "; old link: " + oldLongLink + " ; new link: "
                        + longLink);
                // 构造新code、新link
                // code加上枚举前缀后再取Hash、生成新的短链接
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
                guavaCacheHelper.put(code、shortLinkBean);
                // 把短链接放入布隆过滤器
                bloomFilterHelper.put(code);
            }
            // 未冲突、已存在数据、不做处理、既不放到缓存中、也不放到过滤器中
            else {
                // 记录日志
                log.info("已存在： code " + code + " ; longLink: " + longLink);
            }
        }
    }
    // 通过布隆过滤器判断：如果不存在（100%正确）、则直接放入缓存中
    else {
        // 把短链接放入Guava缓存中
        guavaCacheHelper.put(code、shortLinkBean);
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
## 5、 性能测试

* 测试工具：jmeter
* 机器配置：
  * 处理器：Intel Core i7-8565U @ 1.80GHz 四核
  * 内存：16 GB 2666MHz DDR4
* JVM配置（保守配置、用2G堆内存、1.6G年轻代）：
  * 版本：JDK8
  * JVM设置：-Xms2048M -Xmx2048M -Xmn1640M -Xss1M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=5 -XX:PretenureSizeThreshold=1M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
* 测试结果：


<div align="center">
	<img src="images\08.JMeter.png" />
	</br>
	<span> 图-8 性能测试 </span>
</div>

**性能说明：**

* 平均响应时间：81ms、最大响应时间742ms
* QPS：2031
* 机器位占用一位，如果使用62进制，可以62*2K=12W QPS

## 6、系统扩展
* 机器码可以通过ZooKeeper替代文本方式
* 后期通过Nginx负载均衡+多个节点，实现更高并发

## 7、个人简历
**基本信息**
姓名：许林 地址：武汉市武昌区南湖花园街道 电话：13995526589 Email：coderdream@gmail.com

**教育背景**
1997.9 - 2001.6 湖南科技大学 本科学士学位 电子信息工程 

### 工作经历 

| 服务周期 |    **公司名称**   |          **职位名称**                                                     |
| ---------------------- | ---- | ------------------------------------------------------------ |
| **2021/06 -** **至今** |   武汉天喻软件股份有限公司      | Java SE |
| **2016/04 - 2021/05**  |    武汉佰钧成技术有限责任公司    |  Java高级工程师、项目经理 |
| **2009/01 - 2016/03**  |    惠普企业服务公司武汉分公司     | Java高级工程师、项目经理 |
| **2003/01 - 2008/12**  |    纬创软件武汉分公司     | Java软件工程师 |


### 项目经验
#### 项目1 2021.6 - 至今

- 项目名称： PDM SaaS

- 项目规模： 长期
- 项目简介： 通过逻辑多租、物理多租共享、打造企业产品全生命周期数字化的PDM系统。通用Part&BOM信息架构和数据源、建设元数据驱动多租的SaaS服务、快速支撑多业态、多子公司。使能研发、营销、行销、制造、供应、服务、采购、财经数字化。
- 角色：核心开发人员、Java SE
- 职责：
  - 系统核心代码开发：基础公共代码、通用服务代码；
  - 模块功能设计：数据模型设计（ER图）、类设计（类图、时序图、功能实现说明）、集成设计、性能设计、兼容性设计；
  - 框架代码定义：输出API接口文档（含性能规格）；框架代码（VO/DO/DTO）、各层接口定义（方法命名/入参/返回值）、关键算法评审、测试框架代码； 
  - 代码质量看护：问题单分析、代码飞检、代码集中检视；
  - 负责生命周期管理、MPN管理、BOM及库存组织BOM等模块的设计与开发；
- 工具与技术： Spring Boot、Spring Cloud、通用数据库平台、规则引擎、Redis、Eureka、Docker、ELK、 PostgreSQL

#### 项目2 2021/1-2021-5 

- 项目名称： 服务外包企业人力资源服务平台一期

- 项目规模： 长期
- 项目简介： 本平台为企业提供基础服务、其中人事管理包括员工的入职、转正、调岗和离职、社保公积金的投保和台账生成；考勤管理包括打卡、请假、加班、计时计件管理和考勤结果计算；薪酬管理包括工资计算和工资单审批、查询等功能。
- 角色：核心开发人员、运维部署
- 职责：
  - 负责员工的入职、转正、调岗和离职等模块开发与测试；
  - 负责社保投保及台账生成的开发与测试；
  - 在Rancher平台部署GitLab持续集成；
- 工具与技术： Spring Boot、Spring Cloud、Redis、Eureka、GitLab、Rancher/k8s

#### 项目3 2020/1-2020/12  

- 项目名称： 公司内部DevOps平台

- 项目规模： 长期
- 项目简介： 为适应快速发展的技术趋势、加快客户价值的交付速度、公司希望搭建DevOps平台、为公司信息技术中心和产品交付部提供支撑。核心功能包括项目管理和缺陷管理；Rancher包括数据库集群、私有镜像服务、GitLab持续集成；项目日志查询平台。
- 角色：核心开发人员
- 职责：
  - 与项目组的BA团队对接、确认客户需求；
  - 负责核心代码开发与测试；
  - 与开发测试团队制定开发、测试计划并高质量实施、按时交付版本；
- 工具与技术： Spring Cloud、Rancher/k8s、ELK、GitLab、 Prometheus、Harbor

#### 项目4 2017/12-2019/12		

- 项目名称： 项目暨人力内部采购供应对接系统

- 项目规模： 两年
- 项目简介： 为提高结构性产能而开发的系统、打通人力与项目的沟通渠道、包括人力看板、任务看板等功能。
- 角色： 项目管理、核心开发人员
- 职责：
  - 与项目组的BA团队对接、确认客户需求；
  - 负责核心代码开发与测试；
  - 与开发测试团队制定开发、测试计划并高质量实施、按时交付版本；
- 工具与技术： Spring MVC、MyBatis、MSSQL 2008、BI

#### 项目5 2017/05-2017/11		

- 项目名称： 项目评价审计系统

- 项目规模： 半年
- 项目简介： 为透明化项目评价、特增加项目审计环节、对目标合理性、操作规范性和数据准确性进行审核。
- 角色： 项目管理、核心开发人员
- 职责：
  - 确认客户需求；
  - 负责核心代码开发与测试；
  - 与开发测试团队制定开发、测试计划并高质量实施、按时交付版本；
- 工具与技术： NodeJS、Koa、MS SQL

#### 项目6 2016/04-2017/04		

- 项目名称： SCM大循环（国家计划统筹调度）

- 项目规模： 长期
- 客户：  华为
- 项目简介： SCM大循环是泛网络订单系统的一个子系统、负责给已签订的上游报价合同制定要货计划、发货计划及到货计划、是华为ERP重要的支撑系统。
- 角色： 项目管理
- 职责：
  - 负责与华为项目经理对接、按照华为版本火车的节奏、规划每个大版本的需求安排计划；
  - 与项目组的BA团队对接、承接版本的需求；
  - 与开发测试团队制定开发、测试计划并高质量实施、按时交付版本；
- 工具与技术： WebIX、Oracle、WPF

#### 项目7 2014/05-2016/04		

- 项目名称： HP Helion（惠普云计算项目）

- 项目规模： 长期
- 客户：  惠普
- 项目简介： HP Helion 构建于 OpenStack 技术的基础之上、可以提高企业的生产效率、允许充分利用的 IT 预算、并让的开发人员能够以更快的速度部署新的应用。 在实现这一切的同时、保持数据的可用性和安全性。
- 角色： 系统分析与开发
- 职责：
  - 网络自动化相关子项目开发与配置
  - OpenStack 安装与配置；
  - OpenStack API文档编写；
- 工具与技术： Ubuntu、OpenStack、Python、Django、Mule ESB、Java、Maven、Jenkins


### 8、参考文档
1. [请实现一个「短域名」的系统设计](https://leetcode-cn.com/circle/discuss/EkCOT9/)
2. [短网址(short URL)系统的原理及其实现](https://segmentfault.com/a/1190000012088345)
3. [高性能短链设计](https://mp.weixin.qq.com/s/YTrBaERcyjvw7A0Fg2Iegw)





