FROM openjdk:11
MAINTAINER Tommy Zhang <zdp118@gmail.com>
VOLUME /tmp
ARG JAR_FILE=build/libs/short_url_distributed-1.0.0.jar
COPY ${JAR_FILE} shorturl.jar
# 从设计上来看需要至少保证2.5G内存
ENTRYPOINT ["sh", "-c", "java -Xmx2560m -Xms2560m -XX:MetaspaceSize=128m -jar /shorturl.jar ${0} ${@}"]