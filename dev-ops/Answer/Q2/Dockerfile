# this file should put in code repo
FROM openjdk:11-jdk-alpine

ARG JAR_FILE=target/*.jar

COPY ./target/spring-boot-helloworld-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
