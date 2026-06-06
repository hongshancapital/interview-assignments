# domain

#### 介绍
短域名服务设计与实现

设计方案：采用redis进行存储，支持分布式，破除内存壁垒
两步走：

1）长域名转短域名并存储
使用redis，使用固定key和redis自增函数获取累计自增数值，加上固定前缀作为key，将长域名值作为value，存储至redis。
2）短域名解析与跳转长域名并存储
根据命名规则过滤短域名url，并做对应长域名的转换，存储。

由于时间因素，采用分布式redis内存数据库实现，对于8位要求后续采用二次加密修正。


#### 软件架构
软件架构说明
![输入图片说明](https://images.gitee.com/uploads/images/2021/1011/180311_cb3445f6_745324.png "无标题.png")


#### 安装教程

1.  下载代码
2.  需要有redis/mysql(注意修改dbname) 
3.  idea导入工程，运行DomainApplication.java启动程序即可

####  压测方案
采用jmeter工具进行并发和性能压测，出具压测报告，由于采用redis方案，支持分布式应用。

#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
