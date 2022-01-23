FROM maven:3-openjdk-8-slim as BUILD
COPY . /usr/src/app
RUN mvn --batch-mode -DskipTests -f /usr/src/app/pom.xml clean package

FROM openjdk:8-jdk-alpine
# 示例项目编译出来的是war包
COPY --from=BUILD /usr/src/app/target/*.war /opt/target/hello-world.war
EXPOSE 8080
# 经测试仍然可以用"java -jar"命令启动
CMD ["java", "-jar", "/opt/target/hello-world.war"]