# Spring Boot2 项目

1、mysql5.7新建数据库 nxzw_ljfl ，导入doc文件夹的sql  ，roo用户，密码root
2、redis 本机安装端口 6379
3、启动入口(主启动类): GjljflApplication.java
4、Swagger2 文档地址：
http://localhost:8090/doc.html

设计方案
两步走：
1）长域名转短域名并存储
使用redis，使用固定key和redis自增函数获取累计自增数值，加上固定前缀作为key，将长域名值作为value，存储至redis。
若redis不成功，存ConcurrentHashMap
2）接受短域名信息，返回长域名信息
从缓存中读取值返回给前端。

![image](https://raw.githubusercontent.com/gwh2008/interview-assignments/master/java/spring-boot/doc/p1.png)
![image](https://raw.githubusercontent.com/gwh2008/interview-assignments/master/java/spring-boot/doc/p2.png)
![image](https://raw.githubusercontent.com/gwh2008/interview-assignments/master/java/spring-boot/doc/p3.png)
![image](https://raw.githubusercontent.com/gwh2008/interview-assignments/master/java/spring-boot/doc/p4.png)
![image](https://raw.githubusercontent.com/gwh2008/interview-assignments/master/java/spring-boot/doc/p5.png)

