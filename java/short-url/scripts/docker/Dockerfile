#FROM openjdk:17.0.1-jdk-slim
#FROM openjdk:8u265-jre
from openjdk:11-jdk-oracle

MAINTAINER zhangchi<504841840@qq.com>

RUN mkdir /short-url
ADD short-url.jar /short-url

WORKDIR /short-url

EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/short-url/short-url.jar"]
