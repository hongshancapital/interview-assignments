/bin/bash
# 获取配置文件（只是作为例子，实际没有这个文件）
git clone https://github.com/mytestConfig/application.yml  /usr/local/application/shortUrlService
git clone https://github.com/mytestConfig/application-prod.yml  /usr/local/application/shortUrlService

# 启动程序 ，简单配置一下
# 启动时要判断是否已经启动，这里只写最简单的
# web 应用使用G1
java -jar -XX:+UseG1GC java -Xms2g -Xmx2g -Xmn512m --spring-profile-active=prod short-url-service-0.0.1-SNAPSHOT.jar
