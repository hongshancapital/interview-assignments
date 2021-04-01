# 编译运行说明

## 本地首次编译步骤

需要依次进入以下子目录:

* `base/root`
* `base/root-webbox`
* `support/self-execute`
* `support/self-execute-starter/`

每个目录均需执行这行命令

`mvn clean install`

将root、root-webbox、self-execute和self-execute-starter四个基础jar安装到本地
repository中。

## 修改数据库配置文件

打开`app/shorturl-app/src/main/resources/system.properties`, 修改以下变量

* jdbc-uri 数据库连接地址
* jdbc-driver jdbc驱动名称
* jdbc-username 数据库用户名
* jdbc-password 数据库密码
* redis-host redis缓存地址
* redis-port redis缓存端口号
* redis-password redis缓存连接密码，没有就留空

## 全量编译

在源代码根pom.xml目录下, 执行命令

`mvn clean package`

编译成功后，可在`app/shorturl-app/target/shorturl.war`处找到可以运行war.

## 运行命令

`java -jar app/shorturl-app/target/shorturl-app.war` 即可运行服务

运行服务后，可以通过下列地址访问swagger接口文档

`http://localhost:8080/swagger-ui/index.html`

## 运行测试用例命令

`java -jar -Dtest=true app/shorturl-app/target/shorturl-app.war` 即可运行测试用例

