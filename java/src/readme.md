# 短域名映射服务

## 作者

尹建华 18500499170（同微信）

noah_bentusi@126.com

## 服务框架

源码所基于的服务框架是本人近几年在工作当中积累起来的一套 SOA分布式服务框架。设计
思想是将服务细粒度的拆分成一个个组件。并将war或spring-boot运行包视为一个容器。框
架可以根据需要，自由的将不同组件打包到单个容器或者分散在多个容器中。

每个组件在设计时都是面向服务无状态的。根据业务压力大小，将运行压力较大的组件拆分
成独立容器，由单独结点运行。因为是服务是无状态的，运行结点亦可横向扩展，形成一个
运行集群。

### 组件说明如下

#### base组

该组中的组件为基础框架，为项目源代码提供基础性支撑。

* base 框架根包，所有组件都继承这个包。这个包提供基础公共依赖
* base-webbox web接口包，所有包含Controller或页面的组件都继承这个包。
* utils 工具包，里面包含累积下来的工具代码。
* utils-webbox web工具包，里面包含累积下来的web相关的工具代码
* app-framework-webbox 框架骨架包，里面提供运行容器基本骨架代码和配置。重要的有
  挂载当前容器的组件的功能。

#### support组

该组中的组件是支持包，为特定功能提供功能

* dubbo-support dubbo远程调用支持。使不同容器间的组件可以相互调用。
* jpa-support jpa数据库对象映射支持。
* lang-pack I18N国际化支持。
* redis-cache redis缓存服务支持。
* self-execute 自运行包的支持代码。当前是spring-boot实现。
* self-execute-starter 自运行包的配置代码。当前是spring-boot实现。
* wsock-support websocket工具代码支持
* wsock-support-webbox websocket前端支持
* xtransaction-support 数据库远程事务支持。支持事务可以跨节点存在

#### modules组

该组中为实现业务逻辑的组件

* shorturl/shorturl-api 短域名映射的接口声明
* shorturl/shorturl 短域名映射的接口实现
* shorturl/shorturl-webbox 短域名映射的Controller实现
* shorturl/shorturl-export 短域名映射接口的dubbo注册工程
* shorturl/shorturl-import 短域名映射接口的dubbo存根工程

一个业务逻辑基本上都是由以五个组件组成（根据需要可以没有webbox）。

在单容器方案中，可以把组件打包一个war。当前源代码就是这样的配置

`singleton.war(shorturl-webbox shorturl-api shorturl)`

也可以为多容器广案，将组件打包为两个war。通过dubbo进行分布式部署。

`frontend.war(shorturl-webbox shorturl-api shorturl-import)`
`service.war(shorturl-api shorturl shorturl-export)`

`shorturl-export`组件负责将`shorturl`（即`shorturl-api`的实现）通过dubbo暴露在局
域网内（当前代码是使用redis当作register）。
`shorturl-import`组件负责将对`shorturl-api`的调用，转换成dubbo请求到相应的容器。

### 编译运行

编译运行说明 请参看 [install.md](install.md)。

### 数据库及流程说明

短域名映射服务使用`mysql`和`redis`两层储存缓存设计。

#### redis缓存

redis缓存使用两组键值，对短域名进行缓存。缓存的时间由环境变量`surl.cache`控制，默
认是3600秒。

* `md5:xxxx` 对原始url作md5计算，用于相同url多次映射请求，返回相同的结果。避免数
 据库相同URL记录过多。
* `surl:xxxx` 缓存短域名的映射关系，用于减轻数据库的查询压力。

#### mysql数据库

因为使用了JPA的DDL功能。运行前，不需要事先建表。服务启动时，会自动建表。

surl表

* id bigint(20) 自增主键
* ip varchar(42) 请求的源IP地址
* shortUrl varchar(8) 映射的短域名
* status int(11) 映射记录状态 0 - 有效, 1 - 无效
* timestamp datetime(6) 映射的时间戳
* type int(11) 映射记录的类型 1 - 临时映射, 2 - 短期映射, 3 - 永久映射
* url mediumtext 映射记录的原始URL

索引有

* (shortUrl)
* (shortUrl, status)
* (timestamp)
* (type, status)
* (ip)

### 测试用例说明

框架之前是基于jetty。此次作业改为`spring-boot`。`spring-boot-test`不兼容框架当前
的组件式打包方式。故将测试用例打包进行了正式代码中，使用环境变量值`test=true`激活
测试代码。

测试用例代码在`app/shorturl-app`工程中，`com.sequoiacap.test.ShortUrlTests`。

现有测试30条，覆盖核心代码分支9个中的7个。覆盖率在77%。

